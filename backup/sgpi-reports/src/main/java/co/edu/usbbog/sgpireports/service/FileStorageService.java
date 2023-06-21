package co.edu.usbbog.sgpireports.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
public class FileStorageService implements IFileStorageService{

	// Carpetas para la manipulación de archivos
	private final Path rootFirmas = Paths.get("Firmas");
	private final Path rootReporte = Paths.get("Reportes");
	private final Path rootPlantillas = Paths.get("Plantillas");
	private final Path rootImagenes = Paths.get("Imagenes");

	// Lista de nombres de los archivos de las plantillas para los reportes
	private final List<String> nombrePlantillas = new ArrayList<>(Arrays.asList("reporteSemillero.jrxml",
			"reporteSemilleroIntegrantes.jrxml", "reporteSemilleroProduccion.jrxml",
			"reporteParticipacionesEventosSemillero.jrxml", "reporteParticipacionesConvSemillero.jrxml",
			"reporteProyectosConvAbiertasSemillero.jrxml", "reporteSemilleroProyectosActivos.jrxml",
			"reporteSemilleroProyectosFinalizados.jrxml", "reporteSemilleroInvestigadoresF.jrxml",
			"reportePresupuestoSemillero.jrxml", "reporteGIProduccion.jrxml", "reporteGIIntegrantes.jrxml",
			"reporteGI.jrxml", "reporteParticipacionesEventosGI.jrxml", "reporteParticipacionesConvGI.jrxml",
			"reporteProyectosConvAbiertasGI.jrxml", "reporteGIProyectosActivos.jrxml",
			"reporteGIProyectosFinalizados.jrxml", "reporteGIInvestigadoresF.jrxml", "reportePresupuestoGI.jrxml",
			"reporteSemillerosxPrograma.jrxml", "reporteGIxFacultad.jrxml", "reporteProyecto.jrxml"));

	// Lista de formatos para los nombres de los reportes
	private final List<String> nombrePDF = new ArrayList<>(Arrays.asList("RepSem-%s-%s.pdf", "RepSemInt-%s-%s.pdf",
			"RepSemProd-%s-%s.pdf", "RepSemPartEventos-%s-%s.pdf", "RepSemPartConv-%s-%s.pdf",
			"RepSemPartConvA-%s-%s.pdf", "RepSemProyAct-%s-%s.pdf", "RepSemProyFin-%s-%s.pdf",
			"RepSemInvForm-%s-%s.pdf", "RepPresSem-%s-%s.pdf", "RepGIProd-%s-%s.pdf", "RepGIInt-%s-%s.pdf",
			"RepGI-%s-%s.pdf", "RepGIPartEventos-%s-%s.pdf", "RepGIPartConv-%s-%s.pdf", "RepGIPartConvA-%s-%s.pdf",
			"RepGIProyAct-%s-%s.pdf", "RepGIProyFin-%s-%s.pdf", "RepGIInvForm-%s-%s.pdf", "RepPresGI-%s-%s.pdf",
			"RepSemsProg-%s-%s.pdf", "RepGIsFac-%s-%s.pdf", "RepProy-%s-%s.pdf"));

	// Inicializa las carpetas requeridas
	public void init() {
		try {
			if (!Files.exists(rootFirmas)) {
				Files.createDirectory(rootFirmas);
			}
			if (!Files.exists(rootReporte)) {
				Files.createDirectory(rootReporte);
			}
			if (!Files.exists(Paths.get("target/Plantillas"))) {
				if (Files.list(Paths.get("target/Plantillas")).count() == 0) {
					try {
						FileSystemUtils.copyRecursively(rootPlantillas, Paths.get("target/Plantillas"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (!Files.exists(Paths.get("target/Imagenes"))) {
				if (Files.list(Paths.get("target/Imagenes")).count() == 0) {
					try {
						FileSystemUtils.copyRecursively(rootImagenes, Paths.get("target/Imagenes"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
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
				.compileReport(new FileInputStream(rootFirmas.resolve(nombrePlantillas.get(rep)).toFile()));
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
