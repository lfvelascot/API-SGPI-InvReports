package co.edu.usbbog.sgpireports.service;

import java.io.IOException;
import net.sf.jasperreports.engine.JRException;


public interface IReportesService  {
	
	public String crearReporte(int cc, int rep, String usuario) throws JRException, IOException;
}
