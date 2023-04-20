package co.edu.usbbog.sgpi.service;

import java.time.LocalDate;
import java.util.List;

import co.edu.usbbog.sgpi.model.AreaConocimiento;
import co.edu.usbbog.sgpi.model.Comentario;
import co.edu.usbbog.sgpi.model.Evento;
import co.edu.usbbog.sgpi.model.MacroProyecto;
import co.edu.usbbog.sgpi.model.Materia;
import co.edu.usbbog.sgpi.model.Participaciones;
import co.edu.usbbog.sgpi.model.Participantes;
import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;
import net.minidev.json.JSONObject;

public interface IGestionProyectosAulaIntegradorService {
	/**
	 * todos los proyectos
	 * 
	 * @return
	 */
	public List<Proyecto> todosLosProyectos();

	/**
	 * todos los proyectos de una clase
	 * 
	 * @param clase
	 * @return
	 */
	public List<Proyecto> todosLosProyectosPorClase(String clase);

	/**
	 * todos los proyectos por tipo de proyecto
	 * 
	 * @param tipo
	 * @return
	 */
	public List<Proyecto> todosLosProyectosPorTipoProyecto(String tipo);

	/**
	 * eliminar un proyecto
	 * 
	 * @param id
	 * @return
	 */
	public boolean eliminarProyecto(int id);

	/**
	 * crear un proyecto
	 * 
	 * @param proyecto
	 * @param tipo
	 * @param rol
	 * @param clase
	 * @param cedula
	 * @param fecha
	 * @param macro
	 * @return
	 */
	public boolean crearProyecto(Proyecto proyecto, String tipo, String rol, String clase, String cedula,
			LocalDate fecha, int macro);

	/**
	 * todos los productos de un proyecto
	 * 
	 * @param proyecto
	 * @return
	 */
	public List<Producto> todosLosProductos(Proyecto proyecto);

	/**
	 * eliminar un producto
	 * 
	 * @param id
	 * @return
	 */
	public boolean eliminarProducto(int id);

	/**
	 * eliminar un producto
	 * 
	 * @param producto
	 * @return
	 */
	boolean crearProducto(Producto producto);


	/**
	 * agregar un participante a un proyecto
	 * 
	 * @param participante
	 * @param rol
	 * @param cedula
	 * @param proyecto
	 * @return
	 */
	public boolean crearParticipante(Participantes participante, String rol, String cedula, int proyecto);

	/**
	 * todos los comentarios por producto
	 * 
	 * @param productoId
	 * @return
	 */
	public List<Comentario> ComentariosPorProducto(int productoId);

	/**
	 * eliminar un comentario
	 * 
	 * @param id
	 * @return
	 */
	public boolean eliminarComentario(int id);

	/**
	 * crear un comentario
	 * 
	 * @param comentario
	 * @param cedula
	 * @return
	 */
	public boolean crearComentario(Comentario comentario, String cedula);

	/**
	 * asignar una calificacion a un producto
	 * 
	 * @param comentarioid
	 * @param calificacion
	 * @return
	 */
	public boolean asignarCalificacion(int comentarioid, double calificacion);
	/**
	 * eliminar una calificacion 
	 * @param comentarioid
	 * @return
	 */
	public boolean eliminarCalificacion(int comentarioid);
	/**
	 * asignar un proyecto a un materia 
	 * @param proyecto
	 * @param materia
	 * @return
	 */
	public boolean asignarProyectosaMaterias(Proyecto proyecto, Materia materia);
	/**
	 * buscar un proyecto 
	 * @param proyectoId
	 * @return
	 */
	public Proyecto buscarProyecto(int proyectoId);
	/**
	 * detalles proyecto
	 */
	public JSONObject detallesProyecto(int proyectoId);
	/**
	 * buscar un producto 
	 * @param productoId
	 * @return
	 */
	public Producto buscarProducto(int productoId);
	/**
	 * participar en un evento 
	 * @param participaciones
	 * @param parse
	 * @param asString
	 * @return
	 */
	public boolean participarEvento(Participaciones participaciones, LocalDate parse, String asString);
	/**
	 * participar en un evento externo de la universidad 
	 * @param evento
	 * @param proyecto
	 * @param fecha
	 * @param reconocimiento
	 * @param entidad
	 * @param sitio_web
	 * @param url_memoria
	 * @return
	 */
	public boolean participarEventoExtrerna(Evento evento, int proyecto, LocalDate fecha, String reconocimiento,
			String entidad, String sitio_web, String url_memoria);
	/**
	 * buscar participaciones 
	 * @param proyecto
	 * @return
	 */
	public List<Participaciones> buscarParticipaciones(int proyecto );
	/**
	 * agregar un antecedente a un proyecto 
	 * @param proyeto
	 * @param antecedente
	 * @return
	 */
	public boolean agregarAntecedente(Proyecto proyeto, Proyecto antecedente);
	/**
	 * agregar un area de conocimiento a un proyecto 
	 * @param proyecto
	 * @param area
	 * @return
	 */
	public boolean agregarAreaConocimiento(int proyecto, int area);
	/**
	 * buscar un area de conocimiento 
	 * @param proyecto
	 * @return
	 */
	public List<AreaConocimiento> buscarAreasProyecto(int proyecto);
	/**
	 * proyectos de clase en los que participa un usuario 
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> proyectosParticipanteClase(String cedula);
	/**
	 * actualizar un proyecto 
	 * @param id
	 * @param titulo
	 * @param descripcion
	 * @param metodologia
	 * @param justificacion
	 * @return
	 */
	public boolean actualizarProyecto(int id, String titulo, String descripcion, String metodologia,
			String justificacion,String conclusiones,String visualizacion);
	/**
	 * listar participantes de un proyecto 
	 * @param id
	 * @return
	 */
	public List<Participantes> listarParticipantesPorPoryecto(int id);
	/**
	 * actualizar un participante 
	 * @param id
	 * @param cedula
	 * @param fechafin
	 * @return
	 */
	public boolean actualizarParticipante(int id, String cedula, LocalDate fechafin);
	/**
	 * lista todas las areas de conocimiento 
	 * @return
	 */
	public List<AreaConocimiento> listarAreaConocimiento();
	/**
	 * listar todos los eventos
	 * @return
	 */
	public List<JSONObject> listarEvento();
	/**
	 * areas de conocimiento por proyecto 
	 * @param proyecto
	 * @return
	 */
	public List<AreaConocimiento> areasConocimientoProyecto(int proyecto);
    /**
     * listar macro proyectos abiertos 
     * @return
     */
	public List<MacroProyecto> macroProyectosAbiertos();
	/**
	 * eliminar un area de conocimiento de un proyecto 
	 * @param area
	 * @param proyecto
	 * @return
	 */
	public boolean eliminarArea(int area, int proyecto);
	/**
	 * crear un macro proyecto 
	 * @param macro
	 * @return
	 */
	public boolean crearMacro(MacroProyecto macro);
	/**
	 * cerrar un macro proyecto 
	 * @param parseInt
	 * @return
	 */
	public boolean cerrarMacro(int parseInt);
	/**
	 * modificar un macro proyecto 
	 * @param macro
	 * @param nombre
	 * @param descripcion
	 * @return
	 */
	public boolean modificarMacro(int macro, String nombre, String descripcion);
	/**
	 * listar todos los proytectode de convocatoria 
	 * @param convocatoria
	 * @param id
	 * @return
	 */
	public List<JSONObject> tusProyectosConvocatoria(int convocatoria, int id);

	public List<JSONObject> proyectosConvocatoriaUsuario(String cedula);
}
