package co.edu.usbbog.sgpi.controller;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.usbbog.sgpi.model.Facultad;
import co.edu.usbbog.sgpi.model.GrupoInvestigacion;
import co.edu.usbbog.sgpi.model.Programa;
import co.edu.usbbog.sgpi.model.Semillero;
import co.edu.usbbog.sgpi.model.TipoUsuario;
import co.edu.usbbog.sgpi.model.Usuario;
import co.edu.usbbog.sgpi.service.IGestionUsuariosService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
@RequestMapping("/gestionusuario")
public class GestionUsuarioController {
	@Autowired
	private IGestionUsuariosService iGestionUsuariosService;

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
	 * verificar su existe un usuario
	 * 
	 * @param cedula
	 * @return
	 */
	@GetMapping("/existeusuario/{cedula}")
	public boolean isExistUsuario(@PathVariable String cedula) {
		if (iGestionUsuariosService.existeUsuario(cedula)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * buscar un usuario por la cedula
	 * 
	 * @param cedula
	 * @return
	 */
	@GetMapping("/buscarusuario/{cedula}")
	public JSONObject buscarUsuario(@PathVariable String cedula) {
		Usuario usuario = iGestionUsuariosService.buscarUsuario(cedula);
		JSONObject salida = usuario.toJson();
		return salida;
	}

	/**
	 * eliminar un usuario con la cedula
	 * 
	 * @param cedula
	 * @return
	 */
	@GetMapping("/eliminarusuario/{cedula}")
	public String eliminarUsuario(@PathVariable String cedula) {
		iGestionUsuariosService.eliminarUsuario(cedula);
		return "elimino";
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
		BigInteger nbumero= new BigInteger(entrada.getAsString("codUniversitario"));
		
		String num= entrada.getAsString("codUniversitario");
		Usuario usuario = new Usuario(entrada.getAsString("cedula"),
				nbumero, entrada.getAsString("correoEst"),
				entrada.getAsString("contrasena"), entrada.getAsString("nombres"), entrada.getAsString("apellidos"),
				entrada.getAsString("visibilidad"));
		usuario.setTelefono(entrada.getAsString("telefono"));
		usuario.setCorreoPersonal(entrada.getAsString("correoPersonal"));
		if (entrada.getAsString("correoEst").contains("@academia.usbbog.edu.co")) {
			if (entrada.getAsString("tipo").equals("Estudiante activo")) {
				tipoUsuario = entrada.getAsString("tipo");
			} else {
				salida.put("respuesta", "el tipo de usuario es incorrecto ");
				return salida;
			}
		} else if (entrada.getAsString("correoEst").contains("@usbbog.edu.co")) {
			tipoUsuario = entrada.getAsString("tipo");
		}
		if (iGestionUsuariosService.crearUsuario(usuario, entrada.getAsString("programa"), entrada.getAsString("tipo"))) {
			salida.put("respuesta", "usuario creado");

		} else {
			salida.put("respuesta", "el usuario ya existe");
		}
		return salida;
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
		if (iGestionUsuariosService.modificarUsuario(entrada.getAsString("cedula"), entrada.getAsString("telefono"),
				entrada.getAsString("clave"), entrada.getAsString("correoP"))) {
			salida.put("respuesta", "el usuario fue actualizado");
		} else {
			salida.put("respuesta", "el usuario no fue actualizado");
		}
		return salida;
	}

	/**
	 * verificar si existe un semillero por id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/existesemillero/{id}")
	public boolean isExistSemillero(@PathVariable Integer id) {
		JSONObject salida = new JSONObject();
		if (iGestionUsuariosService.existeSemillero(id)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * asignar un semillero a un usuario
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/asignarsemillero")
	public JSONObject asignarsemillero(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (iGestionUsuariosService.asignarSemillero(entrada.getAsString("cedula"),
				Integer.parseInt(entrada.getAsString("semillero")))) {
			salida.put("respuesta", "el usuario fue asignado exitosamente");
		} else {
			salida.put("respuesta", "el usuario fue no asignado ");
		}
		return salida;
	}

	/**
	 * eliminar usuario de un semillero
	 * 
	 * @param cedula
	 * @return
	 */
	@PostMapping("/eliminarusuariosemillero/{cedula}")
	public boolean eliminarUsuarioSemillero(@PathVariable String cedula) {
		return iGestionUsuariosService.eliminarUsuarioSemillero(cedula);

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
	 * crear tipo de usuario
	 * 
	 * @param tipoUsuario
	 * @return
	 */
	@PostMapping("/creartipousuario")
	public JSONObject guardarTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
		final JSONObject salida = new JSONObject();
		if (isExistTipoUsuario(tipoUsuario.getNombre())) {
			salida.put("respuesta", "el tipo de usuario ya existe");
			return salida;
		} else {
			iGestionUsuariosService.crearTipoUsuario(tipoUsuario);
			salida.put("respuesta", "tipo de usuario creado");
			return salida;
		}
	}

	/**
	 * eliminar tipo de usuario
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/eliminartipousuario/{nombre}")
	public String eliminarTipoUsuario(@PathVariable String nombre) {
		if (iGestionUsuariosService.eliminarTipoUsuario(nombre)) {
			return "elimino";
		} else {
			return "no se pudo eliminar";
		}
	}

	/**
	 * quitar rol a un usuario
	 * 
	 * @param entrada
	 * @return
	 */
	@GetMapping("/eliminartipousuarioausuario")
	public String quitarTipoUsuarioAUsuario(@RequestBody JSONObject entrada) {
		if (iGestionUsuariosService.eliminarTipoUsuarioAUsuario(entrada.getAsString("cedula"),
				entrada.getAsString("nombre"))) {
			return "elimino";
		} else {
			return "no se pudo eliminar";
		}
	}

	/**
	 * listar tipo de usuario
	 * 
	 * @return
	 */
	@GetMapping("/listartiposusuario")
	public JSONArray listarTiposUsuario() {
		JSONArray salida = new JSONArray();
		List<TipoUsuario> tiposUsuario = iGestionUsuariosService.todosLosTipoUsuario();
		for (Iterator iterator = tiposUsuario.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			salida.add(tipoUsuario.toJson());
		}
		return salida;
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
			if(iGestionUsuariosService.asignarTipoUsuario(usuario, tipoUsuario)) {
				
				salida.put("respuesta", "ingresado correctamente");	
			}else {
				salida.put("respuesta", "el usuario o el tipo de usuario no existe");
			}
			
		} else {
			salida.put("respuesta", "el usuario o el tipo de usuario no existe");
		}
		return salida;
	}
	@GetMapping("/desasignarRoles/{cedula}")
	public JSONObject desasignarRoles(@PathVariable String cedula) {
		JSONObject salida = new JSONObject();
		if(iGestionUsuariosService.desasignarRol(cedula)) {
			salida.put("respuesta", "Se desabilito tu cuenta");
		}else {
			salida.put("respuesta","error");
		}
		return salida;
	}

	/**
	 * asignar tipo de usuario
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/asignarrolusuario")
	public JSONObject asignarRolUsuario(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		if (iGestionUsuariosService.asignarRolUsuario(entrada.getAsString("cedula"),
				entrada.getAsString("rol")) == "se agrego el rol") {
			salida.put("respuesta", "se agrego el rol");
		} else if (iGestionUsuariosService.asignarRolUsuario(entrada.getAsString("cedula"),
				entrada.getAsString("rol")) == "el usuario no existe") {
			salida.put("respuesta", "el usuario no existe");
		} else if (iGestionUsuariosService.asignarRolUsuario(entrada.getAsString("cedula"),
				entrada.getAsString("rol")) == "el rol no existe") {
			salida.put("respuesta", "el rol no existe");
		} else if (iGestionUsuariosService.asignarRolUsuario(entrada.getAsString("cedula"),
				entrada.getAsString("rol")) == "ya tiene ese rol") {
			salida.put("respuesta", "ya tiene ese rol");
		} else {
			salida.put("respuesta", "el rol no se pudo asignar");
		}
		return salida;
	}

	/**
	 * asignar decano
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/asignardecano")
	public JSONObject asignarDecano(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		try {
			Facultad facultad = iGestionUsuariosService
					.buscarFacultad(Integer.parseInt(entrada.getAsString("facultad")));
			if (iGestionUsuariosService.asignarDecano(facultad, entrada.getAsString("decano"))) {
				salida.put("respuesta", "el decano fue ingresado correctamente");
			} else {

				salida.put("respuesta", "el usuario no tiene el rol necesario para ser decano ");
			}
		} catch (Exception e) {
			salida.put("respuesta", "el id del decano tiene un error");
		}
		return salida;
	}

	/**
	 * eliminar decano de una facultad
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/eliminardecanofacultad")
	public boolean eliminarDecanoFacultad(@RequestBody JSONObject entrada) {
		return iGestionUsuariosService.eliminarDecanoFacultad(entrada.getAsString("cedula"),
				entrada.getAsString("facultad"));
	}

	/**
	 * asignar coordinador de investigacion
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/asignarcoorinv")
	public JSONObject asignarCoorInv(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		try {
			Facultad facultad = iGestionUsuariosService
					.buscarFacultad(Integer.parseInt(entrada.getAsString("facultad")));
			if (iGestionUsuariosService.asignarCoorInv(facultad, entrada.getAsString("coorinv"))) {
				salida.put("respuesta", "el coordinador de investigaciones fue ingresado correctamente");
			} else {

				salida.put("respuesta", "el usuario no fue asignado existosamente");
			}
		} catch (Exception e) {
			salida.put("respuesta", "el id del coorInv tiene un error");
		}
		return salida;
	}

	/**
	 * desasignar coordinador de investigacion de la facultad
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/eliminarcoorinvfacultad")
	public boolean eliminarCoorInvFacultad(@RequestBody JSONObject entrada) {
		return iGestionUsuariosService.eliminarCoorInvFacultad(entrada.getAsString("cedula"),
				entrada.getAsString("facultad"));

	}

	/**
	 * verificar si existe facultad
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/existefacultad1/{id}")
	private boolean isExistFacultad(int id) {
		if (iGestionUsuariosService.existeFacultad(id)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * asignar director de programa
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/asignardirectorprograma")
	public JSONObject asignarDirectorPrograma(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		try {
			Programa programa = iGestionUsuariosService.buscarPrograma(Integer.parseInt(entrada.getAsString("id")));
			if (iGestionUsuariosService.asignarDirectorPrograma(programa, entrada.getAsString("director"))) {
				salida.put("respuesta", "el director de progrma fue ingresado correctamente");
			} else {

				salida.put("respuesta", "el director de progrma no fue asignado existosamente");
			}
		} catch (Exception e) {
			salida.put("respuesta", "el id del director de progrma tiene un error");
		}
		return salida;
	}

	/**
	 * asignar director de grupo
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/asignardirectorgrupo")
	public JSONObject asignarDirectorGrupo(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		try {
			GrupoInvestigacion grupo = iGestionUsuariosService.buscarGrpo(Integer.parseInt(entrada.getAsString("id")));
			if (iGestionUsuariosService.asignarDirectorGrupo(grupo, entrada.getAsString("director"))) {
				salida.put("respuesta", "el director de grupo de investigaciones fue ingresado correctamente");
			} else {

				salida.put("respuesta", "el director de grupo no fue asignado existosamente");
			}
		} catch (Exception e) {
			salida.put("respuesta", "el id del director de grupo tiene un error");
		}
		return salida;
	}

	/**
	 * desasignar director de grupo
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/removerdirectorgrupo")
	public boolean eliminarDirectorGrupo(@RequestBody JSONObject entrada) {
		return iGestionUsuariosService.eliminarDirectorGrupo(entrada.getAsString("cedula"),
				entrada.getAsString("grupo"));
	}

	/**
	 * asignar lider de semillero
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/asignarlidersemillero")
	public JSONObject asignarLiderSemillero(@RequestBody JSONObject entrada) {
		JSONObject salida = new JSONObject();
		try {
			Semillero semi = iGestionUsuariosService.buscarSemillero(Integer.parseInt(entrada.getAsString("id")));
			if (iGestionUsuariosService.asignarLiderSemillero(semi, entrada.getAsString("lider"))) {
				salida.put("respuesta", "el lider de semillero fue ingresado correctamente");
			} else {

				salida.put("respuesta", "el lider de semillero no fue asignado existosamente");
			}
		} catch (Exception e) {
			salida.put("respuesta", "el id del lider de semillero tiene un error");
		}
		return salida;
	}

	/**
	 * desasignar lider de semillero
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/removerlidersemillero")
	public boolean eliminarLiderSemillero(@RequestBody JSONObject entrada) {
		return iGestionUsuariosService.eliminarLiderSemillero(entrada.getAsString("cedula"),
				entrada.getAsString("semillero"));
	}

	/**
	 * verificar si existe facultad
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/existefacultad/{id}")
	private boolean isExistFacultad(Integer id) {
		if (iGestionUsuariosService.existeFacultad(id)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * login
	 * 
	 * @param entrada
	 * @return
	 */
	@PostMapping("/login")
	private JSONObject Login(@RequestBody JSONObject entrada) {
		JSONObject salida = iGestionUsuariosService.login(entrada.getAsString("correoEstudiantil"),
				entrada.getAsString("contrasena"), entrada.getAsString("tipoUsuario"));
		JSONObject salida2 = new JSONObject();
		if (salida == null) {
			salida2.put("respuesta", "el usuario o contraseña son incorrectos");
			return salida2;
		}
		return salida;
	}

	/**
	 * lista de roles que tiene un usuario
	 * 
	 * @param cedula
	 * @return
	 */
	@GetMapping("/roles/{cedula}")
	private JSONArray roles(@PathVariable String cedula) {
		JSONArray salida = new JSONArray();
		List<TipoUsuario> tiposUsuario = iGestionUsuariosService.roles(cedula);
		for (Iterator iterator = tiposUsuario.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			salida.add(tipoUsuario.toJson());
		}
		return salida;
	}

	/**
	 * todos los roles
	 * 
	 * @return
	 */
	@GetMapping("/todosroles")
	private JSONArray todosRoles() {
		JSONArray salida = new JSONArray();
		List<TipoUsuario> tiposUsuario = iGestionUsuariosService.todosLosRoles();
		for (Iterator iterator = tiposUsuario.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			salida.add(tipoUsuario.toJson());
		}
		return salida;
	}

	/**
	 * listar roles por usuario
	 * 
	 * @param tipo
	 * @return
	 */
	@GetMapping("/litarusuariosportipo/{tipo}")
	private JSONArray litarUsuariosPorTipo(@PathVariable String tipo) {
		JSONArray salida = new JSONArray();
		List<Usuario> tiposUsuario = iGestionUsuariosService.todosPorRol(tipo);
		for (Iterator iterator = tiposUsuario.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();
			salida.add(usuario.toJson());
		}

		return salida;
	}
}
