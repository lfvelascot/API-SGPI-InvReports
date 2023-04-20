package co.edu.usbbog.sgpi.service;



import java.util.List;


import co.edu.usbbog.sgpi.model.Comentario;

import co.edu.usbbog.sgpi.model.Evento;
import co.edu.usbbog.sgpi.model.MacroProyecto;

import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;



public interface IGestionProductosService {

	//actualizar tipoproyecto
	
	public List<Producto> todosLosProductos();
	public List<Producto> todosLosProductosPorProyecto(int proyecto);
	public boolean eliminarProductos(int id);
	public boolean crearProducto(Producto producto, int proyecto);
	public boolean modificarProducto(int id, String titulo_producto, String tipo_producto, String url_repo, String fecha);
	public Producto productoporid(int id);

	
	public List<Comentario> ComentariosPorProducto(int producto_id);
	public boolean eliminarComentario(int id);
	public boolean crearComentario(Comentario comentario, int producto_id);
	public boolean modificarComentario(int id, String comentario, String fase, String nivel, String fecha);
	public Comentario comentarioporid(int id);
	//actualizar comentario
	
	//LA NOTA????

	public boolean asignarCalificacion(double calificacion, int id);
	public boolean eliminarCalificacion(int id);
	//actualizar nota
	

}
