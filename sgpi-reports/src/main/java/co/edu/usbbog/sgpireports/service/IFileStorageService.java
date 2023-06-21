package co.edu.usbbog.sgpireports.service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import net.sf.jasperreports.engine.JRException;

public interface IFileStorageService {

	// Lista de nombres de los archivos de las plantillas para los reportes
	public final List<String> nombrePlantillas = new ArrayList<>(Arrays.asList("reporteSemillero.jrxml",
			"reporteSemilleroIntegrantes.jrxml", "reporteSemilleroProduccion.jrxml",
			"reporteParticipacionesEventosSemillero.jrxml", "reporteParticipacionesConvSemillero.jrxml",
			"reporteProyectosConvAbiertasSemillero.jrxml", "reporteSemilleroProyectosActivos.jrxml",
			"reporteSemilleroProyectosFinalizados.jrxml", "reporteSemilleroInvestigadoresF.jrxml",
			"reportePresupuestoSemillero.jrxml", "reporteGIProduccion.jrxml", "reporteGIIntegrantes.jrxml",
			"reporteGI.jrxml", "reporteParticipacionesEventosGI.jrxml", "reporteParticipacionesConvGI.jrxml",
			"reporteProyectosConvAbiertasGI.jrxml", "reporteGIProyectosActivos.jrxml",
			"reporteGIProyectosFinalizados.jrxml", "reporteGIInvestigadoresF.jrxml", "reportePresupuestoGI.jrxml",
			"reporteSemillerosxPrograma.jrxml", "reporteGIxFacultad.jrxml", "reporteProyecto.jrxml",
			"reporteActividadSemillero.jrxml", "reporteActividadGI.jrxml", "reporteUsoPresSemillero.jrxml",
			"reporteUsoPresGI.jrxml"));

	// Lista de formatos para los nombres de los reportes
	public final List<String> nombrePDF = new ArrayList<>(Arrays.asList("RepSem-%s-%s.pdf", "RepSemInt-%s-%s.pdf",
			"RepSemProd-%s-%s.pdf", "RepSemPartEventos-%s-%s.pdf", "RepSemPartConv-%s-%s.pdf",
			"RepSemPartConvA-%s-%s.pdf", "RepSemProyAct-%s-%s.pdf", "RepSemProyFin-%s-%s.pdf",
			"RepSemInvForm-%s-%s.pdf", "RepPresSem-%s-%s.pdf", "RepGIProd-%s-%s.pdf", "RepGIInt-%s-%s.pdf",
			"RepGI-%s-%s.pdf", "RepGIPartEventos-%s-%s.pdf", "RepGIPartConv-%s-%s.pdf", "RepGIPartConvA-%s-%s.pdf",
			"RepGIProyAct-%s-%s.pdf", "RepGIProyFin-%s-%s.pdf", "RepGIInvForm-%s-%s.pdf", "RepPresGI-%s-%s.pdf",
			"RepSemsProg-%s-%s.pdf", "RepGIsFac-%s-%s.pdf", "RepProy-%s-%s.pdf", "RepActSem-%s-%s.pdf",
			"RepActGI-%s-%s.pdf", "RepUPresSem-%s-%s.pdf", "RepUPresGI-%s-%s.pdf"));

	// Carpetas para la manipulación de archivos
	public final Path rootFirmas = Paths.get("Firmas");
	public final Path rootReporte = Paths.get("Reportes");
	public final Path rootPlantillas = Paths.get("Plantillas");
	public final Path rootImagenes = Paths.get("Imagenes");

	public void init();

	public boolean save(MultipartFile file, String usuario);

	public Resource loadF(String filename);

	public Resource loadI(String filename);

	public String saveReporte(int cc, int rep, String usuario, Map<String, Object> datos)
			throws FileNotFoundException, JRException, MalformedURLException;
}