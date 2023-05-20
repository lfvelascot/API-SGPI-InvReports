package co.edu.usbbog.sgpireports.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbbog.sgpireports.service.IReportesService;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;

@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportesController {
	
	@Autowired
	private IReportesService reportes;
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/semillero/{cc}/{usuario}")
	public JSONObject reporteSemillero(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteSemillero(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/gi/{cc}/{usuario}")
	public JSONObject reporteGI(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporGI(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/gi/i/{cc}/{usuario}")
	public JSONObject reporteGIIntegrantes(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteIntegrantesGI(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/semillero/i/{cc}/{usuario}")
	public JSONObject reporteSemilleroIntegrantes(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteIntegrantesSemillero(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/semillero/prod/{cc}/{usuario}")
	public JSONObject reporteSemilleroProduccion(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteProduccionSemillero(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/semillero/pact/{cc}/{usuario}")
	public JSONObject reporteSemilleroProyActivos(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteProyectosActivosSemillero(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/gi/pact/{cc}/{usuario}")
	public JSONObject reporteGIProyActivos(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteProyectosActivosGI(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/gi/prod/{cc}/{usuario}")
	public JSONObject reporteGIProduccion(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteProduccionGI(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/semillero/pfin/{cc}/{usuario}")
	public JSONObject reporteSemilleroProyFin(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteProyectosFinSemillero(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/gi/pfin/{cc}/{usuario}")
	public JSONObject reporteGIProyFin(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteProyectosFinGI(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/facultad/{cc}/{usuario}")
	public JSONObject reporteGIxFacultad(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteGIsPorFacultad(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/semillero/pconv/{cc}/{usuario}")
	public JSONObject reporteSemilleroProyConvocatorias(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteParticipacionConvSemillero(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/gi/pconv/{cc}/{usuario}")
	public JSONObject reporteGIProyConvocatorias(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteParticipacionConvsGI(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/semillero/peventos/{cc}/{usuario}")
	public JSONObject reporteSemilleroProyEventos(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteParticipacionEventosSemillero(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@GetMapping("/gi/pceventos/{cc}/{usuario}")
	public JSONObject reporteGIProyEventos(@PathVariable int cc,@PathVariable String usuario) throws JRException, IOException {
		String lista = reportes.crearReporteParticipacionEventosGI(cc, usuario);
		JSONObject salida2 = new JSONObject();
		salida2.put("lista", lista);
		return salida2;
	}

}
