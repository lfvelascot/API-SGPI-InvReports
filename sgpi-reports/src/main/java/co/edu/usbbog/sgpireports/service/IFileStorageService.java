package co.edu.usbbog.sgpireports.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
	  public void init();

	  public boolean save(MultipartFile file, String usuario);

	  public Resource load(String filename);

	  public Stream<Path> loadAll();
	  
	  public Resource loadF(String filename);
}
