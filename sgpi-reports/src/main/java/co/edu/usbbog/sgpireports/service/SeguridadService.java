package co.edu.usbbog.sgpireports.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeguridadService implements ISeguridadService {

	@Autowired
	private HttpServletRequest request;
	private final int key = 9856;

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

	public void encriptar(File salida) throws IOException {
		FileInputStream fis = new FileInputStream(salida);
		byte data[] = new byte[fis.available()];
		fis.read(data);
		int i = 0;
		for (byte b : data) {
			data[i] = (byte) (b ^ key);
			i++;
		}
		fis.close();
		FileOutputStream fos = new FileOutputStream(salida);
		fos.write(data);
		fos.close();
		System.out.println("Encryption Done...");
	}

	public byte[] descrifrar2(File entrada) throws IOException {
		FileInputStream fis = new FileInputStream(entrada);
		byte data[] = new byte[fis.available()];
		fis.read(data);
		int i = 0;
		for (byte b : data) {
			data[i] = (byte) (b ^ key);
			i++;
		}
		fis.close();
		return data;
	}

}
