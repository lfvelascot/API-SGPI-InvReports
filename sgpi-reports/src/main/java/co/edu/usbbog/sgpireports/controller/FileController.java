package co.edu.usbbog.sgpireports.controller;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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

import co.edu.usbbog.sgpireports.message.*;
import co.edu.usbbog.sgpireports.model.Log;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.service.IFileStorageService;
import co.edu.usbbog.sgpireports.service.IGestionLog;
import co.edu.usbbog.sgpireports.service.IGestionUsuariosService;
import co.edu.usbbog.sgpireports.service.ISeguridadService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;    
@RestController
@CrossOrigin(origins = { "http://backend-node:3000", "http://localhost:3000", "http://localhost:5173","https://tecnosoft.ingusb.com" })
@RequestMapping("/archivo")
public class FileController {

	@Autowired
	private IFileStorageService storageService;
	@Autowired
	private IGestionUsuariosService iGestionUsuariosService;
	@Autowired
	private ISeguridadService seguridad;
	@Autowired
	private IGestionLog logs;
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
			Log log = new Log(getNow(),"cargarFirma" ,"Cargar firma: "+usuario+" Respuesta: "+ respuesta);
			log.setUsuario(usuario);
			logs.saveData(log);
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
	@GetMapping("/get/firma/{cc:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFileF(@PathVariable String cc) throws IOException {
		Log log = new Log(getNow(),"getFileR" ,"Obtener archivo firma usuario: "+cc);
		log.setUsuario(cc);
		logs.saveData(log);
		if (seguridad.isValid()) {
			try {
				Usuario user = iGestionUsuariosService.buscarUsuario(cc);
				return (user.getFirma() != null)
						? respuestas.sentRespuestaRecurso(storageService.loadF(user.getFirma().getNombre()), 0)
						: respuestas.sentRespuestaRecurso(null, 2);
			} catch (EntityNotFoundException e) {
				return respuestas.sentRespuestaRecurso(null, 1);
			}
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
		Log log = new Log(getNow(),"getFileR" ,"Obtener archivo reporte: "+filename);
		logs.saveData(log);
		return (seguridad.isValid()) ? respuestas.sentRespuestaRecurso(storageService.loadR(filename), 0)
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
		logs.saveData(new Log(getNow(),"getFileI" ,"Obtener archivo imagen: "+filename));
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
		Log log = new Log(getNow(),"getFileFirma" ,"Obtener archivo firma: "+filename);
		logs.saveData(log);
		return (seguridad.isValid())
				? ((filename.equals("sin-firma.png"))
						? respuestas.sentRespuestaRecurso(storageService.loadI(filename), 0)
						: respuestas.sentRespuestaRecurso(storageService.loadF(filename), 0))
				: respuestas.sentRespuestaRecurso(null, 1);
	}
	
	public LocalDateTime getNow() {
		return LocalDateTime.now().minusHours(5);
	}

}
