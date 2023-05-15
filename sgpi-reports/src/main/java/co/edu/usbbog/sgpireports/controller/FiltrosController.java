package co.edu.usbbog.sgpireports.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/reporte")
public class FiltrosController {
	
	@Autowired
	private IGestionFiltros filtros;
	
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/semilleros/{cc}")
	public JSONArray buscarSemillerosPorPrograma(@PathVariable int cc) {
		List<Semillero> lista = filtros.buscarSemillerosPorPrograma(cc);
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
	@GetMapping("/semilleros/facultad/{cc}")
	public JSONArray buscarSemillerosPorFacultad(@PathVariable int cc) {
		List<Semillero> lista = filtros.buscarSemillerosPorGI(cc);
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
	@GetMapping("/semilleros")
	public JSONArray buscarSemilleros() {
		List<Semillero> lista = filtros.buscarSemilleros();
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
	@GetMapping("/gi/{cc}")
	public JSONArray buscarGIPorPrograma(@PathVariable int cc) {
		List<GrupoInvestigacion> lista = filtros.buscarGruposInvPorPrograma(cc);
		JSONArray salida = new JSONArray();
		for(GrupoInvestigacion p : lista) {
			salida.add(p.toJson());
		}
		return salida;
	}
	
	/**
	 * Lista de Grupos de investigación para filtros
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/gi/g")
	public JSONArray buscarGIs() {
		List<GrupoInvestigacion> lista = filtros.buscarGruposInv();;
		JSONArray salida = new JSONArray();
		for(GrupoInvestigacion p : lista) {
			salida.add(p.toJsonF());
		}
		return salida;
	}
	
	/**
	 * Lista de Grupos de investigación
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/gi")
	public JSONArray buscarGIsGeneral() {
		List<GrupoInvestigacion> lista = filtros.buscarGruposInv();;
		JSONArray salida = new JSONArray();
		for(GrupoInvestigacion p : lista) {
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
	 * Lista de facultades
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/facultad/g")
	public JSONArray buscarFacultadesGeneral() {
		List<Facultad> lista = filtros.buscarFacultades();
		JSONArray salida = new JSONArray();
		for(Facultad p : lista) {
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
	@GetMapping("/programa/{cc}")
	public JSONArray buscarProgramasPorFacultad(@PathVariable int cc) {
		List<Programa> lista = filtros.buscarProgramasPorFacultad(cc);
		JSONArray salida = new JSONArray();
		for(Programa p : lista) {
			salida.add(p.toJsonF());
		}
		return salida;
	}
	
	/**
	 * Lista de programas para filtros
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/programa")
	public JSONArray buscarProgramas() {
		List<Programa> lista = filtros.buscarProgramas();
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
	@GetMapping("/sproyecto/{cc}")
	public JSONArray buscarProyectosPorSemillero(@PathVariable int cc) {
		List<Proyecto> lista = filtros.buscarProyectosPorSemillero(cc);
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
	@GetMapping("/giproyecto/{cc}")
	public JSONArray buscarProyectosPorGI(@PathVariable int cc) {
		List<Proyecto> lista = filtros.buscarProyectosPorGI(cc);
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
	@GetMapping("/gi/f/{cc}")
	public JSONArray buscarGIPorFacultad(@PathVariable int cc) {
		List<GrupoInvestigacion> lista = filtros.buscarGruposInvPorFacultad(cc);
		JSONArray salida = new JSONArray();
		for(GrupoInvestigacion p : lista) {
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
	@GetMapping("/gi/ff/{cc}")
	public JSONArray buscarGIPorFacultadFiltro(@PathVariable int cc) {
		List<GrupoInvestigacion> lista = filtros.buscarGruposInvPorFacultad(cc);
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
	@GetMapping("/estadosp")
	public JSONArray buscarEstadosProyectos() {
		List<String> lista = filtros.buscarEstadoProyectos();
		JSONArray salida = new JSONArray();
		for(int i = 0; i < lista.size();i++) {
			salida.add(lista.get(i));
		}
		return salida;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/proyecto/estado/{cc}")
	public JSONArray buscarProyectosPorEstado(@PathVariable String cc) {
		List<Proyecto> lista = filtros.buscarProyectosPorEstado(cc);
		JSONArray salida = new JSONArray();
		for(Proyecto p : lista) {
			salida.add(p.toJson());
		}
		return salida;
	}
	
}
