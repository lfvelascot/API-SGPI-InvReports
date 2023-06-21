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
@CrossOrigin(origins = { "http://backend-node:3000" })
@RequestMapping("/report")
public class ReportesController {

	@Autowired
	private IReportesService reportes;
	@Autowired
	private HttpServletRequest request;

	/**
	 * @return booleano validando que la peticion sea hecha exclusivamente del mismo
	 *         servidor
	 */
	private boolean isValid() {
		String ipAddress = request.getHeader("X-Forward-For");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("127.0.0.1")
				|| request.getRemoteHost().contains("backend-sgpi");
	}

	/**
	 * Genera los reportes (23 en total)
	 * 
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
				int rep = Integer.valueOf(entrada.getAsString("reporte"));
				if (rep < 23) {
					salida2.put("nombreDocumento", reportes.crearReporte(Integer.valueOf(entrada.getAsString("dato")),
							rep, entrada.getAsString("usuario")));
				}
			} catch (NumberFormatException e) {
				return null;
			}

		}
		return salida2;
	}

	/**
	 * Genera los reportes con parametros de años (2 en total)
	 * 
	 * @param entrada JSON con 5 datos: usuario, numero de reporte, año de inicio y
	 *                año de fin y dato para el reporte
	 * @return salida2 JSON con nombre del archivo generado
	 * @throws IOException Lectura y escritura del archivo generado para el reporte
	 * @throws JRException errores de Jasper Report
	 */
	@PostMapping("/generar/anios")
	public JSONObject generarReporteAnios(@RequestBody JSONObject entrada) throws JRException, IOException {
		JSONObject salida2 = new JSONObject();
		if (isValid()) {
			try {
				int rep = Integer.valueOf(entrada.getAsString("reporte")),
						inicio = Integer.valueOf(entrada.getAsString("inicio")),
						fin = Integer.valueOf(entrada.getAsString("fin"));
				if (rep >= 23 && rep < 27) {
					if (fin >= inicio) {
						salida2.put("nombreDocumento",
								reportes.crearReporteAnios(Integer.valueOf(entrada.getAsString("dato")), rep,
										entrada.getAsString("usuario"), inicio, fin));
					} else {
						salida2.put("nombreDocumento", "Año de fin menor al año de inicio");
					}

				}
			} catch (NumberFormatException e) {
				return null;
			}

		}
		return salida2;
	}

}
