package co.edu.usbbog.sgpireports.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.usbbog.sgpireports.model.TipoUsuario;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.model.Actividad;
import co.edu.usbbog.sgpireports.service.IGestionUsuariosService;
import co.edu.usbbog.sgpireports.service.JSONService;
import net.minidev.json.*;

@RestController
@CrossOrigin
@RequestMapping("/gestionusuario")
public class GestionUsuariosController {

	@Autowired
	private IGestionUsuariosService iGestionUsuariosService;

	private JSONService jsonService = new JSONService();
	
    public Logger logger = LoggerFactory.getLogger(GestionUsuariosController.class);

	/**
	 * login
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/login")
	private JSONObject Login(@RequestBody JSONObject entrada) {
		JSONObject salida = iGestionUsuariosService.login(entrada.getAsString("correoEstudiantil"),
				entrada.getAsString("contrasena"));
		JSONObject salida2 = new JSONObject();
		if (salida == null) {
			salida2.put("respuesta", "Correo o contraseña inconrrectos");
			return salida2;
		}
		return salida;
	}

	/**
	 * crear un usuario
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/guardarusuario")
	public JSONObject guardarUsuario(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		String tipoUsuario = "";
		BigInteger nbumero = new BigInteger(entrada.getAsString("codUniversitario"));
		String num = entrada.getAsString("codUniversitario");
		Usuario usuario = new Usuario(entrada.getAsString("cedula"), nbumero, entrada.getAsString("correoEst"),
				entrada.getAsString("contrasena"), entrada.getAsString("nombres"), entrada.getAsString("apellidos"),
				entrada.getAsString("visibilidad"));
		usuario.setTelefono(entrada.getAsString("telefono"));
		usuario.setCorreoPersonal(entrada.getAsString("correoPersonal"));
		if (iGestionUsuariosService.existsByCorreo(entrada.getAsString("correoEst"))) {
			salida.put("respuesta", "Ya existe un usuario registrado con el correo institucional ingresado");
			return salida;
		}
		if (usuario.getCorreoEst().contains("@academia.usbbog.edu.co")) {
			if (isEstudianteRol(entrada.getAsString("tipo"))) {
				tipoUsuario = entrada.getAsString("tipo");
			} else {
				salida.put("respuesta", "El tipo de usuario es incorrecto ");
				return salida;
			}
		} else if (usuario.getCorreoEst().contains("@usbbog.edu.co")) {
			if (isEstudianteRol(entrada.getAsString("tipo"))) {
				salida.put("respuesta", "El tipo de usuario es incorrecto ");
				return salida;
			} else {
				tipoUsuario = entrada.getAsString("tipo");
			}
		}
		if (iGestionUsuariosService.crearUsuario(usuario, entrada.getAsString("programa"), tipoUsuario)) {
			salida.put("respuesta", "Usuario creado");

		} else {
			salida.put("respuesta", "El usuario ya existe");
		}
		return salida;
	}
	
	private boolean isEstudianteRol (String tipo ) {
		return tipo.equals("ESTUDIANTE ACTIVO") || tipo.equals("ESTUDIANTE INACTIVO") ;
	}

	/**
	 * modificar un usuario
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/modificarusuario")
	public JSONObject modificarUsuario(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (iGestionUsuariosService.modificarUsuario(entrada.getAsString("cedula"),
				entrada.getAsString("telefono"),
				entrada.getAsString("clave"),
				entrada.getAsString("correoP"))) {
			salida.put("respuesta", "el usuario fue actualizado");
		} else {
			salida.put("respuesta", "el usuario no fue actualizado");
		}
		return salida;
	}

	/**
	 * crear tipo de usuario
	 * 
	 * @param tipoUsuario
	 * @return
	 */
	@PostMapping("/creartipousuario")
	public JSONObject guardarTipoUsuario(@RequestBody JSONObject entrada) {
		TipoUsuario nuevo = new TipoUsuario(entrada.getAsString("nombre"),
				entrada.getAsString("descripcion"));
		List<Actividad> actividades = new ArrayList<Actividad>();
		String json = entrada.toString();
		JSONObject salida = new JSONObject();
		for (String act : jsonService.getList(json)) {
			actividades.add(iGestionUsuariosService.buscarActividad(act));
		}
		if (actividades.isEmpty()) {
			salida.put("respuesta", "No se selecciono ninguna actividad");
			return salida;
		}
		if (isExistTipoUsuario(nuevo.getNombre())) {
			salida.put("respuesta", "el tipo de usuario ya existe");
			return salida;
		} else {
			iGestionUsuariosService.crearTipoUsuario(nuevo, actividades);
			salida.put("respuesta", "tipo de usuario creado");
			return salida;
		}
	}

