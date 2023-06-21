package co.edu.usbbog.sgpireports;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.edu.usbbog.sgpireports.service.FileStorageService;

@SpringBootApplication
public class SgpiReportsApplication implements CommandLineRunner {

	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(SgpiReportsApplication.class, args);
	}

	// garantiza que existan las carpetas requeridas para la manipulación de archivos
	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}

}
