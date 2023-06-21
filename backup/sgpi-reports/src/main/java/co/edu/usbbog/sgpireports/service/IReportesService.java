package co.edu.usbbog.sgpireports.service;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;

public interface IReportesService {
	
	// Generación del reporte con el dato, tipo de reporte y cedula del usuario
	public String crearReporte(int cc, int rep, String usuario) throws JRException, IOException;
}
