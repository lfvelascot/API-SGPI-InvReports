package co.edu.usbbog.sgpi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usbbog.sgpi.model.AreaConocimiento;
import co.edu.usbbog.sgpi.model.Clase;
import co.edu.usbbog.sgpi.model.Comentario;
import co.edu.usbbog.sgpi.model.Evento;
import co.edu.usbbog.sgpi.model.MacroProyecto;
import co.edu.usbbog.sgpi.model.Materia;
import co.edu.usbbog.sgpi.model.Participaciones;
import co.edu.usbbog.sgpi.model.Participantes;
import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpi.model.TipoProyecto;
import co.edu.usbbog.sgpi.model.TipoUsuario;
import co.edu.usbbog.sgpi.model.Usuario;
import co.edu.usbbog.sgpi.repository.IAreaConocimientoRepository;
import co.edu.usbbog.sgpi.repository.IClaseRepository;
import co.edu.usbbog.sgpi.repository.IComentarioRepository;
import co.edu.usbbog.sgpi.repository.IEventoRepository;
import co.edu.usbbog.sgpi.repository.IMacroProyectoRepository;
import co.edu.usbbog.sgpi.repository.IParticipacionesRepository;
import co.edu.usbbog.sgpi.repository.IParticipantesRepository;
import co.edu.usbbog.sgpi.repository.IProductoRepository;
import co.edu.usbbog.sgpi.repository.IProyectoConvocatoriaRepository;
import co.edu.usbbog.sgpi.repository.IProyectoRepository;
import co.edu.usbbog.sgpi.repository.ITipoProyectoRepository;
import co.edu.usbbog.sgpi.repository.ITipoUsuarioRepository;
import co.edu.usbbog.sgpi.repository.IUsuarioRepository;
import net.minidev.json.JSONObject;

@Service
public class GestionProyectosAulaIntegradorService implements IGestionProyectosAulaIntegradorService {
	@Autowired
	private IProyectoRepository iProyectoRepository;
	@Autowired
	private IClaseRepository iClaseRepository;
	@Autowired
	ITipoUsuarioRepository iTipoUsuarioRepository;
	@Autowired
	private ITipoProyectoRepository iTipoProyectoRepository;
	@Autowired
	private IMacroProyectoRepository iMacroProyectoRepository;
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	@Autowired
	private IParticipantesRepository iParticipantesRepository;
	@Autowired
	private IProductoRepository iProductoRepository;
	@Autowired
	private IComentarioRepository iComentarioRepository;
	@Autowired
	private IEventoRepository iEventoRepository;
	@Autowired
	private IParticipacionesRepository iParticipacionesRepository;
	@Autowired
	private IAreaConocimientoRepository iAreaConocimientoRepository;
	@Autowired
	private IProyectoConvocatoriaRepository iProyectoConvocatoriaRepository;
	@Override
	public List<Proyecto> todosLosProyectos() {
		List<Proyecto> proyecto = iProyectoRepository.findAll();
		if (proyecto.equals(null)) {
			proyecto = new ArrayList<Proyecto>();
		}

		return proyecto;
	}

	@Override
	public List<Proyecto> todosLosProyectosPorClase(String clase) {
		Clase cla = iClaseRepository.getById(Integer.parseInt(clase));
		List<Proyecto> proyectos = cla.getProyectos();
		if (proyectos.equals(null)) {
			proyectos = new ArrayList<Proyecto>();
		}
		return proyectos;
	}

	@Override
	public List<Proyecto> todosLosProyectosPorTipoProyecto(String tipo) {
		TipoProyecto tipoProyecto = iTipoProyectoRepository.getById(tipo);
		List<Proyecto> proyectos = tipoProyecto.getProyectos();
		if (proyectos.equals(null)) {
			proyectos = new ArrayList<Proyecto>();
		}
		return proyectos;
	}

	@Override
	public boolean eliminarProyecto(int id) {
		if (iProyectoRepository.existsById(id)) {
			iProyectoRepository.deleteById(id);
			return true;
		}
		return false;
	}

