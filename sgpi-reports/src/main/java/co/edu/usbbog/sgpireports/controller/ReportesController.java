package co.edu.usbbog.sgpireports.controller;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbbog.sgpireports.model.Log;
import co.edu.usbbog.sgpireports.service.IGestionLog;
import co.edu.usbbog.sgpireports.service.IReportesService;
import co.edu.usbbog.sgpireports.service.ISeguridadService;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;

@RestController
@CrossOrigin(origins = { "http://backend-node:3000", "http://localhost:3000", "http://localhost:5173",
		"https://tecnosoft.ingusb.com" })
@RequestMapping("/report")
public class ReportesController {

	@Autowired
	private IReportesService reportes;
	@Autowired
	private ISeguridadService seguridad;
	@Autowired
	private IGestionLog logs;

	/**
	 * Genera los reportes (23 en total)
	 * 
	 * @param entrada JSON con tres datos: usuario, numero de reporte y dato para
	 *                elreporte
	 * @return salida2 JSON con nombre del archivo generado
	 * @throws IOException Lectura y escritura del archivo generado para el reporte
	 * @throws JRException errores de Jasper Report
	 */
	@PostMapping("/generar")
	public JSONObject generarReporte(@RequestBody JSONObject entrada) throws JRException, IOException {
		JSONObject salida = new JSONObject();
		if (seguridad.isValid() && seguridad.validateJSON(0, entrada)) {
			try {
				int rep = Integer.valueOf(entrada.getAsString("reporte"));
				if (rep < 23) {
					salida.put("nombreDocumento", reportes.crearReporte(Integer.valueOf(entrada.getAsString("dato")),
							rep, entrada.getAsString("usuario")));
				} else {
					salida.put("nombreDocumento", "ID reporte fuera de rango" + String.valueOf(rep));
				}
			} catch (Exception e) {
				salida.put("nombreDocumento", e.getMessage());
			}
		} else {
			salida.put("nombreDocumento", "N/A");
			;
		}
		Log log = new Log(getNow(), "generarReporte", "Generar reporte #" + entrada.getAsString("reporte")
				+ " Dato: " + entrada.getAsString("dato") + " Respuesta: " + salida.getAsString("nombreDocumento"));
		log.setUsuario(entrada.getAsString("usuario"));
		logs.saveData(log);
		return salida;
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

		if (seguridad.isValid() && seguridad.validateJSON(1, entrada)) {
			try {
				int rep = Integer.valueOf(entrada.getAsString("reporte")),
						inicio = Integer.valueOf(entrada.getAsString("inicio")),
						fin = Integer.valueOf(entrada.getAsString("fin"));
				if (rep >= 23 && rep < 27 && fin >= inicio) {
					salida2.put("nombreDocumento",
							reportes.crearReporteAnios(Integer.valueOf(entrada.getAsString("dato")), rep,
									entrada.getAsString("usuario"), inicio, fin));
				} else {
					salida2.put("nombreDocumento", "Año de fin menor al año de inicio/ID reporte fuera de rango");
				}
			} catch (Exception e) {
				salida2.put("nombreDocumento", e.getMessage());
			}
		} else {
			salida2.put("nombreDocumento", "N/A");
		}
		Log log = new Log(getNow(), "generarReporte",
				"Generar reporte #" + entrada.getAsString("reporte") + " Dato: " + entrada.getAsString("dato")
						+ " periodo: " + entrada.getAsString("inicio") + "-" + entrada.getAsString("fin")
						+ " Respuesta: " + salida2.getAsString("nombreDocumento"));
		log.setUsuario(entrada.getAsString("usuario"));
		logs.saveData(log);
		return salida2;
	}
	
	public LocalDateTime getNow() {
		return LocalDateTime.now().minusHours(5);
	}

}
