package co.edu.usbbog.sgpireports.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeguridadService implements ISeguridadService{
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * Valida el acceso a las peticiones para hacerlos solo desde el equipo local
	 * 
	 * @return boolean validando el permiso de acceso desde la IP remota del cliente
	 */
	public boolean isValid() {
		String ipAddress = request.getHeader("X-Forward-For");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("127.0.0.1")
				|| request.getRemoteHost().contains("backend-node");
	}


}
