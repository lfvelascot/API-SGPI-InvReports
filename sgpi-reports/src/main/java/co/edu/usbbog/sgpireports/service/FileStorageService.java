package co.edu.usbbog.sgpireports.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class FileStorageService implements IFileStorageService {


	// Inicializa las carpetas requeridas
	public void init() {
		try {
			if (!Files.exists(rootFirmas)) {
				Files.createDirectory(rootFirmas);
			}
			if (!Files.exists(rootReporte)) {
				Files.createDirectory(rootReporte);
			}
			if (Files.exists(Paths.get("target", "Plantillas"))) {
				FileUtils.deleteDirectory(Paths.get("target", "Plantillas").toFile());
			}
			Files.createDirectory(Paths.get("target", "Plantillas"));
			FileSystemUtils.copyRecursively(rootPlantillas, Paths.get("target", "Plantillas"));
			if (Files.exists(Paths.get("target", "Imagenes"))) {
				FileUtils.deleteDirectory(Paths.get("target", "Imagenes").toFile());
			}
			Files.createDirectory(Paths.get("target", "Imagenes"));
			FileSystemUtils.copyRecursively(rootImagenes, Paths.get("target", "Imagenes"));
		} catch (IOException e) {
			try {
				if (!Files.exists(rootFirmas)) {
					Files.createDirectory(rootFirmas);
				}
				if (!Files.exists(rootReporte)) {
					Files.createDirectory(rootReporte);
				}
				if (!Files.exists(rootImagenes)) {
					throw new RuntimeException("Could not initialize folder for images!");
				}
				if (!Files.exists(rootPlantillas)) {
					throw new RuntimeException("Could not initialize folder for templates!");
				}
			} catch (IOException e2) {
				throw new RuntimeException("Could not initialize folders for use!");
			}
		}
	}

	// Permite guardar las firmas de los usuarios
	public boolean save(MultipartFile file, String usuario) {
		try {
			if (validateFirma(file)) {
				Files.copy(file.getInputStream(), this.rootFirmas.resolve("Firma-" + usuario + ".png"));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	private boolean validateFirma(MultipartFile file) {
		return file.getOriginalFilename().contains(".png") && file.getSize() < 2097153;
	}

	// metodo que indicara si no fue posible leer el archivo
	public Resource loadF(String filename) {
		try {
			Path file = rootFirmas.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				return loadI("sin-firma.png");
			}
		} catch (MalformedURLException e) {
			return null;
		}
	}

	// Permite obtener las imagenes alojadas en la API
	public Resource loadI(String filename) {
		try {
			Path file = rootImagenes.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	// Actualiza las firmas de los usuarios
	public boolean update(MultipartFile file, String usuario) {
		try {
			if (validateFirma(file)) {
				Files.deleteIfExists(this.rootFirmas.resolve("Firma-" + usuario + ".png"));
				return save(file, usuario);
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Permite obtener el archivo pdf de un reporte
	public Resource loadR(String filename) {
		try {
			Path file = rootReporte.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				return null;
			}
		} catch (MalformedURLException e) {
			return null;
		}
	}

	// Genera y guarda los reportes
	public String saveReporte(int cc, int rep, String usuario, Map<String, Object> datos)
			throws FileNotFoundException, JRException, MalformedURLException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream(rootPlantillas.resolve(nombrePlantillas.get(rep)).toFile()));
		String nombre = String.format(nombrePDF.get(rep), datos.get("n").toString(), usuario.toString());
		JasperExportManager.exportReportToPdfFile(JasperFillManager.fillReport(reporte, datos, new JREmptyDataSource()),
				this.rootReporte.resolve(nombre).toString());
		if (new UrlResource(rootReporte.resolve(nombre).toUri()).exists()) {
			return nombre;
		} else {
			return "Error al generar reporte";
		}
	}

}
