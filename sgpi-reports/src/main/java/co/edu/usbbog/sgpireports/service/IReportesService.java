package co.edu.usbbog.sgpireports.service;

import java.io.IOException;

import co.edu.usbbog.sgpireports.service.report.MiselaneaService;
import net.sf.jasperreports.engine.JRException;

public interface IReportesService {
	
	public MiselaneaService extras = new MiselaneaService();
	// Generación del reporte con el dato, tipo de reporte y cedula del usuario
	public String crearReporte(int cc, int rep, String usuario) throws JRException, IOException;
	// Generacion de reportes con parametros de años
	public String crearReporteAnios(int cc, int rep, String usuario, int anioInicio, int anioFin) throws JRException, IOException;
}
