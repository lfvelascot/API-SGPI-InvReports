package co.edu.usbbog.sgpireports.service;

import java.io.IOException;
import net.sf.jasperreports.engine.JRException;


public interface IReportesService  {
	
	public String crearReporteSemillero(int cc, String usuario) throws JRException, IOException;
	public String crearReporGI(int cc, String usuario) throws JRException, IOException;
	public String crearReporteIntegrantesGI(int cc, String usuario) throws JRException, IOException;
	public String crearReporteIntegrantesSemillero(int cc, String usuario) throws JRException, IOException;
	public String crearReporteProduccionSemillero(int cc, String usuario) throws JRException, IOException;
	public String crearReporteProduccionGI(int cc, String usuario) throws JRException, IOException;
	public String crearReporteParticipacionEventosSemillero(int cc, String usuario) throws JRException, IOException;
	public String crearReporteParticipacionEventosGI(int cc, String usuario) throws JRException, IOException;
	public String crearReporteParticipacionConvSemillero(int cc, String usuario) throws JRException, IOException;
	public String crearReporteParticipacionConvsGI(int cc, String usuario) throws JRException, IOException;
	public String crearReporteProyectosActivosSemillero(int cc, String usuario) throws JRException, IOException;
	public String crearReporteProyectosActivosGI(int cc, String usuario) throws JRException, IOException;
	public String crearReporteProyectosFinSemillero(int cc, String usuario) throws JRException, IOException;
	public String crearReporteProyectosFinGI(int cc, String usuario) throws JRException, IOException;
	public String crearReporteGIsPorFacultad(int cc, String usuario) throws JRException, IOException;
	public String crearReporteProyectoDetallado (int cc, String usuario) throws JRException, IOException;
	public String crearReporteProyectosConvAbiertassGI(int cc, String usuario) throws JRException, IOException;
	public String crearReporteProyectosConvAbiertasSemillero(int cc, String usuario) throws JRException, IOException;
	public String crearReporteSemillerosxPrograma(int cc, String usuario) throws JRException, IOException;
}
