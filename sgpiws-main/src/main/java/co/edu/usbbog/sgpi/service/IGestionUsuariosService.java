package co.edu.usbbog.sgpi.service;

import java.util.List;

import co.edu.usbbog.sgpi.model.Facultad;
import co.edu.usbbog.sgpi.model.GrupoInvestigacion;
import co.edu.usbbog.sgpi.model.Programa;
import co.edu.usbbog.sgpi.model.Semillero;
import co.edu.usbbog.sgpi.model.TipoUsuario;
import co.edu.usbbog.sgpi.model.Usuario;
import net.minidev.json.JSONObject;

public interface IGestionUsuariosService {
	//listar usuarios
	public List<Usuario> todosLosUsuarios();
	//buscar usuario por cedula
	public Usuario buscarUsuario(String cedula);
	//existe usuario
	public boolean existeUsuario(String cedula);
	//eliminar un usuario
	public boolean eliminarUsuario(String cedula);
	//crear un usuario
	public boolean crearUsuario(Usuario usuario, String programa, String tipousuario);
	//existe semillero
	public boolean existeSemillero(Integer id);
	//asignar un usuario a un semillero
	public boolean asignarSemillero(String cedula,int semillero);
	//existe tipo de usuario
	public boolean existeTipoUsuario(String nombre);
	//todos los tipos de usuario
	public List<TipoUsuario> todosLosTipoUsuario();
	//buscar tipo de usuario por su nombre
	public TipoUsuario buscarTipoUsuario(String nombre);
	//exite facultad
	public boolean existeFacultad(Integer id);
	//asignar decano a facultad
	public boolean asignarDecano(Facultad facultad, String  decano);
	//buscar facultad por id
	public Facultad buscarFacultad(int id);
	//asignar coordinador de investigacion a facultad 
	public boolean asignarCoorInv(Facultad facultad, String coorInv);
	//asignar director programa
	public boolean asignarDirectorPrograma(Programa programa, String director);
	//buscar programa
	public Programa buscarPrograma(int parseInt);
	//buscar grupo de investigacion
	public GrupoInvestigacion buscarGrpo(int parseInt);
	//asignar grupo investigacion
	public boolean asignarDirectorGrupo(GrupoInvestigacion grupo,String director);
	//buscar semillero
	public Semillero buscarSemillero(int parseInt);
	//asignar lider de semillero
	public boolean asignarLiderSemillero(Semillero semillero,String lider);
	//eliminar un usuario de un semillero
	public boolean eliminarUsuarioSemillero(String cedula);
	//eliminar tipo de usuario
	public boolean eliminarTipoUsuario(String nombre);
	//eliminar tipo de usuario a usuario
	public boolean eliminarTipoUsuarioAUsuario(String cedula,String nombre);
	//remover decano de la facultad
	public boolean eliminarDecanoFacultad(String cedula,String facultad);
	//remover coordinador de investigacion de facultad
	boolean eliminarCoorInvFacultad(String cedula, String facultad);
	//crear un tipo de usuario
	public boolean crearTipoUsuario(TipoUsuario tipoUsuario);
	//remover lider de semillero
	boolean eliminarLiderSemillero(String cedula, String semillero);
	//remover director de grupo de investigacion
	public boolean eliminarDirectorGrupo(String cedula, String grupo);
	//Login
	public JSONObject login(String correo, String contrasena,String tipo);
	//asignar tipo de usuario
	boolean asignarTipoUsuario(Usuario usuario, TipoUsuario tipoUsuario);
	
	//cambiar estado de cuenta
	public boolean  validarCuenta(String cedula, String visibilidad);
	//requerimientos que no estan en el arbol
	//El sistema debe permitir al administrador validar la cuenta de usuario de manera que pueda ser usada
	//El sistema deberá permitir cambiar la contraseña del usuario de manera que él pueda escoger la contraseña de su preferencia
	public boolean cambiarContrasenaUsuario(String contrasena);
	//crear metodos para asignar decano, coordinador de investigaciones y director de facultad
	public List<TipoUsuario> roles(String cedula);
	/**
	 * modificar un usuario
	 * @param cedula
	 * @param telefono
	 * @param clave
	 * @param correoP
	 * @return
	 */
	public boolean modificarUsuario(String cedula,String telefono,String clave, String correoP);
	/**
	 * listar todos los roles
	 * @return
	 */
	public List<TipoUsuario> todosLosRoles();
	/**
	 * lista de usuarios por rol 
	 * @param tipo
	 * @return
	 */
	public List<Usuario> todosPorRol(String tipo);
	/**
	 * asignar un rol a un usuario ES
	 * @param usuario
	 * @param tipoUsuario
	 * @return
	 */
	String asignarRolUsuario(String usuario, String tipoUsuario);
	public boolean desasignarRol(String cedula);
}
