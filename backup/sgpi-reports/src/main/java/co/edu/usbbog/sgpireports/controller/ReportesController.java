package co.edu.usbbog.sgpireports.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

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
@CrossOrigin(origins = { "http://localhost:3000" })
@RequestMapping("/report")
public class ReportesController {

	@Autowired
	private IReportesService reportes;
	@Autowired
	private HttpServletRequest request;

	/**
	 * @return booleano validando que la peticion sea hecha exclusivamente del mismo servidor
	 */
	private boolean isValid() {
		String ipAddress = request.getHeader("X-Forward-For");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress.equals("0:0:0:0:0:0:0:1");
	}

	/**
	 * Genera los reportes (23 en total)
	 * @param entrada JSON con tres datos: usuario, numero de reporte y dato para el
	 *                reporte
	 * @return salida2 JSON con nombre del archivo generado
	 * @throws IOException Lectura y escritura del archivo generado para el reporte
	 * @throws JRException errores de Jasper Report
	 */
	@PostMapping("/generar")
	public JSONObject generarReporte(@RequestBody JSONObject entrada) throws JRException, IOException {
		JSONObject salida2 = new JSONObject();
		if (isValid()) {
			try {
				salida2.put("nombreDocumento", reportes.crearReporte(
						Integer.valueOf(entrada.getAsString("dato")),
						Integer.valueOf(entrada.getAsString("reporte")),
						entrada.getAsString("usuario")));
			} catch(NumberFormatException e ) {
				return null;
			}
			
		}
		return salida2;
	}

}
