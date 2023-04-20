package co.edu.usbbog.sgpi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpi.model.Clase;
import co.edu.usbbog.sgpi.model.Comentario;
import co.edu.usbbog.sgpi.model.Participantes;
import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.TipoProyecto;
import co.edu.usbbog.sgpi.model.TipoUsuario;
import co.edu.usbbog.sgpi.model.Usuario;
import co.edu.usbbog.sgpi.repository.IClaseRepository;
import co.edu.usbbog.sgpi.repository.IComentarioRepository;
import co.edu.usbbog.sgpi.repository.IParticipantesRepository;
import co.edu.usbbog.sgpi.repository.IProductoRepository;
import co.edu.usbbog.sgpi.repository.IProyectoRepository;
import co.edu.usbbog.sgpi.repository.ITipoProyectoRepository;
import co.edu.usbbog.sgpi.repository.ITipoUsuarioRepository;
import co.edu.usbbog.sgpi.repository.IUsuarioRepository;
import net.minidev.json.JSONObject;
@Service
public class GestionTrabajosGradoService implements IGestionTrabajosGradoService {
	@Autowired 
	private ITipoProyectoRepository iTipoProyectoRepository;
	@Autowired
	private IProyectoRepository iProyectoRepository;
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
	private IClaseRepository iClaseRepository;;
	private static Logger logger = LoggerFactory.getLogger(BibliotecaService.class);
	@Override
	public List<Proyecto> todosLosProyectos(String tipo) {
		TipoProyecto tipoPro=iTipoProyectoRepository.getById("Grado");
		List<Proyecto> proyectos = tipoPro.getProyectos();
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
	public boolean crearProyecto(Proyecto proyecto,String tipo,String rol,String clase,String cedula,LocalDate fecha) {
		Clase clas = iClaseRepository.getById(Integer.parseInt(clase));
		if (!iClaseRepository.existsById(clas.getNumero())) {
			return false;
		}
		TipoProyecto tp = iTipoProyectoRepository.getById(tipo);
		proyecto.setTipoProyecto(tp);
		proyecto.setId(iProyectoRepository.save(proyecto).getId());
		iTipoProyectoRepository.save(tp);
		clas.getProyectos().add(proyecto);
		iClaseRepository.save(clas);
		Usuario usu= iUsuarioRepository.getById(cedula);
		Participantes participante = new Participantes(cedula,proyecto.getId(), fecha);
		participante.setRol(rol);
		iParticipantesRepository.save(participante);
		Usuario profesor= clas.getProfesor();
		if(profesor!=null) {
			Participantes par=new Participantes(profesor.getCedula(), proyecto.getId(), participante.getParticipantesPK().getFechaInicio());
			par.setRol("Lider");
			iParticipantesRepository.save(par);
		}
		return iProyectoRepository.existsById(proyecto.getId());
	}


	@Override
	public List<Producto> todosLosProductos() {
		
		return null;
	}

	@Override
	public List<Producto> todosLosProductos(Proyecto proyecto) {
		List<Producto> productos=proyecto.getProductos();
		if (productos.equals(null)) {
			productos = new ArrayList<Producto>();
		}
		return productos;
	}

	@Override
	public boolean eliminarProductos(int id) {
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
	public List<Participantes> todosLosParticipantesPorProyecto(Proyecto proyecto) {
		List<Participantes> usuarios =proyecto.getParticipantes();
		if (usuarios.equals(null)) {
			usuarios = new ArrayList<Participantes>();
		}
		return usuarios;
	}

	@Override
	public boolean crearParticipante(Participantes participante, String rol) {
		participante.setRol(rol);
		iParticipantesRepository.save(participante);
		return iParticipantesRepository.existsById(participante.getParticipantesPK());
	}

	@Override
	public boolean eliminarParticipante(LocalDate fecha_inicio) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Comentario> ComentariosPorProducto(Producto producto) {
		List<Comentario> comentarios=producto.getComentarios();
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
	public boolean crearComentario(Comentario comentario,String cedula) {
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
	public List<Comentario> todosLosComentariosPorProducto(int productoid) {
		Producto producto = iProductoRepository.getById(productoid);
		List<Comentario> comentarios = producto.getComentarios();
		if (comentarios.equals(null)) {
			comentarios = new ArrayList<Comentario>();
		}
		return comentarios;
		
	}

	@Override
	public List<Proyecto> todosLosProyectosFinalizados(String grado,String estado) {
		List<Proyecto> x = new ArrayList<>();
		if(grado!= null) {
			List<Proyecto> estados = iProyectoRepository.findByEstado(estado);
			if(estado!=null) {
				List<Proyecto> proyectoTerminado = iProyectoRepository.findByTipoProyectoAndEstado(grado,estado);
				return proyectoTerminado;
			}
			else {
				logger.info("El estado no existe");
				return x;
			}
		}
		else {		
			logger.info("El tipo no existe");
			return x;
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
	public List<JSONObject> proyectosParticipante(String cedula) {
		List<JSONObject> x = iProyectoRepository.proyectosParticipaGrado(cedula);
		return x;
	}

}
