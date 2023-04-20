package co.edu.usbbog.sgpi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.service.IBibliotecaService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
@RequestMapping("/biblioteca")
public class BibliotecaController {

	@Autowired
	private IBibliotecaService bibliotecaService;

	// usamos el metodo todosLosProyectosDeGrado para poder listar los trabajos de
	// grado
	@GetMapping(value = "/listarGrado")
	public JSONArray listarGrado() {
		JSONArray salida = new JSONArray();
		List<Proyecto> pro = bibliotecaService.todosLosProyectosDeGrado();
		for (Proyecto proyecto : pro) {
			salida.add(proyecto.toJson());
		}
		return salida;
	}

	// usamos el metodo todosLosProyectosTerminados para poder listar los trabajos
	// de grado finalizados
	@GetMapping(value = "/listarGradoTerminado")
	public JSONArray listarGradoTerminado() {
		JSONArray salida = new JSONArray();
		List<Proyecto> pro = bibliotecaService.todosLosProyectosTerminados("grado", "finalizado");
		for (Proyecto proyecto : pro) {
			salida.add(proyecto.toJson());
		}
		return salida;
	}

	// usamos el metodo proyectoporid para poder obtener toda la informacion del
	// proyecto en especifico
	@GetMapping(value = "/listarporid/{id}")
	public JSONObject listarPorId(@PathVariable int id) {
		JSONObject x = new JSONObject();
		if (bibliotecaService.proyectoporid(id) != null) {
			Proyecto pro = bibliotecaService.proyectoporid(id);
			return pro.toJson();
		} else {
			return x;
		}
	}
}