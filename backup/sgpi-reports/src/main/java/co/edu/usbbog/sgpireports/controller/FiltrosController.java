package co.edu.usbbog.sgpireports.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
@CrossOrigin(origins = { "http://localhost:3000" })
@RequestMapping("/info")
public class FiltrosController {

	@Autowired
	private IGestionFiltros filtros;
	@Autowired
	private HttpServletRequest request;

	/**
	 * Valida el acceso a las peticiones para hacerlos solo desde el equipo local
	 * @return boolean validando el permiso de acceso desde la IP remota del cliente
	 */
	private boolean isValid() {
		String ipAddress = request.getHeader("X-Forward-For");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress.equals("0:0:0:0:0:0:0:1");
	}

	/**
	 * Busca los semilleros de un programa
	 * @param entrada JSON con dato del ID de un programa
	 * @return JSONArray con los datos de los semilleros
	 */
	@GetMapping("/semillero/programa")
	public JSONArray buscarSemillerosPorPrograma(@RequestBody JSONObject entrada) {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Semillero> lista = filtros
					.buscarSemillerosPorPrograma(Integer.valueOf(entrada.getAsString("programa")));
			for (Semillero p : lista) {
				salida.add(p.toJson());
			}
		}
		return salida;
	}

	/**
	 * Busca los semilleros
	 * @return JSONArray con los datos de los semilleros
	 */
	@GetMapping("/semillero")
	public JSONArray buscarSemilleros() {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Semillero> lista = filtros.buscarSemilleros();
			for (Semillero p : lista) {
				salida.add(p.toJson());
			}
		}
		return salida;
	}

	/**
	 * Busca los semilleros
	 * @return JSONArray con el id y nombre de los semilleros
	 */
	@GetMapping("/semilleros")
	public JSONArray buscarSemillerosF() {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Semillero> lista = filtros.buscarSemilleros();
			for (Semillero p : lista) {
				salida.add(p.toJsonF());
			}
		}
		return salida;
	}

	/**
	 * Busca las facultades
	 * @return JSONArray con los IDs y nombres de las facultades
	 */
	@GetMapping("/facultad")
	public JSONArray buscarFacultades() {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Facultad> lista = filtros.buscarFacultades();
			for (Facultad p : lista) {
				salida.add(p.toJsonF());
			}
		}
		return salida;
	}

	/**
	 * Busca los programas de una facultad
	 * @param entrada JSON con dato del ID de la facultad
	 * @return JSONArray con los IDs y nombres de los programas
	 */
	@GetMapping("/facultad/programa")
	public JSONArray buscarProgramasPorFacultad(@RequestBody JSONObject entrada) {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Programa> lista = filtros.buscarProgramasPorFacultad(Integer.valueOf(entrada.getAsString("facultad")));
			for (Programa p : lista) {
				salida.add(p.toJsonF());
			}
		}
		return salida;
	}

	/**
	 * Busca los programas de una facultad
	 * @param entrada JSON con dato del ID de la facultad
	 * @return JSONArray con los IDs y nombres de los programas
	 */
	@GetMapping("/programas")
	public JSONArray buscarProgramas() {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Programa> lista = filtros.buscarProgramas();
			for (Programa p : lista) {
				salida.add(p.toJsonF());
			}
		}
		return salida;
	}

	/**
	 * Busca los proyectos de un semillero
	 * @param entrada JSON con dato del ID del semillero
	 * @return JSONArray con los IDs y nombres de los proyectos
	 */
	@GetMapping("/facultad/gi/semillero/proyecto")
	public JSONArray buscarProyectosPorSemillero(@RequestBody JSONObject entrada) {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Proyecto> lista = filtros
					.buscarProyectosPorSemillero(Integer.valueOf(entrada.getAsString("semillero")));
			for (Proyecto p : lista) {
				salida.add(p.toJsonF());
			}
		}
		return salida;
	}

	/**
	 * Busca los grupos de investigación de una facultad
	 * @param entrada JSON con dato del ID de la facultad
	 * @return JSONArray con los IDs y nombres de los grupos de investigación
	 */
	@GetMapping("/facultad/gi")
	public JSONArray buscarGIPorFacultadFiltro(@RequestBody JSONObject entrada) {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<GrupoInvestigacion> lista = filtros
					.buscarGruposInvPorFacultad(Integer.valueOf(entrada.getAsString("facultad")));
			for (GrupoInvestigacion p : lista) {
				salida.add(p.toJsonF());
			}
		}
		return salida;
	}

	/**
	 * Busca los semilleros de un grupo de investigación
	 * @param entrada JSON con dato del ID del grupo de investigación
	 * @return JSONArray con los IDs y nombres de los semilleros
	 */
	@GetMapping("/facultad/gi/semillero")
	public JSONArray buscarSemillerosPorGIFiltro(@RequestBody JSONObject entrada) {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Semillero> lista = filtros.buscarSemillerosPorGrupoInv(Integer.valueOf(entrada.getAsString("gi")));
			for (Semillero p : lista) {
				salida.add(p.toJsonF());
			}
		}
		return salida;
	}

	/**
	 * Busca los semilleros de un grupo de investigación
	 * @param entrada JSON con dato del ID del grupo de investigación
	 * @return JSONArray con los IDs y nombres de los semilleros
	 */
	@GetMapping("/gi")
	public JSONArray buscarGIs() {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<GrupoInvestigacion> lista = filtros.buscarGruposInv();
			for (GrupoInvestigacion p : lista) {
				salida.add(p.toJson());
			}
		}
		return salida;
	}
	/**
	 * Busca los grupos de investigación
	 * @return JSONArray con los IDs y nombres de los grupos de investigación
	 */
	@GetMapping("/gis")
	public JSONArray buscarGIsF() {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<GrupoInvestigacion> lista = filtros.buscarGruposInv();
			for (GrupoInvestigacion p : lista) {
				salida.add(p.toJsonF());
			}
		}
		return salida;
	}

	/**
	 * Busca los semilleros de un grupo de investigación
	 * @param entrada JSON con dato del ID del grupo de investigación
	 * @return JSONArray con los IDs y nombres de los semilleros
	 */
	@GetMapping("/gi/facultad")
	public JSONArray buscarGIsxFacultad(@RequestBody JSONObject entrada) {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<GrupoInvestigacion> lista = filtros
					.buscarGruposInvPorFacultad(Integer.valueOf(entrada.getAsString("facultad")));
			for (GrupoInvestigacion p : lista) {
				salida.add(p.toJson());
			}
		}
		return salida;
	}

	/**
	 * Busca todos proyectos
	 * @return JSONArray con los datos de los proyectos
	 */
	@GetMapping("/proyectos")
	public JSONArray buscarProyectosF() {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Proyecto> lista = filtros.buscarProyectos();
			for (Proyecto p : lista) {
				salida.add(p.toJsonF());
			}
		}
		return salida;
	}

	/**
	 * Busca proyectos publicos
	 * @return JSONArray con los datos de los proyectos
	 */
	@GetMapping("/proyecto")
	public JSONArray buscarProyectos() {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Proyecto> lista = filtros.buscarProyectos();
			for (Proyecto p : lista) {
				salida.add(p.toJson());
			}
		}
		return salida;
	}

	/**
	 * Busca proyectos publicos por su estado
	 * @param entrada JSON con dato del estado de los proyectos a buscar
	 * @return JSONArray con los datos de los proyectos
	 */
	@GetMapping("/proyecto/estado")
	public JSONArray buscarProyectosPorEstado(@RequestBody JSONObject entrada) {
		JSONArray salida = new JSONArray();
		if (isValid()) {
			List<Proyecto> lista = filtros.buscarProyectosPorEstado(entrada.getAsString("estado"));
			for (Proyecto p : lista) {
				salida.add(p.toJson());
			}
		}
		return salida;
	}

}
