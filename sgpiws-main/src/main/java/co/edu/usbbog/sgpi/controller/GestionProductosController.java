package co.edu.usbbog.sgpi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbbog.sgpi.model.Comentario;

import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.service.IGestionProductosService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
@RequestMapping("/productos")
public class GestionProductosController {
	@Autowired
	private IGestionProductosService gestionProductos;

	// metodo para listar productos
	@GetMapping(value = "/listarproductos")
	public JSONArray listarProductos() {

		JSONArray salida = new JSONArray();
		List<Producto> pro = gestionProductos.todosLosProductos();
		for (Producto producto : pro) {
			salida.add(producto.toJson());
		}
		return salida;
	}

	// metodo para listar productos de un proyecto
	@GetMapping(value = "/listarproductosproyecto/{proyecto}")
	public JSONArray listaProductosPorProyecto(@PathVariable int proyecto) {

		JSONArray salida = new JSONArray();
		List<Producto> pro = gestionProductos.todosLosProductosPorProyecto(proyecto);

		for (Iterator iterator = pro.iterator(); iterator.hasNext();) {
			Producto producto = (Producto) iterator.next();
			salida.add(producto.toJson());
		}
		return salida;
	}

	// metodo para eliminar un producto
	@GetMapping(value = "/eliminarproducto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String eliminarProducto(@PathVariable int id) {
		if (gestionProductos.eliminarProductos(id)) {
			return "Se elimino con exito";
		}
		// gestionInstitucionalService.eliminarGrupoInvestigacion(id);
		return "Fallo la eliminacion";
	}

	// metodo para crear un producto
	@PostMapping(value = "/crearproducto")
	public JSONObject crearProducto(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();

		Producto producto = new Producto(

				entrada.getAsString("titulo_producto"), entrada.getAsString("tipo_producto"),
				entrada.getAsString("url_repo"),
				LocalDate.parse(entrada.getAsString("fecha"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

		if (gestionProductos.crearProducto(producto, Integer.parseInt(entrada.getAsString("proyecto")))) {
			salida.put("respuesta", "el producto se creo");
		} else {
			salida.put("respuesta", "el producto no se creo");
		}
		return salida;
	}

	// metodo para modificar un producto
	@PostMapping("/modificarproducto")
	public JSONObject modificarProducto(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (!gestionProductos.modificarProducto(Integer.parseInt(entrada.getAsString("id")),
				entrada.getAsString("titulo_producto"), entrada.getAsString("tipo_producto"),
				entrada.getAsString("url_repo"), entrada.getAsString("fecha"))) {
			salida.put("respuesta", "se actualizo el producto");
		} else {
			salida.put("respuesta", "no se pudo actualizar el producto");
		}
		return salida;
	}

	// metodo para obtener la info de un producto
	@GetMapping(value = "/productoid/{id}")
	public JSONObject productolistarPorId(@PathVariable int id) {
		JSONObject x = new JSONObject();
		if (gestionProductos.productoporid(id) != null) {
			Producto producto = gestionProductos.productoporid(id);
			return producto.toJson();
		} else {
			return x;
		}
	}

	// metodo para obtener los comentarios de un producto
	@GetMapping(value = "/listarcomentariosporproducto/{producto_id}")
	public JSONArray listarComentariosPorProducto(@PathVariable int producto_id) {

		JSONArray salida = new JSONArray();
		List<Comentario> comen = gestionProductos.ComentariosPorProducto(producto_id);
		for (Iterator iterator = comen.iterator(); iterator.hasNext();) {
			Comentario comentario = (Comentario) iterator.next();
			salida.add(comentario.toJson());
		}
		return salida;
	}

	// metodo para eliminar un comentario
	@GetMapping(value = "/eliminarcomentario/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String eliminarComentario(@PathVariable int id) {
		if (gestionProductos.eliminarComentario(id)) {
			return "Se elimino con exito";
		}
		// gestionInstitucionalService.eliminarGrupoInvestigacion(id);
		return "Fallo la eliminacion";
	}

	// metodo para crear un comentario y asignarlo a un producto
	@PostMapping(value = "/crearcomentario")
	public JSONObject crearComentario(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Comentario comentario = new Comentario(

				entrada.getAsString("comentario"), entrada.getAsString("fase"), entrada.getAsString("nivel"),
				LocalDate.parse(entrada.getAsString("fecha")));

		if (gestionProductos.crearComentario(comentario, Integer.parseInt(entrada.getAsString("producto_id")))) {
			salida.put("respuesta", "el comentario se creo");
		} else {
			salida.put("respuesta", "el comentario no se creo");
		}
		return salida;
	}

	// metodo para actualizar el comentario
	@PostMapping("/modificarcomentario")
	public JSONObject modificarComentario(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (!gestionProductos.modificarComentario(Integer.parseInt(entrada.getAsString("id")),
				entrada.getAsString("comentario"), entrada.getAsString("fase"), entrada.getAsString("nivel"),
				entrada.getAsString("fecha"))) {
			salida.put("respuesta", "se actualizo el comentario");
		} else {
			salida.put("respuesta", "no se pudo actualizar el comentario");
		}
		return salida;
	}

	// metodo para obtener la info del comentario
	@GetMapping(value = "/comentarioid/{id}")
	public JSONObject comentariolistarPorId(@PathVariable int id) {
		JSONObject x = new JSONObject();
		if (gestionProductos.comentarioporid(id) != null) {
			Comentario comenta = gestionProductos.comentarioporid(id);
			return comenta.toJson();
		} else {
			return x;
		}
	}

	// metodo para signar una nota
	@PostMapping(value = "/asignarnota")
	public JSONObject asignarNota(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();

		if (gestionProductos.asignarCalificacion(Double.parseDouble(entrada.getAsString("calificacion")),
				Integer.parseInt(entrada.getAsString("id")))) {
			salida.put("respuesta", "se asigno la nota");
		} else {
			salida.put("respuesta", "NO se pudo");
		}
		return salida;
	}

	// metodo para eliminar una nota
	@PostMapping(value = "/eliminarnota")
	public JSONObject eliminarNota(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();

		if (gestionProductos.eliminarCalificacion(Integer.parseInt(entrada.getAsString("id")))) {
			salida.put("respuesta", "se elimino la nota");
		} else {
			salida.put("respuesta", "NO se pudo");
		}
		return salida;
	}

}