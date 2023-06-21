package co.edu.usbbog.sgpireports.controller;

import javax.servlet.http.HttpServletRequest;

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

import co.edu.usbbog.sgpireports.message.*;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.service.FileStorageService;
import co.edu.usbbog.sgpireports.service.IGestionUsuariosService;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/archivo")
public class FileController {

	@Autowired
	FileStorageService storageService;
	@Autowired
	private IGestionUsuariosService iGestionUsuariosService;

	@Autowired
	private HttpServletRequest request;

	/**
	 * Valida el acceso a las peticiones para hacerlos solo desde el equipo local
	 * @return boolean validando el permiso de acceso desde la IP remota del cliente
	 */
	private boolean isValid() {
		String ipAddress = request.getHeader("X-Forward-For");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("127.0.0.1");
	}
	/**
	 * Carga de la firma de los usuarios
	 * @param nombre del archivo
	 * @return Respuesta HTTP 
	 */
	// metodo utilizado para cargar el archivo en el aplicativo
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> cargarFirma(@RequestParam("file") MultipartFile file,
			@RequestParam("usuario") String usuario) {
		if (isValid()) {
			String message = "ERROR CARGA : El tamaño o formato del archivo no es valido";
			Usuario user = iGestionUsuariosService.buscarUsuario(usuario);
			ResponseEntity<ResponseMessage> salida = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			if (user != null) {
				try {
					if (user.getFirma() == null) {
						if (storageService.save(file, usuario)) {
							iGestionUsuariosService.saveFirma(usuario);
							return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Firma cargada"));
						} else {
							return salida;
						}
					} else {
						if (storageService.update(file, usuario)) {
							return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Firma actualizada"));
						} else {
							return salida;
						}
					}
				} catch (Exception e) {
					return salida;
				}
			} else {
				return salida;
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(""));
		}
	}

	/**
	 * Extraer las firmas recibidas de los usuarios
	 * @param nombre del archivo
	 * @return archivo para su descarga o despliegue
	 */
	// metodo que obtendra el nombre del archivo
	@GetMapping("/get/firma")
	@ResponseBody
	public ResponseEntity<Resource> getFileF(@RequestBody JSONObject entrada) {
		if (isValid()) {
			Usuario user = iGestionUsuariosService.buscarUsuario(entrada.getAsString("cc"));
			if (user.getFirma() != null) {
				try {
					Resource file = storageService.loadF(user.getFirma().getNombre());
					if (file != null) {
						return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
								"attachment; filename=\"" + file.getFilename() + "\"").body(file);
					} else {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
					}
				} catch (Exception e) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	/**
	 * Extraer los archivo de los reportes generados
	 * @param nombre del archivo
	 * @return archivo para su descarga o despliegue
	 */
	@GetMapping("/get/reporte")
	@ResponseBody
	public ResponseEntity<Resource> getFileR(@RequestBody JSONObject entrada) {
		if (isValid()) {
			Resource file = storageService.loadR(entrada.getAsString("reporte"));
			if (file != null) {
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
						.body(file);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	/**
	 * Extraer las imagenes alojadas en la aplicación
	 * @param nombre del archivo
	 * @return archivo para su descarga o despliegue
	 */
	@GetMapping("/files/i/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFileI(@PathVariable String filename) {
		if (isValid()) {
			Resource file = storageService.loadI(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	/**
	 * Extraer las firmas recibidas de los usuarios
	 * @param nombre del archivo
	 * @return archivo para su descarga o despliegue
	 */
	@GetMapping("/files/f/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFileFirma(@PathVariable String filename) {
		if (isValid()) {
			Resource file = storageService.loadF(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

}
