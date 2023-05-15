package co.edu.usbbog.sgpireports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.usbbog.sgpireports.service.IGestionUsuariosService;
import co.edu.usbbog.sgpireports.message.ResponseMessage;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.service.FileStorageService;
import co.edu.usbbog.sgpireports.service.GesionUsuariosService;

@RestController
@CrossOrigin
@RequestMapping("/archivo")
public class FileController {

	@Autowired
	FileStorageService storageService;
	@Autowired
	private IGestionUsuariosService iGestionUsuariosService;
	
	// metodo utilizado para cargar el archivo en el aplicativo
		@PostMapping("/upload")
		public ResponseEntity<ResponseMessage> uploadFirma(@RequestParam("file") MultipartFile file,@RequestParam("usuario") String usuario) {
			String message = "No se pudo cargar";
			Usuario user = iGestionUsuariosService.buscarUsuario(usuario);
			if(user != null) {
				try {
					if (user.getFirma() == null) {
						if(storageService.save(file,usuario)) {
							String nombreArchivo = "Firma-"+usuario+".png";
							message = "http://localhost:8081/archivo/files/"+nombreArchivo;
							iGestionUsuariosService.saveFirma(usuario, nombreArchivo);
							return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
						} else {
							return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(message));
						}
					} else {
						if(storageService.update(file,usuario)) {
							String nombreArchivo = "Firma-"+usuario+".png";
							message = "http://localhost:8081/archivo/files/" + nombreArchivo;
							return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
						} else {
							return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(message));
						}					}
				} catch (Exception e) {
					message = "Could not upload the file: " + file.getOriginalFilename() + "!";
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
				}
			} else {
				message = "El usuario no existe";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
			
		}
		
		// metodo que obtendra el nombre del archivo
		@GetMapping("/files/f/{filename:.+}")
		@ResponseBody
		public ResponseEntity<Resource> getFileF(@PathVariable String filename) {
			Resource file = storageService.loadF(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
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