	/**
	 *
	 */
	@Override
	public boolean crearProyecto(Proyecto proyecto, String tipo, String rol, String clase, String cedula,
			LocalDate fecha, int macro) {
		Clase clas = iClaseRepository.getById(Integer.parseInt(clase));
		if (!iClaseRepository.existsById(clas.getNumero())) {
			return false;
		}
		MacroProyecto macroP = iMacroProyectoRepository.getById(macro);
		TipoProyecto tp = iTipoProyectoRepository.getById(tipo);
		proyecto.setTipoProyecto(tp);
		// MacroProyecto macroProyectp= iMacroProyectoRepository.getById(macro);
		if (macro < 0) {
			proyecto.setMacroProyecto(macroP);
		} else {
			proyecto.setMacroProyecto(null);
		}
		proyecto.setId(iProyectoRepository.save(proyecto).getId());
		iTipoProyectoRepository.save(tp);
		clas.getProyectos().add(proyecto);
		iClaseRepository.save(clas);
		Usuario usu = iUsuarioRepository.getById(cedula);

		Participantes participante = new Participantes(cedula, proyecto.getId(), fecha);
		participante.setRol(rol);
		iParticipantesRepository.save(participante);
		Usuario profesor = clas.getProfesor();
		if (profesor != null) {
			Participantes par = new Participantes(profesor.getCedula(), proyecto.getId(),
					participante.getParticipantesPK().getFechaInicio());
			par.setRol("Lider");
			iParticipantesRepository.save(par);
		}
		return iProyectoRepository.existsById(proyecto.getId());
	}



	@Override
	public List<Producto> todosLosProductos(Proyecto proyecto) {
		List<Producto> productos = proyecto.getProductos();
		if (productos.equals(null)) {
			productos = new ArrayList<Producto>();
		}
		return productos;
	}

	@Override
	public boolean eliminarProducto(int id) {
		Producto producto = iProductoRepository.getById(id);
		if (producto.equals(null)) {
			return false;
		} else {
			iProductoRepository.deleteById(id);
			return true;
		}

	}

	@Override
	public boolean crearProducto(Producto producto) {
		if (iProductoRepository.existsById(producto.getId())) {
			return false;
		} else {
			iProductoRepository.save(producto);
			return iProductoRepository.existsById(producto.getId());
		}
	}

	@Override
	public Proyecto buscarProyecto(int proyectoId) {
		Proyecto proyecto = iProyectoRepository.getById(proyectoId);
		return proyecto;

	}

	@Override
	public Producto buscarProducto(int productoId) {
		Producto producto = iProductoRepository.getById(productoId);
		return producto;
	}


	@Override
	public boolean crearParticipante(Participantes participante, String rol, String cedula, int proyecto) {

		Usuario usu = iUsuarioRepository.getById(cedula);
		Proyecto pro = iProyectoRepository.getById(proyecto);
		List<TipoUsuario> tipos= usu.getTiposUsuario();
		if(pro.getTipoProyecto().getNombre().equals("Semillero")) {
			int i=0;
			TipoUsuario tipo=iTipoUsuarioRepository.getById("Semillerista");
			
			for (Iterator iterator = tipos.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
				if(tipoUsuario.getNombre().equals("Semillerista")) {
					i=i+1;
				}
			}
			if(i>0) {
				
			}else {
				tipo.getUsuarios().add(usu);
				iTipoUsuarioRepository.save(tipo);
				List<ProyectosConvocatoria> proCon= iProyectoConvocatoriaRepository.findAll();
				TipoUsuario tipo2=iTipoUsuarioRepository.getById("Investigador formacion");
				i=0;
				for (Iterator iterator = proCon.iterator(); iterator.hasNext();) {
					ProyectosConvocatoria proyectosConvocatoria = (ProyectosConvocatoria) iterator.next();
					if(proyectosConvocatoria.getProyecto().getId().equals(pro.getId())) {
						for (Iterator iterator1 = tipos.iterator(); iterator1.hasNext();) {
							TipoUsuario tipoUsuario = (TipoUsuario) iterator1.next();
							if(tipoUsuario.getNombre().equals("Investigador formacion ")) {
								i=i+1;
							}
					}
						if(i>0) {
							
						}else{
							tipo2.getUsuarios().add(usu);
							iTipoUsuarioRepository.save(tipo2);
						}
						}
						
				}
				
			}
		}
		
		List<Participantes> par = pro.getParticipantes();
		for (Iterator iterator = par.iterator(); iterator.hasNext();) {
			Participantes participantes = (Participantes) iterator.next();
			if (participantes.getUsuario().getCedula().equals(cedula)) {
				return false;
			}
		}
		participante.setRol(rol);
		iParticipantesRepository.save(participante);
		return iParticipantesRepository.existsById(participante.getParticipantesPK());
	}

	@Override
	public boolean eliminarComentario(int id) {
		Comentario comentario = iComentarioRepository.getById(id);
		if (comentario.equals(null)) {
			return false;
		} else {
			iComentarioRepository.deleteById(id);
			return true;
		}
	}

