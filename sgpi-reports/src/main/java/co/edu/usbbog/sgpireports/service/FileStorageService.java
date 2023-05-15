package co.edu.usbbog.sgpireports.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.edu.usbbog.sgpireports.controller.GestionUsuariosController;
@Service
public class FileStorageService implements IFileStorageService {

	private final Path rootAnexos = Paths.get("uploads");
	private final Path rootFirmas = Paths.get("firmas");
	private final Path rootImagenes = Paths.get("Imagenes");
	
    public Logger logger = LoggerFactory.getLogger(FileStorageService.class);

	@Override
	public void init() {
		try {
			if (!Files.exists(rootAnexos)) {
				Files.createDirectory(rootAnexos);
			}
			if (!Files.exists(rootFirmas)) {
				Files.createDirectory(rootFirmas);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public boolean save(MultipartFile file, String usuario) {
		try {
			String nombre = file.getOriginalFilename();
			if (nombre.contains(".png") && file.getSize() < 2097153) {
				logger.info("Si entro "+ nombre);
				Files.copy(file.getInputStream(), this.rootFirmas.resolve("Firma-"+usuario+".png"));
				return true;
			} else {
				logger.info("No entro "+nombre);
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	//metodo que indicara si no fue posible leer el archivo
	  @Override
	  public Resource load(String filename) {
	    try {
	      Path file = rootAnexos.resolve(filename);
	      Resource resource = new UrlResource(file.toUri());
	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Could not read the file!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	  }
	  
		//metodo que indicara si no fue posible leer el archivo
	  @Override
	  public Resource loadF(String filename) {
	    try {
	      Path file = rootFirmas.resolve(filename);
	      Resource resource = new UrlResource(file.toUri());
	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Could not read the file!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	  }
	  

	  public Resource loadI(String filename) {
	    try {
	      Path file = rootImagenes.resolve(filename);
	      Resource resource = new UrlResource(file.toUri());
	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Could not read the file!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	  }

	  
	//metodo que indica que no fue posible cargar el archivo
	  @Override
	  public Stream<Path> loadAll() {
	    try {
	      return Files.walk(this.rootAnexos, 1).filter(path -> !path.equals(this.rootAnexos)).map(this.rootAnexos::relativize);
	    } catch (IOException e) {
	      throw new RuntimeException("Could not load the files!");
	    }
	  }

	public boolean update(MultipartFile file, String usuario) {
		try {
			Files.deleteIfExists(this.rootFirmas.resolve("Firma-"+usuario+".png"));
			save(file,usuario);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
