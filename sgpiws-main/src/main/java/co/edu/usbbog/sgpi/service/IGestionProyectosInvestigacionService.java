package co.edu.usbbog.sgpi.service;

import java.time.LocalDate;
import java.util.List;
import co.edu.usbbog.sgpi.model.AreaConocimiento;
import co.edu.usbbog.sgpi.model.Comentario;
import co.edu.usbbog.sgpi.model.Compra;
import co.edu.usbbog.sgpi.model.Participaciones;
import co.edu.usbbog.sgpi.model.Participantes;
import co.edu.usbbog.sgpi.model.Presupuesto;
import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpi.model.TipoProyecto;
import net.minidev.json.JSONObject;

public interface IGestionProyectosInvestigacionService {
	/**
	 * lista de proyectos de semillero
	 * 
	 * @return
	 */
	public List<Proyecto> todosLosProyectosSemillero();

	/**
	 * todos los proyectos de un semillero
	 * 
	 * @param semillero
	 * @return
	 */
	public List<Proyecto> todosLosProyectosPorSemillero(int semillero);
	/**
	 * eliminar un proyecto en especifico
	 * 
	 * @param id
	 * @return
	 */
	public boolean eliminarProyecto(int id);

	/**
	 * asignar un tipo de proyecto a un proyecto
	 * 
	 * @param proyecto
	 * @param investigacion
	 * @return
	 */
	public boolean asignarTipoProyecto(Proyecto proyecto, TipoProyecto investigacion);
	/**
	 * listar todos los productos de un proyecto
	 * 
	 * @param proyecto
	 * @return
	 */
	public List<Producto> todosLosProductos(Proyecto proyecto);

	/**
	 * eliminar un producto de un proyecto
	 * 
	 * @param id
	 * @return
	 */
	public boolean eliminarProductos(int id);

	/**
	 * crear un producto de un proyecto
	 * 
	 * @param producto
	 * @return
	 */
	public boolean crearProducto(Producto producto);

	/**
	 * lista todos los participantes por proyecto
	 * 
	 * @param proyecto
	 * @return
	 */
	public List<Participantes> todosLosParticipantesPorProyecto(Proyecto proyecto);

	/**
	 * agregar un participante a un proyecto
	 * 
	 * @param participante
	 * @param rol
	 * @return
	 */
	public boolean crearParticipante(Participantes participante, String rol);
	/**
	 * listar comentarios de un producto
	 * 
	 * @param productoid
	 * @return
	 */
	public List<Comentario> ComentariosPorProducto(int productoid);

	/**
	 * elimiar un comentario de un producto
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
	 * asignar calificacion a un producto
	 * 
	 * @param comentarioid
	 * @param calificacion
	 * @return
	 */
	public boolean asignarCalificacion(int comentarioid, double calificacion);

	/**
	 * eliminar calificacion de un producto
	 * 
	 * @param comentarioid
	 * @return
	 */
	public boolean eliminarCalificacion(int comentarioid);

	/**
	 * presupuesto de un proyecto
	 * 
	 * @param proyecto
	 * @return
	 */
	public List<Presupuesto> PresupuestoPorProyecto(Proyecto proyecto);

	/**
	 * eliminar presupuesto
	 * 
	 * @param id
	 * @return
	 */
	public boolean eliminarPresupuesto(int id);

	/**
	 * crear presupuesto
	 * 
	 * @param presupuesto
	 * @param cedula
	 * @return
	 */
	public boolean crearPresupuesto(Presupuesto presupuesto, String cedula);

	/**
	 * listar compras por presupuesto de un proyecto
	 * 
	 * @param presupuesto
	 * @return
	 */
	public List<Compra> CompraPorPresupuesto(Presupuesto presupuesto);

	/**
	 * eliminar un compra
	 * 
	 * @param id
	 * @return
	 */
	public boolean eliminarCompra(int id);

