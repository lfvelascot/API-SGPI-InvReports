package co.edu.usbbog.sgpireports.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	 * Genera los reportes 
	 * 
	 * @param entrada
	 * @return salida2
	 * @throws FileNotFoundException 
	 * @throws JRException 
	 */
	@PostMapping("/generar")
	public JSONObject generarReporte(@RequestBody JSONObject entrada) throws JRException, IOException {
		String salida = reportes.crearReporte(Integer.valueOf(entrada.getAsString("dato")),
				Integer.valueOf(entrada.getAsString("reporte")),
				entrada.getAsString("usuario"));
		JSONObject salida2 = new JSONObject();
		salida2.put("nombreDocumento", salida);
		return salida2;
	}

}
