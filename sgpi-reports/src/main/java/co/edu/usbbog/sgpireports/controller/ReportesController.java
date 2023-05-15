package co.edu.usbbog.sgpireports.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

}
