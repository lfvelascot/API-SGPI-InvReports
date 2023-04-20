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
import co.edu.usbbog.sgpi.model.Comentario;
import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.service.IGestionTrabajosGradoService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
@RestController
@CrossOrigin
@RequestMapping("/gestiontrabajogrado")
public class GestionTrabajosGradoController {
	@Autowired
	private IGestionTrabajosGradoService iGestionTrabajosGradoService;
	/**
	 * todos los proyetos 
	 * @return
	 */
	@GetMapping("/todosLosproyectos")
	public JSONArray todosLosProyectos() {
		JSONArray salida = new JSONArray();
		List<Proyecto> proyectos = iGestionTrabajosGradoService.todosLosProyectos("Grado");
		for (Iterator iterator = proyectos.iterator(); iterator.hasNext();) {
			Proyecto proyecto = (Proyecto) iterator.next();
			salida.add(proyecto.toJson());
		}
		return salida;
	}
	/**
	 * crear proyecto 
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
		if (iGestionTrabajosGradoService.crearProyecto(proyecto, entrada.getAsString("tipo"),
				entrada.getAsString("rol"), entrada.getAsString("clase"),entrada.getAsString("usuario"),LocalDate.parse(entrada.getAsString("fechainicio")))) {
			salida.put("respuesta", "el proyecto fue creado");
		} else {
			salida.put("respuesta", "el proyecto no fue creado");
		}
		return salida;
	}
	/**
	 * eliminar un proyecto 
	 * @param id
	 * @return
	 */
	@GetMapping("/eliminarproyecto/{id}")
	public JSONObject elinimarProyecto(@PathVariable String id) {
		JSONObject salida = new JSONObject();
		if (iGestionTrabajosGradoService.eliminarProyecto(Integer.parseInt(id))) {
			salida.put("respuesta", "proyeto fue eliminado correctamente");
		} else {
			salida.put("respuesta", "el proyecto no fue eliminado");
		}
		return salida;
	}
	/**
	 * crear producto 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/crearproducto")
	public JSONObject crearProducto(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Proyecto proyecto = iGestionTrabajosGradoService
				.buscarProyecto(Integer.parseInt(entrada.getAsString("proyectoid")));
		
		if(proyecto.getTipoProyecto().getNombre().equals("Grado")) {
			Producto producto = new Producto( entrada.getAsString("titulo"),
					entrada.getAsString("tipo"), entrada.getAsString("url"), LocalDate.parse(entrada.getAsString("fecha")));
		
		if (iGestionTrabajosGradoService.crearProducto(producto)) {
			salida.put("respuesta", "el producto fue guardado");
		} else {
			salida.put("respuesta", "el producto no fue guardado");
		}
		}else {
			salida.put("respuesta","el tipo del proyecto no es valido");
		}
			
		return salida;
	}
	/**
	 * elimiar un producto 
	 * @param id
	 * @return
	 */
	@GetMapping("/eliminarproducto/{id}")
	public JSONObject elinimarProducto(@PathVariable String id) {
		JSONObject salida = new JSONObject();
		if (iGestionTrabajosGradoService.eliminarProductos(Integer.parseInt(id))) {
			salida.put("respuesta", "proyeto fue eliminado correctamente");
		} else {
			salida.put("respuesta", "el proyecto no fue eliminado");
		}
		return salida;
	}
	/**
	 * crear comentario de un producto 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/crearcomentario")
	public JSONObject crearComentario(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Producto producto = iGestionTrabajosGradoService
				.buscarProducto(Integer.parseInt(entrada.getAsString("productoid")));
		Comentario comentario = new Comentario(
				entrada.getAsString("comentario"), entrada.getAsString("fase"), entrada.getAsString("nivel"),
				LocalDate.parse(entrada.getAsString("fecha")));
		if (iGestionTrabajosGradoService.crearComentario(comentario, entrada.getAsString("cedula"))) {
			salida.put("respuesta", "el comentario fue guardado");
		} else {
			salida.put("respuesta", "el comentario no fue guardado");
		}
		return salida;
	}
	/**
	 * eliminar un comentario 
	 * @param id
	 * @return
	 */
	@GetMapping("/eliminarcomentario/{id}")
	public JSONObject elinimarComentario(@PathVariable String id) {
		JSONObject salida = new JSONObject();
		if (iGestionTrabajosGradoService.eliminarComentario(Integer.parseInt(id))) {
			salida.put("respuesta", "el comentario fue eliminado correctamente");
		} else {
			salida.put("respuesta", "el comentario no fue eliminado");
		}
		return salida;
	}
	/**
	 * listar trabajos de grado terminados
	 * @return
	 */
	@GetMapping(value = "/listarGradoTerminado")
	public JSONArray listarGradoTerminado() {
		JSONArray salida = new JSONArray();
		List<Proyecto> pro = iGestionTrabajosGradoService.todosLosProyectosFinalizados("grado", "finalizado");
		for (Proyecto proyecto : pro) {
			salida.add(proyecto.toJson());
		}
		return salida;
	}
	/**
	 * lista de proyectos de tipo Grado de un usuario  
	 * @param cedula
	 * @return
	 */
	@GetMapping(value = "/proyectosgrado/{cedula}")
	public  List<JSONObject>  poryectosParparticipante(@PathVariable String cedula	) {
		 List<JSONObject> x = iGestionTrabajosGradoService.proyectosParticipante(cedula);
		 return x;
		}	
}
