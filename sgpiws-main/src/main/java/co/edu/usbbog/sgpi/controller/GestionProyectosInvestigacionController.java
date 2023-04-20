package co.edu.usbbog.sgpi.controller;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbbog.sgpi.model.AreaConocimiento;
import co.edu.usbbog.sgpi.model.Comentario;
import co.edu.usbbog.sgpi.model.Participaciones;
import co.edu.usbbog.sgpi.model.Participantes;
import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpi.service.IGestionProyectosInvestigacionService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;



@RestController
@CrossOrigin
@RequestMapping("/gestionproyectosinvestigacion")
public class GestionProyectosInvestigacionController {
	@Autowired
	private IGestionProyectosInvestigacionService iGestionProyectosInvestigacionService ;
	/**
	 * crear un proyecto 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/crearproyecto")
	public JSONObject crearProyectoAulaIntegrador(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Proyecto proyecto = new Proyecto(entrada.getAsString("titulo"),
				entrada.getAsString("estado"), entrada.getAsString("descripcion"),
				LocalDate.parse(entrada.getAsString("fechainicio")),
				Short.parseShort(entrada.getAsString("visibilidad")), entrada.getAsString("ciudad"),
				entrada.getAsString("metodologia"), entrada.getAsString("justificacion"));
		if (iGestionProyectosInvestigacionService.crearProyecto(proyecto, entrada.getAsString("tipo"),
				entrada.getAsString("rol"), entrada.getAsString("clase"),entrada.getAsString("usuario"),LocalDate.parse(entrada.getAsString("fechainicio")),entrada.getAsString("semillero"),Integer.parseInt(entrada.getAsString("macro")))) {
			salida.put("respuesta", "el proyecto fue creado");
		} else {
			salida.put("respuesta", "el proyecto no fue creado");
		}
		return salida;
	}
	/**
	 * verificar si se encuentra en un semillero 
	 * @param cedula
	 * @return
	 */
	@GetMapping("/verficarSemillero/{cedula}")
	public JSONObject verficarSemillero(@PathVariable String cedula) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosInvestigacionService.verificarSemillero(cedula)) {
			salida.put("respuesta", "este usuario ya esta asignado a un semillero");
		} else {
			salida.put("respuesta", "el usuario no esta inscrito aun semillero");
		}
		return salida;
	}
	/**
	 * agregar participante a un proyecto 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/agregarParticipante")
	public JSONObject agregarParticipante(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Participantes participante = new Participantes(entrada.getAsString("usuario"),
				Integer.parseInt(entrada.getAsString("id")), LocalDate.parse(entrada.getAsString("fechainicio")));
		if (iGestionProyectosInvestigacionService.crearParticipante(participante, entrada.getAsString("rol"))) {
			salida.put("respuesta", "el participante fue agregado");
		} else {
			salida.put("respuesta", "el participante no pudo ser agregado");
		}
		return salida;
	}
	/**
	 * proyectos de tipo semillero por usuario  
	 * @param cedula
	 * @return
	 */
	@GetMapping(value = "/proyectossemillero/{cedula}")
	public  List<JSONObject>  listaProyectos(@PathVariable String cedula	) {
		 List<JSONObject> x = iGestionProyectosInvestigacionService.proyectosParticipanteSemillero(cedula);
		 return x;
		}
	/**
	 * proyectos de tipo semillero por usuario  
	 * @param cedula
	 * @return
	 */
	@GetMapping("/todosLosproyectosusuariosemillero/{cedula}")
	public  List<JSONObject> todosLosProyectosUsuarioSemillero(@PathVariable String cedula) {
		List<JSONObject> proyectos = iGestionProyectosInvestigacionService.todosLosProyectosUsuarioSemillero(cedula);
		return proyectos;
	}
	/**
	 * eliminar proyecto 
	 * @param id
	 * @return
	 */
	@GetMapping("/eliminarproyecto/{id}")
	public JSONObject elinimarProyecto(@PathVariable String id) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosInvestigacionService.eliminarProyecto(Integer.parseInt(id))) {
			salida.put("respuesta", "proyeto fue eliminado correctamente");
		} else {
			salida.put("respuesta", "el proyecto no fue eliminado");
		}
		return salida;
	}
	/**
	 * crear un producto  
	 * @param entrada
	 * @return
	 */
	@PostMapping("/crearproducto")
	public JSONObject crearProducto(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Proyecto proyecto = iGestionProyectosInvestigacionService
				.buscarProyecto(Integer.parseInt(entrada.getAsString("proyectoid")));
		Producto producto = new Producto( entrada.getAsString("titulo"),
				entrada.getAsString("tipo"), entrada.getAsString("url"), LocalDate.parse(entrada.getAsString("fecha")));
		if (iGestionProyectosInvestigacionService.crearProducto(producto)) {
			salida.put("respuesta", "el producto fue guardado");
		} else {
			salida.put("respuesta", "el producto no fue guardado");
		}
		return salida;
	}
	/**
	 * eliminar un producto 
	 * @param id
	 * @return
	 */
	@GetMapping("/eliminarproducto/{id}")
	public JSONObject elinimarProducto(@PathVariable String id) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosInvestigacionService.eliminarProducto(Integer.parseInt(id))) {
			salida.put("respuesta", "proyeto fue eliminado correctamente");
		} else {
			salida.put("respuesta", "el proyecto no fue eliminado");
		}
		return salida;
	}
	/**
	 * crear comentario 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/crearcomentario")
	public JSONObject crearComentario(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Producto producto = iGestionProyectosInvestigacionService
				.buscarProducto(Integer.parseInt(entrada.getAsString("productoid")));
		Comentario comentario = new Comentario(
				entrada.getAsString("comentario"), entrada.getAsString("fase"), entrada.getAsString("nivel"),
				LocalDate.parse(entrada.getAsString("fecha")));
		if (iGestionProyectosInvestigacionService.crearComentario(comentario, entrada.getAsString("cedula"))) {
			salida.put("respuesta", "el comentario fue guardado");
		} else {
			salida.put("respuesta", "el comentario no fue guardado");
		}
		return salida;
	}
	/**
	 * elimianr comentario 
	 * @param id
	 * @return
	 */
	@GetMapping("/eliminarcomentario/{id}")
	public JSONObject elinimarComentario(@PathVariable String id) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosInvestigacionService.eliminarComentario(Integer.parseInt(id))) {
			salida.put("respuesta", "el comentario fue eliminado correctamente");
		} else {
			salida.put("respuesta", "el comentario no fue eliminado");
		}
		return salida;
	}
	/**
	 * comentarios por productos 
	 * @param id
	 * @return
	 */
	@GetMapping("/comentariosproducto/{id}")
	public JSONArray ComentariosPorProducto(@PathVariable String id) {
		JSONArray salida = new JSONArray();

		List<Comentario> comentarios = iGestionProyectosInvestigacionService
				.ComentariosPorProducto(Integer.parseInt(id));
		for (Iterator<Comentario> iterator = comentarios.iterator(); iterator.hasNext();) {
			Comentario comentario = (Comentario) iterator.next();
			salida.add(comentario.toJson());
		}
		return salida;
	}
	/**
	 * participar en un evento 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/participarevento")
	public JSONObject participarEvento(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Participaciones participaciones = new Participaciones(Integer.parseInt(entrada.getAsString("evento")),
				Integer.parseInt(entrada.getAsString("proyecto")));
		if (iGestionProyectosInvestigacionService.participarEvento(participaciones,
				LocalDate.parse(entrada.getAsString("fecha")), entrada.getAsString("reconocimiento"))) {
			salida.put("respuesta", "se unio al evento exitosamente");
		} else {
			salida.put("respuesta", "no se unio al evento ");
		}

		return salida;
	}
	/**
	 * lista de participaciones en eventos 
	 * @param proyecto
	 * @return
	 */
	@GetMapping("/participacionesproyecto/{proyecto}")
	public JSONArray participacionesProyecto(@PathVariable String proyecto) {
		JSONArray salida = new JSONArray();
		List<Participaciones> participaciones = iGestionProyectosInvestigacionService
				.buscarParticipaciones(Integer.parseInt(proyecto));
		for (Iterator<Participaciones> iterator = participaciones.iterator(); iterator.hasNext();) {
			Participaciones participacion = (Participaciones) iterator.next();
			salida.add(participacion.toJson());
		}
		return salida;
	}
	/**
	 * agregar antecedente 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/agregarantecedente")
	public JSONObject agregarAntecedente(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Proyecto proyecto = iGestionProyectosInvestigacionService
				.buscarProyecto(Integer.parseInt(entrada.getAsString("proyecto")));
		Proyecto antecedente = iGestionProyectosInvestigacionService
				.buscarProyecto(Integer.parseInt(entrada.getAsString("antecedente")));
		if (antecedente.getFechaFin().equals(null)) {
			salida.put("respuesta ", "el antecedente aun no a terminado");
		} else {
			if (proyecto.equals(antecedente)) {
				salida.put("respuesta", "el proyecto no puede ser antedente de si mismo");
			} else {

				if (iGestionProyectosInvestigacionService.agregarAntecedente(proyecto, antecedente)) {
					salida.put("respuesta", "se agrego exitosamente el antecedente");
				} else {
					salida.put("respuesta", "no se agrego el antecedente");
				}
			}
		}

		return salida;
	}
	/**
	 * agregar area de conocimiento 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/agregarareaconocimiento")
	public JSONObject agregarAreaConocimiento(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosInvestigacionService.agregarAreaConocimiento(
				Integer.parseInt(entrada.getAsString("proyecto")), Integer.parseInt(entrada.getAsString("area")))) {
			salida.put("respuesta", "se agrego exitosamente las areas");
		} else {
			salida.put("respuesta", "No se agrego las areas");
		}
		return salida;
	}
	/**
	 * listar areas de conocimiento por proyecto 
	 * @param proyecto
	 * @return
	 */
	@GetMapping("/listarareasproyecto/{proyecto}")
	public JSONArray listarAreasProyecto(@PathVariable String proyecto) {
		JSONArray salida = new JSONArray();
		List<AreaConocimiento> areaConocimientos = iGestionProyectosInvestigacionService
				.buscarAreasProyecto(Integer.parseInt(proyecto));
		for (Iterator<AreaConocimiento> iterator = areaConocimientos.iterator(); iterator.hasNext();) {
			AreaConocimiento areas = (AreaConocimiento) iterator.next();
			salida.add(areas.toJson());
		}
		return salida;
	}
	/**
	 * listar antecedentes de un proyecto
	 * @param proyecto
	 * @return
	 */
	@GetMapping("/listarAntecedentes/{proyecto}")
	public JSONArray listarAntecedenteProyecto(@PathVariable String proyecto) {
		JSONArray salida = new JSONArray();
		List<Proyecto> pro = iGestionProyectosInvestigacionService
				.buscarAntecedentes(Integer.parseInt(proyecto));
		for (Iterator iterator = pro.iterator(); iterator.hasNext();) {
			Proyecto proyecto2 = (Proyecto) iterator.next();
			salida.add(proyecto2.toJson());
		}
		return salida;
	}
	/**
	 * participar en una convocatoria 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/participarconvocatoria/")
	public JSONObject participarconvocatoria(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		ProyectosConvocatoria proyectoConvocatoria= new ProyectosConvocatoria(Integer.parseInt(entrada.getAsString("proyecto")),Integer.parseInt(entrada.getAsString("convocatoria")) );
		if (iGestionProyectosInvestigacionService.participarConvocatoria(proyectoConvocatoria,entrada.getAsString("proyecto_id"))) {
			salida.put("respuesta", "se agrego exitosamente a la convocatoria");
		} else {
			salida.put("respuesta", "No se agrego a la convocatoria");
		}
		return salida;
	}
	/**
	 * proyectos de un usuario de tipo convocatoria 
	 * @param cedula
	 * @return
	 */
	@GetMapping("/tusProyectosConvocatoria/{cedula}")
	public List<JSONObject> tusProyectoConvocatoria(@PathVariable String cedula) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.tusProyectosConvocatoria(cedula);	
		return pro;
	}
	/**
	 * proyectos de semillero de un usuario 
	 * @param cedula
	 * @return
	 */
	@GetMapping("/tusProyectosSemillero/{cedula}")
	public List<JSONObject> tusProyectoSemillero(@PathVariable String cedula) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.tusProyectoSemillero(cedula);	
		return pro;
	}
	/**
	 * dar aval a un proyecto 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/aval/")
	public JSONObject darAval(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		ProyectosConvocatoria proyectoConvocatoria= new ProyectosConvocatoria(Integer.parseInt(entrada.getAsString("proyecto")),Integer.parseInt(entrada.getAsString("convocatoria")));
		if (iGestionProyectosInvestigacionService.darAval(proyectoConvocatoria,entrada.getAsString("estado"),entrada.getAsString("reconocimiento"))){
			salida.put("respuesta", "Se realizo la validacion exitosamente");
		} else {
			salida.put("respuesta", "no se pudo realizar ");
		}
		return salida;
	}
	/**
	 * lista de proyectos de convocatoria
	 * @param proyecto
	 * @return
	 */
	@GetMapping("/paticipacionesConvocatoria/{proyecto}")
	public List<JSONObject> proyectoConvocatoria(@PathVariable String proyecto) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.paticipacionesConvocatoria(proyecto);
		return pro;	
	}
	/**
	 * lista de todos los proyectos de grado 
	 * @return
	 */
	@GetMapping("/proyectosGrado")
	public List<JSONObject> proyectoGrado() {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.proyectosGrado();	
		return pro;
	}
	/**
	 * proyectos de clase en estado propuesta 
	 * @param curso
	 * @return
	 */
	@GetMapping("/proyectosPropuestaClase/{curso}")
	public List<JSONObject> proyectoPropuestaClase(@PathVariable int curso) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.proyectosPropuestaClase(curso);	
		return pro;
	}
	/**
	 * proyectos de clase en estado desarrollo
	 * @param curso
	 * @return
	 */
	@GetMapping("/proyectosDesarrolloClase/{curso}")
	public List<JSONObject> proyectoDesarrolloClase(@PathVariable int curso) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.proyectosDesarrolloClase(curso);	
		return pro;
	}
	/**
	 * evaluar un proyecto 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/evaluar/")
	public JSONObject Evaluar(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosInvestigacionService.evaluar(Integer.parseInt(entrada.getAsString("proyecto")),entrada.getAsString("estado"),entrada.getAsString("reconocimiento"))) {
			salida.put("respuesta", "Se realizo la validacion exitosamente");
		} else {
			salida.put("respuesta", "no se pudo realizar ");
		}
		return salida;
	}
	/**
	 * proyectos de clase en estado finalizado 
	 * @param curso
	 * @return
	 */
	@GetMapping("/proyectosFinalizadosClase/{curso}")
	public List<JSONObject> proyectosFinalizadosClase(@PathVariable int curso) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.proyectosFinalizadosClase(curso);	
		return pro;
	}
	/**
	 * trabajos de grado en estado inicio
	 * @param cedula
	 * @return
	 */
	@GetMapping("/TrabajoGradoInicio/{cedula}")
	public List<JSONObject> TrabajoGradoInicio(@PathVariable String cedula) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.trabajoGradoInicio(cedula);	
		return pro;
	}
	/**
	 * trabajos de grado en estado desarrollo 
	 * @param cedula
	 * @return
	 */
	@GetMapping("/TrabajoGradoDesarrollo/{cedula}")
	public List<JSONObject> TrabajoGradoDesarrollo(@PathVariable String cedula) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.trabajoGradoDesarrollo(cedula);	
		return pro;
	}
	/**
	 * trabajos de grado en estado correciones 
	 * @param cedula
	 * @return
	 */
	@GetMapping("/TrabajoGradoJurado/{cedula}")
	public List<JSONObject> TrabajoGradoJurado(@PathVariable String cedula) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.trabajoGradoJurado(cedula);	
		return pro;
	}
	/**
	 * trabajos de grado en estado finalizados 
	 * @param cedula
	 * @return
	 */
	@GetMapping("/TrabajoGradoFinalizados/{cedula}")
	public List<JSONObject> TrabajoGradoFinalizados(@PathVariable String cedula) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.trabajoGradoFinalizados(cedula);	
		return pro;
	}
	/**
	 * trabajos de grado en estado rechazados
	 * @param cedula
	 * @return
	 */
	@GetMapping("/TrabajoGradoRechazados/{cedula}")
	public List<JSONObject> TrabajoGradoRechazados(@PathVariable String cedula) {
		List<JSONObject> pro = iGestionProyectosInvestigacionService.trabajoGradoRechazados(cedula);	
		return pro;
	}
	/**
	 * cambiar el estado del proyecto de grado 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/cambiarEstadoTrabajoGrado/")
	public JSONObject CambioEstadoTrabajoGrado(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosInvestigacionService.cambioEstadoTrabajoGrado(Integer.parseInt(entrada.getAsString("proyecto")),entrada.getAsString("estado"),entrada.getAsString("reconocimientos"))) {
			salida.put("respuesta", "Se realizo la validacion exitosamente");
		} else {
		
			salida.put("respuesta", "no se pudo realizar ");
		}
		return salida;
	}
	/**
	 * salir de un semillero 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/salirSemillero/")
	public JSONObject SalirSemillero(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosInvestigacionService.salirSemillero(entrada.getAsString("cedula"),Integer.parseInt(entrada.getAsString("semillero")))) {
			salida.put("respuesta", "Se realizo la validacion exitosamente");
		} else {
			salida.put("respuesta", "no se pudo realizar ");
		}
		return salida;
	}
	@GetMapping("/ProyectosFinalizas/")
	public List<JSONObject> proyectosFinalizados() {
	List<JSONObject> pro = iGestionProyectosInvestigacionService.proyectosFinalizados();
	return pro;
	}
}

