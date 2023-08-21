package co.edu.usbbog.sgpireports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Log;
import co.edu.usbbog.sgpireports.repository.ILogRepository;

@Service
public class GestionLog implements IGestionLog{
	
	@Autowired
	private ILogRepository logs;

	@Override
	public boolean saveData(Log log) {
		return logs.save(log) != null;
	}

	@Override
	public List<Log> getLogsByUser(String cc) {
		return logs.findLogsByUser(cc);
	}

	@Override
	public List<Log> getAllLogs() {
		return logs.findAll();
	}

	@Override
	public List<Log> getLogsByAccion(String accion) {
		return logs.findLogsByAccion(accion);
	}

}
