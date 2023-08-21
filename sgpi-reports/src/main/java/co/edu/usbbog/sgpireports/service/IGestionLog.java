package co.edu.usbbog.sgpireports.service;

import java.util.List;

import co.edu.usbbog.sgpireports.model.Log;

public interface IGestionLog {
	
	public boolean saveData(Log log);
	
	public List<Log> getLogsByUser(String cc);
	
	
	public List<Log> getAllLogs();
	
	public List<Log> getLogsByAccion(String accion);
	

}
