package co.edu.usbbog.sgpireports.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

@Service
public class SeguridadService implements ISeguridadService {

	@Autowired
	private HttpServletRequest request;
	private final int key = 9856;
	private final List<String> keys1 = new ArrayList<>(Arrays.asList("dato", "reporte", "usuario"));
	private final List<String> keys2 = new ArrayList<>(Arrays.asList("dato", "reporte", "usuario", "inicio", "fin"));

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

	@Override
	public boolean validateJSON(int i, JSONObject entrada) {
		var keys = entrada.keySet();
		var values = entrada.values();
		switch (i) {
		case 0:
			if (keys.size() != 3) {
				return false;
			} else {
				for (String key : keys) {
					if (!keys1.contains(key)) {
						return false;
					}
				}
				for (Object value : values) {
					String v = String.valueOf(value);
					if (v.length() != 0) {
						if (!v.chars().allMatch(Character::isDigit)) {
							return false;
						}
					} else {
						return false;
					}

				}
				return true;
			}
		case 1:
			if (keys.size() != 5) {
				return false;
			} else {
				for (String key : keys) {
					if (!keys2.contains(key)) {
						return false;
					}
				}
				for (Object value : values) {
					String v = String.valueOf(value);
					if (v.length() != 0) {
						if (!v.chars().allMatch(Character::isDigit)) {
							return false;
						}
					} else {
						return false;
					}
				}
				return true;
			}
		default:
			return false;
		}
	}

}
