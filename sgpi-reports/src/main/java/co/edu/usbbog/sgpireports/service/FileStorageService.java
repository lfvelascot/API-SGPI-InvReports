package co.edu.usbbog.sgpireports.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import co.edu.usbbog.sgpireports.model.Usuario;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class FileStorageService implements IFileStorageService {

	@Autowired
	private IGestionUsuariosService usuarios;
	@Autowired
	private ISeguridadService seguridad;

	private final Path rootFirmas = Paths.get("Firmas");
	private final Path rootReporte = Paths.get("Reportes");
	private final Path rootPlantillas = Paths.get("Plantillas");
	private final Path rootImagenes = Paths.get("Imagenes");

	// Inicializa las carpetas requeridas
	public void init() {
		try {
			createDirectorys(rootFirmas);
			createDirectorys(rootReporte);
			validateDirectory(rootImagenes);
			validateDirectory(rootPlantillas);
			copyFiles(Paths.get("target", "Plantillas"), rootPlantillas);
			copyFiles(Paths.get("target", "Imagenes"), rootImagenes);
		} catch (IOException e) {
			try {
				createDirectorys(rootFirmas);
				createDirectorys(rootReporte);
				validateDirectory(rootImagenes);
				validateDirectory(rootPlantillas);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void createDirectorys(Path ruta) throws IOException {
		if (!Files.exists(ruta)) {
			Files.createDirectory(ruta);
		}
	}

	private void validateDirectory(Path ruta) throws IOException {
		if (!Files.exists(ruta)) {
			throw new RuntimeException("No existe la carpeta de imagenes!");
		} else {
			if (Files.list(ruta).toList().isEmpty()) {
				throw new RuntimeException("No existen los recursos de imagenes!");
			}
		}
	}

	private void copyFiles(Path ruta, Path ruta2) throws IOException {
		if (Files.exists(ruta)) {
			FileUtils.deleteDirectory(ruta.toFile());
		}
		Files.createDirectory(ruta);
		FileSystemUtils.copyRecursively(ruta2, ruta);
	}

	// Permite guardar las firmas de los usuarios
	public String save(MultipartFile file, String usuario) {
		Usuario user = usuarios.buscarUsuario(usuario);
		if (user != null) {
			try {
				if (validateFirma(file)) {
					String nombre = "Firma-" + usuario + ".png";
					String respuesta = "";
					if (user.getFirma() == null) {
						if (!usuarios.saveFirma(usuario)) {
							return "Error carga de datos";
						}
						respuesta = "Firma cargada correctamente";
					} else {
						Files.deleteIfExists(this.rootFirmas.resolve(nombre));
						respuesta = "Firma actualizada correctamente";
					}
					Files.copy(file.getInputStream(), this.rootFirmas.resolve(nombre));
					seguridad.encriptar(this.rootFirmas.resolve(nombre).toFile());
					return respuesta;
				} else {
					return "ERROR CARGA : El tamaño o formato del archivo no es valido";
				}
			} catch (Exception e) {
				throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
			}
		} else {
			return "Error usuario";
		}
	}

	private boolean validateFirma(MultipartFile file) {
		return file.getOriginalFilename().contains(".png") && file.getSize() < 2097153;
	}

	// metodo que indicara si no fue posible leer el archivo
	public Resource loadF(String filename) {
		try {
			ByteArrayResource salida = new ByteArrayResource(seguridad.descrifrar2(rootFirmas.resolve(filename).toFile()));
			return (salida.exists() || salida.isReadable()) ? salida : loadI("sin-firma.png");
		} catch (IOException e) {
			return null;
		}
	}

	// Permite obtener las imagenes alojadas en la API
	public Resource loadI(String filename) {
		try {
			Resource resource = new UrlResource(rootImagenes.resolve(filename).toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	// Permite obtener el archivo pdf de un reporte
	public Resource loadR(String filename) {
		try {
			Resource resource = new UrlResource(rootReporte.resolve(filename).toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			return null;
		}
	}

	// Genera y guarda los reportes
	public String saveReporte(int cc, int rep, String usuario, Map<String, Object> datos)
			throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream(rootPlantillas.resolve(nombrePlantillas.get(rep)).toFile()));
		String nombre = String.format(nombrePDF.get(rep), datos.get("n").toString(), usuario.toString());
		JasperExportManager.exportReportToPdfFile(JasperFillManager.fillReport(reporte, datos, new JREmptyDataSource()),
				rootReporte.resolve(nombre).toString());
		return (new UrlResource(rootReporte.resolve(nombre).toUri()).exists()) ? nombre : "Error al generar reporte";
	}


}