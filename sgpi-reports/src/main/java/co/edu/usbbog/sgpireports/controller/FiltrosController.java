package co.edu.usbbog.sgpireports.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbbog.sgpireports.model.Facultad;
import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.service.IGestionFiltros;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
@RequestMapping("/info")
public class FiltrosController {
	
	@Autowired
	private IGestionFiltros filtros;
	
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@PostMapping("/semillero/programa")
	public JSONArray buscarSemillerosPorPrograma(@RequestBody JSONObject entrada) {
		List<Semillero> lista = filtros.buscarSemillerosPorPrograma(Integer.valueOf(entrada.getAsString("programa")));
		JSONArray salida = new JSONArray();
		for(Semillero p : lista) {
			salida.add(p.toJson());
		}
		return salida;
	}
	
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/semillero")
	public JSONArray buscarSemilleros() {
		List<Semillero> lista = filtros.buscarSemilleros();
		JSONArray salida = new JSONArray();
		for(Semillero p : lista) {
			salida.add(p.toJson());
		}
		return salida;
	}
	
	/**
	 * Lista de facultades solo para filtros
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/facultad")
	public JSONArray buscarFacultades() {
		List<Facultad> lista = filtros.buscarFacultades();
		JSONArray salida = new JSONArray();
		for(Facultad p : lista) {
			salida.add(p.toJsonF());
		}
		return salida;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@PostMapping("/facultad/programa")
	public JSONArray buscarProgramasPorFacultad(@RequestBody JSONObject entrada) {
		List<Programa> lista = filtros.buscarProgramasPorFacultad(Integer.valueOf(entrada.getAsString("facultad")));
		JSONArray salida = new JSONArray();
		for(Programa p : lista) {
			salida.add(p.toJsonF());
		}
		return salida;
	}
	
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@PostMapping("/facultad/gi/semillero/proyecto")
	public JSONArray buscarProyectosPorSemillero(@RequestBody JSONObject entrada) {
		List<Proyecto> lista = filtros.buscarProyectosPorSemillero(Integer.valueOf(entrada.getAsString("semillero")));
		JSONArray salida = new JSONArray();
		for(Proyecto p : lista) {
			salida.add(p.toJsonF());
		}
		return salida;
	}
	
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@PostMapping("/facultad/gi")
	public JSONArray buscarGIPorFacultadFiltro(@RequestBody JSONObject entrada) {
		List<GrupoInvestigacion> lista = filtros.buscarGruposInvPorFacultad(
				Integer.valueOf(entrada.getAsString("facultad")));
		JSONArray salida = new JSONArray();
		for(GrupoInvestigacion p : lista) {
			salida.add(p.toJsonF());
		}
		return salida;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@PostMapping("/facultad/gi/semillero")
	public JSONArray buscarSemillerosPorGIFiltro(@RequestBody JSONObject entrada) {
		List<Semillero> lista = filtros.buscarSemillerosPorGrupoInv(
				Integer.valueOf(entrada.getAsString("gi")));
		JSONArray salida = new JSONArray();
		for(Semillero p : lista) {
			salida.add(p.toJsonF());
		}
		return salida;
	}
	
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/proyecto")
	public JSONArray buscarProyectos() {
		List<Proyecto> lista = filtros.buscarProyectos();
		JSONArray salida = new JSONArray();
		for(Proyecto p : lista) {
			salida.add(p.toJson());
		}
		return salida;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@PostMapping("/proyecto/estado")
	public JSONArray buscarProyectosPorEstado(@RequestBody JSONObject entrada) {
		List<Proyecto> lista = filtros.buscarProyectosPorEstado(entrada.getAsString("estado"));
		JSONArray salida = new JSONArray();
		for(Proyecto p : lista) {
			salida.add(p.toJson());
		}
		return salida;
	}
	
}
