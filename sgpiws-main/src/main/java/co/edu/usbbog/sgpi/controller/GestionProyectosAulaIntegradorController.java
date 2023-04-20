package co.edu.usbbog.sgpi.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Iterator;
import java.util.List;
import co.edu.usbbog.sgpi.model.AreaConocimiento;
import co.edu.usbbog.sgpi.model.Comentario;
import co.edu.usbbog.sgpi.model.Evento;
import co.edu.usbbog.sgpi.model.MacroProyecto;
import co.edu.usbbog.sgpi.model.Participaciones;
import co.edu.usbbog.sgpi.model.Participantes;
import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.service.IGestionProyectosAulaIntegradorService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
@RequestMapping("/gestionproyectosaulaintegrador")
public class GestionProyectosAulaIntegradorController {
	@Autowired
	private IGestionProyectosAulaIntegradorService iGestionProyectosAulaIntegradorService;

	// duda por la facultad
	/**
	 * crear in proyecto 
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
		int macros;
		if(entrada.getAsString("macro")=="") {
			macros=0;
		}else {
			macros=Integer.parseInt(entrada.getAsString("macro"));
		}
		if (iGestionProyectosAulaIntegradorService.crearProyecto(proyecto, entrada.getAsString("tipo"),
				entrada.getAsString("rol"), entrada.getAsString("clase"),entrada.getAsString("usuario"),LocalDate.parse(entrada.getAsString("fechainicio")),macros)) {
			salida.put("respuesta", "el proyecto fue creado");
		} else {
			salida.put("respuesta", "el proyecto no fue creado");
		}
		return salida;
	}
	/**
	 * crear un macroproyecto
	 * @param entrada
	 * @return
	 */
	@PostMapping("/crearMacro")
	public JSONObject crearMacro(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		
		MacroProyecto macro= new MacroProyecto(entrada.getAsString("nombre"), entrada.getAsString("descripcion"), LocalDate.parse(entrada.getAsString("fechainicio")),entrada.getAsString("estado"));
		
		if (iGestionProyectosAulaIntegradorService.crearMacro(macro)){
			salida.put("respuesta", "el macro proyecto se creo correctamente");
		} else {
			salida.put("respuesta", "el macro proyecto proyecto no se pudo crear correctamente");
		}
		return salida;
	}
	/**
	 * cerrar macroproyecto
	 * @param entrada
	 * @return
	 */
	@PostMapping("/cerrarMacro")
	public JSONObject cerrarrMacro(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosAulaIntegradorService.cerrarMacro(Integer.parseInt(entrada.getAsString("macro")))){
			salida.put("respuesta", "el macro se finalizar correctamente");
		} else {
			salida.put("respuesta", "el macro no se puedo finalizar");
		}
		return salida;
	}
	/**
	 * agregar un participante a un proyecto 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/agregarParticipante")	 
	public JSONObject agregarParticipante(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Participantes participante = new Participantes(entrada.getAsString("usuario"),
				Integer.parseInt(entrada.getAsString("id")), LocalDate.parse(entrada.getAsString("fechainicio")));
		if (iGestionProyectosAulaIntegradorService.crearParticipante(participante, entrada.getAsString("rol"),entrada.getAsString("usuario"),Integer.parseInt(entrada.getAsString("id")))) {
			salida.put("respuesta", "el participante fue agregado");
		} else {
			salida.put("respuesta", "el participante no pudo ser agregado");
		}
		return salida;
	}
	/**
	 * eliminar un participante del proyecto 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/eliminarparticipante")
	public JSONObject eliminarParticipante(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosAulaIntegradorService.actualizarParticipante(Integer.parseInt( entrada.getAsString("id")),
				entrada.getAsString("cedula"),
				 LocalDate.parse(entrada.getAsString("fechafin"))
				)) { 
			salida.put("respuesta", "el participante termino su proceso en el proyecto");
		} else {
			salida.put("respuesta", "hubo un error");
		}
		return salida;
	}
	/**
	 * todos los proyectos 
	 * @return
	 */
	@GetMapping("/todosLosproyectos")
	public JSONArray todosLosProyectos() {
		JSONArray salida = new JSONArray();
		List<Proyecto> proyectos = iGestionProyectosAulaIntegradorService.todosLosProyectos();
		for (Iterator iterator = proyectos.iterator(); iterator.hasNext();) {
			Proyecto proyecto = (Proyecto) iterator.next();
			salida.add(proyecto.toJson()); 
		}
		return salida;
	}
	/**
	 * lista de macroproyectos abiertos 
	 * @return
	 */
	@GetMapping("/macroProyectos")
	public JSONArray macroProyectos() {
		JSONArray salida = new JSONArray();
		List<MacroProyecto> Macroproyectos = iGestionProyectosAulaIntegradorService.macroProyectosAbiertos();
		for (Iterator iterator = Macroproyectos.iterator(); iterator.hasNext();) {
			MacroProyecto macroProyecto = (MacroProyecto) iterator.next();
			salida.add(macroProyecto.toJson());
		}
		return salida;
	}
	/**
	 * lista de areas de conocimiento por proyecto
	 * @param proyecto
	 * @return
	 */
	@GetMapping("/areasconocimientoproyecto/{proyecto}")
	public JSONArray areasConocimientoProyecto(@PathVariable int proyecto){
		JSONArray salida = new JSONArray();
		List<AreaConocimiento> area = iGestionProyectosAulaIntegradorService.areasConocimientoProyecto(proyecto);
		for (Iterator iterator = area.iterator(); iterator.hasNext();) {
			AreaConocimiento areaConocimiento = (AreaConocimiento) iterator.next();
			salida.add(areaConocimiento.toJson());
		}
		return salida;
	}
	/**
	 * todos los proyectos de una clase
	 * @param clase
	 * @return
	 */
	@GetMapping("/todoslosproyectosporclase/{clase}")
	public JSONArray todosLosProyectosPorClase(@PathVariable String clase) {
		JSONArray salida = new JSONArray();
		List<Proyecto> proyectos = iGestionProyectosAulaIntegradorService.todosLosProyectosPorClase(clase);
		for (Iterator<Proyecto> iterator = proyectos.iterator(); iterator.hasNext();) {
			Proyecto proyecto = (Proyecto) iterator.next();
			salida.add(proyecto.toJson());
		}
		return salida;
	}
	/**
	 * lista de todos los proyctos por tipos de proyecto
	 * @param tipo
	 * @return
	 */
	@GetMapping("/todoslosproyectosportipoproyecto/{tipo}")
	public JSONArray todosLosProyectosPorTipoProyecto(@PathVariable String tipo) {
		JSONArray salida = new JSONArray();
		List<Proyecto> proyectos = iGestionProyectosAulaIntegradorService.todosLosProyectosPorTipoProyecto(tipo);
		for (Iterator<Proyecto> iterator = proyectos.iterator(); iterator.hasNext();) {
			Proyecto proyecto = (Proyecto) iterator.next();
			salida.add(proyecto.toJson());
		}
		return salida;
	}
	/**
	 * eliminar proyecto
	 * @param id
	 * @return
	 */
	@GetMapping("/eliminarproyecto/{id}")
	public JSONObject elinimarProyecto(@PathVariable String id) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosAulaIntegradorService.eliminarProyecto(Integer.parseInt(id))) {
			salida.put("respuesta", "proyeto fue eliminado correctamente");
		} else {
			salida.put("respuesta", "el proyecto no fue eliminado");
		}
		return salida;
	}
	/**
	 * eliminar area de conocimiento 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/eliminarArea")
	public JSONObject elinimarArea(@RequestBody JSONObject entrada) {
		JSONObject salida= new JSONObject();
		if (iGestionProyectosAulaIntegradorService.eliminarArea(Integer.parseInt(entrada.getAsString("areasConocimiento")),Integer.parseInt(entrada.getAsString("proyecto")))) {
			salida.put("respuesta", "Se eliminado la area");
		} else {
			salida.put("respuesta", "la area no fue eliminada");
		}
		return salida;
	}
	/**
	 * eliminar producto
	 * @param id
	 * @return
	 */
	@GetMapping("/eliminarproducto/{id}")
	public JSONObject elinimarProducto(@PathVariable String id) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosAulaIntegradorService.eliminarProducto(Integer.parseInt(id))) {
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
		Producto producto = iGestionProyectosAulaIntegradorService
				.buscarProducto(Integer.parseInt(entrada.getAsString("productoid")));
		Comentario comentario = new Comentario(
				entrada.getAsString("comentario"), entrada.getAsString("fase"), entrada.getAsString("nivel"),
				LocalDate.parse(entrada.getAsString("fecha")));
		if (iGestionProyectosAulaIntegradorService.crearComentario(comentario, entrada.getAsString("cedula"))) {
			salida.put("respuesta", "el comentario fue guardado");
		} else {
			salida.put("respuesta", "el comentario no fue guardado");
		}
		return salida;
	}
	/**
	 * eliminar comentario de un producto 
	 * @param id
	 * @return
	 */
	@GetMapping("/eliminarcomentario/{id}")
	public JSONObject elinimarComentario(@PathVariable String id) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosAulaIntegradorService.eliminarComentario(Integer.parseInt(id))) {
			salida.put("respuesta", "el comentario fue eliminado correctamente");
		} else {
			salida.put("respuesta", "el comentario no fue eliminado");
		}
		return salida;
	}
	/**
	 * lista de comentarios por productos
	 * @param id
	 * @return
	 */
	@GetMapping("/comentariosproducto/{id}")
	public JSONArray ComentariosPorProducto(@PathVariable String id) {
		JSONArray salida = new JSONArray();

		List<Comentario> comentarios = iGestionProyectosAulaIntegradorService
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
		if (iGestionProyectosAulaIntegradorService.participarEvento(participaciones,
				LocalDate.parse(entrada.getAsString("fecha")), entrada.getAsString("reconocimiento"))) {
			salida.put("respuesta", "se unio al evento exitosamente");
		} else {
			salida.put("respuesta", "no se unio al evento ");
		}

		return salida;
	}
	/**
	 * participar en un evento externo 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/participareventoExterno")
	public JSONObject participarEventoExterno(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Evento evento =  new Evento( entrada.getAsString("nombre"), LocalDate.parse( entrada.getAsString("fecha")), entrada.getAsString("estado"));
		
		if (iGestionProyectosAulaIntegradorService.participarEventoExtrerna(evento,Integer.parseInt(entrada.getAsString("proyecto")),
				LocalDate.parse(entrada.getAsString("fecha1")), entrada.getAsString("reconocimiento"),entrada.getAsString("entidad"),entrada.getAsString("sitioWeb"),entrada.getAsString("url_memoria"))) {
			salida.put("respuesta", "se unio al evento exitosamente");
		} else {
			salida.put("respuesta", "no se unio al evento ");
		}

		return salida;
	}
	/**
	 * lista de participaciones por proyecto 
	 * @param proyecto
	 * @return
	 */
	@GetMapping("/participacionesproyecto/{proyecto}")
	public JSONArray participacionesProyecto(@PathVariable String proyecto) {
		JSONArray salida = new JSONArray();
		List<Participaciones> participaciones = iGestionProyectosAulaIntegradorService
				.buscarParticipaciones(Integer.parseInt(proyecto));
		for (Iterator<Participaciones> iterator = participaciones.iterator(); iterator.hasNext();) {
			Participaciones participacion = (Participaciones) iterator.next();
			salida.add(participacion.toJson());
		}
		return salida;
	}
	/**
	 * agregar antecedentes a un proyecto  
	 * @param entrada
	 * @return
	 */
	@PostMapping("/agregarantecedente")
	public JSONObject agregarAntecedente(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Proyecto proyecto = iGestionProyectosAulaIntegradorService
				.buscarProyecto(Integer.parseInt(entrada.getAsString("proyecto")));
		Proyecto antecedente = iGestionProyectosAulaIntegradorService
				.buscarProyecto(Integer.parseInt(entrada.getAsString("antecedente")));
		if (antecedente.getFechaFin() == null) {
			salida.put("respuesta ", "el antecedente aun no a terminado");
		} else {
			if (proyecto == antecedente) {
				salida.put("respuesta", "el proyecto no puede ser antedente de si mismo");
			} else {

				if (iGestionProyectosAulaIntegradorService.agregarAntecedente(proyecto, antecedente)) {
					salida.put("respuesta", "se agrego exitosamente el antecedente");
				} else {
					salida.put("respuesta", "no se agrego el antecedente ");
				}
			}
		}

		return salida;
	}
	/**
	 * agregar area e conocimiento a proyecto 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/agregarareaconocimiento")
	public JSONObject agregarAreaConocimiento(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (iGestionProyectosAulaIntegradorService.agregarAreaConocimiento(
				Integer.parseInt(entrada.getAsString("proyecto")), Integer.parseInt(entrada.getAsString("area")))) {
			salida.put("respuesta", "se agrego exitosamente las areas");
		} else {
			salida.put("respuesta", "No se agrego las areas");
		}
		return salida;
	}
	/**
	 * lista todas la areas de conocimiento
	 * @return
	 */
	@GetMapping("/listarareas")
	public JSONArray listarAreas() {
		JSONArray salida = new JSONArray();
		List<AreaConocimiento> areaConocimientos = iGestionProyectosAulaIntegradorService
				.listarAreaConocimiento();
		for (Iterator<AreaConocimiento> iterator = areaConocimientos.iterator(); iterator.hasNext();) {
			AreaConocimiento areas = (AreaConocimiento) iterator.next();
			salida.add(areas.toJson());
		}
		return salida;
	}
	/**
	 * listar areas de conocimiento por proeycto 
	 * @param proyecto
	 * @return
	 */
	@GetMapping("/listarareasproyecto/{proyecto}")
	public JSONArray listarAreasProyecto(@PathVariable String proyecto) {
		JSONArray salida = new JSONArray();
		List<AreaConocimiento> areaConocimientos = iGestionProyectosAulaIntegradorService
				.buscarAreasProyecto(Integer.parseInt(proyecto));
		for (Iterator<AreaConocimiento> iterator = areaConocimientos.iterator(); iterator.hasNext();) {
			AreaConocimiento areas = (AreaConocimiento) iterator.next();
			salida.add(areas.toJson());
		}
		return salida;
	}
	/**
	 * buscar un pruyecto por el id 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/listarporid/{id}")
	public JSONObject listarPorId(@PathVariable int id) {
		JSONObject x= new JSONObject();
		if(iGestionProyectosAulaIntegradorService.buscarProyecto(id) !=null) {
			Proyecto pro = iGestionProyectosAulaIntegradorService.buscarProyecto(id);
			return pro.toJson();
		}
		else {
			return x;
		}	
	}
	/**
	 * buscar los detalles de un proyecto por el id 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/detallePorId/{id}")
	public JSONObject detallePorId(@PathVariable int id) {
		JSONObject x= new JSONObject();
		if(iGestionProyectosAulaIntegradorService.detallesProyecto(id) !=null) {
			JSONObject pro = iGestionProyectosAulaIntegradorService.detallesProyecto(id);
			return pro;
		}
		else {
			return x;
		}	
	}
	/**
	 * lista de eventos  
	 * @return
	 */
	@GetMapping(value = "/listareventos")
	public  List<JSONObject>  listarEventos() {
		 List<JSONObject> x = iGestionProyectosAulaIntegradorService.listarEvento();
		 return x;
		}	
	/**
	 * lista de proyectos de aula de un usuario 
	 * @param cedula
	 * @return
	 */
	@GetMapping(value = "/proyectosAI/{cedula}")
	public  List<JSONObject>  poryectosParparticipante(@PathVariable String cedula	) {
		 List<JSONObject> x = iGestionProyectosAulaIntegradorService.proyectosParticipanteClase(cedula);
		 return x;
		}
	/**
	 * actualizar un proyecto 
	 * @param entrada
	 * @return
	 */
	@PostMapping(value = "/actualizarproyecto")
	public  JSONObject ActualizarProyecto(@RequestBody JSONObject entrada	) {
		JSONObject salida=new JSONObject();
		if(iGestionProyectosAulaIntegradorService.actualizarProyecto(Integer.parseInt( entrada.getAsString("id")),entrada.getAsString("titulo"),entrada.getAsString("descripcion"),entrada.getAsString("metodologia"),entrada.getAsString("justificacion"),entrada.getAsString("conclusiones"),entrada.getAsString("visibilidad"))) {
			salida.put("respuesta", "el proyecto fue actualizado");
			
		}else {
			salida.put("respuesta", "Actualizacion no valida");
			
		}
		return salida;
		}
	/**
	 * lista de participantes por proyecto 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/listarparticipantesporporyecto/{id}")
	public  JSONArray listarParticipantesPorPoryecto(@PathVariable int id	) {
		JSONArray salida=new JSONArray();
		List<Participantes> parti= iGestionProyectosAulaIntegradorService.listarParticipantesPorPoryecto(id);
		for (Iterator iterator = parti.iterator(); iterator.hasNext();) {
			Participantes participantes = (Participantes) iterator.next();
			salida.add(participantes.toJson());
		}
		  return salida;
		}
	/**
	 * lista de proyectos de convocatoria 
	 * @param entrada
	 * @return
	 */
	@PostMapping(value = "/tusProyectosConvocatoria")
	public   List<JSONObject> tusProyectosConvocatoria(@RequestBody JSONObject entrada	) {
		 List<JSONObject> x = iGestionProyectosAulaIntegradorService.tusProyectosConvocatoria(Integer.parseInt(entrada.getAsString("convocatoria")),Integer.parseInt(entrada.getAsString("id")));
		 return x;
		}
	@GetMapping(value = "/ProyectosConvocatoriaUsuario/{cedula}")
	public   List<JSONObject> proyectosConvocatoriaUsuario(@PathVariable String cedula	) {
		 List<JSONObject> x = iGestionProyectosAulaIntegradorService.proyectosConvocatoriaUsuario(cedula);
		 return x;
		}
	/**
	 * modificar macro proyecto 
	 * @param entrada
	 * @return
	 */
	@PostMapping(value = "/ModificarMacro")
	public  JSONObject modificarMacro(@RequestBody JSONObject entrada	) {
		JSONObject salida=new JSONObject();
		
		if(iGestionProyectosAulaIntegradorService.modificarMacro(Integer.parseInt(entrada.getAsString("id")),entrada.getAsString("nombre") ,entrada.getAsString("descripcion"))) {
			salida.put("respuesta", "el macro proyecto fue modificado");
		}else {
			salida.put("respuesta", "el macro proyecto proyecto no se pudo modificar");	
		}
		return salida;
		}
}
