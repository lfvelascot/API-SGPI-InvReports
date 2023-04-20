package co.edu.usbbog.sgpi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usbbog.sgpi.model.AreaConocimiento;
import co.edu.usbbog.sgpi.model.Clase;
import co.edu.usbbog.sgpi.model.Comentario;
import co.edu.usbbog.sgpi.model.Compra;
import co.edu.usbbog.sgpi.model.MacroProyecto;
import co.edu.usbbog.sgpi.model.Participaciones;
import co.edu.usbbog.sgpi.model.Participantes;
import co.edu.usbbog.sgpi.model.Presupuesto;
import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpi.model.Semillero;
import co.edu.usbbog.sgpi.model.TipoProyecto;
import co.edu.usbbog.sgpi.model.TipoUsuario;
import co.edu.usbbog.sgpi.model.Usuario;
import co.edu.usbbog.sgpi.repository.IAreaConocimientoRepository;
import co.edu.usbbog.sgpi.repository.IComentarioRepository;
import co.edu.usbbog.sgpi.repository.ICompraRepository;
import co.edu.usbbog.sgpi.repository.IMacroProyectoRepository;
import co.edu.usbbog.sgpi.repository.IParticipacionesRepository;
import co.edu.usbbog.sgpi.repository.IParticipantesRepository;
import co.edu.usbbog.sgpi.repository.IPresupuestoRepository;
import co.edu.usbbog.sgpi.repository.IProductoRepository;
import co.edu.usbbog.sgpi.repository.IProyectoConvocatoriaRepository;
import co.edu.usbbog.sgpi.repository.IProyectoRepository;
import co.edu.usbbog.sgpi.repository.ISemilleroRepository;
import co.edu.usbbog.sgpi.repository.ITipoProyectoRepository;
import co.edu.usbbog.sgpi.repository.ITipoUsuarioRepository;
import co.edu.usbbog.sgpi.repository.IUsuarioRepository;
import net.minidev.json.JSONObject;

@Service
public class GestionProyectosInvestigacionService implements IGestionProyectosInvestigacionService {
	@Autowired
	private IProyectoRepository iProyectoRepository;
	@Autowired
	private ITipoProyectoRepository iTipoProyectoRepository;
	@Autowired
	private ISemilleroRepository iSemilleroRepository;
	@Autowired
	private IParticipantesRepository iParticipantesRepository;
	@Autowired
	private IProductoRepository iProductoRepository;
	@Autowired
	private IComentarioRepository iComentarioRepository;
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	@Autowired
	private ITipoUsuarioRepository iTipoUsuarioRepository;
	@Autowired
	private IPresupuestoRepository iPresupuestoRepository;
	@Autowired
	private ICompraRepository iCompraRepository;
	@Autowired
	private IParticipacionesRepository iParticipacionesRepository;
	@Autowired
	private IAreaConocimientoRepository iAreaConocimientoRepository;
	@Autowired
	private IProyectoConvocatoriaRepository iProyectoConvocatoriaRepository;
	@Autowired
	private IMacroProyectoRepository iMacroProyectoRepository;

	@Override
	public List<Proyecto> todosLosProyectosSemillero() {
		TipoProyecto tipoPro = iTipoProyectoRepository.getById("semillero");
		List<Proyecto> proyectos = tipoPro.getProyectos();
		if (proyectos.equals(null)) {
			proyectos = new ArrayList<Proyecto>();
		}
		return proyectos;
	}

