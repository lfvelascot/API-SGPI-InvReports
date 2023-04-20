package co.edu.usbbog.sgpi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpi.model.Comentario;
import co.edu.usbbog.sgpi.model.Evento;
import co.edu.usbbog.sgpi.model.MacroProyecto;
import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.repository.IComentarioRepository;
import co.edu.usbbog.sgpi.repository.IProductoRepository;
import co.edu.usbbog.sgpi.repository.IProyectoRepository;

@Service
public class GestionProductosService implements IGestionProductosService {

	@Autowired
	private IProductoRepository productoRepo;

	@Autowired
	private IProyectoRepository proyectoRepo;

	@Autowired
	private IComentarioRepository comentarioRepo;

	// metodo para obtener todos los productos
	@Override
	public List<Producto> todosLosProductos() {
		List<Producto> productos = productoRepo.findAll();
		return productos;
	}

//metodo para obtener todos los productos de un proyecto en especifico
	@Override
	public List<Producto> todosLosProductosPorProyecto(int proyecto) {
		Proyecto pro = proyectoRepo.getById(proyecto);
		List<Producto> productos = pro.getProductos();
		if (productos.equals(null)) {
			productos = new ArrayList<Producto>();
		}
		return productos;
	}

//metodo para eliminar un producto
	@Override
	public boolean eliminarProductos(int producto) {
		List<Comentario> com = comentarioRepo.findByProducto(producto);
		boolean pro = productoRepo.existsById(producto);
		if (com.isEmpty() && pro == true) {
			productoRepo.deleteById(producto);
			return !productoRepo.existsById(producto);
		}
		return false;
	}

// metodo para crear un producto
	@Override
	public boolean crearProducto(Producto producto, int proyecto) {
		Proyecto proyec = proyectoRepo.getById(proyecto);
		if (proyec == null) {
			return false;
		}
		producto.setProyecto(proyec);
		productoRepo.save(producto);
		return true;
	}

//metodo para actualizar un producto
	@Override
	public boolean modificarProducto(int id, String titulo_producto, String tipo_producto, String url_repo,
			String fecha) {
		Producto producto = productoRepo.getById(id);
		if (titulo_producto != "") {
			producto.setTituloProducto(titulo_producto);
		}
		if (tipo_producto != "") {
			producto.setTipoProducto(tipo_producto);
		}
		if (url_repo != "") {
			producto.setUrlRepo(url_repo);
		}
		if (fecha != "") {
			producto.setFecha(LocalDate.parse(fecha));
		}
		productoRepo.save(producto);
		return !productoRepo.existsById(producto.getId());
	}

	// metodo para obtener los datos de un producto
	@Override
	public Producto productoporid(int id) {
		Producto producto = productoRepo.getById(id);
		return producto;
	}

	// metodo para obtener los comentarios de un producto en especifico
	@Override
	public List<Comentario> ComentariosPorProducto(int producto_id) {
		Producto pro = productoRepo.getById(producto_id);
		List<Comentario> comentarios = pro.getComentarios();
		if (comentarios.equals(null)) {
			comentarios = new ArrayList<Comentario>();
		}
		return comentarios;
	}

//metodo para eliminar un comentario
	@Override
	public boolean eliminarComentario(int id) {
		boolean com = comentarioRepo.existsById(id);
		if (com == true) {
			comentarioRepo.deleteById(id);
			return !comentarioRepo.existsById(id);

		}
		return false;
	}

//metodo para crear un comentario
	@Override
	public boolean crearComentario(Comentario comentario, int producto_id) {
		Producto pro = productoRepo.getById(producto_id);
		if (pro == null) {
			return false;
		}
		comentario.setProductoId(pro);
		comentarioRepo.save(comentario);
		return true;
	}

	// metodo para actualizar un comentario
	@Override
	public boolean modificarComentario(int id, String comentario, String fase, String nivel, String fecha) {
		Comentario comenta = comentarioRepo.getById(id);
		if (comentario != "") {
			comenta.setComentario(comentario);
		}
		if (fase != "") {
			comenta.setFase(fase);
		}
		if (nivel != "") {
			comenta.setNivel(nivel);
		}
		if (fecha != "") {
			comenta.setFecha(LocalDate.parse(fecha));
		}
		comentarioRepo.save(comenta);
		return !comentarioRepo.existsById(comenta.getId());
	}

	// metodo para obtener la informacion de un comentario
	@Override
	public Comentario comentarioporid(int id) {
		Comentario comenta = comentarioRepo.getById(id);
		return comenta;
	}

	// metodo para asignar una calificacion a un comentario
	@Override
	public boolean asignarCalificacion(double calificacion, int id) {
		boolean comentario = comentarioRepo.existsById(id);
		if (comentario == false) {
			return false;
		}
		comentarioRepo.asignarCalificacion(calificacion, id);
		return true;
	}

//metodo para eliminar la calificacion asignada a un comentario
	@Override
	public boolean eliminarCalificacion(int id) {
		boolean comentario = comentarioRepo.existsById(id);
		if (comentario == false) {
			return false;
		}
		comentarioRepo.eliminarCalificacion(id);
		return true;
	}


}