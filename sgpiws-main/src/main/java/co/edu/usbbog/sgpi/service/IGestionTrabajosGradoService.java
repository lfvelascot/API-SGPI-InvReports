package co.edu.usbbog.sgpi.service;

import java.time.LocalDate;
import java.util.List;

import co.edu.usbbog.sgpi.model.Comentario;
import co.edu.usbbog.sgpi.model.Participantes;
import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;
import net.minidev.json.JSONObject;

public interface IGestionTrabajosGradoService {
	/**
	 * todos los proyectos por tipo de proyecto 
	 * @param tipo
	 * @return
	 */
	public List<Proyecto> todosLosProyectos(String tipo);
	/**
	 * eliminar un proyecto 
	 * @param id
	 * @return
	 */
	public boolean eliminarProyecto(int id);
	/**
	 * crear un proyecto 
	 * @param proyecto
	 * @param tipo
	 * @param rol
	 * @param clase
	 * @param cedula
	 * @param fecha
	 * @return
	 */
	public boolean crearProyecto(Proyecto proyecto,String tipo,String rol,String clase,String cedula,LocalDate fecha);
	/**
	 * todos los productos 
	 * @return
	 */
	public List<Producto> todosLosProductos();
	/**
	 * todos los productos de un proyecto 
	 * @param proyecto
	 * @return
	 */
	public List<Producto> todosLosProductos(Proyecto proyecto);
	/**
	 * eliminar un producto de un proyecto 
	 * @param id
	 * @return
	 */
	public boolean eliminarProductos(int id);
	/**
	 * crear un producto 
	 * @param producto
	 * @return
	 */
	public boolean crearProducto(Producto producto);
	/**
	 * todos los participantes de un proyecto 
	 * @param proyecto
	 * @return
	 */
	public List<Participantes> todosLosParticipantesPorProyecto(Proyecto proyecto);
	/**
	 * agregar un participante a un proyecto 
	 * @param participante
	 * @param rol
	 * @return
	 */
	public boolean crearParticipante(Participantes participante, String rol);
	/**
	 * eliminar un participante de un proyecto 
	 * @param fecha_inicio
	 * @return
	 */
	public boolean eliminarParticipante(LocalDate fecha_inicio);
	/**
	 * listar todos los comentarios de un producto
	 * @param producto
	 * @return
	 */
	public List<Comentario> ComentariosPorProducto(Producto producto);
	/**
	 * eliminar un comentario 
	 * @param id
	 * @return
	 */
	public boolean eliminarComentario(int id);
	/**
	 * crear un comentario 
	 * @param comentario
	 * @param cedula
	 * @return
	 */
	public boolean crearComentario(Comentario comentario, String cedula);
	/**
	 * todos los comentarios de un producto 
	 * @param productoid
	 * @return
	 */
	public List<Comentario> todosLosComentariosPorProducto(int productoid);


	/**
	 * todos los proyectos de grado finalizados 
	 * @param grado
	 * @param estado
	 * @return
	 */
	public List<Proyecto> todosLosProyectosFinalizados(String grado,String estado);
	/**
	 * buscar un proyecto 
	 * @param proyectoId
	 * @return
	 */
	public Proyecto buscarProyecto(int proyectoId);
	/**
	 * buscar un producto 
	 * @param productoId
	 * @return
	 */
	public Producto buscarProducto(int productoId);
	/**
	 * proyectos en los que participa un usuario 
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> proyectosParticipante(String cedula);
}
