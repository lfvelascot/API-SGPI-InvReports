package co.edu.usbbog.sgpireports.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.usbbog.sgpireports.service.IGestionUsuariosService;
import net.minidev.json.JSONObject;
import co.edu.usbbog.sgpireports.message.ResponseMessage;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.service.FileStorageService;

@RestController
@CrossOrigin
@RequestMapping("/archivo")
public class FileController {

	@Autowired
	FileStorageService storageService;
	@Autowired
	private IGestionUsuariosService iGestionUsuariosService;

	public Logger logger = LoggerFactory.getLogger(FileController.class);

	// metodo utilizado para cargar el archivo en el aplicativo
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> cargarFirma(@RequestParam("file") MultipartFile file,
			@RequestParam("usuario") String usuario) {
		String message = "ERROR CARGA : El tamaño o formato del archivo no es valido";
		Usuario user = iGestionUsuariosService.buscarUsuario(usuario);
		if (user != null) {
			try {
				if (user.getFirma() == null) {
					if (storageService.save(file, usuario)) {
						message = "OK 1";
						iGestionUsuariosService.saveFirma(usuario);
						return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
					} else {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(message));
					}
				} else {
					if (storageService.update(file, usuario)) {
						message = "OK 2";
						return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
					} else {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(message));
					}
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}

	}

	// metodo que obtendra el nombre del archivo
	@PostMapping("/get/firma")
	@ResponseBody
	public ResponseEntity<Resource> getFileF(@RequestBody JSONObject entrada) {
		Usuario user = iGestionUsuariosService.buscarUsuario(entrada.getAsString("cc"));
		if (user.getFirma() != null) {
			Resource file = storageService.loadF(user.getFirma().getNombre());
			if (file != null) {
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
						.body(file);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// metodo que obtendra el nombre del archivo
	@PostMapping("/get/reporte")
	@ResponseBody
	public ResponseEntity<Resource> getFileR(@RequestBody JSONObject entrada) {
		Resource file = storageService.loadR(entrada.getAsString("reporte"));
		if (file != null) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// metodo que obtendra el nombre del archivo
	@GetMapping("/files/i/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFileI(@PathVariable String filename) {
		Resource file = storageService.loadI(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@GetMapping("/files/f/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFileFirma(@PathVariable String filename) {
		Resource file = storageService.loadF(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	// metodo que obtendra el nombre del archivo
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