	@Override
	public boolean crearComentario(Comentario comentario, String cedula) {
		Usuario usu = iUsuarioRepository.getById(cedula);
		List<TipoUsuario> tipo = usu.getTiposUsuario();
		TipoUsuario profesor = iTipoUsuarioRepository.getById("profesor");
		tipo.contains(profesor);
		List<Clase> clases = usu.getClases();
		if (clases != null) {
			if (iComentarioRepository.existsById(comentario.getId())) {
				return false;
			} else {
				iComentarioRepository.save(comentario);
				return iComentarioRepository.existsById(comentario.getId());
			}
		}
		return false;
	}

	@Override
	public List<Comentario> ComentariosPorProducto(int productoid) {
		Producto producto = iProductoRepository.getById(productoid);
		List<Comentario> comentarios = producto.getComentarios();
		if (comentarios.equals(null)) {
			comentarios = new ArrayList<Comentario>();
		}
		return comentarios;
	}

	@Override
	public boolean asignarCalificacion(int comentarioid, double calificacion) {
		Comentario comentario = iComentarioRepository.getById(comentarioid);
		if (comentario != null) {
			comentario.setCalificacion(calificacion);
			iComentarioRepository.save(comentario);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean eliminarCalificacion(int comentarioid) {
		Comentario comentario = iComentarioRepository.getById(comentarioid);
		if (comentario != null) {
			comentario.setCalificacion(null);
			iComentarioRepository.save(comentario);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean asignarProyectosaMaterias(Proyecto proyecto, Materia materia) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean participarEvento(Participaciones participaciones, LocalDate fecha, String reconocimiento) {

		if (participaciones != null) {
			participaciones.setFechaPart(fecha);
			try {
				participaciones.setReconocimientos(reconocimiento);
			} catch (Exception e) {
				participaciones.setReconocimientos(null);
			}
			iParticipacionesRepository.save(participaciones);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean participarEventoExtrerna(Evento evento, int proyecto, LocalDate fecha, String reconocimiento,
			String entidad, String sitio_web, String url_memoria) {
		evento.setEntidad(entidad);
		evento.setSitioWeb(sitio_web);
		evento.setUrlMemoria(url_memoria);
		Evento eve = iEventoRepository.save(evento);
		Participaciones participaciones = new Participaciones(eve.getId(), proyecto);
		participaciones.setFechaPart(fecha);
		participaciones.setReconocimientos(reconocimiento);

		iParticipacionesRepository.save(participaciones);
		return iEventoRepository.existsById(eve.getId());
	}

	@Override
	public List<Participaciones> buscarParticipaciones(int proyecto) {
		Proyecto pro = iProyectoRepository.getById(proyecto);
		List<Participaciones> participaciones = pro.getParticipaciones();
		if (participaciones.equals(null)) {
			participaciones = new ArrayList<Participaciones>();
		}
		return participaciones;
	}

	@Override
	public boolean agregarAntecedente(Proyecto proyecto, Proyecto antecedente) {
		if (antecedente != null && proyecto != null) {
			//if (proyecto.getFechaInicio().isBefore(antecedente.getFechaFin())) {
				if (proyecto.getAntecedentes().isEmpty()) {

					proyecto.setAntecedentes(new ArrayList<Proyecto>());
					proyecto.getAntecedentes().add(antecedente);
					iProyectoRepository.save(proyecto);
				} else {
					if (!proyecto.getAntecedentes().contains(proyecto)) {
						return false;
					} else {
						proyecto.getAntecedentes().add(antecedente);
						iProyectoRepository.save(proyecto);
					}
				}
/*			 else {
				return false;
			}*/
			return !proyecto.getAntecedentes().contains(antecedente);
		} else {
			return false;
		}
	}

	@Override
	public boolean agregarAreaConocimiento(int proyecto, int area) {
		Proyecto pro = iProyectoRepository.getById(proyecto);
		AreaConocimiento are = iAreaConocimientoRepository.getById(area);
		List<AreaConocimiento> areass = pro.getAreasConocimiento();
		for (Iterator iterator = areass.iterator(); iterator.hasNext();) {
			AreaConocimiento areaConocimiento = (AreaConocimiento) iterator.next();
			if (areaConocimiento.getId() == area) {
				return false;
			}

		}
		are.getProyectos().add(pro);
		iAreaConocimientoRepository.save(are);
		return true;
	}

	@Override
	public List<AreaConocimiento> buscarAreasProyecto(int proyecto) {
		Proyecto pro = iProyectoRepository.getById(proyecto);
		List<AreaConocimiento> areas = pro.getAreasConocimiento();
		if (areas.equals(null)) {
			areas = new ArrayList<AreaConocimiento>();
		}
		return areas;
	}

	@Override
	public List<JSONObject> proyectosParticipanteClase(String cedula) {
		List<JSONObject> x = iProyectoRepository.proyectosParticipaClase(cedula);
		return x;
	}

	@Override
	public boolean actualizarProyecto(int id, String titulo, String descripcion, String metodologia,
			String justificacion,String conclusiones,String visualizacion) {

		Proyecto pro = iProyectoRepository.getById(id);
		if (!titulo.equals("")) {
			pro.setTitulo(titulo);
		}
		if (!descripcion.equals("")){
			pro.setDescripcion(descripcion);
		}
		if (!metodologia.equals("")) {
			pro.setMetodologia(metodologia);
		}
		if (!justificacion.equals("")) {
			pro.setJustificacion(justificacion);
		}if (!conclusiones.equals("")) {
			pro.setConclusiones(conclusiones);
		}if(!visualizacion.equals("")) {
			pro.setVisibilidad((short) Integer.parseInt(visualizacion));
		}
		iProyectoRepository.save(pro);
		return iProyectoRepository.existsById(pro.getId());
	}

	@Override
	public List<Participantes> listarParticipantesPorPoryecto(int id) {
		Proyecto pro = iProyectoRepository.getById(id);
		List<Participantes> participante = pro.getParticipantes();
		if (participante.equals(null)) {
			participante = new ArrayList<Participantes>();
		}
		return participante;
	}

	@Override
	public boolean actualizarParticipante(int id, String cedula, LocalDate fechafin) {

		Proyecto pro = iProyectoRepository.getById(id);
		List<Participantes> part = pro.getParticipantes();
		Usuario usu = iUsuarioRepository.getById(cedula);
		for (Iterator iterator = part.iterator(); iterator.hasNext();) {
			Participantes participantes = (Participantes) iterator.next();
			if (participantes.getUsuario().getCedula().equals(usu.getCedula())) {
				participantes.setFechaFin(fechafin);
				participantes.setRol("Inactivo");
				iParticipantesRepository.save(participantes);
				return true;
			}
		}
		return true;
		// iParticipantesRepository.actualizarParticipante(id,cedula,fechafin);
	}

	@Override
	public List<AreaConocimiento> listarAreaConocimiento() {
		List<AreaConocimiento> area = iAreaConocimientoRepository.findAll();
		if (area.equals(null)) {
			area = new ArrayList<AreaConocimiento>();
		}
		return area;
	}

	@Override
	public List<JSONObject> listarEvento() {
		List<JSONObject> x = iEventoRepository.listarEventos();
		return x;
	}

	@Override
	public List<AreaConocimiento> areasConocimientoProyecto(int proyecto) {
		Proyecto pro = iProyectoRepository.getById(proyecto);
		List<AreaConocimiento> area = pro.getAreasConocimiento();
		if (area.equals(null)) {
			area = new ArrayList<AreaConocimiento>();
		}
		return area;
	}

	@Override
	public List<MacroProyecto> macroProyectosAbiertos() {
		List<MacroProyecto> macros = iMacroProyectoRepository.ListMacros();
		return macros;
	}

	@Override
	public boolean eliminarArea(int areaConocimiento, int proyecto) {
		iProyectoRepository.eliminarArea(areaConocimiento, proyecto);
		return true;
	}

	@Override
	public boolean crearMacro(MacroProyecto macro) {
		MacroProyecto macroP = iMacroProyectoRepository.save(macro);
		return iMacroProyectoRepository.existsById(macroP.getId());
	}

	@Override
	public boolean modificarMacro(int macro, String nombre, String detalle) {
		MacroProyecto macroP = iMacroProyectoRepository.getById(macro);
		if(nombre!=null) {
			macroP.setNombre(nombre);
		}
		if(detalle!=null) {
			macroP.setDescripcion(detalle);
		}
		iMacroProyectoRepository.save(macroP);
		return iMacroProyectoRepository.existsById(macroP.getId());
	}

	@Override
	public boolean cerrarMacro(int macroP) {
		MacroProyecto macro = iMacroProyectoRepository.getById(macroP);
		macro.setEstado("Finalizado");
		macro.setFechaFin(LocalDate.now());
		iMacroProyectoRepository.save(macro);
		return iMacroProyectoRepository.existsById(macro.getId());
	}

	@Override
	public List<JSONObject> tusProyectosConvocatoria(int convocatoria,int id) {
		List<JSONObject> x = iProyectoRepository.tusProyectosConvocatoria(convocatoria,id);
		return x;
	}

	@Override
	public JSONObject detallesProyecto(int proyectoId) {
		JSONObject proyecto=iProyectoRepository.buscarProyecto(proyectoId);
		return proyecto;
	}

	@Override
	public List<JSONObject> proyectosConvocatoriaUsuario(String cedula) {
		List<JSONObject> x = iProyectoRepository.proyectosConvocatoriaUsuario(cedula);
		return x;
	
	}

}