package co.edu.usbbog.sgpireports.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbbog.sgpireports.service.IGestionBusquedas;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin(origins = { "http://backend-node:3000","http://localhost:3000" })
@RequestMapping("/info")
public class BusquedasController {

	@Autowired
	private IGestionBusquedas busqueda;
	@Autowired
	private HttpServletRequest request;

	/**
	 * Valida el acceso a las peticiones para hacerlos solo desde el equipo local
	 * 
	 * @return boolean validando el permiso de acceso desde la IP remota del cliente
	 */
	private boolean isValid() {
		String ipAddress = request.getHeader("X-Forward-For");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("127.0.0.1")
				|| request.getRemoteHost().contains("backend-node");
	}

	/**
	 * Busca los semilleros de un programa
	 * 
	 * @param entrada JSON con dato del ID de un programa
	 * @return JSONArray con los datos de los semilleros
	 */
	@PostMapping("/semillero/programa")
	public JSONArray buscarSemillerosPorPrograma(@RequestBody JSONObject entrada) {
		if (isValid()) {
			return busqueda.semillerosToJSONArray(
					busqueda.buscarSemillerosPorPrograma(Integer.valueOf(entrada.getAsString("programa"))));
		} else {
			return null;
		}
	}

	/**
	 * Busca los semilleros
	 * 
	 * @return JSONArray con los datos de los semilleros
	 */
	@GetMapping("/semillero")
	public JSONArray buscarSemilleros() {
		if (isValid()) {
			return busqueda.semillerosToJSONArray(busqueda.buscarSemilleros());
		} else {
			return null;
		}
	}

	/**
	 * Busca los semilleros de un grupo de investigación
	 * 
	 * @param entrada JSON con dato del ID del grupo de investigación
	 * @return JSONArray con los IDs y nombres de los semilleros
	 */
	@GetMapping("/gi")
	public JSONArray buscarGIs() {
		if (isValid()) {
			return busqueda.gruposToJSONArray(busqueda.buscarGruposInv());
		} else {
			return null;
		}
	}

	/**
	 * Busca los semilleros de un grupo de investigación
	 * 
	 * @param entrada JSON con dato del ID del grupo de investigación
	 * @return JSONArray con los IDs y nombres de los semilleros
	 */
	@PostMapping("/gi/facultad")
	public JSONArray buscarGIsxFacultad(@RequestBody JSONObject entrada) {
		if (isValid()) {
			return busqueda.gruposToJSONArray(
					busqueda.buscarGruposInvPorFacultad(Integer.valueOf(entrada.getAsString("facultad"))));
		} else {
			return null;
		}
	}

	/**
	 * Busca proyectos publicos
	 * 
	 * @return JSONArray con los datos de los proyectos
	 */
	@GetMapping("/proyecto")
	public JSONArray buscarProyectos() {
		if (isValid()) {
			return busqueda.proyectosToJSONArray(busqueda.buscarProyectos());
		} else {
			return null;
		}
	}

	/**
	 * Busca proyectos publicos por su estado
	 * 
	 * @param entrada JSON con dato del estado de los proyectos a buscar
	 * @return JSONArray con los datos de los proyectos
	 */
	@PostMapping("/proyecto/estado")
	public JSONArray buscarProyectosPorEstado(@RequestBody JSONObject entrada) {
		if (isValid()) {
			return busqueda.proyectosToJSONArray(busqueda.buscarProyectosPorEstado(entrada.getAsString("estado")));
		} else {
			return null;
		}
	}

}