	/**
	 * modificar tipo de usuario
	 * 
	 * @param tipoUsuario
	 * @return
	 */
	@PostMapping("/modificartipousuario")
	public JSONObject modificarTipoUsuario(@RequestBody JSONObject entrada) {
		TipoUsuario nuevo = new TipoUsuario(entrada.getAsString("nombre"),
				entrada.getAsString("descripcion"));
		String vnombre = entrada.getAsString("vnombre");
		List<Actividad> actividades = new ArrayList<Actividad>();
		final JSONObject salida = new JSONObject();
		for (String act : jsonService.getList(JSONObject.toJSONString(entrada))) {
			actividades.add(iGestionUsuariosService.buscarActividad(act));
		}
		if (actividades.isEmpty()) {
			salida.put("respuesta", "No se selecciono ninguna actividad");
			return salida;
		}
		if (isExistTipoUsuario(vnombre)) {
			if(iGestionUsuariosService.modificarTipoUsuario(nuevo, vnombre, actividades)) {
				salida.put("respuesta", "Tipo de usuario modificado");
				return salida;
			} else {
				salida.put("respuesta", "No se pudo modificar el tipo de usuario "+vnombre);
				return salida;
			}
		} else {
			salida.put("respuesta", "Tipo de usuario no existe");
			return salida;
		}
	}

	/**
	 * asignar tipo de usuario
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/asignartipousuario")
	public JSONObject asignarTipoUsuario(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		Usuario usuario = iGestionUsuariosService.buscarUsuario(entrada.getAsString("cedula"));
		TipoUsuario tipoUsuario = iGestionUsuariosService.buscarTipoUsuario(entrada.getAsString("rol"));
		if (usuario != null && tipoUsuario != null) {
			if (iGestionUsuariosService.asignarTipoUsuario(usuario.getCedula(), tipoUsuario.getNombre())) {
				salida.put("respuesta", "Ingresado correctamente");
			} else {
				salida.put("respuesta", "El usuario o el tipo de usuario no existe");
			}

		} else {
			salida.put("respuesta", "El usuario o el tipo de usuario no existe");
		}
		return salida;
	}

	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/existetipousuario/{nombre}")
	public boolean isExistTipoUsuario(@PathVariable String nombre) {
		return iGestionUsuariosService.existeTipoUsuario(nombre);
	}
	
	/**
	 * lista de todos los usuarios
	 * 
	 * @return
	 */
	@GetMapping("/listarusuarios")
	public JSONArray getAllUsuarios() {
		JSONArray salida = new JSONArray();
		List<Usuario> usuarios = iGestionUsuariosService.todosLosUsuarios();
		for (Iterator<Usuario> iterator = usuarios.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();
			salida.add(usuario.toJson());
		}
		return salida;
	}
	
	/**
	 * verificar si existe un tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/showprofile/{cc}")
	public JSONObject buscarUsuario(@PathVariable String cc) {
		Usuario user = iGestionUsuariosService.buscarUsuario(cc);
		JSONObject json = user.toJson();
		json.appendField("firma","http://localhost:8081/archivo/files/"+ user.getFirma().getNombre());
		return json;
	}
	
	
	

}