	/**
	 * crear una compra
	 * 
	 * @param compra
	 * @return
	 */
	public boolean crearCompra(Compra compra);
	/**
	 * buscar un proyecto por su id 
	 * @param parseInt
	 * @return
	 */
	public Proyecto buscarProyecto(int id);
	/**
	 * buscar un produto en especifico 
	 * @param parseInt
	 * @return
	 */
	public Producto buscarProducto(int id);
	/**
	 * eliminar un producto 
	 * @param id
	 * @return
	 */
	boolean eliminarProducto(int id);
	/**
	 * participar en un evento 
	 * @param participaciones
	 * @param fecha
	 * @param reconocimiento
	 * @return
	 */
	boolean participarEvento(Participaciones participaciones, LocalDate fecha, String reconocimiento);
	/**
	 * buscar participaciones en eventos de un proyecto 
	 * @param parseInt
	 * @return
	 */
	public List<Participaciones> buscarParticipaciones(int parseInt);
	/**
	 * agregar un antecedente a un proyecto 
	 * @param proyecto
	 * @param antecedente
	 * @return
	 */
	public boolean agregarAntecedente(Proyecto proyecto, Proyecto antecedente);
	/**
	 * agregar una area de conocimiento a un proyecto 
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	public boolean agregarAreaConocimiento(int parseInt, int parseInt2);
	/**
	 * buscar areas de conocimiento de un proyecto 
	 * @param parseInt
	 * @return
	 */
	public List<AreaConocimiento> buscarAreasProyecto(int id);
	/**
	 * participar en un proyecto de convocatoria 
	 * @param proyectosConvocatoria
	 * @param estado
	 * @return
	 */
	public boolean participarConvocatoria(ProyectosConvocatoria proyectosConvocatoria, String estado);
	/**
	 * todos los proyecto de semillero de un usuario 
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> todosLosProyectosUsuarioSemillero(String cedula);
	/**
	 * todos los proyectos de un semillero en los que un usuario este participando 
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> proyectosParticipanteSemillero(String cedula);

	/**
	 * verificar si un usuario esta inscrito aun semillero 
	 * @param cedula
	 * @return
	 */
	public boolean verificarSemillero(String cedula);

	/**
	 * lista de antecedentes de un proyecto 
	 * @param parseInt
	 * @return
	 */
	public List<Proyecto> buscarAntecedentes(int parseInt);

	/**
	 * crear proyecto
	 * @param proyecto
	 * @param tipo
	 * @param rol
	 * @param clase
	 * @param usuario
	 * @param fecha
	 * @param semillero
	 * @param macro
	 * @return
	 */
	public boolean crearProyecto(Proyecto proyecto, String tipo, String rol, String clase,
			String usuario, LocalDate fecha, String semillero, int macro);

	/**
	 * todos los proyecto de convocatoria en los que participa un usuario 
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> tusProyectosConvocatoria(String cedula);

	/**
	 * todos los proyectos de semillero de un usuario 
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> tusProyectoSemillero(String cedula);

	/**
	 * dar aval a un proyecto de convocatoria
	 * @param proyectoConvocatoria
	 * @param estado
	 * @param reconocimiento
	 * @return
	 */
	public boolean darAval(ProyectosConvocatoria proyectoConvocatoria, String estado, String reconocimiento);

	/**
	 * listar participaciones de una convocatoria 
	 * @param proyecto
	 * @return
	 */
	public List<JSONObject> paticipacionesConvocatoria(String proyecto);

	/**
	 *  listar todos los proyectos de grado 
	 * @return
	 */
	public List<JSONObject> proyectosGrado();

	/**
	 * listar proyecto de clase en estado de propuesta
	 * @param curso
	 * @return
	 */
	public List<JSONObject> proyectosPropuestaClase(int curso);

	/**
	 * listar proyecto de clase en estado de desarrollo
	 * @param curso
	 * @return
	 */
	public List<JSONObject> proyectosDesarrolloClase(int curso);

	/**
	 * listar proyecto de clase en estado de finalizado
	 * @param curso
	 * @return
	 */
	public List<JSONObject> proyectosFinalizadosClase(int curso);

	/**
	 * evaluar proyectos 
	 * @param proyecto
	 * @param estado
	 * @param reconocimiento
	 * @return
	 */
	public boolean evaluar(int proyecto, String estado, String reconocimiento);

	/**
	 * trabajos de grado en estado de inicio 
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> trabajoGradoInicio(String cedula);

	/**
	 * trabajos de grado en estado de desarrollo  
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> trabajoGradoDesarrollo(String cedula);

	/**
	 * trabajos de grado en estado de correcciones 
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> trabajoGradoJurado(String cedula);

	/**
	 * trabajos de grado en estado de finalizado
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> trabajoGradoFinalizados(String cedula);

	/**
	 * trabajos de grado en estado de rechazado 
	 * @param cedula
	 * @return
	 */
	public List<JSONObject> trabajoGradoRechazados(String cedula);

	/**
	 * cambiar estaso de proyecto de grado 
	 * @param proyecto
	 * @param estado
	 * @param reconocimiento
	 * @return
	 */
	public boolean cambioEstadoTrabajoGrado(int proyecto, String estado, String reconocimiento);

	/**
	 * Salir de un semillero 
	 * @param cedula
	 * @param semillero
	 * @return
	 */
	public boolean salirSemillero(String cedula, int semillero);

	public List<JSONObject> proyectosFinalizados();

}
