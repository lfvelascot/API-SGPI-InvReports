package co.edu.usbbog.sgpireports.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.model.datamodels.*;
import co.edu.usbbog.sgpireports.model.Facultad;
import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.repository.IFacultadRepository;
import co.edu.usbbog.sgpireports.repository.IGrupoInvestigacionRepository;
import co.edu.usbbog.sgpireports.repository.IProgramaRepository;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;
import co.edu.usbbog.sgpireports.repository.ISemilleroRepository;
import co.edu.usbbog.sgpireports.repository.IUsuarioRepository;
import co.edu.usbbog.sgpireports.service.report.*;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ReportesService implements IReportesService {

	@Autowired
	private IUsuarioRepository usuarios;
	@Autowired
	private IFacultadRepository facultad;
	@Autowired
	private ISemilleroRepository semillero;
	@Autowired
	private IGrupoInvestigacionRepository gi;
	@Autowired
	private IProyectoRepository proyecto;
	@Autowired
	private IProgramaRepository programa;

	@Autowired
	private EventosRService eventos;
	@Autowired
	private LineasInvestigacionRService lineas;
	@Autowired
	private MiembrosExtraRService miembrosExtra;
	@Autowired
	private MiembrosSemilleroRService miembros;
	@Autowired
	private ParticipantesRService participantes;
	@Autowired
	private ProductosRService productos;
	@Autowired
	private ProyectoConvocatoriaRService proyectoConv;
	@Autowired
	private SemillerosRService semilleros;
	@Autowired
	private TiposRService datosTablas;
	@Autowired
	private ProgramaRService programas;
	@Autowired
	private GrupoInvRService grupos;
	@Autowired
	private AreasConocimientoRService areas;
	@Autowired
	private ComentarioRService comentario;
	@Autowired
	private ProyectosRService proyectos;

	private final Path rootReportes = Paths.get("reportes");

	@Override
	public String crearReporteSemillero(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteSemillero.jrxml"));
		Map<String, Object> datosSemillero = getDatosSemillero(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteSemillero-" + datosSemillero.get("nombreSemillero").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteIntegrantesSemillero(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteSemilleroIntegrantes.jrxml"));
		Map<String, Object> datosSemillero = getDatosSemilleroIntegrantes(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteSemilleroIntegrantes-" + datosSemillero.get("nombreSemillero").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteProduccionSemillero(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteSemilleroProduccion.jrxml"));
		Map<String, Object> datosSemillero = getDatosSemilleroProduccion(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteSemilleroProduccion-" + datosSemillero.get("nombreSemillero").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteProduccionGI(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteGIProduccion.jrxml"));
		Map<String, Object> datosSemillero = getDatosGIProduccion(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteGIProduccion-" + datosSemillero.get("nombre").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteIntegrantesGI(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteGIIntegrantes.jrxml"));
		Map<String, Object> datosSemillero = getDatosGIIntegrantes(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteGIIntegrantes-" + datosSemillero.get("nombre").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporGI(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteGI.jrxml"));
		Map<String, Object> datosGI = getDatosGI(cc);
		datosGI = setDatosCreador(datosGI, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosGI, new JREmptyDataSource());
		String nombre = "ReporteGI-" + datosGI.get("nombre").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	// Desde aqui
	@Override
	public String crearReporteParticipacionEventosSemillero(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteParticipacionesEventosSemillero.jrxml"));
		Map<String, Object> datosSemillero = getDatosParticipacionesEventosSemillero(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteParticipacionesEventosSemillero-" + datosSemillero.get("nombreSemillero").toString()
				+ ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteParticipacionEventosGI(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteParticipacionesEventosGI.jrxml"));
		Map<String, Object> datosSemillero = getDatosParticipacionesEventosGI(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteGIParticipacionesEventos-" + datosSemillero.get("nombre").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteParticipacionConvSemillero(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteParticipacionesConvSemillero.jrxml"));
		Map<String, Object> datosSemillero = getDatosParticipacionesConvSemillero(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteSemilleroParticpacionesConv-" + datosSemillero.get("nombreSemillero").toString()
				+ ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteParticipacionConvsGI(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteParticipacionesConvGI.jrxml"));
		Map<String, Object> datosSemillero = getDatosParticipacionesConvGI(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteGIParticipacionesConv-" + datosSemillero.get("nombre").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteProyectosConvAbiertasSemillero(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteProyectosConvAbiertasSemillero.jrxml"));
		Map<String, Object> datosSemillero = getDatosProyectosConvAbiertasSemillero(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteSemilleroParticpacionesConv-" + datosSemillero.get("nombreSemillero").toString()
				+ ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteProyectosConvAbiertassGI(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteProyectosConvAbiertasGI.jrxml"));
		Map<String, Object> datosSemillero = getDatosProyectosConvAbiertasGI(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteGIParticipacionesConv-" + datosSemillero.get("nombre").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteProyectosActivosSemillero(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteSemilleroProyectosActivos.jrxml"));
		Map<String, Object> datosSemillero = getDatosProyectosActivosSemillero(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteProyectosActSemillero-" + datosSemillero.get("nombreSemillero").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteSemillerosxPrograma(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteSemillerosxPrograma.jrxml"));
		Map<String, Object> datosSemillero = getDatosSemillerosxPrograma(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteProyectosActSemillero-" + datosSemillero.get("nombreSemillero").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteProyectosActivosGI(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteGIProyectosActivos.jrxml"));
		Map<String, Object> datosSemillero = getDatosProyectosActivosGI(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteProyectosActGI-" + datosSemillero.get("nombre").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteProyectosFinSemillero(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteSemilleroProyectosFinalizados.jrxml"));
		Map<String, Object> datosSemillero = getDatosProyectosFinSemillero(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteProyectosFinSemillero-" + datosSemillero.get("nombreSemillero").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre.toLowerCase()).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteProyectosFinGI(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteGIProyectosFinalizados.jrxml"));
		Map<String, Object> datosSemillero = getDatosProyectosFinGI(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteProyectosFinGI-" + datosSemillero.get("nombre").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre.toLowerCase()).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteGIsPorFacultad(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteGIxFacultad.jrxml"));
		Map<String, Object> datosSemillero = getDatosGIxFacultad(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteGIsFacultad-" + datosSemillero.get("nombre").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre.toLowerCase()).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	@Override
	public String crearReporteProyectoDetallado(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/reporteProyecto.jrxml"));
		Map<String, Object> datosSemillero = getDatosProyecto(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero, new JREmptyDataSource());
		String nombre = "ReporteProyecto-" + datosSemillero.get("nombre").toString() + ".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		return salida;
	}

	private Map<String, Object> getDatosProyectosConvAbiertasSemillero(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Semillero s = semillero.getById(cc);
		var aux1 = proyectos.getProyectosSemilleroC(cc);
		var aux2 = participantes.getIntegrantesC(cc);
		var aux3 = proyectoConv.getProyectosConvAbiertas(cc);
		var aux4 = datosTablas.getProyectosEstadosConv(s.getProyectos());
		var aux5 = proyectoConv.getProyectosConvCerradas(cc);
		var aux6 = datosTablas.getProyectosAnioConvSem(cc);
		var aux7 = productos.getProductosProyectosSemConv(cc);
		map = setDatosSem(map, s);
		map = setDatosLiderSem(map, s.getLiderSemillero());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("TotalProyectos", String.valueOf(s.countProyectos()) + " proyectos finalizados");
		map.put("totalMiembros", String.valueOf(miembros.countMiembrosSemillero(cc)) + " miembros");
		return map;
	}

	private Map<String, Object> getDatosProyectosConvAbiertasGI(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrupoInvestigacion s = gi.getById(cc);
		int y = proyectos.countProyectosGI(s);
		var aux0 = semilleros.getSemillerosConGI(s.getSemilleros());
		int x = aux0.size();
		var aux1 = proyectos.getProyectosGIC(s.getSemilleros());
		var aux2 = participantes.getIntegrantesCGI(s.getSemilleros());//
		var aux3 = proyectoConv.getProyectosConvAbiertasGI(s.getSemilleros());
		var aux4 = datosTablas.getProyectosEstadosConvGI(s.getSemilleros());
		var aux5 = proyectoConv.getProyectosConvCerradasGI(s.getSemilleros());
		var aux6 = datosTablas.getProyectosAnioConvGI(s.getSemilleros());
		var aux7 = productos.getProductosProyectosGIConv(s.getSemilleros());
		map = setDatosGi(map, s);
		map = setDatosDirectorGI(map, s.getDirectorGrupo());
		map.put("datasource0", aux0);
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("TotalProyectos", String.valueOf(y) + " proyectos ");
		map.put("totalMiembros", String.valueOf(x) + " miembros");
		return map;
	}

	private Map<String, Object> getDatosSemillerosxPrograma(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Programa s = programa.getById(cc);
		var aux1 = semilleros.getSemillerosGI(s.getSemilleros());
		map = setDatosPrograma(map, s);
		map.put("datasource1", aux1);
		map.put("datasource2", miembros.getLideresSemillerosDetalladoPrograma(s.getSemilleros()));
		map.put("totalMiembros", String.valueOf(aux1.size()) + " semilleros");
		return map;
	}

	// Inician carga de datos
	private Map<String, Object> getDatosParticipacionesEventosSemillero(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Semillero s = semillero.getById(cc);
		var aux1 = proyectos.getProyectosSemilleroE(cc);
		var aux2 = participantes.getIntegrantesE(cc);
		var aux3 = eventos.getProyectosEventos(cc);
		var aux4 = datosTablas.getProyectosAnioE(cc);
		var aux5 = productos.getProductosProyectosESem(s.getProyectos());
		map = setDatosSem(map, s);
		map = setDatosLiderSem(map, s.getLiderSemillero());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("TotalProyectosActivos", String.valueOf(s.countProyectos()) + " proyecto(s) finalizado(s)");
		map.put("totalMiembros", String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s)");
		return map;
	}

	private Map<String, Object> getDatosParticipacionesEventosGI(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrupoInvestigacion s = gi.getById(cc);
		int y = proyectos.countProyectosGI(s);
		int x = s.countSemilleros();
		var aux0 = semilleros.getSemillerosEGI(s.getSemilleros());
		var aux1 = proyectos.getProyectosEGI(s.getSemilleros());
		var aux2 = participantes.getIntegrantesEGI(s.getSemilleros());//
		var aux3 = eventos.getProyectosEventosGI(s.getSemilleros());
		var aux4 = datosTablas.getProyectosAnioEGI(s.getSemilleros());
		var aux5 = productos.getProductosProyectosEGI(s.getSemilleros());
		map = setDatosGi(map, s);
		map = setDatosDirectorGI(map, s.getDirectorGrupo());
		map.put("datasource0", aux0);
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("TotalProyectosActivos", String.valueOf(y) + " proyecto(s)");
		map.put("totalMiembros", String.valueOf(x) + " semilleros asociados(s)");
		return map;
	}

	private Map<String, Object> getDatosParticipacionesConvSemillero(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Semillero s = semillero.getById(cc);
		var aux1 = proyectos.getProyectosSemilleroC(cc); // Existe 
		var aux2 = participantes.getIntegrantesC(cc); // Existe
		var aux3 = proyectoConv.getProyectosConvAbiertas(cc); // Existe
		var aux4 = datosTablas.getProyectosEstadosConv(s.getProyectos());
		var aux5 = proyectoConv.getProyectosConvCerradas(cc); // Existe
		var aux6 = datosTablas.getProyectosAnioConvSem(cc);
		var aux7 = productos.getProductosProyectosSemConv(cc); // Existe
		map = setDatosSem(map, s);
		map = setDatosLiderSem(map, s.getLiderSemillero());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("TotalProyectosActivos", String.valueOf(s.countProyectos()) + " proyecto(s) finalizado(s)");
		map.put("totalMiembros", String.valueOf(miembros.countParticipantesProyectos(aux2)) + " participante(s)");
		return map;
	}

	private Map<String, Object> getDatosParticipacionesConvGI(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrupoInvestigacion s = gi.getById(cc);
		var aux0 = semilleros.getSemillerosConGI(s.getSemilleros());
		var aux1 = proyectos.getProyectosGIC(s.getSemilleros());
		var aux2 = participantes.getIntegrantesCGI(s.getSemilleros());//
		var aux3 = proyectoConv.getProyectosConvAbiertasGI(s.getSemilleros());
		var aux4 = datosTablas.getProyectosEstadosConvGI(s.getSemilleros());
		var aux5 = proyectoConv.getProyectosConvCerradasGI(s.getSemilleros());
		var aux6 = datosTablas.getProyectosAnioConvGI(s.getSemilleros());
		var aux7 = productos.getProductosProyectosGIConv(s.getSemilleros());
		int x = aux0.size();
		int y = aux1.size();
		map = setDatosGi(map, s);
		map = setDatosDirectorGI(map, s.getDirectorGrupo());
		map.put("datasource0", aux0);
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("TotalProyectosActivos", String.valueOf(y) + " proyecto(s)");
		map.put("totalMiembros", String.valueOf(x) + " semillero(s) asociado(s)");
		return map;
	}
	private Map<String, Object> getDatosProyectosActivosSemillero(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Semillero s = semillero.getById(cc);
		List<ProyectoR> aux1 = proyectos.getProyectosActivosSemillero(cc);
		List<ParticipantesR> aux2 = participantes.getIntegrantesProyActivos(cc);
		List<ProductoR> aux3 = productos.getProductosProyectosActSemillero(cc);
		List<TipoPEstado> aux4 = datosTablas.getProyectosActTipos(s.getProyectos());
		List<TipoPEstado> aux5 = datosTablas.getProyectosActProductos(s.getProyectos());
		int x = semilleros.numProyectosActivos(s.getProyectos());
		int y = participantes.countParticipantesProyectosActSemillero(cc);
		map = setDatosSem(map, s);
		map = setDatosLiderSem(map, s.getLiderSemillero());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("TotalProyectosActivos", String.valueOf(x) + " proyectos activos");
		map.put("totalMiembros", String.valueOf(y) + " miembros de los proyectos");
		return map;
	}

	private Map<String, Object> getDatosProyectosActivosGI(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrupoInvestigacion s = gi.getById(cc);
		int x = proyectos.countProyectosFinalizadosGI(s), y = proyectos.countProyectosGI(s);
		var aux1 = proyectos.getProyectosActivosGI(s.getSemilleros());
		var aux2 = participantes.getIntegrantesProyActivosGI(s.getSemilleros());
		List<ProductoR> aux3 = productos.getProductosProyectosActGI(s.getSemilleros());
		List<TipoPEstado> aux4 = datosTablas.getProyectosActTipos(datosTablas.getAllProyectosGI(s.getSemilleros()));
		List<TipoPEstado> aux5 = datosTablas.getProyectosActProductos(datosTablas.getAllProyectosGI(s.getSemilleros()));
		map = setDatosGi(map, s);
		map = setDatosDirectorGI(map, s.getDirectorGrupo());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("TotalProyectosActivos", String.valueOf(y - x) + " proyectos activos");
		return map;
	}

	private Map<String, Object> getDatosProyectosFinSemillero(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Semillero s = semillero.getById(cc);
		var aux1 = proyectos.getProyectosFinSemillero(cc);
		var aux2 = participantes.getIntegrantesProyFin(cc);
		var aux3 = productos.getProductosProyectosFin(cc);
		List<TipoPEstado> aux4 = datosTablas.getProyectosFinTipos(s.getProyectos());
		List<TipoPEstado> aux5 = datosTablas.getProduccionAnioSem(s.getProyectos());
		int x = semilleros.numProyectosFinalizados(s.getProyectos());
		int y = participantes.countParticipantesProyectosFinSemillero(cc);
		map = setDatosSem(map, s);
		map = setDatosLiderSem(map, s.getLiderSemillero());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyectos activos");
		map.put("totalMiembros", String.valueOf(y) + " miembros de los proyectos");
		return map;
	}

	private Map<String, Object> getDatosProyectosFinGI(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrupoInvestigacion s = gi.getById(cc);
		int x = proyectos.countProyectosFinalizadosGI(s);
		var aux1 = proyectos.getProyectosFinGI(s.getSemilleros());
		var aux2 = participantes.getIntegrantesProyFinGI(s.getSemilleros());
		var aux3 = productos.getProductosProyectosFinGI(s.getSemilleros());
		List<TipoPEstado> aux5 = datosTablas.getProduccionAnioGI(s.getSemilleros());
		List<TipoPEstado> aux6 = datosTablas.getProyectosFinTipos(datosTablas.getAllProyectosGI(s.getSemilleros()));
		map = setDatosGi(map, s);
		map = setDatosDirectorGI(map, s.getDirectorGrupo());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux6);
		map.put("datasource5", aux5);
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyectos finalizados");
		return map;
	}

	private Map<String, Object> getDatosGIxFacultad(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Facultad s = facultad.getById(cc);
		int x = proyectos.countProyectosFacultadFin(cc);
		int totalProyectos = proyectos.countProyectosFacultad(cc);
		int proyectosConProductos = proyectos.countProyectosConProductoFacultad(cc);
		var sproyectos = datosTablas.getAllProyectos(gi.findByFacultad(cc));
		var gis = gi.findByFacultad(cc);
		map.put("nombre", s.getNombre());
		if (s.getDecano() == null) {
			map.put("liderSemillero", "sin decano registrado");
			map.put("correoILider", "sin decano registrado");
			map.put("telefonoLider", "sin decano registrado");
		} else {
			map.put("liderSemillero", s.getDecano().getNombreCompleto());
			map.put("correoILider", s.getDecano().getCorreoEst());
			map.put("telefonoLider", s.getDecano().getTelefono());
		}
		if (s.getCoorInv() == null) {
			map.put("coordinadorNombre", "sin director registrado");
			map.put("coordinadorCorreo", "sin director registrado");
			map.put("coordinadorTelefono", "sin director registrado");
		} else {
			map.put("coordinadorNombre", s.getCoorInv().getNombreCompleto());
			map.put("coordinadorCorreo", s.getCoorInv().getCorreoEst());
			map.put("coordinadorTelefono", s.getCoorInv().getTelefono());
		}
		var aux1 = programas.getProgramasFacultad(s.getProgramas());
		var aux2 = grupos.getGruposInv(gis);
		var aux3 = miembros.getDirectoresGI(gis);
		var aux4 = lineas.getLineasInvFacultad(gis);
		var aux5 = datosTablas.getProyectosEstadosFacultad(sproyectos);
		var aux6 = datosTablas.getProyectosGIFacultad(sproyectos);
		var aux7 = datosTablas.getProyectosTipoFacultad(sproyectos);
		var aux8 = datosTablas.getProyectosAnioFacultad(sproyectos);
		var aux9 = proyectos.getProyectosFacultad(sproyectos);
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("datasource8", aux8);
		map.put("datasource9", aux9);
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyectos finalizados");
		map.put("TotalProyectosActivos", String.valueOf(totalProyectos - x) + " proyectos finalizados");
		map.put("totalProyectosConP", String.valueOf(proyectosConProductos) + " proyectos con productos");
		map.put("totalProyectosSinP",String.valueOf(totalProyectos - proyectosConProductos) + " proyectos sin productos");
		map.put("TotalProyectos", String.valueOf(totalProyectos) + " proyectos");
		map.put("totalMiembros", String.valueOf(aux2.size()) + " grupos de investigación");
		map.put("PorProyectosFinalizados", String.valueOf((100 * x) / totalProyectos) + " % proyectos finalizados");
		map.put("PorProyectosActivos",String.valueOf((100 * (totalProyectos - x)) / totalProyectos) + " % proyectos activos");
		map.put("PorProyectosConP",String.valueOf((100 * proyectosConProductos) / totalProyectos) + " % proyectos con productos");
		map.put("PorProyectosSinP", String.valueOf((100 * (totalProyectos - proyectosConProductos)) / totalProyectos)+ " % proyectos sin productos");
		return map;
	}

	private String getFechaFormateada(LocalDate fecha) {
		if (fecha != null) {
			return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else {
			return "";
		}
	}

	private Map<String, Object> getDatosProyecto(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Proyecto s = proyecto.getById(cc);
		var aux1 = areas.getAreasProyecto(s.getAreasConocimiento());
		var aux2 = productos.getProductosProyecto(s.getProductos());
		var aux3 = participantes.getIntegrantesProyecto(s.getParticipantes());
		var aux4 = eventos.getParticipacionesProyecto(s.getParticipaciones());
		var aux5 = proyectoConv.getParticipacionesProyecto(s.getProyectosConvocatoria());
		var aux6 = comentario.getComentariosProyecto(s.getProductos());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("nombre", s.getTitulo());
		map.put("estado", s.getEstado());
		map.put("descripcion", s.getDescripcion());
		if (s.getMacroProyecto() != null) {
			map.put("macroProyectoNombre", s.getMacroProyecto().getNombre());
			map.put("macroProyectoDescripcion", s.getMacroProyecto().getDescripcion());
			map.put("macroProyectoEstado", s.getMacroProyecto().getEstado());
			map.put("macroProyectoInicio", getFechaFormateada(s.getMacroProyecto().getFechaInicio()));
			map.put("macroProyectoFin", getFechaFormateada(s.getMacroProyecto().getFechaFin()));
		} else {
			map.put("macroProyectoNombre", "N/A");
		}
		map.put("inicio", getFechaFormateada(s.getFechaInicio()));
		map.put("fin", getFechaFormateada(s.getFechaFin()));
		map = setDatosVaciosString(map, s.getRetroalimentacionFinal(), "retroalimentacion", "Sin retroalimentacion");
		map = setDatosVaciosString(map, s.getConclusiones(), "conclusiones", "Sin conclusiones");
		map.put("ciudad", s.getCiudad());
		map.put("metodologia", s.getMetodologia());
		map.put("justificacion", s.getJustificacion());
		map.put("gi", s.getSemillero().getGrupoInvestigacion().getNombre());
		map.put("semillero", s.getSemillero().getNombre());
		return map;
	}

	private Map<String, Object> setDatosVaciosString(Map<String, Object> map, String dato, String clave, String vacio) {
		if (dato == null) {
			map.put(clave, vacio);
		} else {
			map.put(clave, dato);
		}
		return map;
	}

	private Map<String, Object> getDatosSemilleroProduccion(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Semillero s = semillero.getById(cc);
		var aux2 = datosTablas.getProyectosSemilleroPorEstado(cc);
		var aux3 = proyectos.getProyectosSemillero(cc);
		var aux1 = productos.getProductosSemillero(cc);
		var aux4 = datosTablas.getProducciónPorAnio(cc);
		int totalProyectos = s.countProyectos();
		int x = semilleros.numProyectosFinalizados(s.getProyectos());
		int proyectosSinProductos = proyectos.numProyectosSinProductoSemillero(cc);
		int proyectosConProductos = aux3.size() - proyectosSinProductos;
		map = setDatosSem(map, s);
		map = setDatosLiderSem(map, s.getLiderSemillero());
		map.put("datasource1", aux3);
		map.put("datasource2", aux2);
		map.put("datasource3", datosTablas.getProyectosSemilleroPorTipo(cc));
		map.put("datasource4", aux1);
		map.put("datasource5", aux4);
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyectos finalizados");
		map.put("TotalProyectosActivos", String.valueOf(totalProyectos - x) + " proyectos finalizados");
		map.put("totalProyectosConP", String.valueOf(proyectosConProductos) + " proyectos con productos");
		map.put("totalProyectosSinP", String.valueOf(proyectosSinProductos) + " proyectos sin productos");
		map.put("TotalProyectos", String.valueOf(totalProyectos) + " proyectos del semillero " + s.getNombre());
		map.put("PorProyectosFinalizados", String.valueOf((100 * x) / totalProyectos) + " % proyectos finalizados");
		map.put("PorProyectosActivos",
				String.valueOf((100 * (totalProyectos - x)) / totalProyectos) + " % proyectos activos");
		map.put("PorProyectosConP",
				String.valueOf((100 * proyectosConProductos) / totalProyectos) + " % proyectos con productos");
		map.put("PorProyectosSinP",
				String.valueOf((100 * proyectosSinProductos) / totalProyectos) + " % proyectos sin productos");
		return map;
	}

	private Map<String, Object> getDatosGIProduccion(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrupoInvestigacion s = gi.getById(cc);
		int proyectosFinalizados = proyectos.countProyectosFinalizadosGI(s);
		int totalProyectos = proyectos.countProyectosGI(s);
		int proyectosConProductos = proyectos.countProyectosConProductoGI(s.getSemilleros());
		int proyectosSinProductos = totalProyectos - proyectosConProductos;
		var aux1 = semilleros.getSemillerosGI(s.getSemilleros());
		var aux2 = proyectos.getProyectosGI(s.getSemilleros());
		var aux3 = productos.getProductosGI(s.getSemilleros());
		var aux4 = datosTablas.getTiposProyectosGI(s.getSemilleros());
		var aux5 = datosTablas.getProyectosEstadoGI(s.getSemilleros());
		var aux6 = datosTablas.getProduccionAnioGI(s.getSemilleros());
		var aux7 = datosTablas.getProduccionSemillerosGI(s.getSemilleros());
		map = setDatosGi(map, s);
		map = setDatosDirectorGI(map, s.getDirectorGrupo());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("totalProyectosFinalizados", String.valueOf(proyectosFinalizados) + " proyectos finalizados");
		map.put("TotalProyectosActivos",
				String.valueOf(totalProyectos - proyectosFinalizados) + " proyectos finalizados");
		map.put("totalProyectosConP", String.valueOf(proyectosConProductos) + " proyectos con productos");
		map.put("totalProyectosSinP", String.valueOf(proyectosSinProductos) + " proyectos sin productos");
		map.put("TotalProyectos", String.valueOf(totalProyectos) + " proyectos del grupo " + s.getNombre());
		map.put("PorProyectosFinalizados",
				String.valueOf((100 * proyectosFinalizados) / totalProyectos) + " % proyectos finalizados");
		map.put("PorProyectosActivos", String.valueOf((100 * (totalProyectos - proyectosFinalizados)) / totalProyectos)
				+ " % proyectos activos");
		map.put("PorProyectosConP",
				String.valueOf((100 * proyectosConProductos) / totalProyectos) + " % proyectos con productos");
		map.put("PorProyectosSinP",
				String.valueOf((100 * proyectosSinProductos) / totalProyectos) + " % proyectos sin productos");
		map.put("totalMiembros", String.valueOf(s.countSemilleros()) + " semilleros");
		return map;
	}

	private Map<String, Object> getDatosGIIntegrantes(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrupoInvestigacion s = gi.getById(cc);
		int x = proyectos.countProyectosFinalizadosGI(s), y = proyectos.countProyectosGI(s);
		var aux1 = semilleros.getSemillerosGI(s.getSemilleros());
		map = setDatosGi(map, s);
		map = setDatosDirectorGI(map, s.getDirectorGrupo());
		map.put("datasource1", aux1);
		map.put("datasource2", miembros.getLideresSemillerosDetallado(s));
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyectos finalizados");
		map.put("TotalProyectosActivos", String.valueOf(y - x) + " proyectos finalizados");
		map.put("totalMiembros", String.valueOf(aux1.size()) + " semilleros");
		return map;
	}

	private Map<String, Object> getDatosSemilleroIntegrantes(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Semillero s = semillero.getById(cc);
		var aux1 = miembros.getMiembrosSemillero(cc);
		var aux2 = miembrosExtra.getMiembrosSemilleroExtra(cc);
		map = setDatosSem(map, s);
		map = setDatosLiderSem(map, s.getLiderSemillero());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("totalMiembros", String.valueOf(aux1.size()) + " miembros");
		return map;
	}

	private Map<String, Object> setDatosCreador(Map<String, Object> datosSemillero, String usuario) {
		Usuario u = usuarios.getById(usuario);
		datosSemillero.put("nombreU", u.getNombreCompleto());
		datosSemillero.put("tipoU", u.getTiposUsuario().get(0).getNombre());
		datosSemillero.put("adicionalU", u.getProgramaId().getNombre());
		datosSemillero.put("firmaU", u.getFirma().getNombre());
		datosSemillero.put("logo1", "logo_usbbog_1.jpg");
		return datosSemillero;
	}

	private Map<String, Object> getDatosSemillero(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Semillero s = semillero.getById(cc);
		var aux1 = miembros.getMiembrosSemillero(cc);
		var aux2 = datosTablas.getProyectosSemilleroPorEstado(cc);
		var aux3 = proyectos.getProyectosSemillero(cc);
		var aux4 = participantes.getIntegrantes(cc);
		var aux5 = proyectoConv.getProyectosConvAbiertas(cc);
		int x = semilleros.numProyectosFinalizados(s.getProyectos());
		map = setDatosSem(map, s);
		map = setDatosLiderSem(map, s.getLiderSemillero());
		map.put("datasource1", aux1);
		map.put("datasource2", aux3);
		map.put("datasource3", aux2);
		map.put("datasource4", datosTablas.getProyectosSemilleroPorTipo(cc));
		map.put("datasource5", aux4);
		map.put("datasource6", aux5);
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyectos finalizados");
		map.put("TotalProyectosActivos", String.valueOf(s.countProyectos() - x) + " proyectos finalizados");
		map.put("totalMiembros", String.valueOf(aux1.size()) + " miembros");
		return map;
	}

	private Map<String, Object> getDatosGI(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrupoInvestigacion s = gi.getById(cc);
		int x = proyectos.countProyectosFinalizadosGI(s), y = proyectos.countProyectosGI(s);
		var aux1 = semilleros.getSemillerosGI(s.getSemilleros());
		map = setDatosGi(map, s);
		map = setDatosDirectorGI(map, s.getDirectorGrupo());
		map.put("datasource1", lineas.getLineasInvGI(s));
		map.put("datasource2", aux1);
		map.put("datasource3", miembros.getLideresSemillerosDetallado(s));
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyectos finalizados");
		map.put("TotalProyectosActivos", String.valueOf(y - x) + " proyectos finalizados");
		map.put("totalMiembros", String.valueOf(aux1.size()) + " semilleros");
		return map;
	}

	private Map<String, Object> setDatosDirectorGI(Map<String, Object> map, Usuario d) {
		if (d == null) {
			map.put("liderSemillero", "sin director registrado");
			map.put("correoILider", "sin director registrado");
			map.put("telefonoLider", "sin director registrado");
			map.put("programaLider", "sin director registrado");
		} else {
			map.put("liderSemillero", d.getNombreCompleto());
			map.put("correoILider", d.getCorreoEst());
			map.put("telefonoLider", d.getTelefono());
			map.put("programaLider", d.getProgramaId().getNombre());
		}
		return map;
	}

	private Map<String, Object> setDatosLiderSem(Map<String, Object> map, Usuario l) {
		if (l == null) {
			map.put("liderSemillero", "sin lider registrado");
			map.put("correoILider", "sin lider registrado");
			map.put("telefonoLider", "sin lider registrado");
			map.put("programaLider", "sin lider registrado");
		} else {
			map.put("liderSemillero", l.getNombreCompleto());
			map.put("correoILider", l.getCorreoEst());
			map.put("telefonoLider", l.getTelefono());
			map.put("programaLider", l.getProgramaId().getNombre());
		}
		return map;
	}

	private Map<String, Object> setDatosGi(Map<String, Object> map, GrupoInvestigacion s) {
		map.put("nombre", s.getNombre());
		map.put("fechaFun", getFechaFormateada(s.getFechaFun()));
		map.put("fechaCat", getFechaFormateada(s.getFechaCat()));
		map.put("categoria", s.getCategoria());
		return map;
	}

	private Map<String, Object> setDatosSem(Map<String, Object> map, Semillero s) {
		map.put("nombreSemillero", s.getNombre());
		map.put("descripcion", s.getDescripcion());
		map.put("fechaFun", getFechaFormateada(s.getFechaFun()));
		map.put("grupoInv", s.getGrupoInvestigacion().getNombre());
		map.put("lineaInv", s.getLineaInvestigacion().getNombre());
		return map;
	}

	private Map<String, Object> setDatosPrograma(Map<String, Object> map, Programa s) {
		map.put("nombre", s.getNombre());
		map.put("facultad", s.getNombre());
		if (s.getDirector() == null) {
			map.put("liderSemillero", "sin decano registrado");
			map.put("correoILider", "sin decano registrado");
			map.put("telefonoLider", "sin decano registrado");
		} else {
			map.put("liderSemillero", s.getDirector().getNombreCompleto());
			map.put("correoILider", s.getDirector().getCorreoEst());
			map.put("telefonoLider", s.getDirector().getTelefono());
		}
		return map;
	}

}