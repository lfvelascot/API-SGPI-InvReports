package co.edu.usbbog.sgpireports.service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import net.sf.jasperreports.engine.JRException;

public interface IFileStorageService {
	
	public void init();

	public boolean save(MultipartFile file, String usuario);

	public Resource loadF(String filename);
	
	public Resource loadI(String filename);
	
	public String saveReporte(int cc, int rep, String usuario, Map<String, Object> datos) throws FileNotFoundException, JRException, MalformedURLException;
}