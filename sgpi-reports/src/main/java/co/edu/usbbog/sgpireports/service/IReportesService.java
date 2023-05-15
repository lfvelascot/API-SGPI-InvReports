package co.edu.usbbog.sgpireports.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import co.edu.usbbog.sgpireports.model.MiembrosSemillero;
import co.edu.usbbog.sgpireports.model.Proyecto;
import net.sf.jasperreports.engine.JRException;


public interface IReportesService  {
	
	public List<MiembrosSemillero> getMiembrosSemillero(int cc);
	public String crearReporteSemillero(int cc, String usuario) throws JRException, IOException;
	public Map <String,Object> getDatosSemillero(int cc);
	public String crearReporGI(int cc, String usuario) throws FileNotFoundException, JRException;
}
