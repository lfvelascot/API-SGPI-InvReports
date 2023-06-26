package co.edu.usbbog.sgpireports.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import co.edu.usbbog.sgpireports.service.IFileStorageService;
import co.edu.usbbog.sgpireports.service.IGestionUsuariosService;
import co.edu.usbbog.sgpireports.service.ISeguridadService;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin(origins = { "http://backend-node:3000", "http://localhost:3000" })
@RequestMapping("/archivo")
public class FileController {

	@Autowired
	private IFileStorageService storageService;
	@Autowired
	private IGestionUsuariosService iGestionUsuariosService;
	@Autowired
	private ISeguridadService seguridad;
	@Autowired
	private Response respuestas;
	/**
	 * Carga de la firma de los usuarios
	 * 
	 * @param nombre del archivo
	 * @return Respuesta HTTP
	 */
	// metodo utilizado para cargar el archivo en el aplicativo
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> cargarFirma(@RequestParam("file") MultipartFile file,
			@RequestParam("usuario") String usuario) {
		if (seguridad.isValid()) {
			String respuesta = storageService.save(file, usuario);
			return (respuesta == "Firma cargada correctamente" || respuesta == "Firma actualizada correctamente")
					? respuestas.getRespuestaMensaje(respuesta, 0)
					: respuestas.getRespuestaMensaje(respuesta, 1);
		} else {
			return respuestas.getRespuestaMensaje("", 3);
		}
	}

	/**
	 * Extraer las firmas recibidas de los usuarios
	 * 
	 * @param nombre del archivo
	 * @return archivo para su descarga o despliegue
	 * @throws IOException
	 */
	@PostMapping("/get/firma")
	@ResponseBody
	public ResponseEntity<Resource> getFileF(@RequestBody JSONObject entrada) throws IOException {
		if (seguridad.isValid()) {
			Usuario user = iGestionUsuariosService.buscarUsuario(entrada.getAsString("cc"));
			return (user.getFirma() != null)
					? respuestas.sentRespuestaRecurso(storageService.loadF(user.getFirma().getNombre()), 0)
					: respuestas.sentRespuestaRecurso(null, 2);
		} else {
			return respuestas.sentRespuestaRecurso(null, 1);
		}
	}

	/**
	 * Extraer los archivo de los reportes generados
	 * 
	 * @param nombre del archivo
	 * @return archivo para su descarga o despliegue
	 */
	@GetMapping("/get/reporte/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFileR(@PathVariable String filename) {
		return (seguridad.isValid())
				? respuestas.sentRespuestaRecurso(storageService.loadR(filename), 0)
				: respuestas.sentRespuestaRecurso(null, 1);
	}

	/**
	 * Extraer las imagenes alojadas en la aplicación
	 * 
	 * @param nombre del archivo
	 * @return archivo para su descarga o despliegue
	 */
	@GetMapping("/files/i/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFileI(@PathVariable String filename) {
		return (seguridad.isValid()) ? respuestas.sentRespuestaRecurso(storageService.loadI(filename), 0)
				: respuestas.sentRespuestaRecurso(null, 1);
	}

	/**
	 * Extraer las firmas recibidas de los usuarios
	 * 
	 * @param nombre del archivo
	 * @return archivo para su descarga o despliegue
	 */
	@GetMapping("/files/f/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFileFirma(@PathVariable String filename) {
		return (seguridad.isValid()) ? respuestas.sentRespuestaRecurso(storageService.loadF(filename), 0)
				: respuestas.sentRespuestaRecurso(null, 1);
	}

}
