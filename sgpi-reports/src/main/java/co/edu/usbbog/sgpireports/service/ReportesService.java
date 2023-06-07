package co.edu.usbbog.sgpireports.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.model.datamodels.ComentarioR;
import co.edu.usbbog.sgpireports.model.datamodels.CompraR;
import co.edu.usbbog.sgpireports.model.datamodels.PresupuestoR;
import co.edu.usbbog.sgpireports.model.datamodels.ProyectoR;
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
	private PresupuestoRService presupuesto;
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

	private final List<String> nombrePDF = new ArrayList<>(Arrays.asList("RepSem-%s-%s.pdf", "RepSemInt-%s-%s.pdf",
			"RepSemProd-%s-%s.pdf", "RepSemPartEventos-%s-%s.pdf", "RepSemPartConv-%s-%s.pdf",
			"RepSemPartConvA-%s-%s.pdf", "RepSemProyAct-%s-%s.pdf", "RepSemProyFin-%s-%s.pdf",
			"RepSemInvForm-%s-%s.pdf", "RepPresSem-%s-%s.pdf", "RepGIProd-%s-%s.pdf", "RepGIInt-%s-%s.pdf",
			"RepGI-%s-%s.pdf", "RepGIPartEventos-%s-%s.pdf", "RepGIPartConv-%s-%s.pdf", "RepGIPartConvA-%s-%s.pdf",
			"RepGIProyAct-%s-%s.pdf", "RepGIProyFin-%s-%s.pdf", "RepGIInvForm-%s-%s.pdf", "RepPresGI-%s-%s.pdf",
			"RepSemsProg-%s-%s.pdf", "RepGIsFac-%s-%s.pdf", "RepProy-%s-%s.pdf"));

	public String crearReporte(int cc, int rep, String usuario) throws JRException, IOException {
		if (rep < 23 && rep >= 0) {
			JasperReport reporte = JasperCompileManager
					.compileReport(new FileInputStream("src/main/resources/" + nombrePlantillas.get(rep)));
			Map<String, Object> datos = getDatos(new HashMap<String, Object>(), cc, rep, usuario);
			if (datos != null) {
				String nombre = String.format(nombrePDF.get(rep), datos.get("n").toString(), usuario.toString());
				JasperExportManager.exportReportToPdfFile(
						JasperFillManager.fillReport(reporte, datos, new JREmptyDataSource()),
						this.rootReportes.resolve(nombre).toString());
				return nombre;
			} else {
				return "Error al generar reporte";
			}
		} else {
			return "Sin reporte";
		}
	}

	private Map<String, Object> getDatos(Map<String, Object> map, int cc, int rep, String usuario) {
		Semillero s = null;
		GrupoInvestigacion g = null;
		map = setDatosCreador(map, usuario);
		if (map == null) {
			return null;
		} else if (rep <= 9) {
			try {
				s = semillero.getById(cc);
				if (!s.equals(null)) {
					map = setDatosSem(map, s);
				}
			} catch (javax.persistence.EntityNotFoundException e) {
				return null;
			}
		} else if (rep <= 19 && rep > 9) {
			try {
				g = gi.getById(cc);
				if (!g.equals(null)) {
					map = setDatosGi(map, g);
				}
			} catch (javax.persistence.EntityNotFoundException e) {
				return null;
			}
		}
		switch (rep) {
		case 0:
			return getDatosSemilleroP(map, s);
		case 1:
			return getDatosSemilleroIntegrantesP(map, s);
		case 2:
			return getDatosSemilleroProduccionP(map, s);
		case 3:
			return getDatosParticipacionesEventosSemilleroP(map, s);
		case 4:
			return getDatosParticipacionesConvSemilleroP(map, s);
		case 5:
			return getDatosProyectosConvAbiertasSemilleroP(map, s);
		case 6:
			return getDatosProyectosActivosSemilleroP(map, s);
		case 7:
			return getDatosProyectosFinSemilleroP(map, s);
		case 8:
			return getDatosInvestigadoresFormP(map, s);
		case 9:
			return getDatosPresupuestoSemP(map, s);
		case 10:
			return getDatosGIProduccionP(map, g);
		case 11:
			return getDatosGIIntegrantesP(map, g);
		case 12:
			return getDatosGIP(map, g);
		case 13:
			return getDatosParticipacionesEventosGI(map, g);
		case 14:
			return getDatosParticipacionesConvGIP(map, g);
		case 15:
			return getDatosProyectosConvAbiertasGIP(map, g);
		case 16:
			return getDatosProyectosActivosGIP(map, g);
		case 17:
			return getDatosProyectosFinGIP(map, g);
		case 19:
			return getDatosPresupuestoGIP(map, g);
		case 18:
			return getDatosInvestigadoresFormPGI(map, g); // Falta
		case 20:
			try {
				Programa p = programa.getById(cc);
				if (p.equals(null)) {
					return null;
				}
				map = setDatosPrograma(map, p);
				return getDatosSemillerosxProgramaP(map, p);
			} catch (javax.persistence.EntityNotFoundException e) {
				return null;
			}
		case 21:
			try {
				Facultad f = facultad.getById(cc);
				if (f.equals(null)) {
					return null;
				}
				map = getDatosGIxFacultadP(map, f);
				return setDatosFacultad(map, f);
			} catch (EntityNotFoundException e) {
				return null;
			}
		case 22:
			try {
				Proyecto pr = proyecto.getById(cc);
				if (pr.equals(null)) {
					return null;
				}
				return getDatosProyectoP(map, pr);
			} catch (EntityNotFoundException e) {
				return null;
			}
		default:
			return null;
		}
	}

	private Map<String, Object> getDatosPresupuestoGIP(Map<String, Object> map, GrupoInvestigacion g) {
		List<ProyectoR> aux1 = proyectos.getProyectosPresGI(g.getSemilleros());
		List<PresupuestoR> aux2 = presupuesto.getPresupuestosGI(g.getSemilleros());
		List<CompraR> aux3 = presupuesto.getComprasGI(g.getSemilleros());
		double totalPresupuesto = presupuesto.getTotalPresupuestoGI(g.getSemilleros());
		double totalCompras = presupuesto.getTotalComprasGI(g.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("totalMiembros", "$ " + String.valueOf(totalPresupuesto) + " Total presupuestos");
		map.put("TotalProyectosActivos", "$ " + String.valueOf(totalCompras) + " Total compras");
		return map;
	}

	private Map<String, Object> getDatosPresupuestoSemP(Map<String, Object> map, Semillero s) {
		List<ProyectoR> aux1 = proyectos.getProyectosPresSem(s.getProyectos());
		List<PresupuestoR> aux2 = presupuesto.getPresupuestosSem(s.getProyectos());
		List<CompraR> aux3 = presupuesto.getComprasSem(s.getProyectos());
		double totalPresupuesto = presupuesto.getTotalPresupuestoSem(s.getProyectos());
		double totalCompras = presupuesto.getTotalComprasSem(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("totalMiembros", "$ " + String.valueOf(totalPresupuesto) + " Total presupuestos");
		map.put("TotalProyectosActivos", "$ " + String.valueOf(totalCompras) + " Total compras");
		return map;
	}

	private Map<String, Object> getDatosInvestigadoresFormPGI(Map<String, Object> map, GrupoInvestigacion g) {
		var aux1 = miembros.getMiembrosGIInv(g.getSemilleros());
		var aux2 = miembrosExtra.getMiembrosGIExtraInv(g.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("totalMiembros", String.valueOf(aux1.size()) + " miembro(s) activos(s)");
		map.put("totalSemilleros", String.valueOf(g.getSemilleros().size()) + " semillero(s) asociados");
		return map;
	}

	private Map<String, Object> getDatosInvestigadoresFormP(Map<String, Object> map, Semillero s) {
		var aux = usuarios.getMiembrosSemillero(s.getId());
		var aux1 = miembros.getMiembrosSemilleroInv(aux);
		var aux2 = miembrosExtra.getMiembrosSemilleroExtraInv(aux);
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("totalMiembros", String.valueOf(aux1.size()) + " investigador(es) en formación");
		return map;
	}

	private Map<String, Object> getDatosProyectosConvAbiertasSemilleroP(Map<String, Object> map, Semillero s) {
		var aux1 = proyectos.getProyectosSemilleroCA(s.getProyectos());
		var aux2 = participantes.getIntegrantesCA(s.getProyectos());
		var aux3 = proyectoConv.getProyectosConvAbiertas(s.getProyectos());
		var aux4 = datosTablas.getProyectosEstadosConvA(s.getProyectos());
		var aux7 = productos.getProductosProyectosSemConvA(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource7", aux7);
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) en convocatoria(s) abierta(s)");
		map.put("totalProyectosFinalizados",
				String.valueOf(proyectoConv.countConvocatorias(aux3)) + " convocatoria(s) abierta(s)");
		map.put("totalMiembros",
				String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s) asociado(s)");
		return map;
	}

	private Map<String, Object> getDatosProyectosConvAbiertasGIP(Map<String, Object> map, GrupoInvestigacion s) {
		var aux0 = semilleros.getSemillerosConAGI(s.getSemilleros());
		var aux1 = proyectos.getProyectosGICA(s.getSemilleros());
		var aux2 = participantes.getIntegrantesCGIA(s.getSemilleros());//
		var aux3 = proyectoConv.getProyectosConvAbiertasGI(s.getSemilleros());
		var aux4 = datosTablas.getProyectosEstadosConvAGI(s.getSemilleros());
		var aux7 = productos.getProductosProyectosGIConvA(s.getSemilleros());
		var aux6 = datosTablas.getParticpacionesCASemilleros(s.getSemilleros());
		map.put("datasource0", aux0);
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) en convocatoria(s) abierta(s)");
		map.put("totalProyectosFinalizados",
				String.valueOf(proyectoConv.countConvocatorias(aux3)) + " convocatoria(s) abierta(s)");
		map.put("totalMiembros",
				String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s) asociado(s)");
		return map;
	}

	private Map<String, Object> getDatosSemillerosxProgramaP(Map<String, Object> map, Programa s) {
		var aux1 = semilleros.getSemillerosGI(s.getSemilleros());
		var aux2 = miembros.getLideresSemillerosDetalladoPrograma(s.getSemilleros());
		var aux3 = miembros.getMiembrosSemilleros(s.getSemilleros());
		var aux4 = miembrosExtra.getMiembrosGIExtraInv(s.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("totalMiembros", String.valueOf(aux1.size()) + " semillero(s)");
		map.put("totalMiembros2", String.valueOf(aux3.size()) + " miembro(s)");
		return map;
	}

	// Inician carga de datos

	private Map<String, Object> getDatosParticipacionesEventosSemilleroP(Map<String, Object> map, Semillero s) {
		var aux1 = proyectos.getProyectosSemilleroE(s.getProyectos());
		var aux2 = participantes.getIntegrantesE(s.getProyectos());
		var aux3 = eventos.getProyectosEventos(s.getProyectos());
		var aux4 = datosTablas.getProyectosAnioE(s.getProyectos());
		var aux5 = productos.getProductosProyectosESem(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) asociado(s)");
		map.put("totalMiembros", String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s)");
		return map;
	}

	private Map<String, Object> getDatosParticipacionesEventosGI(Map<String, Object> map, GrupoInvestigacion s) {
		var aux0 = semilleros.getSemillerosEGI(s.getSemilleros());
		var aux1 = proyectos.getProyectosEGI(s.getSemilleros());
		var aux2 = participantes.getIntegrantesEGI(s.getSemilleros());//
		var aux3 = eventos.getProyectosEventosGI(s.getSemilleros());
		var aux4 = datosTablas.getProyectosAnioEGI(s.getSemilleros());
		var aux5 = productos.getProductosProyectosEGI(s.getSemilleros());
		var aux6 = datosTablas.getParticpacionesESemilleros(s.getSemilleros());
		int x = datosTablas.countSemillerosPE(aux6);
		map.put("datasource0", aux0);
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s)");
		map.put("totalMiembros", String.valueOf(x) + " semillero(s) asociados(s)");
		return map;
	}

	private Map<String, Object> getDatosParticipacionesConvSemilleroP(Map<String, Object> map, Semillero s) {
		var aux1 = proyectos.getProyectosSemilleroC(s.getProyectos()); // Existe
		var aux2 = participantes.getIntegrantesC(s.getProyectos()); // Existe
		var aux3 = proyectoConv.getProyectosConvAbiertas(s.getProyectos()); // Existe
		var aux4 = datosTablas.getProyectosEstadosConv(s.getProyectos());
		var aux5 = proyectoConv.getProyectosConvCerradas(s.getProyectos()); // Existe
		var aux6 = datosTablas.getProyectosAnioConvSem(s.getProyectos());
		var aux7 = productos.getProductosProyectosSemConv(s.getProyectos()); // Existe
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) asociado(s)");
		map.put("totalMiembros", String.valueOf(miembros.countParticipantesProyectos(aux2)) + " participante(s)");
		return map;
	}

	private Map<String, Object> getDatosParticipacionesConvGIP(Map<String, Object> map, GrupoInvestigacion s) {
		var aux0 = semilleros.getSemillerosConGI(s.getSemilleros());
		var aux1 = proyectos.getProyectosGIC(s.getSemilleros());
		var aux2 = participantes.getIntegrantesCGI(s.getSemilleros());//
		var aux3 = proyectoConv.getProyectosConvAbiertasGI(s.getSemilleros());
		var aux4 = datosTablas.getProyectosEstadosConvGI(s.getSemilleros());
		var aux5 = proyectoConv.getProyectosConvCerradasGI(s.getSemilleros());
		var aux6 = datosTablas.getProyectosAnioConvGI(s.getSemilleros());
		var aux7 = productos.getProductosProyectosGIConv(s.getSemilleros());
		map.put("datasource0", aux0);
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s)");
		map.put("totalMiembros", String.valueOf(aux0.size()) + " semillero(s) asociado(s)");
		return map;
	}

	private Map<String, Object> getDatosProyectosActivosSemilleroP(Map<String, Object> map, Semillero s) {
		var aux1 = proyectos.getProyectosActivosSemillero(s.getProyectos());
		var aux2 = participantes.getIntegrantesProyActivos(s.getProyectos());
		var aux3 = productos.getProductosProyectosActSemillero(s.getProyectos());
		var aux4 = datosTablas.getProyectosActTipos(s.getProyectos());
		var aux5 = datosTablas.getProyectosActProductos(s.getProyectos());
		var aux6 = comentario.getProyectosActComentarios(s.getProyectos());
		int y = participantes.countParticipantesProyectosActSemillero(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) activos");
		map.put("totalMiembros", String.valueOf(y) + " miembro(s) de los proyectos");
		return map;
	}

	private Map<String, Object> getDatosProyectosActivosGIP(Map<String, Object> map, GrupoInvestigacion s) {
		var aux1 = proyectos.getProyectosActivosGI(s.getSemilleros());
		var aux2 = participantes.getIntegrantesProyActivosGI(s.getSemilleros());
		var aux3 = productos.getProductosProyectosActGI(s.getSemilleros());
		var aux4 = datosTablas.getProyectosActTipos(datosTablas.getAllProyectosGI(s.getSemilleros()));
		var aux5 = datosTablas.getProyectosActProductos(datosTablas.getAllProyectosGI(s.getSemilleros()));
		var aux6 = comentario.getProyectosActComentariosGI(s.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) activo(s)");
		map.put("totalMiembros",
				String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s) de los proyectos");
		return map;
	}

	private Map<String, Object> getDatosProyectosFinSemilleroP(Map<String, Object> map, Semillero s) {
		var aux1 = proyectos.getProyectosFinSemillero(s.getProyectos());
		var aux2 = participantes.getIntegrantesProyFin(s.getProyectos());
		var aux3 = productos.getProductosProyectosFin(s.getProyectos());
		var aux4 = datosTablas.getProyectosFinTipos(s.getProyectos());
		var aux5 = datosTablas.getProduccionAnioSem(s.getProyectos());
		var aux6 = comentario.getProyectosFinComentarios(s.getProyectos());
		int y = participantes.countParticipantesProyectosFinSemillero(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("totalProyectosFinalizados", String.valueOf(aux1.size()) + " proyecto(s) finalizado(s)");
		map.put("totalMiembros", String.valueOf(y) + " miembro(s) de los proyectos");
		return map;
	}

	private Map<String, Object> getDatosProyectosFinGIP(Map<String, Object> map, GrupoInvestigacion s) {
		var aux1 = proyectos.getProyectosFinGI(s.getSemilleros());
		var aux2 = participantes.getIntegrantesProyFinGI(s.getSemilleros());
		var aux3 = productos.getProductosProyectosFinGI(s.getSemilleros());
		var aux4 = datosTablas.getProduccionAnioGI(s.getSemilleros());
		var aux5 = datosTablas.getProyectosFinTipos(datosTablas.getAllProyectosGI(s.getSemilleros()));
		var aux6 = comentario.getProyectosFinComentariosGI(s.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("totalProyectosFinalizados", String.valueOf(aux1.size()) + " proyecto(s) finalizado(s)");
		map.put("totalMiembros", String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s)");
		return map;
	}

	private Map<String, Object> getDatosGIxFacultadP(Map<String, Object> map, Facultad s) {
		var sproyectos = datosTablas.getAllProyectos(gi.findByFacultad(s.getId()));
		int totalProyectos = sproyectos.size();
		int x = proyectos.countProyectosFacultadFin(sproyectos);
		int proyectosConProductos = proyectos.countProyectosConProductoFacultad(sproyectos);
		var gis = gi.findByFacultad(s.getId());
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
		if (totalProyectos == 0) {
			map.put("totalProyectosFinalizados", "0 proyectos finalizados");
			map.put("TotalProyectosActivos", "0 proyectos activos");
			map.put("totalProyectosConP", "0 proyectos con productos");
			map.put("totalProyectosSinP", " proyectos sin productos");
			map.put("TotalProyectos", "0 proyectos");
			map.put("PorProyectosFinalizados", "0 % proyectos finalizados");
			map.put("PorProyectosActivos", "0 % proyectos activos");
			map.put("PorProyectosConP", "0 % proyectos con productos");
			map.put("PorProyectosSinP", "0 % proyectos sin productos");
		} else {
			map.put("totalProyectosFinalizados", String.valueOf(x) + " proyecto(s) finalizado(s)");
			map.put("TotalProyectosActivos", String.valueOf(totalProyectos - x) + " proyecto(s) finalizado(s)");
			map.put("totalProyectosConP", String.valueOf(proyectosConProductos) + " proyecto(s) con productos");
			map.put("totalProyectosSinP",
					String.valueOf(totalProyectos - proyectosConProductos) + " proyecto(s) sin productos");
			map.put("TotalProyectos", String.valueOf(totalProyectos) + " proyecto(s) en total");
			map.put("PorProyectosFinalizados",
					String.valueOf((100 * x) / totalProyectos) + " % proyecto(s) finalizado(s)");
			map.put("PorProyectosActivos",
					String.valueOf((100 * (totalProyectos - x)) / totalProyectos) + " % proyecto(s) activo(s)");
			map.put("PorProyectosConP",
					String.valueOf((100 * proyectosConProductos) / totalProyectos) + " % proyecto(s) con productos");
			map.put("PorProyectosSinP",
					String.valueOf((100 * (totalProyectos - proyectosConProductos)) / totalProyectos)
							+ " % proyecto(s) sin productos");
		}
		map.put("totalMiembros", String.valueOf(aux2.size()) + " grupo(s) de investigación");
		map.put("totalLineas", String.valueOf(aux4.size()) + " linea(s) de investigación");
		return map;
	}

	private String getFechaFormateada(LocalDate fecha) {
		if (fecha != null) {
			return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else {
			return "N/A";
		}
	}

	private Map<String, Object> getDatosProyectoP(Map<String, Object> map, Proyecto s) {
		var aux1 = areas.getAreasProyecto(s.getAreasConocimiento()); // Se crea
		var aux2 = productos.getProductosProyecto(s.getProductos()); // Existe
		var aux3 = participantes.getIntegrantesProyecto(s.getParticipantes()); // Existe
		var aux4 = eventos.getParticipacionesProyecto(s.getParticipaciones()); // Existe
		var aux5 = proyectoConv.getParticipacionesProyecto(s.getProyectosConvocatoria()); // Existe
		var aux6 = comentario.getComentariosProyecto(s.getProductos()); // Se crea
		var aux7 = presupuesto.getPresupuestosProyecto(s); // Se crea
		var aux8 = presupuesto.getComprasProyecto(s); // Se crea
		var aux9 = proyectos.getProyectosFacultad(s.getAntecedentes());
		var aux10 = miembros.getMiembrosSemillero(usuarios.findParticipantesProyecto(s.getId()));
		map.put("nombre", s.getTitulo());
		map.put("estado", s.getEstado());
		if (s.getNota() == null) {
			map.put("nota", "N/A");
		} else {
			map.put("nota", String.valueOf(s.getNota()));
		}
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
		map.put("tipo", s.getTipoProyecto().getNombre());
		map.put("semillero", s.getSemillero().getNombre());
		map.put("totalMiembros", String.valueOf(miembros.countParticipantesProyectos(aux3)) + " participante(s)");
		map.put("totalMiembrosA",
				String.valueOf(miembros.countParticipantesProyectosA(aux3)) + " participante(s) activo(s)");
		map.put("totalParticipacionesEventos", String.valueOf(aux4.size()) + " participacion(es) en eventos");
		map.put("totalPartcipacionesConv", String.valueOf(aux5.size()) + "participacion(es) en convocatorias");
		map.put("totalProductos", String.valueOf(aux2.size()) + " producto(s) existente(s)");
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("datasource8", aux8);
		map.put("datasource9", aux9);
		map.put("datasource10", aux10);
		map.put("n", s.getTitulo());
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

	private Map<String, Object> getDatosSemilleroProduccionP(Map<String, Object> map, Semillero s) {
		var aux2 = datosTablas.getProyectosSemilleroPorEstado(s.getId());
		var aux3 = proyectos.getProyectosSemillero(s.getProyectos());
		var aux1 = productos.getProductosSemillero(s.getProyectos());
		var aux4 = datosTablas.getProducciónPorAnio(s.getProyectos());
		List<ComentarioR> aux5 = comentario.getComentariosProyectos(s.getProyectos());
		int totalProyectos = s.countProyectos();
		int x = semilleros.numProyectosFinalizados(s.getProyectos());
		int proyectosSinProductos = proyectos.numProyectosSinProductoSemillero(s.getProyectos());
		int proyectosConProductos = aux3.size() - proyectosSinProductos;
		map.put("datasource1", aux3);
		map.put("datasource2", aux2);
		map.put("datasource3", datosTablas.getProyectosSemilleroPorTipo(s.getId()));
		map.put("datasource4", aux1);
		map.put("datasource5", aux4);
		map.put("datasource6", aux5);
		if (totalProyectos == 0) {
			map.put("totalProyectosFinalizados", "0 proyecto(s) finalizado(s)");
			map.put("TotalProyectosActivos", "0 proyecto(s) activo(s)");
			map.put("totalProyectosConP", "0 proyecto(s) con productos");
			map.put("totalProyectosSinP", "0 proyecto(s) sin productos");
			map.put("TotalProyectos", "0 proyecto(s) de " + s.getNombre());
			map.put("PorProyectosFinalizados", "0 % proyecto(s) finalizado(s)");
			map.put("PorProyectosActivos", "0 % proyecto(s) activo(s)");
			map.put("PorProyectosConP", "0 % proyecto(s) con productos");
			map.put("PorProyectosSinP", "0 % proyecto(s) sin productos");
		} else {
			map.put("totalProyectosFinalizados", String.valueOf(x) + " proyecto(s) finalizado(s)");
			map.put("TotalProyectosActivos", String.valueOf(totalProyectos - x) + " proyecto(s) activo(s)");
			map.put("totalProyectosConP", String.valueOf(proyectosConProductos) + " proyecto(s) con productos");
			map.put("totalProyectosSinP", String.valueOf(proyectosSinProductos) + " proyecto(s) sin productos");
			map.put("TotalProyectos", String.valueOf(totalProyectos) + " proyecto(s) de " + s.getNombre());
			map.put("PorProyectosFinalizados",
					String.valueOf((100 * x) / totalProyectos) + " % proyecto(s) finalizado(s)");
			map.put("PorProyectosActivos",
					String.valueOf((100 * (totalProyectos - x)) / totalProyectos) + " % proyecto(s) activo(s)");
			map.put("PorProyectosConP",
					String.valueOf((100 * proyectosConProductos) / totalProyectos) + " % proyecto(s) con productos");
			map.put("PorProyectosSinP",
					String.valueOf((100 * proyectosSinProductos) / totalProyectos) + " % proyecto(s) sin productos");
		}
		return map;
	}

	private Map<String, Object> getDatosGIProduccionP(Map<String, Object> map, GrupoInvestigacion s) {
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
		var aux7 = datosTablas.getProyFinSemillerosGI(s.getSemilleros());
		aux7 = datosTablas.getProduccionSemillerosGI2(s.getSemilleros(), aux7);
		List<ComentarioR> aux8 = comentario.getProductosGI(s.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", aux5);
		map.put("datasource6", aux6);
		map.put("datasource7", aux7);
		map.put("datasource8", aux8);
		if (totalProyectos == 0) {
			map.put("totalProyectosFinalizados", "0 proyectos finalizados");
			map.put("TotalProyectosActivos", "0 proyectos finalizados");
			map.put("totalProyectosConP", "0 proyectos con productos");
			map.put("totalProyectosSinP", "0 proyectos sin productos");
			map.put("TotalProyectos", "0 proyectos del grupo " + s.getNombre());
			map.put("PorProyectosFinalizados", "0 % proyectos finalizados");
			map.put("PorProyectosActivos", "0 % proyectos activos");
			map.put("PorProyectosConP", "0 % proyectos con productos");
			map.put("PorProyectosSinP", "0 % proyectos sin productos");
		} else {
			map.put("totalProyectosFinalizados", String.valueOf(proyectosFinalizados) + " proyecto(s) finalizado(s)");
			map.put("TotalProyectosActivos",
					String.valueOf(totalProyectos - proyectosFinalizados) + " proyecto(s) activo(s)");
			map.put("totalProyectosConP", String.valueOf(proyectosConProductos) + " proyecto(s) con productos");
			map.put("totalProyectosSinP", String.valueOf(proyectosSinProductos) + " proyecto(s) sin productos");
			map.put("TotalProyectos", String.valueOf(totalProyectos) + " proyecto(s) de " + s.getNombre());
			map.put("PorProyectosFinalizados",
					String.valueOf((100 * proyectosFinalizados) / totalProyectos) + " % proyecto(s) finalizado(s)");
			map.put("PorProyectosActivos",
					String.valueOf((100 * (totalProyectos - proyectosFinalizados)) / totalProyectos)
							+ " % proyecto(s) activo(s)");
			map.put("PorProyectosConP",
					String.valueOf((100 * proyectosConProductos) / totalProyectos) + " % proyecto(s) con productos");
			map.put("PorProyectosSinP",
					String.valueOf((100 * proyectosSinProductos) / totalProyectos) + " % proyecto(s) sin productos");
		}
		map.put("tp", totalProyectos);
		map.put("totalMiembros", String.valueOf(s.countSemilleros()) + " semillero(s)");
		return map;
	}

	private Map<String, Object> getDatosGIIntegrantesP(Map<String, Object> map, GrupoInvestigacion s) {
		int x = proyectos.countProyectosFinalizadosGI(s), y = proyectos.countProyectosGI(s);
		var aux1 = semilleros.getSemillerosGI(s.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", miembros.getMiembrosSemillero(usuarios.getMiembrosGI(s.getId())));
		map.put("datasource3", miembrosExtra.getMiembrosSemilleroExtra(usuarios.getMiembrosGI(s.getId())));
		map.put("datasource4", miembros.getLideresSemillerosDetallado(s));
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyecto(s) finalizado(s)");
		map.put("TotalProyectosActivos", String.valueOf(y - x) + " proyecto(s) activo(s)");
		map.put("totalMiembros", String.valueOf(aux1.size()) + " semillero(s)");
		map.put("totalProyectos", y);
		return map;
	}

	private Map<String, Object> getDatosSemilleroIntegrantesP(Map<String, Object> map, Semillero s) {
		var aux = usuarios.getMiembrosSemillero(s.getId());
		var aux1 = miembros.getMiembrosSemillero(aux);
		var aux2 = miembrosExtra.getMiembrosSemilleroExtra(aux);
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("totalMiembros", String.valueOf(aux1.size()) + " miembro(s)");
		return map;
	}

	private Map<String, Object> setDatosCreador(Map<String, Object> datosSemillero, String usuario) {
		Usuario u = null;
		try {
			u = usuarios.getById(usuario);
			if (u.equals(null)) {
				return null;
			}
		} catch (javax.persistence.EntityNotFoundException e) {
			return null;
		}
		datosSemillero.put("nombreU", u.getNombreCompleto());
		datosSemillero.put("tipoU", u.getTiposUsuario().get(0).getNombre());
		if (u.getProgramaId() != null) {
			datosSemillero.put("adicionalU", u.getProgramaId().getNombre());
		} else {
			datosSemillero.put("adicionalU", "");
		}
		datosSemillero.put("firmaU", u.getFirma().getNombre());
		datosSemillero.put("logo1", "logo_usbbog_1.jpg");
		return datosSemillero;
	}

	private Map<String, Object> getDatosSemilleroP(Map<String, Object> map, Semillero s) {
		var aux1 = miembros.getMiembrosSemillero(usuarios.getMiembrosSemillero(s.getId()));
		var aux2 = datosTablas.getProyectosSemilleroPorEstado(s.getId());
		var aux3 = proyectos.getProyectosSemillero(s.getProyectos());
		var aux4 = participantes.getIntegrantes(s.getProyectos());
		var aux5 = proyectoConv.getProyectosConvAbiertas(s.getProyectos());
		int x = semilleros.numProyectosFinalizados(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", aux3);
		map.put("datasource3", aux2);
		map.put("datasource4", datosTablas.getProyectosSemilleroPorTipo(s.getId()));
		map.put("datasource5", aux4);
		map.put("datasource6", aux5);
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyecto(s) finalizado(s)");
		map.put("TotalProyectosActivos", String.valueOf(s.countProyectos() - x) + " proyecto(s) activos(s)");
		map.put("totalMiembros", String.valueOf(aux1.size()) + " miembro(s)");
		return map;
	}

	private Map<String, Object> getDatosGIP(Map<String, Object> map, GrupoInvestigacion s) {
		int x = proyectos.countProyectosFinalizadosGI(s), y = proyectos.countProyectosGI(s);
		var aux1 = semilleros.getSemillerosGI(s.getSemilleros());
		map.put("datasource1", lineas.getLineasInvGI(s));
		map.put("datasource2", aux1);
		map.put("datasource3", miembros.getLideresSemillerosDetallado(s));
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyecto(s) finalizado(s)");
		map.put("TotalProyectosActivos", String.valueOf(y - x) + " proyecto(s) activos(s)");
		map.put("totalMiembros", String.valueOf(aux1.size()) + " semillero(s)");
		return map;
	}

	private Map<String, Object> setDatosGi(Map<String, Object> map, GrupoInvestigacion s) {
		map.put("nombre", s.getNombre());
		map.put("fechaFun", getFechaFormateada(s.getFechaFun()));
		map.put("fechaCat", getFechaFormateada(s.getFechaCat()));
		map.put("categoria", s.getCategoria());
		var d = s.getDirectorGrupo();
		if (d == null) {
			map.put("liderSemillero", "sin director registrado");
			map.put("correoILider", "sin director registrado");
			map.put("telefonoLider", "sin director registrado");
			map.put("programaLider", "sin director registrado");
		} else {
			map.put("liderSemillero", d.getNombreCompleto());
			map.put("correoILider", d.getCorreoEst());
			map.put("telefonoLider", d.getTelefono());
			if (d.getProgramaId() != null) {
				map.put("programaLider", d.getProgramaId().getNombre());
			} else {
				map.put("programaLider", "N/A");
			}
		}
		map.put("n", s.getNombre());
		return map;
	}

	private Map<String, Object> setDatosSem(Map<String, Object> map, Semillero s) {
		map.put("nombreSemillero", s.getNombre());
		map.put("descripcion", s.getDescripcion());
		map.put("fechaFun", getFechaFormateada(s.getFechaFun()));
		map.put("grupoInv", s.getGrupoInvestigacion().getNombre());
		map.put("lineaInv", s.getLineaInvestigacion().getNombre());
		var l = s.getLiderSemillero();
		if (l == null) {
			map.put("liderSemillero", "sin lider registrado");
			map.put("correoILider", "sin lider registrado");
			map.put("telefonoLider", "sin lider registrado");
			map.put("programaLider", "sin lider registrado");
		} else {
			map.put("liderSemillero", l.getNombreCompleto());
			map.put("correoILider", l.getCorreoEst());
			map.put("telefonoLider", l.getTelefono());
			if (l.getProgramaId() != null) {
				map.put("programaLider", l.getProgramaId().getNombre());
			} else {
				map.put("programaLider", "N/A");
			}
		}
		map.put("n", s.getNombre());
		return map;
	}

	private Map<String, Object> setDatosPrograma(Map<String, Object> map, Programa s) {
		map.put("nombre", s.getNombre());
		var i = s.getFacultadId();
		map.put("facultad", i.getNombre());
		if (s.getDirector() == null) {
			map.put("liderSemillero1", "sin director registrado");
			map.put("correoILider1", "sin director registrado");
			map.put("telefonoLider1", "sin director registrado");
		} else {
			map.put("liderSemillero", s.getDirector().getNombreCompleto());
			map.put("correoILider1", s.getDirector().getCorreoEst());
			map.put("telefonoLider1", s.getDirector().getTelefono());
		}
		if (i.getDecano() == null) {
			map.put("liderSemillero", "sin decano registrado");
			map.put("correoILider", "sin decano registrado");
			map.put("telefonoLider", "sin decano registrado");
		} else {
			map.put("liderSemillero", i.getDecano().getNombreCompleto());
			map.put("correoILider", i.getDecano().getCorreoEst());
			map.put("telefonoLider", i.getDecano().getTelefono());
		}
		map.put("n", s.getNombre());
		return map;
	}

	private Map<String, Object> setDatosFacultad(Map<String, Object> map, Facultad s) {
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
		map.put("n", s.getNombre());
		return map;
	}

}