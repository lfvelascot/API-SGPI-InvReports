package co.edu.usbbog.sgpireports.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.LineaInvestigacion;
import co.edu.usbbog.sgpireports.model.MiembrosSemillero;
import co.edu.usbbog.sgpireports.model.Participantes;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.ProyectoR;
import co.edu.usbbog.sgpireports.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.repository.IConvocatoriaRepository;
import co.edu.usbbog.sgpireports.repository.IGrupoInvestigacionRepository;
import co.edu.usbbog.sgpireports.repository.IParticipacionesRepository;
import co.edu.usbbog.sgpireports.repository.IParticipantesRepository;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;
import co.edu.usbbog.sgpireports.repository.ISemilleroRepository;
import co.edu.usbbog.sgpireports.repository.IUsuarioRepository;
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
	private ISemilleroRepository semillero;
	@Autowired
	private IProyectoRepository proyecto;
	@Autowired
	private IGrupoInvestigacionRepository gi;
	@Autowired
	private IParticipantesRepository pa;
	@Autowired
	private IParticipacionesRepository pe;
	@Autowired
	private IConvocatoriaRepository c;
	private final Path rootReportes = Paths.get("reportes");
	
	@Override
	public List<MiembrosSemillero> getMiembrosSemillero(int cc) {
		List<Usuario> aux = usuarios.getMiembrosSemillero(cc);
		List<MiembrosSemillero> salida = new ArrayList<>();
		for(Usuario u: aux) {
			salida.add(new MiembrosSemillero(u.getCodUniversitario().toString(),
					u.getNombreCompleto(),
					u.getTelefono(),
					u.getCorreoEst(),
					u.getProgramaId().getNombre()));
		}
		return salida;
	}
	@Override
	public String crearReporteSemillero(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reporteSemillero.jrxml"));
		Map<String, Object> datosSemillero = getDatosSemillero(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero,new JREmptyDataSource());
		String nombre = "ReporteSemillero-"+datosSemillero.get("nombreSemillero").toString()+".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		nombre = "ReporteSemillero-"+datosSemillero.get("nombreSemillero").toString()+".html";
		salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToHtmlFile(p, salida);
		return salida;
	}
	
	public String crearReporteIntegrantesSemillero(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reporteSemilleroIntegrantes.jrxml"));
		Map<String, Object> datosSemillero = getDatosSemilleroIntegrantes(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero,new JREmptyDataSource());
		String nombre = "ReporteSemilleroIntegrantes-"+datosSemillero.get("nombreSemillero").toString()+".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		nombre = "ReporteSemilleroIntegrantes-"+datosSemillero.get("nombreSemillero").toString()+".html";
		salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToHtmlFile(p, salida);
		return salida;
	}
	
	public String crearReporteIntegrantesGI(int cc, String usuario) throws JRException, IOException {
		JasperReport reporte = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reporteGIIntegrantes.jrxml"));
		Map<String, Object> datosSemillero = getDatosGIIntegrantes(cc);
		datosSemillero = setDatosCreador(datosSemillero, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosSemillero,new JREmptyDataSource());
		String nombre = "ReporteGIIntegrantes-"+datosSemillero.get("nombre").toString()+".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		nombre = "ReporteGIIntegrantes-"+datosSemillero.get("nombre").toString()+".html";
		salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToHtmlFile(p, salida);
		return salida;
	}
	
	
	private Map<String, Object> getDatosGIIntegrantes(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrupoInvestigacion s = gi.getById(cc);
		int x = getProyectosFinalizadosGI(s), y = getProyectosGI(s);
		var aux1 = getSemillerosGI(s.getSemilleros());
		map.put("nombre", s.getNombre());
		map.put("fechaFun", s.getFechaFun().toString());
		map.put("fechaCat", s.getFechaCat().toString());
		map.put("categoria", s.getCategoria());
		if(s.getDirectorGrupo() == null) {
			map.put("liderSemillero", "sin director registrado");
			map.put("correoILider", "sin director registrado");
			map.put("telefonoLider", "sin director registrado");
			map.put("programaLider", "sin director registrado");
		} else {
			map.put("liderSemillero", s.getDirectorGrupo().getNombreCompleto());
			map.put("correoILider", s.getDirectorGrupo().getCorreoEst());
			if(s.getDirectorGrupo().getTelefono() == null) {
				map.put("telefonoLider", "sin dato");
			} else {
				map.put("telefonoLider", s.getDirectorGrupo().getTelefono());
			}
			map.put("programaLider", s.getDirectorGrupo().getProgramaId().getNombre());
		}
		map.put("datasource2", aux1);
		map.put("datasource3", getLideresSemillerosDetallado(s));
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyectos finalizados");
		map.put("TotalProyectosActivos", String.valueOf(y - x) + " proyectos finalizados");
		map.put("totalMiembros", String.valueOf(aux1.size())+" semilleros");
		return map;
	}
	private Map<String, Object> getDatosSemilleroIntegrantes(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Semillero s = semillero.getById(cc);
		var aux1 = getMiembrosSemillero(cc);
		var aux2 = getMiembrosSemilleroExtra(cc);
		int x = numProyectosFinalizados(s.getProyectos());
		map.put("nombreSemillero", s.getNombre());
		map.put("descripcion", s.getDescripcion());
		map.put("fechaFun", s.getFechaFun().toString());
		map.put("grupoInv", s.getGrupoInvestigacion().getNombre());
		if(s.getLiderSemillero() == null) {
			map.put("liderSemillero", "sin lider registrado");
			map.put("correoILider", "sin lider registrado");
			map.put("telefonoLider", "sin lider registrado");
			map.put("programaLider", "sin lider registrado");
		} else {
			map.put("liderSemillero", s.getLiderSemillero().getNombreCompleto());
			map.put("correoILider", s.getLiderSemillero().getCorreoEst());
			map.put("telefonoLider", s.getLiderSemillero().getTelefono());
			map.put("programaLider", s.getLiderSemillero().getProgramaId().getNombre());
		}
		map.put("lineaInv", s.getLineaInvestigacion().getNombre());
		map.put("datasource1", aux1);
		map.put("datasource2", aux2);
		map.put("totalMiembros", String.valueOf(aux1.size())+" miembros");
		return map;
	}

	private List<MiembrosExtra> getMiembrosSemilleroExtra(int cc) {
		List<Usuario> aux = usuarios.getMiembrosSemillero(cc);
		List<MiembrosExtra> salida = new ArrayList<>();
		for(Usuario u: aux) {
			salida.add(new MiembrosExtra(
					u.getTiposUsuario().get(0).getNombre(),
					u.getTiposUsuario().get(0).getNombre(),
					String.valueOf(pa.contProyectosPorUsuario(u.getCedula())),
					String.valueOf(pe.countParticipacionesPorUsuario(u.getCedula())),
					String.valueOf(c.CountParticpacionesEnConvocatorias(u.getCedula()))));
		}
		return salida;
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
	private int numProyectosFinalizados(List<Proyecto> aux2) {
		int i = 0;
		for(Proyecto t : aux2) {
			if(t.getFechaFin() != null) {
				i += 1;
			}
		}
		return i;
	}
	private List<ProyectosConvocatoriaR> getProyectosConvAbiertas(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ProyectosConvocatoriaR> salida = new ArrayList<>();
		for(Proyecto p: aux) {
			List <ProyectosConvocatoria> aux2 = p.getProyectosConvocatoria();
			if(!aux.isEmpty()) {
				for(ProyectosConvocatoria pc : aux2) {
					String fechaFin = null;
					if (pc.getConvocatoria().getFechaFinal() != null) {
						fechaFin = pc.getConvocatoria().getFechaFinal().toString();
					}
					salida.add(new ProyectosConvocatoriaR(p.getTitulo(),
							pc.getConvocatoria().getNombreConvocatoria(),
							pc.getConvocatoria().getFechaInicio().toString(),
							fechaFin,
							pc.getConvocatoria().getEntidad(),
							p.getSemillero().getNombre(),
							pc.getIdProyecto()));
				}
			}
		}
		return salida;
	}
	private List<TipoPEstado> getProyectosSemilleroPorEstado(int cc) {
		List<String> prueba = proyecto.getEstadosSemillero(cc);
		List<TipoPEstado> salida = new ArrayList<>();
		for(String i : prueba) {
			salida.add(new TipoPEstado(i,proyecto.findByEstadoS(i,cc).size()));
		}
		return salida;
	}
	
	private List<TipoPEstado> getProyectosSemilleroPorTipo(int cc) {
		List<String> prueba = proyecto.getTiposSemillero(cc);
		List<TipoPEstado> salida = new ArrayList<>();
		for(String i : prueba) {
			salida.add(new TipoPEstado(i,proyecto.findByTipoProyectoS(i,cc).size()));
		}
		return salida;
	}
	private List<ProyectoR> getProyectosSemillero(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ProyectoR> salida = new ArrayList<>();
		for(Proyecto p: aux) {
			salida.add(new ProyectoR(
					p.getTitulo(),
					p.getEstado(), 
					p.getTipoProyecto().getNombre(),
					p.getDescripcion(),
					p.getFechaInicio().toString(),
					p.getFechaFin(),
					p.getMetodologia(),
					"",
					p.getSemillero().getNombre()));
		}
		return salida;
	}
	private List<ParticipantesR> getIntegrantes(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ParticipantesR> salida = new ArrayList<>();
		for(Proyecto p: aux) {
			List<Participantes> participantes = p.getParticipantes();
			for(Participantes pa : participantes) {
				String fin = null;
				if(pa.getFechaFin() != null) {
					fin = pa.getFechaFin().toString();
				}
				salida.add(new ParticipantesR(p.getTitulo(), pa.getUsuario().getNombreCompleto(),
						pa.getRol(),
						pa.getParticipantesPK().getFechaInicio().toString(),
						fin,
						p.getSemillero().getNombre()));
			}
		}
		return salida;
	}
	
	@Override
	public Map<String, Object> getDatosSemillero(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Semillero s = semillero.getById(cc);
		var aux1 = getMiembrosSemillero(cc);
		var aux2 = getProyectosSemilleroPorEstado(cc);
		var aux3 = getProyectosSemillero(cc);
		var aux4 = getIntegrantes(cc);
		var aux5 = getProyectosConvAbiertas(cc);
		int x = numProyectosFinalizados(s.getProyectos());
		map.put("nombreSemillero", s.getNombre());
		map.put("descripcion", s.getDescripcion());
		map.put("fechaFun", s.getFechaFun().toString());
		map.put("grupoInv", s.getGrupoInvestigacion().getNombre());
		if(s.getLiderSemillero() == null) {
			map.put("liderSemillero", "sin lider registrado");
			map.put("correoILider", "sin lider registrado");
			map.put("telefonoLider", "sin lider registrado");
			map.put("programaLider", "sin lider registrado");
		} else {
			map.put("liderSemillero", s.getLiderSemillero().getNombreCompleto());
			map.put("correoILider", s.getLiderSemillero().getCorreoEst());
			map.put("telefonoLider", s.getLiderSemillero().getTelefono());
			map.put("programaLider", s.getLiderSemillero().getProgramaId().getNombre());
		}
		map.put("lineaInv", s.getLineaInvestigacion().getNombre());
		map.put("datasource1", aux1);
		map.put("datasource2", aux3);
		map.put("datasource3", aux2);
		map.put("datasource4", getProyectosSemilleroPorTipo(cc));
		map.put("datasource5", aux4);
		map.put("datasource6", aux5);
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyectos finalizados");
		map.put("TotalProyectosActivos", String.valueOf(s.getProyectos().size() - x) + " proyectos finalizados");
		map.put("totalMiembros", String.valueOf(aux1.size())+" miembros");
		return map;
	}
	

	
	@Override
	public String crearReporGI(int cc, String usuario) throws FileNotFoundException, JRException {
		JasperReport reporte = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reporteGI.jrxml"));
		Map<String, Object> datosGI = getDatosGI(cc);
		datosGI = setDatosCreador(datosGI, usuario);
		JasperPrint p = JasperFillManager.fillReport(reporte, datosGI,new JREmptyDataSource());
		String nombre = "ReporteGI-"+datosGI.get("nombre").toString()+".pdf";
		String salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToPdfFile(p, salida);
		nombre = "ReporteGI-"+datosGI.get("nombre").toString()+".html";
		salida = this.rootReportes.resolve(nombre).toString();
		JasperExportManager.exportReportToHtmlFile(p, salida);
		return salida;
	}
	
	private Map<String, Object> getDatosGI(int cc) {
		Map<String, Object> map = new HashMap<String, Object>();
		GrupoInvestigacion s = gi.getById(cc);
		int x = getProyectosFinalizadosGI(s), y = getProyectosGI(s);
		var aux1 = getSemillerosGI(s.getSemilleros());
		map.put("nombre", s.getNombre());
		map.put("fechaFun", s.getFechaFun().toString());
		map.put("fechaCat", s.getFechaCat().toString());
		map.put("categoria", s.getCategoria());
		if(s.getDirectorGrupo() == null) {
			map.put("liderSemillero", "sin director registrado");
			map.put("correoILider", "sin director registrado");
			map.put("telefonoLider", "sin director registrado");
			map.put("programaLider", "sin director registrado");
		} else {
			map.put("liderSemillero", s.getDirectorGrupo().getNombreCompleto());
			map.put("correoILider", s.getDirectorGrupo().getCorreoEst());
			if(s.getDirectorGrupo().getTelefono() == null) {
				map.put("telefonoLider", "sin dato");
			} else {
				map.put("telefonoLider", s.getDirectorGrupo().getTelefono());
			}
			map.put("programaLider", s.getDirectorGrupo().getProgramaId().getNombre());
		}
		map.put("datasource1", getLineasInvGI(s));
		map.put("datasource2", aux1);
		map.put("datasource3", getLideresSemillerosDetallado(s));
		map.put("totalProyectosFinalizados", String.valueOf(x) + " proyectos finalizados");
		map.put("TotalProyectosActivos", String.valueOf(y - x) + " proyectos finalizados");
		map.put("totalMiembros", String.valueOf(aux1.size())+" semilleros");
		return map;
	}
	private List<MiembrosSemillero> getLideresSemillerosDetallado(GrupoInvestigacion s) {
		List<Semillero> aux = s.getSemilleros();
		List<MiembrosSemillero> salida = new ArrayList<>();
		for(Semillero p: aux) {
			Usuario u = p.getLiderSemillero();
			if(u != null){
				MiembrosSemillero m = new MiembrosSemillero(u.getCodUniversitario().toString(),
						u.getNombreCompleto(),
						u.getTelefono(),
						u.getCorreoEst(),
						u.getProgramaId().getNombre());
				m.setSemillero(p.getNombre());
				salida.add(m);
			}
		}
		return salida;
	}
	private List<LineasGI> getLineasInvGI(GrupoInvestigacion s) {
		List<LineasGI> salida = new ArrayList<>();
		for(LineaInvestigacion l : s.getLineasInvestigacion()) {
			String fin = null;
			if(l.getFecha() != null) {
				fin = l.getFecha().toString();
			}
			salida.add(new LineasGI(l.getNombre(), l.getDescripcion(), fin));
		}
		return salida;
	}
	private int getProyectosFinalizadosGI(GrupoInvestigacion s) {
		int i = 0;
		for(Semillero p : s.getSemilleros()) {
				i += numProyectosFinalizados(p.getProyectos());
		}
		return i;
	}
	
	private int getProyectosGI(GrupoInvestigacion s) {
		int i = 0;
		for(Semillero p : s.getSemilleros()) {
				i += p.getProyectos().size();
		}
		return i;
	}
	
	private List<SemillerosR> getSemillerosGI(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			String lider = "sin lider registrado";
			if(s.getLiderSemillero() != null) {
				lider = s.getLiderSemillero().getNombreCompleto();
			}
			SemillerosR x = new SemillerosR(s.getNombre(),
					s.getFechaFun().toString(), 
					lider,
					s.getLineaInvestigacion().getNombre(),
					getMiembrosSemillero(s.getId()).size());
			x.setTotalEventos(pe.countParticpacionesSemillero(s.getId()));
			x.setTotalConvocatorias(c.countParticipacionesSemillero(s.getId()));
			int i = numProyectosFinalizados(s.getProyectos());
			x.setProyectosActivos(s.getProyectos().size()-i);
			x.setProyectosFinalizados(i);
			x.setTotalProyectos(s.getProyectos().size());
			salida.add(x);
		}
		return salida;
	}
}