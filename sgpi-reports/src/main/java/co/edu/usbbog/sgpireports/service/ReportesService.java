package co.edu.usbbog.sgpireports.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Facultad;
import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.model.datamodels.EventoR;
import co.edu.usbbog.sgpireports.model.datamodels.GrupoInvR;
import co.edu.usbbog.sgpireports.model.datamodels.LineasGI;
import co.edu.usbbog.sgpireports.model.datamodels.MiembrosSemillero;
import co.edu.usbbog.sgpireports.model.datamodels.ParticipantesR;
import co.edu.usbbog.sgpireports.model.datamodels.ProductoR;
import co.edu.usbbog.sgpireports.model.datamodels.ProyectoR;
import co.edu.usbbog.sgpireports.model.datamodels.ProyectosConvocatoriaR;
import co.edu.usbbog.sgpireports.model.datamodels.SemillerosR;
import co.edu.usbbog.sgpireports.model.datamodels.TipoPEstado;
import co.edu.usbbog.sgpireports.repository.IFacultadRepository;
import co.edu.usbbog.sgpireports.repository.IGrupoInvestigacionRepository;
import co.edu.usbbog.sgpireports.repository.IProgramaRepository;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;
import co.edu.usbbog.sgpireports.repository.ISemilleroRepository;
import co.edu.usbbog.sgpireports.repository.IUsuarioRepository;
import co.edu.usbbog.sgpireports.service.report.*;
import net.sf.jasperreports.engine.JRException;

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
	@Autowired
	private CompraRService compras;
	@Autowired
	private IFileStorageService archivo;

	public String crearReporte(int cc, int rep, String usuario) throws JRException, IOException {
		if (rep < 23 && rep >= 0 && !usuario.isEmpty()) {
			Map<String, Object> datos = getDatos(cc, rep, usuario);
			return (datos != null) ? archivo.saveReporte(cc, rep, usuario, datos) : "Error al generar reporte";
		} else {
			return "Sin reporte";
		}
	}

	private Map<String, Object> getDatos(int cc, int rep, String usuario) {
		Semillero s = null;
		GrupoInvestigacion g = null;
		Map<String, Object> map = setDatosCreador(new HashMap<String, Object>(), usuario);
		if (map == null) {
			return null;
		} else if (rep <= 9) {
			try {
				s = semillero.getById(cc);
				map = setDatosSem(map, s);
			} catch (EntityNotFoundException e) {
				return null;
			}
		} else if (rep <= 19 && rep > 9) {
			try {
				g = gi.getById(cc);
				map = setDatosGi(map, g);
			} catch (EntityNotFoundException e) {
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
			return getDatosInvestigadoresFormPGI(map, g);
		case 20:
			try {
				Programa p = programa.getById(cc);
				map = setDatosPrograma(map, p);
				return getDatosSemillerosxProgramaP(map, p);
			} catch (EntityNotFoundException e) {
				return null;
			}
		case 21:
			try {
				Facultad f = facultad.getById(cc);
				map = getDatosGIxFacultadP(map, f);
				return setDatosFacultad(map, f);
			} catch (EntityNotFoundException e) {
				return null;
			}
		case 22:
			try {
				return getDatosProyectoP(map, proyecto.getById(cc));
			} catch (EntityNotFoundException e) {
				return null;
			}
		default:
			return null;
		}
	}

	public String crearReporteAnios(int cc, int rep, String usuario, int anioInicio, int anioFin)
			throws JRException, IOException {
		if (!usuario.isEmpty()) {
			Map<String, Object> datos = getDatosAnios(cc, rep, usuario, anioInicio, anioFin);
			return (datos != null) ? archivo.saveReporte(cc, rep, usuario, datos) : "Error al generar reporte";
		} else {
			return "Sin reporte";
		}
	}

	private Map<String, Object> getDatosAnios(int cc, int rep, String usuario, int anioInicio, int anioFin) {
		if (anioFin < anioInicio) {
			return null;
		}
		Semillero s = null;
		GrupoInvestigacion g = null;
		Map<String, Object> map = setDatosCreador(new HashMap<String, Object>(), usuario);
		if (map == null) {
			return null;
		} else if (rep == 23 || rep == 25) {
			try {
				s = semillero.getById(cc);
				map = setDatosSem(map, s);
			} catch (EntityNotFoundException e) {
				return null;
			}
		} else if (rep == 24 || rep == 26) {
			try {
				g = gi.getById(cc);
				map = setDatosGi(map, g);
			} catch (EntityNotFoundException e) {
				return null;
			}
		} else {
			return null;
		}
		switch (rep) {
		case 23:
			return getDatosProyectosSemAnios(map, s, anioInicio, anioFin);
		case 24:
			return getDatosProyectosGIAnios(map, g, anioInicio, anioFin);
		case 25:
			return getDatosPresupuestoSemAnios(map, s, anioInicio, anioFin);
		case 26:
			return getDatosPresupuestoGIAnios(map, g, anioInicio, anioFin);
		default:
			return null;
		}
	}

	private Map<String, Object> getDatosPresupuestoGIAnios(Map<String, Object> map, GrupoInvestigacion g,
			int anioInicio, int anioFin) {
		String periodo = "(" + String.valueOf(anioInicio) + "-" + String.valueOf(anioFin) + ")";
		List<Proyecto> general = proyecto.getProyectosGIPresAnios(g.getId(), anioInicio, anioFin + 1);
		double totalPresupuesto = presupuesto.getTotalPresupuestoSem(general);
		double totalCompras = compras.getTotalComprasSem(general);
		map.put("datasource1", proyectos.getProyectosPresSem(general));
		map.put("datasource2", presupuesto.getPresupuestosSem(general));
		map.put("datasource3", compras.getComprasSem(general));
		map.put("totalMiembros", "$ " + String.valueOf(totalPresupuesto) + " Total presupuestos");
		map.put("TotalProyectosActivos", "$ " + String.valueOf(totalCompras) + " Total compras");
		map.put("n", g.getNombre() + "-" + periodo);
		map.put("periodo", "Periodo: " + periodo);
		return map;
	}

	private Map<String, Object> getDatosPresupuestoSemAnios(Map<String, Object> map, Semillero s, int anioInicio,
			int anioFin) {
		String periodo = "(" + String.valueOf(anioInicio) + "-" + String.valueOf(anioFin) + ")";
		List<Proyecto> general = proyecto.getProyectosSemPresAnios(s.getId(), anioInicio, anioFin + 1);
		double totalPresupuesto = presupuesto.getTotalPresupuestoSem(general);
		double totalCompras = compras.getTotalComprasSem(general);
		map.put("datasource1", proyectos.getProyectosPresSem(general));
		map.put("datasource2", presupuesto.getPresupuestosSem(general));
		map.put("datasource3", compras.getComprasSem(general));
		map.put("totalMiembros", "$ " + String.valueOf(totalPresupuesto) + " Total presupuestos");
		map.put("TotalProyectosActivos", "$ " + String.valueOf(totalCompras) + " Total compras");
		map.put("n", s.getNombre() + "-" + periodo);
		map.put("periodo", "Periodo: " + periodo);
		return map;
	}

	private Map<String, Object> getDatosProyectosGIAnios(Map<String, Object> map, GrupoInvestigacion g, int anioInicio,
			int anioFin) {
		String periodo = "(" + String.valueOf(anioInicio) + "-" + String.valueOf(anioFin) + ")";
		List<Proyecto> general = proyecto.getProyectosGIAnios(g.getId(), anioInicio, anioFin + 1);
		List<Proyecto> prod = proyecto.getProyectosGIProdAnios(g.getId(), anioInicio, anioFin + 1);
		List<Proyecto> even = proyecto.getProyectosGIEvenAnios(g.getId(), anioInicio, anioFin + 1);
		List<Proyecto> conv = proyecto.getProyectosGIConvAnios(g.getId(), anioInicio, anioFin + 1);
		List<Proyecto> aux = proyectos.unificar(general, prod, even, conv);
		map.put("datasource0", semilleros.getSemillerosGIAnio(aux, g.getSemilleros(), anioInicio, anioFin + 1));
		map.put("datasource1", proyectos.getProyectosSemillero(aux));
		map.put("datasource2", participantes.getIntegrantes(aux));
		map.put("datasource3", productos.getProductosSemillero(prod));
		map.put("datasource4", comentario.getComentariosProyectos(prod));
		map.put("datasource5", proyectoConv.getParticipacionesConv(conv));
		map.put("datasource6", eventos.getProyectosEventos(even));
		map.put("datasource7", datosTablas.getDatosporAnio(aux)); // Barras
		map.put("datasource8", datosTablas.getProyectosTipoFacultad(aux)); // Circulo
		map.put("datasource9", datosTablas.getProyectosEstadosFacultad(aux)); // circulo
		map.put("datasource10", lineas.getLineasInvGI(g));
		map = grupos.getTotalesActividad(map, aux, anioInicio, anioFin + 1);
		map.put("n", g.getNombre() + "-" + periodo);
		map.put("periodo", periodo);
		return map;
	}

	private Map<String, Object> getDatosProyectosSemAnios(Map<String, Object> map, Semillero s, int anioInicio,
			int anioFin) {
		String periodo = "(" + String.valueOf(anioInicio) + "-" + String.valueOf(anioFin) + ")";
		List<Proyecto> general = proyecto.getProyectosSemAnios(s.getId(), anioInicio, anioFin + 1);
		List<Proyecto> prod = proyecto.getProyectosSemProdAnios(s.getId(), anioInicio, anioFin + 1);
		List<Proyecto> even = proyecto.getProyectosSemEvenAnios(s.getId(), anioInicio, anioFin + 1);
		List<Proyecto> conv = proyecto.getProyectosSemConvAnios(s.getId(), anioInicio, anioFin + 1);
		List<Proyecto> aux = proyectos.unificar(general, prod, even, conv);
		map.put("datasource1", proyectos.getProyectosSemillero(aux));
		map.put("datasource2", participantes.getIntegrantes(aux));
		map.put("datasource3", productos.getProductosSemillero(prod));
		map.put("datasource4", comentario.getComentariosProyectos(prod));
		map.put("datasource5", proyectoConv.getParticipacionesConv(conv));
		map.put("datasource6", eventos.getProyectosEventos(even));
		map.put("datasource7", datosTablas.getDatosporAnio(aux)); // Barras
		map.put("datasource8", datosTablas.getProyectosTipoFacultad(aux)); // Circulo
		map.put("datasource9", datosTablas.getProyectosEstadosFacultad(aux)); // circulo
		map = semilleros.getSemilleroAnio(aux, anioInicio, anioFin + 1, map);
		map.put("n", s.getNombre() + "-" + periodo);
		map.put("periodo", periodo);
		return map;
	}

	private Map<String, Object> getDatosPresupuestoGIP(Map<String, Object> map, GrupoInvestigacion g) {
		double totalPresupuesto = presupuesto.getTotalPresupuestoGI(g.getSemilleros());
		double totalCompras = compras.getTotalComprasGI(g.getSemilleros());
		map.put("datasource1", proyectos.getProyectosPresGI(g.getSemilleros()));
		map.put("datasource2", presupuesto.getPresupuestosGI(g.getSemilleros()));
		map.put("datasource3", compras.getComprasGI(g.getSemilleros()));
		map.put("totalMiembros", "$ " + String.valueOf(totalPresupuesto) + " Total presupuestos");
		map.put("TotalProyectosActivos", "$ " + String.valueOf(totalCompras) + " Total compras");
		return map;
	}

	private Map<String, Object> getDatosPresupuestoSemP(Map<String, Object> map, Semillero s) {
		double totalPresupuesto = presupuesto.getTotalPresupuestoSem(s.getProyectos());
		double totalCompras = compras.getTotalComprasSem(s.getProyectos());
		map.put("datasource1", proyectos.getProyectosPresSem(s.getProyectos()));
		map.put("datasource2", presupuesto.getPresupuestosSem(s.getProyectos()));
		map.put("datasource3", compras.getComprasSem(s.getProyectos()));
		map.put("totalMiembros", "$ " + String.valueOf(totalPresupuesto) + " Total presupuestos");
		map.put("TotalProyectosActivos", "$ " + String.valueOf(totalCompras) + " Total compras");
		return map;
	}

	private Map<String, Object> getDatosInvestigadoresFormPGI(Map<String, Object> map, GrupoInvestigacion g) {
		List<MiembrosSemillero> aux1 = miembros.getMiembrosGIInv(g.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", miembrosExtra.getMiembrosGIExtraInv(g.getSemilleros()));
		map.put("totalMiembros", String.valueOf(aux1.size()) + " miembro(s) activos(s)");
		map.put("totalSemilleros", String.valueOf(g.getSemilleros().size()) + " semillero(s) asociados");
		return map;
	}

	private Map<String, Object> getDatosInvestigadoresFormP(Map<String, Object> map, Semillero s) {
		List<Usuario> aux = usuarios.getMiembrosSemillero(s.getId());
		List<MiembrosSemillero> aux1 = miembros.getMiembrosSemilleroInv(aux);
		map.put("datasource1", aux1);
		map.put("datasource2", miembrosExtra.getMiembrosSemilleroExtraInv(aux));
		map.put("totalMiembros", String.valueOf(aux1.size()) + " investigador(es) en formación");
		return map;
	}

	private Map<String, Object> getDatosProyectosConvAbiertasSemilleroP(Map<String, Object> map, Semillero s) {
		List<ProyectoR> aux1 = proyectos.getProyectosSemilleroCA(s.getProyectos());
		List<ParticipantesR> aux2 = participantes.getIntegrantesCA(s.getProyectos());
		List<ProyectosConvocatoriaR> aux3 = proyectoConv.getProyectosConvAbiertas(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", datosTablas.getProyectosEstadosConvA(s.getProyectos()));
		map.put("datasource7", productos.getProductosProyectosSemConvA(s.getProyectos()));
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) en convocatoria(s) abierta(s)");
		map.put("totalProyectosFinalizados",
				String.valueOf(proyectoConv.countConvocatorias(aux3)) + " convocatoria(s) abierta(s)");
		map.put("totalMiembros",
				String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s) asociado(s)");
		return map;
	}

	private Map<String, Object> getDatosProyectosConvAbiertasGIP(Map<String, Object> map, GrupoInvestigacion s) {
		List<ProyectoR> aux1 = proyectos.getProyectosGICA(s.getSemilleros());
		List<ParticipantesR> aux2 = participantes.getIntegrantesCGIA(s.getSemilleros());//
		List<ProyectosConvocatoriaR> aux3 = proyectoConv.getProyectosConvAbiertasGI(s.getSemilleros());
		map.put("datasource0", semilleros.getSemillerosConAGI(s.getSemilleros()));
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", datosTablas.getProyectosEstadosConvAGI(s.getId()));
		map.put("datasource6", datosTablas.getParticpacionesCASemilleros(s.getSemilleros()));
		map.put("datasource7", productos.getProductosProyectosGIConvA(s.getSemilleros()));
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) en convocatoria(s) abierta(s)");
		map.put("totalProyectosFinalizados",
				String.valueOf(proyectoConv.countConvocatorias(aux3)) + " convocatoria(s) abierta(s)");
		map.put("totalMiembros",
				String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s) asociado(s)");
		return map;
	}

	private Map<String, Object> getDatosSemillerosxProgramaP(Map<String, Object> map, Programa s) {
		List<SemillerosR> aux1 = semilleros.getSemillerosGI(s.getSemilleros());
		List<MiembrosSemillero> aux3 = miembros.getMiembrosSemilleros(s.getId());
		map.put("datasource1", aux1);
		map.put("datasource2", miembros.getLideresSemillerosDetalladoPrograma(s.getSemilleros()));
		map.put("datasource3", aux3);
		map.put("datasource4", miembrosExtra.getMiembrosGIExtraInv(s.getSemilleros()));
		map.put("totalMiembros", String.valueOf(aux1.size()) + " semillero(s)");
		map.put("totalMiembros2", String.valueOf(aux3.size()) + " miembro(s)");
		return map;
	}

	private Map<String, Object> getDatosParticipacionesEventosSemilleroP(Map<String, Object> map, Semillero s) {
		List<ProyectoR> aux1 = proyectos.getProyectosSemilleroE(s.getProyectos());
		List<ParticipantesR> aux2 = participantes.getIntegrantesE(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", participantes.getIntegrantesE(s.getProyectos()));
		map.put("datasource3", eventos.getProyectosEventos(s.getProyectos()));
		map.put("datasource4", datosTablas.getProyectosAnioE(s.getProyectos()));
		map.put("datasource5", productos.getProductosProyectosESem(s.getProyectos()));
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) asociado(s)");
		map.put("totalMiembros", String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s)");
		return map;
	}

	private Map<String, Object> getDatosParticipacionesEventosGI(Map<String, Object> map, GrupoInvestigacion s) {
		List<ProyectoR> aux1 = proyectos.getProyectosEGI(s.getSemilleros());
		List<TipoPEstado> aux6 = datosTablas.getParticpacionesESemilleros(s.getSemilleros());
		int x = datosTablas.countSemillerosPE(aux6);
		map.put("datasource0", semilleros.getSemillerosEGI(s.getSemilleros()));
		map.put("datasource1", aux1);
		map.put("datasource2", participantes.getIntegrantesEGI(s.getSemilleros()));
		map.put("datasource3", eventos.getProyectosEventosGI(s.getSemilleros()));
		map.put("datasource4", datosTablas.getProyectosAnioEGI(s.getId()));
		map.put("datasource5", productos.getProductosProyectosEGI(s.getSemilleros()));
		map.put("datasource6", aux6);
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s)");
		map.put("totalMiembros", String.valueOf(x) + " semillero(s) asociados(s)");
		return map;
	}

	private Map<String, Object> getDatosParticipacionesConvSemilleroP(Map<String, Object> map, Semillero s) {
		List<ProyectoR> aux1 = proyectos.getProyectosSemilleroC(s.getProyectos()); // Existe
		List<ParticipantesR> aux2 = participantes.getIntegrantesC(s.getProyectos()); // Existe
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", proyectoConv.getProyectosConvAbiertas(s.getProyectos()));
		map.put("datasource4", datosTablas.getProyectosEstadosConv(s.getProyectos()));
		map.put("datasource5", proyectoConv.getProyectosConvCerradas(s.getProyectos()));
		map.put("datasource6", datosTablas.getProyectosAnioConvSem(s.getProyectos()));
		map.put("datasource7", productos.getProductosProyectosSemConv(s.getProyectos()));
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) asociado(s)");
		map.put("totalMiembros", String.valueOf(miembros.countParticipantesProyectos(aux2)) + " participante(s)");
		return map;
	}

	private Map<String, Object> getDatosParticipacionesConvGIP(Map<String, Object> map, GrupoInvestigacion s) {
		List<SemillerosR> aux0 = semilleros.getSemillerosConGI(s.getSemilleros());
		List<ProyectoR> aux1 = proyectos.getProyectosGIC(s.getSemilleros());
		map.put("datasource0", aux0);
		map.put("datasource1", aux1);
		map.put("datasource2", participantes.getIntegrantesCGI(s.getSemilleros()));
		map.put("datasource3", proyectoConv.getProyectosConvAbiertasGI(s.getSemilleros()));
		map.put("datasource4", datosTablas.getProyectosEstadosConvGI(s.getId()));
		map.put("datasource5", proyectoConv.getProyectosConvCerradasGI(s.getSemilleros()));
		map.put("datasource6", datosTablas.getProyectosAnioConvGI(s.getId()));
		map.put("datasource7", productos.getProductosProyectosGIConv(s.getSemilleros()));
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s)");
		map.put("totalMiembros", String.valueOf(aux0.size()) + " semillero(s) asociado(s)");
		return map;
	}

	private Map<String, Object> getDatosProyectosActivosSemilleroP(Map<String, Object> map, Semillero s) {
		List<ProyectoR> aux1 = proyectos.getProyectosActivosSemillero(s.getProyectos());
		int y = participantes.countParticipantesProyectosActSemillero(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", participantes.getIntegrantesProyActivos(s.getProyectos()));
		map.put("datasource3", productos.getProductosProyectosActSemillero(s.getProyectos()));
		map.put("datasource4", datosTablas.getProyectosActTipos(s.getProyectos()));
		map.put("datasource5", datosTablas.getProyectosActProductos(s.getProyectos()));
		map.put("datasource6", comentario.getProyectosActComentarios(s.getProyectos()));
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) activos");
		map.put("totalMiembros", String.valueOf(y) + " miembro(s) de los proyectos");
		return map;
	}

	private Map<String, Object> getDatosProyectosActivosGIP(Map<String, Object> map, GrupoInvestigacion s) {
		List<ProyectoR> aux1 = proyectos.getProyectosActivosGI(s.getSemilleros());
		List<ParticipantesR> aux2 = participantes.getIntegrantesProyActivosGI(s.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", productos.getProductosProyectosActGI(s.getSemilleros()));
		map.put("datasource4", datosTablas.getProyectosActTipos(proyecto.findByGI(s.getId())));
		map.put("datasource5", datosTablas.getProyectosActProductos(proyecto.findByGI(s.getId())));
		map.put("datasource6", comentario.getProyectosActComentariosGI(s.getSemilleros()));
		map.put("TotalProyectosActivos", String.valueOf(aux1.size()) + " proyecto(s) activo(s)");
		map.put("totalMiembros",
				String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s) de los proyectos");
		return map;
	}

	private Map<String, Object> getDatosProyectosFinSemilleroP(Map<String, Object> map, Semillero s) {
		List<ProyectoR> aux1 = proyectos.getProyectosFinSemillero(s.getProyectos());
		int y = participantes.countParticipantesProyectosFinSemillero(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", participantes.getIntegrantesProyFin(s.getProyectos()));
		map.put("datasource3", productos.getProductosProyectosFin(s.getProyectos()));
		map.put("datasource4", datosTablas.getProyectosFinTipos(s.getProyectos()));
		map.put("datasource5", datosTablas.getProducciónPorAnio(s.getProyectos()));
		map.put("datasource6", comentario.getProyectosFinComentarios(s.getProyectos()));
		map.put("totalProyectosFinalizados", String.valueOf(aux1.size()) + " proyecto(s) finalizado(s)");
		map.put("totalMiembros", String.valueOf(y) + " miembro(s) de los proyectos");
		return map;
	}

	private Map<String, Object> getDatosProyectosFinGIP(Map<String, Object> map, GrupoInvestigacion s) {
		List<ProyectoR> aux1 = proyectos.getProyectosFinGI(s.getSemilleros());
		List<ParticipantesR> aux2 = participantes.getIntegrantesProyFinGI(s.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("datasource3", productos.getProductosProyectosFinGI(s.getSemilleros()));
		map.put("datasource4", datosTablas.getProduccionAnioGI(s.getId()));
		map.put("datasource5", datosTablas.getProyectosFinTipos(proyecto.findByGI(s.getId())));
		map.put("datasource6", comentario.getProyectosFinComentariosGI(s.getSemilleros()));
		map.put("totalProyectosFinalizados", String.valueOf(aux1.size()) + " proyecto(s) finalizado(s)");
		map.put("totalMiembros", String.valueOf(miembros.countParticipantesProyectos(aux2)) + " miembro(s)");
		return map;
	}

	private Map<String, Object> getDatosGIxFacultadP(Map<String, Object> map, Facultad s) {
		List<Proyecto> sproyectos = proyecto.findByFacultad(s.getId());
		int totalProyectos = sproyectos.size();
		List<GrupoInvestigacion> gis = gi.findByFacultad(s.getId());
		List<GrupoInvR> aux2 = grupos.getGruposInv(gis);
		List<LineasGI> aux4 = lineas.getLineasInvFacultad(gis);
		map.put("datasource1", programas.getProgramasFacultad(s.getProgramas()));
		map.put("datasource2", aux2);
		map.put("datasource3", miembros.getDirectoresGI(gis));
		map.put("datasource4", aux4);
		map.put("datasource5", datosTablas.getProyectosEstadosFacultad(sproyectos));
		map.put("datasource6", datosTablas.getProyectosGIFacultad(sproyectos));
		map.put("datasource7", datosTablas.getProyectosTipoFacultad(sproyectos));
		map.put("datasource8", datosTablas.getProyectosAnioFacultad(sproyectos));
		map.put("datasource9", proyectos.getProyectosFacultad(sproyectos));
		if (totalProyectos == 0) {
			map = totalesVacios(map, "", false);
			map.put("TotalProyectos", "0 proyectos");
		} else {
			map = setTotalesProd(map, null, null, s);
			map.put("TotalProyectos", String.valueOf(totalProyectos) + " proyecto(s) en total");
		}
		map.put("totalMiembros", String.valueOf(aux2.size()) + " grupo(s) de investigación");
		map.put("totalLineas", String.valueOf(aux4.size()) + " linea(s) de investigación");
		return map;
	}

	private Map<String, Object> getDatosProyectoP(Map<String, Object> map, Proyecto s) {
		List<ProductoR> aux2 = productos.getProductosProyecto(s.getProductos());
		List<ParticipantesR> aux3 = participantes.getIntegrantesProyecto(s.getParticipantes());
		List<EventoR> aux4 = eventos.getParticipacionesProyecto(s.getParticipaciones());
		List<ProyectosConvocatoriaR> aux5 = proyectoConv.getParticipacionesProyecto(s.getProyectosConvocatoria());
		map.put("datasource1", areas.getAreasProyecto(s.getAreasConocimiento()));
		map.put("datasource2", aux2);
		map.put("datasource3", aux3);
		map.put("datasource4", aux4);
		map.put("datasource5", proyectoConv.getParticipacionesProyecto(s.getProyectosConvocatoria()));
		map.put("datasource6", comentario.getComentariosProyecto(s.getProductos()));
		map.put("datasource7", presupuesto.getPresupuestosProyecto(s));
		map.put("datasource8", compras.getComprasProyecto(s));
		map.put("datasource9", proyectos.getProyectosFacultad(s.getAntecedentes()));
		map.put("datasource10", miembros.getMiembrosSemillero(usuarios.findParticipantesProyecto(s.getId())));
		map.put("nombre", s.getTitulo());
		map.put("estado", s.getEstado());
		map = extras.setDatosVaciosString(map, s.getNota(), "nota", "Sin conclusiones");
		map.put("descripcion", s.getDescripcion());
		if (s.getMacroProyecto() != null) {
			map.put("macroProyectoNombre", s.getMacroProyecto().getNombre());
			map.put("macroProyectoDescripcion", s.getMacroProyecto().getDescripcion());
			map.put("macroProyectoEstado", s.getMacroProyecto().getEstado());
			map.put("macroProyectoInicio", extras.getFechaFormateada(s.getMacroProyecto().getFechaInicio()));
			map.put("macroProyectoFin", extras.getFechaFormateada(s.getMacroProyecto().getFechaFin()));
		} else {
			map.put("macroProyectoNombre", "N/A");
		}
		map.put("inicio", extras.getFechaFormateada(s.getFechaInicio()));
		map.put("fin", extras.getFechaFormateada(s.getFechaFin()));
		map = extras.setDatosVaciosString(map, s.getRetroalimentacionFinal(), "retroalimentacion", "Sin retroalimentacion");
		map = extras.setDatosVaciosString(map, s.getConclusiones(), "conclusiones", "Sin conclusiones");
		map.put("ciudad", s.getCiudad());
		map.put("metodologia", s.getMetodologia());
		map.put("justificacion", s.getJustificacion());
		map.put("tipo", s.getTipoProyecto().getNombre());
		map.put("semillero", s.getSemillero().getNombre());
		map.put("totalMiembros", String.valueOf(miembros.countParticipantesProyectos(aux3)) + " participante(s)");
		map.put("totalMiembrosA",
				String.valueOf(miembros.countParticipantesProyectoA(s.getParticipantes())) + " participante(s) activo(s)");
		map.put("totalParticipacionesEventos", String.valueOf(aux4.size()) + " participacion(es) en eventos");
		map.put("totalPartcipacionesConv", String.valueOf(aux5.size()) + "participacion(es) en convocatorias");
		map.put("totalProductos", String.valueOf(aux2.size()) + " producto(s) existente(s)");
		map.put("n", s.getTitulo());
		return map;
	}

	private Map<String, Object> getDatosSemilleroProduccionP(Map<String, Object> map, Semillero s) {
		int totalProyectos = s.countProyectos();
		map.put("datasource1", proyectos.getProyectosSemillero(s.getProyectos()));
		map.put("datasource2", datosTablas.getProyectosSemilleroPorEstado(s.getId()));
		map.put("datasource3", datosTablas.getProyectosSemilleroPorTipo(s.getId()));
		map.put("datasource4", productos.getProductosSemillero(s.getProyectos()));
		map.put("datasource5", datosTablas.getProducciónPorAnio(s.getProyectos()));
		map.put("datasource6", comentario.getComentariosProyectos(s.getProyectos()));
		map = (totalProyectos == 0) ? totalesVacios(map, s.getNombre(), true) : setTotalesProd(map, null, s, null);
		return map;
	}

	private Map<String, Object> getDatosGIProduccionP(Map<String, Object> map, GrupoInvestigacion s) {
		int totalProyectos = proyectos.countProyectosGI(s);
		map.put("datasource1", semilleros.getSemillerosGI(s.getSemilleros()));
		map.put("datasource2", proyectos.getProyectosGI(s.getSemilleros()));
		map.put("datasource3", productos.getProductosGI(s.getSemilleros()));
		map.put("datasource4", datosTablas.getTiposProyectosGI(s.getId()));
		map.put("datasource5", datosTablas.getProyectosEstadoGI(s.getId()));
		map.put("datasource6", datosTablas.getProduccionAnioGI(s.getId()));
		map.put("datasource7", datosTablas.getProduccionSemillerosGI2(s.getSemilleros(),
				datosTablas.getProyFinSemillerosGI(s.getSemilleros())));
		map.put("datasource8", comentario.getProductosGI(s.getSemilleros()));
		map = (totalProyectos == 0) ? totalesVacios(map, s.getNombre(), false) : setTotalesProd(map, s, null, null);
		map.put("tp", totalProyectos);
		map.put("totalMiembros", String.valueOf(s.countSemilleros()) + " semillero(s)");
		return map;
	}

	private Map<String, Object> setTotalesProd(Map<String, Object> map, GrupoInvestigacion g, Semillero s, Facultad f) {
		int proyectosFinalizados = 0, totalProyectos = 0, proyectosConProductos = 0, proyectosSinProductos = 0;
		if (s != null) {
			totalProyectos = s.countProyectos();
			proyectosFinalizados = semilleros.numProyectosFinalizados(s.getProyectos());
			proyectosSinProductos = proyectos.numProyectosSinProductoSemillero(s.getProyectos());
			proyectosConProductos = totalProyectos - proyectosSinProductos;
			map.put("TotalProyectos", String.valueOf(totalProyectos) + " proyecto(s) de " + s.getNombre());
		} else if (g != null) {
			proyectosFinalizados = proyectos.countProyectosFinalizadosGI(g);
			totalProyectos = proyectos.countProyectosGI(g);
			proyectosConProductos = proyectos.countProyectosConProductoGI(g.getSemilleros());
			proyectosSinProductos = totalProyectos - proyectosConProductos;
			map.put("TotalProyectos", String.valueOf(totalProyectos) + " proyecto(s) de " + g.getNombre());
		} else if (f != null) {
			var fproyectos = proyecto.findByFacultad(f.getId());
			totalProyectos = fproyectos.size();
			proyectosFinalizados = proyectos.countProyectosFin(fproyectos);
			proyectosConProductos = proyectos.countProyectosConProductoFacultad(fproyectos);
			proyectosSinProductos = totalProyectos - proyectosConProductos;
			map.put("TotalProyectos", String.valueOf(totalProyectos) + " proyecto(s) de " + f.getNombre());
		}
		map.put("totalProyectosFinalizados", String.valueOf(proyectosFinalizados) + " proyecto(s) finalizado(s)");
		map.put("TotalProyectosActivos",
				String.valueOf(totalProyectos - proyectosFinalizados) + " proyecto(s) activo(s)");
		map.put("totalProyectosConP", String.valueOf(proyectosConProductos) + " proyecto(s) con productos");
		map.put("totalProyectosSinP", String.valueOf(proyectosSinProductos) + " proyecto(s) sin productos");
		map.put("PorProyectosFinalizados",
				String.valueOf((100 * proyectosFinalizados) / totalProyectos) + " % proyecto(s) finalizado(s)");
		map.put("PorProyectosActivos", String.valueOf((100 * (totalProyectos - proyectosFinalizados)) / totalProyectos)
				+ " % proyecto(s) activo(s)");
		map.put("PorProyectosConP",
				String.valueOf((100 * proyectosConProductos) / totalProyectos) + " % proyecto(s) con productos");
		map.put("PorProyectosSinP",
				String.valueOf((100 * proyectosSinProductos) / totalProyectos) + " % proyecto(s) sin productos");
		return map;
	}

	private Map<String, Object> totalesVacios(Map<String, Object> map, String nombre, boolean tipo) {
		map.put("totalProyectosFinalizados", "0 proyectos finalizados");
		map.put("TotalProyectosActivos", "0 proyectos finalizados");
		map.put("totalProyectosConP", "0 proyectos con productos");
		map.put("totalProyectosSinP", "0 proyectos sin productos");
		map.put("TotalProyectos", (tipo) ? "0 proyectos del semillero " + nombre : "0 proyectos del grupo " + nombre);
		map.put("PorProyectosFinalizados", "0 % proyectos finalizados");
		map.put("PorProyectosActivos", "0 % proyectos activos");
		map.put("PorProyectosConP", "0 % proyectos con productos");
		map.put("PorProyectosSinP", "0 % proyectos sin productos");
		return map;
	}

	private Map<String, Object> getDatosGIIntegrantesP(Map<String, Object> map, GrupoInvestigacion s) {
		int x = proyectos.countProyectosFinalizadosGI(s), y = proyectos.countProyectosGI(s);
		List<SemillerosR> aux1 = semilleros.getSemillerosGI(s.getSemilleros());
		map.put("datasource1", aux1);
		map.put("datasource2", miembros.getMiembrosSemillero(usuarios.getMiembrosGI(s.getId())));
		map.put("datasource3", miembrosExtra.getMiembrosSemilleroExtra(usuarios.getMiembrosGI(s.getId())));
		map.put("datasource4", miembros.getLideresSemillerosDetalladoPrograma(s.getSemilleros()));
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyecto(s) finalizado(s)");
		map.put("TotalProyectosActivos", String.valueOf(y - x) + " proyecto(s) activo(s)");
		map.put("totalMiembros", String.valueOf(aux1.size()) + " semillero(s)");
		map.put("totalProyectos", y);
		return map;
	}

	private Map<String, Object> getDatosSemilleroIntegrantesP(Map<String, Object> map, Semillero s) {
		List<Usuario> aux = usuarios.getMiembrosSemillero(s.getId());
		List<MiembrosSemillero> aux1 = miembros.getMiembrosSemillero(aux);
		map.put("datasource1", aux1);
		map.put("datasource2", miembrosExtra.getMiembrosSemilleroExtra(aux));
		map.put("totalMiembros", String.valueOf(aux1.size()) + " miembro(s)");
		return map;
	}

	private Map<String, Object> getDatosSemilleroP(Map<String, Object> map, Semillero s) {
		List<MiembrosSemillero> aux1 = miembros.getMiembrosSemillero(usuarios.getMiembrosSemillero(s.getId()));
		int x = semilleros.numProyectosFinalizados(s.getProyectos());
		map.put("datasource1", aux1);
		map.put("datasource2", proyectos.getProyectosSemillero(s.getProyectos()));
		map.put("datasource3", datosTablas.getProyectosSemilleroPorEstado(s.getId()));
		map.put("datasource4", datosTablas.getProyectosSemilleroPorTipo(s.getId()));
		map.put("datasource5", participantes.getIntegrantes(s.getProyectos()));
		map.put("datasource6", proyectoConv.getProyectosConvAbiertas(s.getProyectos()));
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyecto(s) finalizado(s)");
		map.put("TotalProyectosActivos", String.valueOf(s.countProyectos() - x) + " proyecto(s) activos(s)");
		map.put("totalMiembros", String.valueOf(aux1.size()) + " miembro(s)");
		return map;
	}

	private Map<String, Object> getDatosGIP(Map<String, Object> map, GrupoInvestigacion s) {
		int x = proyectos.countProyectosFinalizadosGI(s), y = proyectos.countProyectosGI(s);
		List<SemillerosR> aux1 = semilleros.getSemillerosGI(s.getSemilleros());
		map.put("datasource1", lineas.getLineasInvGI(s));
		map.put("datasource2", aux1);
		map.put("datasource3", miembros.getLideresSemillerosDetalladoPrograma(s.getSemilleros()));
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyecto(s) finalizado(s)");
		map.put("TotalProyectosActivos", String.valueOf(y - x) + " proyecto(s) activos(s)");
		map.put("totalMiembros", String.valueOf(aux1.size()) + " semillero(s) asociado(s)");
		return map;
	}

	private Map<String, Object> setDatosGi(Map<String, Object> map, GrupoInvestigacion s) {
		map.put("nombre", s.getNombre());
		map.put("fechaFun", extras.getFechaFormateada(s.getFechaFun()));
		map.put("fechaCat", extras.getFechaFormateada(s.getFechaCat()));
		map.put("categoria", s.getCategoria());
		Usuario d = s.getDirectorGrupo();
		if (d == null) {
			map.put("liderSemillero", "sin director registrado");
			map.put("correoILider", "sin director registrado");
			map.put("telefonoLider", "sin director registrado");
			map.put("programaLider", "sin director registrado");
		} else {
			map.put("liderSemillero", d.getNombreCompleto());
			map.put("correoILider", d.getCorreoEst());
			map.put("telefonoLider", d.getTelefono());
			map.put("programaLider", (d.getProgramaId() != null) ? d.getProgramaId().getNombre() : "N/A");
		}
		map.put("n", s.getNombre());
		return map;
	}

	private Map<String, Object> setDatosSem(Map<String, Object> map, Semillero s) {
		map.put("nombreSemillero", s.getNombre());
		map.put("descripcion", s.getDescripcion());
		map.put("fechaFun", extras.getFechaFormateada(s.getFechaFun()));
		map.put("grupoInv", s.getGrupoInvestigacion().getNombre());
		map.put("lineaInv", s.getLineaInvestigacion().getNombre());
		Usuario l = s.getLiderSemillero();
		if (l == null) {
			map.put("liderSemillero", "sin lider registrado");
			map.put("correoILider", "sin lider registrado");
			map.put("telefonoLider", "sin lider registrado");
			map.put("programaLider", "sin lider registrado");
		} else {
			map.put("liderSemillero", l.getNombreCompleto());
			map.put("correoILider", l.getCorreoEst());
			map.put("telefonoLider", l.getTelefono());
			map.put("programaLider", (l.getProgramaId() != null) ? l.getProgramaId().getNombre() : "N/A");
		}
		map.put("n", s.getNombre());
		return map;
	}

	private Map<String, Object> setDatosPrograma(Map<String, Object> map, Programa s) {
		map.put("nombre", s.getNombre());
		Facultad i = s.getFacultadId();
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
		datosSemillero = getDatosRol(datosSemillero, u);
		datosSemillero.put("firmaU", (u.getFirma() == null) ? "sin-firma.png" : u.getFirma().getNombre());
		datosSemillero.put("logo1", "logo_usbbog_1.jpg");
		return datosSemillero;
	}

	private Map<String, Object> getDatosRol(Map<String, Object> datosSemillero, Usuario u) {
		String fac = facultad.findByDecano(u.getCedula());
		if (fac != null) {
			datosSemillero.put("tipoU", "Decano");
			datosSemillero.put("adicionalU", fac);
			return datosSemillero;
		}
		fac = facultad.findByCoord(u.getCedula());
		if (fac != null) {
			datosSemillero.put("tipoU", "Coordinador(a) de investigación");
			datosSemillero.put("adicionalU", fac);
			return datosSemillero;
		}
		List<Programa> programas = programa.findByDirector(u.getCedula());
		if (!programas.isEmpty()) {
			datosSemillero.put("tipoU", "Director(a) de programa");
			fac = String.join(", ", programas.stream().map(Programa::getNombre).collect(Collectors.toList()));
			datosSemillero.put("adicionalU", fac);
			return datosSemillero;
		}
		fac = gi.findByDirector(u.getCedula());
		if (fac != null) {
			datosSemillero.put("tipoU", "Director(a) de grupo de investigación");
			datosSemillero.put("adicionalU", fac);
			return datosSemillero;
		}
		fac = semillero.findByLider(u.getCedula());
		if (fac != null) {
			datosSemillero.put("tipoU", "Lider de semillero");
			datosSemillero.put("adicionalU", fac);
			return datosSemillero;
		}
		datosSemillero.put("tipoU",
				(!u.getTiposUsuario().isEmpty()) ? u.getTiposUsuario().get(0).getNombre() : "Sin dato");
		datosSemillero.put("adicionalU", (u.getProgramaId() != null) ? u.getProgramaId().getNombre() : "Sin dato");
		return datosSemillero;
	}

}