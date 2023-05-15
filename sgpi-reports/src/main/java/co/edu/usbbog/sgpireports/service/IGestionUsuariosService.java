package co.edu.usbbog.sgpireports.service;

import java.util.List;
import co.edu.usbbog.sgpireports.model.Actividad;
import co.edu.usbbog.sgpireports.model.TipoUsuario;
import co.edu.usbbog.sgpireports.model.Usuario;
import net.minidev.json.JSONObject;

public interface IGestionUsuariosService {
	
	//Login
	public JSONObject login(String correo, String contrasena);
	//crear un usuario
	public boolean crearUsuario(Usuario usuario, String programa, String tipousuario);
	//existe tipo de usuario
	public boolean existeTipoUsuario(String nombre);
	//buscar tipo de usuario por su nombre
	public TipoUsuario buscarTipoUsuario(String nombre);
	//asignar tipo de usuario
	public boolean asignarTipoUsuario(String usuario, String tipoUsuario);
	//crear un tipo de usuario
	public boolean crearTipoUsuario(TipoUsuario tipoUsuario, List<Actividad> actividades);
	//modificar un tipo de usuario
	public boolean modificarTipoUsuario(TipoUsuario tipoUsuario, String nombre, List<Actividad> actividades);
	/**
	 * modificar un usuario
	 * @param cedula
	 * @param telefono
	 * @param clave
	 * @param correoP
	 * @return
	 */
	public boolean modificarUsuario(String cedula,String telefono,String clave, String correoP);
	//cargar firma de un usuario
	public boolean cargarFirma (String cedula, String nombrearchivo, String url);
	//existe usuario
	public boolean existeUsuario(String cedula);
	//Listar roles de un usuario
	public List<TipoUsuario> roles(String cedula);
	//buscar actividad
	public Actividad buscarActividad(String nombre);
	//buscar usuario
	public Usuario buscarUsuario(String asString);
	//Buscar si el correo institucional esta ya registrado
	public boolean existsByCorreo(String correo);
	//Listar todos los usuarios
	public List<Usuario> todosLosUsuarios();
	// Cargar firma
	public boolean saveFirma(String cedula, String nombre);
}
