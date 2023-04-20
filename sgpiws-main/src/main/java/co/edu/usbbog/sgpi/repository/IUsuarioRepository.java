/**
 * 
 */
package co.edu.usbbog.sgpi.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import co.edu.usbbog.sgpi.model.Programa;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import co.edu.usbbog.sgpi.model.Usuario;
import net.minidev.json.JSONObject;

/**
 * @author 57310
 *
 */
public interface IUsuarioRepository extends JpaRepository<Usuario, String> {

	//solo para consultar por programa
			@Query(value = "SELECT * FROM usuario where programa_id = ?1", nativeQuery = true)
			List<Usuario> findByPrograma(int programa_id);
	
	//@Query(value = "SELECT tipo_usuario FROM sgpi_db.usuarios where usuario = ?1 && tipo_usuario = ?2 ", nativeQuery = true)
	//JSONObject findByUsuario(String usuario,String tipo);
	/**
	 * salir de un semillero
	 * @param cedula
	 */
	@Modifying
	@Transactional
	@Query(value= "UPDATE `sgpi_db`.`usuario` SET `semillero_id` = null WHERE (`cedula` = ?1)", nativeQuery=true)
	void setSemilleroById(String cedula);
	/**
	 * eliminar un tipo de usuario a un usuario 
	 * @param cedula
	 * @param nombre
	 */
	@Modifying
	@Transactional
	@Query(value= "DELETE FROM `sgpi_db`.`usuarios` WHERE (`usuario` = ?1) and (`tipo_usuario` = ?2)", nativeQuery=true)
	void deleteUsuariosById(String cedula, String nombre);
	/**
	 * actualiza el director de grupo de un grupo de investigacion 
	 * @param grupo
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE `sgpi_db`.`grupo_investigacion` SET `director_grupo` = null WHERE (`id` = ?1)",nativeQuery=true)
	void deleteDirectorById(String grupo);
	/**
	 * inicio de sesion 
	 * @param correo
	 * @param contrasena
	 * @param tipo
	 * @return
	 */
	@Query(value = "select * from usuario,usuarios where usuario.correo_est=?1 && usuario.contrasena=?2	 && usuarios.tipo_usuario=?3 && usuario.cedula=usuarios.usuario" , nativeQuery = true)
	JSONObject Login(String correo, String contrasena,String tipo );
	/**
	 * busqueda de un usuario en especifico 
	 * @param correo
	 * @return
	 */
	@Query(value = "select * from usuario where usuario.correo_est=?1", nativeQuery = true)
	Usuario JSONObject(String correo);
	/**
	 * lista de todos los usuarios inactivos 
	 * @return
	 */
	@Query(value = "select * from usuarios where tipo_usuario= \"Estudiante inactivo\"", nativeQuery = true)
	JSONObject getByTipoEstudianteInactivo();
	/**
	 * lista de todos los usuarios activos ñ
	 * @return
	 */
	@Query(value = "select * from usuarios where tipo_usuario= \"Estudiante activo\"", nativeQuery = true)
	JSONObject getByTipoEstudianteActivo();	
	/**
	 * lista de todos los usuarios egresados 
	 * @return
	 */
	@Query(value = "select * from usuarios where tipo_usuario= \"Egresado\"", nativeQuery = true)
	JSONObject getByTipoEstudianteEgresado();
	/**
	 *  lista de todos los usuarios investigador en formacion
	 * @return
	 */
	@Query(value = "select * from usuarios where tipo_usuario= \"Investigador formacion\"", nativeQuery = true)
	JSONObject getByTipoInvestigadorFormacion();
	/**
	 *  lista de todos los usuarios personal de biblioteca
	 * @return
	 */
	@Query(value = "select * from usuarios where tipo_usuario= \"Personal biblioteca\"", nativeQuery = true)
	JSONObject getByTipoPersonalBiblioteca();
	/**
	 *  lista de todos los usuarios personal de publicaciones
	 * @return
	 */
	@Query(value = "select * from usuarios where tipo_usuario= \"Personal publicaciones\"", nativeQuery = true)
	JSONObject getByTipoPersonalPublicaciones();
	/**
	 *  lista de todos los usuarios semillrista
	 * @return
	 */
	@Query(value = "select * from usuarios where tipo_usuario= \"Semillerista\"", nativeQuery = true)
	JSONObject getByTipoSemillerista();
	/**
	 * obtener datos de un usuario por su correo 
	 * @param correo
	 * @return
	 */
	@Query(value = "select * from usuario where usuario.correo_est=?1", nativeQuery = true)
	Usuario getByCorreo(String correo);
	/**
	 * eliminar un tipo de usuario de un usuario 
	 * @param usuario
	 * @param tipo
	 */
	@Modifying
	@Transactional
	@Query(value= "delete from usuarios where usuario = ?1 and tipo_usuario= ?2", nativeQuery=true)
	void deleteTipo(String usuario, String tipo);
	/**
	 * obtener nombre con la cedula de un usuario 
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT nombres FROM sgpi_db.usuario where cedula =?1", nativeQuery = true)
	JSONObject getNombre(String cedula);
	/**
	 * eliminar el rol de semillerista de un usuario  
	 * @param cedula
	 */
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `sgpi_db`.`usuarios` WHERE (`usuario` = ?1) and (`tipo_usuario` = 'Semillerista')", nativeQuery = true)
	void quitarRol(String cedula);
}
