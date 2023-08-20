package co.edu.usbbog.sgpireports.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbbog.sgpireports.service.IGestionFiltros;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin(origins = { "http://backend-node:3000","http://localhost:3000", "http://localhost:5173" ,"https://tecnosoft.ingusb.com" })
@RequestMapping("/filtro")
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
		return ipAddress.equals("0:0:0:0:0:0:0:1") ||
				ipAddress.equals("127.0.0.1") ||
				request.getRemoteHost().contains("backend-node");
	}


	/**
	 * Busca los semilleros
	 * @return JSONArray con el id y nombre de los semilleros
	 */
	@GetMapping("/semilleros")
	public JSONArray buscarSemillerosF() {
		if (isValid()) {
			return filtros.semillerosToJSONArray(filtros.buscarSemilleros());
			
		} else {
			return null;
		}
	}

	/**
	 * Busca las facultades
	 * @return JSONArray con los IDs y nombres de las facultades
	 */
	@GetMapping("/facultad")
	public JSONArray buscarFacultades() {
		if (isValid()) {
			return filtros.facultadesToJSONArray(filtros.buscarFacultades());
		} else {
			return null;
		}
	}

	/**
	 * Busca los programas de una facultad
	 * @param entrada JSON con dato del ID de la facultad
	 * @return JSONArray con los IDs y nombres de los programas
	 */
	@PostMapping("/facultad/programa")
	public JSONArray buscarProgramasPorFacultad(@RequestBody JSONObject entrada) {
		if (isValid()) {
			return filtros.programasToJSONArray(filtros.buscarProgramasPorFacultad(Integer.valueOf(entrada.getAsString("facultad"))));
		} else {
			return null;
		}
	}

	/**
	 * Busca los programas de una facultad
	 * @param entrada JSON con dato del ID de la facultad
	 * @return JSONArray con los IDs y nombres de los programas
	 */
	@GetMapping("/programas")
	public JSONArray buscarProgramas() {
		if (isValid()) {
			return filtros.programasToJSONArray(filtros.buscarProgramas());
		} else {
			return null;
		}
	}

	/**
	 * Busca los proyectos de un semillero
	 * @param entrada JSON con dato del ID del semillero
	 * @return JSONArray con los IDs y nombres de los proyectos
	 */
	@PostMapping("/facultad/gi/semillero/proyecto")
	public JSONArray buscarProyectosPorSemillero(@RequestBody JSONObject entrada) {
		if (isValid()) {
			return filtros.proyectosToJSONArray(filtros
					.buscarProyectosPorSemillero(Integer.valueOf(entrada.getAsString("semillero"))));
		} else {
			return null;
		}
	}

	/**
	 * Busca los grupos de investigación de una facultad
	 * @param entrada JSON con dato del ID de la facultad
	 * @return JSONArray con los IDs y nombres de los grupos de investigación
	 */
	@PostMapping("/facultad/gi")
	public JSONArray buscarGIPorFacultadFiltro(@RequestBody JSONObject entrada) {
		if (isValid()) {
			return filtros.gruposToJSONArray(filtros
					.buscarGruposInvPorFacultad(Integer.valueOf(entrada.getAsString("facultad"))));
		} else {
			return null;
		}
	}

	/**
	 * Busca los semilleros de un grupo de investigación
	 * @param entrada JSON con dato del ID del grupo de investigación
	 * @return JSONArray con los IDs y nombres de los semilleros
	 */
	@PostMapping("/facultad/gi/semillero")
	public JSONArray buscarSemillerosPorGIFiltro(@RequestBody JSONObject entrada) {
		if (isValid()) {
			return filtros.semillerosToJSONArray(filtros.buscarSemillerosPorGrupoInv(Integer.valueOf(entrada.getAsString("gi"))));
		} else {
			return null;
		}
	}

	/**
	 * Busca los grupos de investigación
	 * @return JSONArray con los IDs y nombres de los grupos de investigación
	 */
	@GetMapping("/gis")
	public JSONArray buscarGIsF() {
		if (isValid()) {
			return filtros.gruposToJSONArray(filtros.buscarGruposInv());
		} else {
			return null;
		}
	}


	/**
	 * Busca todos proyectos
	 * @return JSONArray con los datos de los proyectos
	 */
	@GetMapping("/proyectos")
	public JSONArray buscarProyectosF() {
		if (isValid()) {
			return filtros.proyectosToJSONArray(filtros.buscarProyectos());
		} else {
			return null;
		}
	}


}
