package co.edu.usbbog.sgpireports.service;

import java.io.File;
import java.io.IOException;

public interface ISeguridadService {
	
	public boolean isValid();
	public void encriptar(File salida) throws IOException;
	public byte[] descrifrar2(File entrada) throws IOException;
}
