package co.edu.usbbog.sgpi;

import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.edu.usbbog.sgpi.service.FilesStorageService;

@SpringBootApplication
public class SgpiwsApplication implements CommandLineRunner{
	  @Resource
	  FilesStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(SgpiwsApplication.class, args);
	}
	@Override
	  public void run(String... arg) throws Exception {
	    
	    storageService.init();
	  }

}
