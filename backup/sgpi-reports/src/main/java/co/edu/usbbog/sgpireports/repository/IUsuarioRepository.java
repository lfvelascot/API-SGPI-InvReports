/**
 * 
 */
package co.edu.usbbog.sgpireports.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.Usuario;
import net.minidev.json.JSONObject;

/**
 * @author 57310
 *
 */
public interface IUsuarioRepository  extends JpaRepository<Usuario, String> {

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
	@Query(value = "select * from usuario where usuario.correo_est=?1 && usuario.contrasena=?2" , nativeQuery = true)
	JSONObject Login(String correo, String contrasena);
	/**
	 * busqueda de un usuario en especifico 
	 * @param correo
	 * @return
	 */
	@Query(value = "select * from usuario where usuario.correo_est=?1", nativeQuery = true)
	Usuario JSONObject(String correo);
	/**
	 *  lista de todos los usuarios por untipo
	 * @return
	 */
	@Query(value = "select u.* from usuarios s inner join usuario u on s.usuario = u.cedula WHERE s.tipo_usuario=?1", nativeQuery = true)
	List<Usuario> getByTipoUsuario(String tipoUsuario);
	/**
	 *  lista de todos los usuarios por untipo
	 * @return
	 */
	@Query(value = "select tipo_usuario from usuarios WHERE usuario=?1", nativeQuery = true)
	String getTipoUsuario(String cedula);
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
	
	/**
	 * obtener datos miembros de semillero
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT * FROM `usuario`WHERE `semillero_id` = ?1 and visibilidad = '1' ORDER BY nombres ASC;", nativeQuery = true)
	List<Usuario> getMiembrosSemillero(int cedula);
	
	/**
	 * obtener datos miembros de semillero
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT u.* FROM usuario u INNER JOIN semillero s on u.semillero_id = s.id WHERE s.grupo_investigacion = ?1 and u.visibilidad = '1' ORDER BY nombres ASC;", nativeQuery = true)
	List<Usuario> getMiembrosGI(int cedula);
	/**
	 * obtener datos miembros de semillero
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT u.* FROM proyecto p inner join participantes pp on p.id = pp.proyecto inner join usuario u on pp.usuario = u.cedula WHERE p.id = ?1 order by u.nombres;", nativeQuery = true)
	List<Usuario> findParticipantesProyecto(Integer id);
}