	@Override
	public List<Proyecto> todosLosProyectosPorSemillero(int semilleroId) {
		Semillero semillero = iSemilleroRepository.getById(semilleroId);
		if (semillero.equals(null)) {
			return null;
		}
		List<Proyecto> proyectos = semillero.getProyectos();
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

	@Override
	public boolean asignarTipoProyecto(Proyecto proyecto, TipoProyecto investigacion) {

		return false;
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
	public boolean eliminarProductos(int id) {
		if (iProyectoRepository.existsById(id)) {
			iProyectoRepository.deleteById(id);
			return true;
		}
		return false;
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
	public List<Participantes> todosLosParticipantesPorProyecto(Proyecto proyecto) {
		List<Participantes> participantes = proyecto.getParticipantes();
		if (participantes.equals(null)) {
			participantes = new ArrayList<Participantes>();
		}
		return participantes;

	}

	@Override
	public boolean crearParticipante(Participantes participante, String rol) {
		participante.setRol(rol);
		iParticipantesRepository.save(participante);
		return iParticipantesRepository.existsById(participante.getParticipantesPK());
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
	public List<Presupuesto> PresupuestoPorProyecto(Proyecto proyecto) {
		List<Presupuesto> presupuestos = proyecto.getPresupuestos();
		if (presupuestos.equals(null)) {
			presupuestos = new ArrayList<Presupuesto>();
		}
		return presupuestos;
	}

	@Override
	public boolean eliminarPresupuesto(int id) {
		Presupuesto presupuesto = iPresupuestoRepository.getById(id);
		if (presupuesto != null) {
			iPresupuestoRepository.delete(presupuesto);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean crearPresupuesto(Presupuesto presupuesto, String cedula) {
		Usuario usu = iUsuarioRepository.getById(cedula);
		List<TipoUsuario> tipo = usu.getTiposUsuario();
		TipoUsuario administrativo = iTipoUsuarioRepository.getById("Administrativo");
		if (administrativo != null) {
			if (iPresupuestoRepository.existsById(presupuesto.getId())) {
				return false;
			} else {
				iPresupuestoRepository.save(presupuesto);
				return iPresupuestoRepository.existsById(presupuesto.getId());
			}
		} else {
			return false;
		}
	}

	@Override
	public List<Compra> CompraPorPresupuesto(Presupuesto presupuesto) {
		List<Compra> compras = presupuesto.getCompras();
		if (compras.equals(null)) {
			compras = new ArrayList<Compra>();
		}
		return compras;

	}

	@Override
	public boolean eliminarCompra(int id) {
		Compra compra = iCompraRepository.getById(id);
		if (compra != null) {
			iCompraRepository.delete(compra);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean crearCompra(Compra compra) {
		if (iCompraRepository.existsById(compra.getId())) {
			return false;
		} else {
			iCompraRepository.save(compra);
		}
		return iCompraRepository.existsById(compra.getId());
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
			//if (proyecto.getFechaInicio().isAfter(antecedente.getFechaFin())) {
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
			/*} else {
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
		if (are.equals(null) || pro.equals(null)) {
			return false;
		} else {
			are.getProyectos().add(pro);
			iAreaConocimientoRepository.save(are);
			return true;
		}
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
	public boolean participarConvocatoria(ProyectosConvocatoria proyectosConvocatoria, String estado) {
		if (iProyectoConvocatoriaRepository.existsById(proyectosConvocatoria.getProyectosConvocatoriaPK())) {
			return false;
		} else {
			proyectosConvocatoria.setIdProyecto(estado);
			iProyectoConvocatoriaRepository.save(proyectosConvocatoria);
			return iProyectoConvocatoriaRepository.existsById(proyectosConvocatoria.getProyectosConvocatoriaPK());
		}
	}

	@Override
	public List<JSONObject> todosLosProyectosUsuarioSemillero(String cedula) {
		Usuario usu=iUsuarioRepository.getById(cedula); 
		
		List<JSONObject> proyectos = iProyectoRepository.proyectosParticipaSemillero(usu.getSemilleroId().getId());
		return proyectos;
		
	}

	@Override
	public List<JSONObject> proyectosParticipanteSemillero(String cedula) {
		Usuario usu=iUsuarioRepository.getById(cedula); 
		
		
			List<JSONObject> proyectos = iProyectoRepository.proyectosParticipaSemillero(usu.getSemilleroId().getId());
			return proyectos;
		
		
		
	}

	@Override
	public boolean verificarSemillero(String cedula) {
		Usuario usu = iUsuarioRepository.getById(cedula);
		int semillero=0;
		try {
		 semillero=usu.getSemilleroId().getId();
		} catch (Exception e) {
			
		}
		
		if (semillero==0) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public List<Proyecto> buscarAntecedentes(int proyecto) {
		Proyecto pro = iProyectoRepository.getById(proyecto);
		List<Proyecto> proyectos = pro.getAntecedentes();
		if (proyectos.equals(null)) {
			proyectos = new ArrayList<Proyecto>();
		}
		return proyectos;
	}

	@Override
	public boolean crearProyecto(Proyecto proyecto, String tipo, String rol, String clase, String cedula,
			LocalDate fecha, String semillero, int macro) {
		Semillero se = iSemilleroRepository.getById(Integer.parseInt(semillero));
		if (!iSemilleroRepository.existsById(se.getId())) {
			return false;
		}
		MacroProyecto macroP = iMacroProyectoRepository.getById(macro);
		if (macro < 0) {
			proyecto.setMacroProyecto(macroP);
		} else {
			proyecto.setMacroProyecto(null);
		}
		TipoProyecto tp = iTipoProyectoRepository.getById(tipo);
		proyecto.setTipoProyecto(tp);
		proyecto.setId(iProyectoRepository.save(proyecto).getId());
		iTipoProyectoRepository.save(tp);
		// se.getProyectos().add(proyecto);
		proyecto.setSemillero(se);
		Usuario usu = iUsuarioRepository.getById(cedula);
		proyecto.setId(iProyectoRepository.save(proyecto).getId());
		Participantes participante = new Participantes(cedula, proyecto.getId(), fecha);
		participante.setRol(rol);
		iParticipantesRepository.save(participante);
		Usuario profesor = se.getLiderSemillero();
		if (profesor != null) {
			Participantes par = new Participantes(profesor.getCedula(), proyecto.getId(),
					participante.getParticipantesPK().getFechaInicio());
			par.setRol("Lider");
			iParticipantesRepository.save(par);
		}
		return iProyectoRepository.existsById(proyecto.getId());
	}

	@Override
	public List<JSONObject> tusProyectosConvocatoria(String cedula) {
		List<JSONObject> x = iProyectoRepository.tusProyectoConvocatoria(cedula);
		return x;
	}

	@Override
	public List<JSONObject> tusProyectoSemillero(String cedula) {
		List<JSONObject> x = iProyectoRepository.tusProyectoSemillero(cedula);
		return x;
	}

	@Override
	public boolean darAval(ProyectosConvocatoria proyectoConvocatoria, String estado,String reconocimiento) {
		proyectoConvocatoria.setIdProyecto(estado);
		proyectoConvocatoria = iProyectoConvocatoriaRepository.save(proyectoConvocatoria);
		Proyecto pro = iProyectoRepository
				.getById(proyectoConvocatoria.getProyectosConvocatoriaPK().getProyectos());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (estado.equals("Validacion 1")) {			
			List<Participantes> parti = pro.getParticipantes();
			TipoUsuario tipo1 = iTipoUsuarioRepository.getById("Investigador formacion");
			TipoUsuario tipo2 = iTipoUsuarioRepository.getById("Docente investigador");
			for (Iterator iterator = parti.iterator(); iterator.hasNext();) {
				Participantes participantes = (Participantes) iterator.next();
				Usuario usu = iUsuarioRepository.getById(participantes.getUsuario().getCedula());
				List<TipoUsuario> tipoUsuario = usu.getTiposUsuario();
				
				if (participantes.getRol().equals("Participante")) {
					int i=0;
					for (Iterator iterator2 = tipoUsuario.iterator(); iterator2.hasNext();) {
						TipoUsuario tipoUsuario2 = (TipoUsuario) iterator2.next();
						if (tipoUsuario2.getNombre().contains("Investigador formacio")) {
							i=i+1;
						}
					}
					if(i>0) {
					} else {
						tipo1.getUsuarios().add(usu);
						iTipoUsuarioRepository.save(tipo1);
					}
				} else if (participantes.getRol().equals("Lider")) {
					int i=0;
					for (Iterator iterator2 = tipoUsuario.iterator(); iterator2.hasNext();) {
						TipoUsuario tipoUsuario2 = (TipoUsuario) iterator2.next();
						if (tipoUsuario2.getNombre().contains("Docente investigador")) {
							i=i+1;
						} 
					}
					if(i>0) {
					}
					else {
						tipo2.getUsuarios().add(usu);
						iTipoUsuarioRepository.save(tipo2);
					}
				}
			}
		}else if(estado.equals("Desarrollo")) {
			pro.setEstado(estado);
			iProyectoRepository.save(pro);
		}else if (estado.equals("Finalizado")) {
			pro.setRetroalimentacionFinal(reconocimiento);
			pro.setEstado(estado);
			pro.setFechaFin(LocalDate.parse(dtf.format(LocalDateTime.now())));
			iProyectoRepository.save(pro);
		}
		return iProyectoConvocatoriaRepository.existsById(proyectoConvocatoria.getProyectosConvocatoriaPK());
	}

	@Override
	public List<JSONObject> paticipacionesConvocatoria(String proyecto) {
		List<JSONObject> x = iProyectoRepository.paticipacionesConvocatoria(Integer.parseInt(proyecto));
		return x;
	}

	@Override
	public List<JSONObject> proyectosGrado() {
		List<JSONObject> x = iProyectoRepository.proyectosGrado();
		return x;
	}

	@Override
	public List<JSONObject> proyectosPropuestaClase(int curso) {
		List<JSONObject> x = iProyectoRepository.proyectosPropuestaClase(curso);
		return x;
	}

	@Override
	public List<JSONObject> proyectosDesarrolloClase(int curso) {
		List<JSONObject> x = iProyectoRepository.proyectosDesarrolloClase(curso);
		return x;
	}

	@Override
	public List<JSONObject> proyectosFinalizadosClase(int curso) {
		List<JSONObject> x = iProyectoRepository.proyectosFinalizadosClase(curso);

		return x;
	}

	@Override
	public boolean evaluar(int proyecto, String estado,String reconocimiento) {
		Proyecto pro = iProyectoRepository.getById(proyecto);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(estado.equals("Finalizado")) {
				pro.setFechaFin(LocalDate.parse(dtf.format(LocalDateTime.now())));
			pro.setRetroalimentacionFinal(reconocimiento);
			pro.setEstado(estado);
			iProyectoRepository.save(pro);
		}else {
			pro.setEstado(estado);
			iProyectoRepository.save(pro);
		}
		return iProyectoRepository.existsById(pro.getId()); // TODO Auto-generated method stub
	}

	

	@Override
	public List<JSONObject> trabajoGradoInicio(String cedula) {
		List<JSONObject> x = iProyectoRepository.trabajoGradoInicio(cedula);

		return x;
	}

	@Override
	public List<JSONObject> trabajoGradoDesarrollo(String cedula) {
		List<JSONObject> x = iProyectoRepository.trabajoGradoDesarrollo(cedula);

		return x;
	}

	@Override
	public List<JSONObject> trabajoGradoJurado(String cedula) {
		List<JSONObject> x = iProyectoRepository.trabajoGradoJurado(cedula);

		return x;
	}

	@Override
	public List<JSONObject> trabajoGradoFinalizados(String cedula) {
		List<JSONObject> x = iProyectoRepository.trabajoGradoFinalizados(cedula);

		return x;
	}

	@Override
	public List<JSONObject> trabajoGradoRechazados(String cedula) {
		List<JSONObject> x = iProyectoRepository.trabajoGradoRechazados(cedula);

		return x;
	}

	@Override
	public boolean cambioEstadoTrabajoGrado(int proyecto, String estado,String reconocimiento) {
		Proyecto pro=iProyectoRepository.getById(proyecto);
		boolean est=false;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(estado.equals("Finalizado")) {
		pro.setFechaFin(LocalDate.parse(dtf.format(LocalDateTime.now())));
		pro.setEstado(estado);
		pro.setRetroalimentacionFinal(reconocimiento);
		iProyectoRepository.save(pro);
		est=true;
		}else {
			pro.setEstado(estado);
			iProyectoRepository.save(pro);
			est=true;
		}
		return est;
	}

	@Override
	public boolean salirSemillero(String cedula, int semillero) {
		Usuario usu=iUsuarioRepository.getById(cedula);
		Semillero semi=iSemilleroRepository.getById(semillero);
		if(semi.getId()==usu.getSemilleroId().getId()) {
		int i=0;
		usu.setSemilleroId(null);
		List<TipoUsuario> tipo=usu.getTiposUsuario();
		
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if(tipoUsuario.getNombre().equals("Semillerista")) {
				iUsuarioRepository.quitarRol(cedula);	
			}
		}
		
		iUsuarioRepository.save(usu);
		}
		return iUsuarioRepository.existsById(cedula);
	}

	@Override
	public List<JSONObject> proyectosFinalizados() {
		List<JSONObject> x = iProyectoRepository.proyectosFinalizados();
		return x;
	}
}
