package co.edu.usbbog.sgpireports.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbbog.sgpireports.model.TipoUsuario;

public interface ITipoUsuarioRepository extends JpaRepository<TipoUsuario,String> {
	/**
	 * actualiza el tipo de usuario 
	 * @param cedula
	 */
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `sgpi_db`.`usuarios` WHERE (`usuario` =?1)", nativeQuery = true)
	void desasignarRoles(String cedula);
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `sgpi_db`.`actividades` WHERE (`tipo_usuario`=?1)", nativeQuery = true)
	void desasignarActividades(String tipoUsuario);
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `sgpi_db`.`usuarios` WHERE (`tipo_usuario`=?1)", nativeQuery = true)
	void desasignarRol(String tipoUsuario);

    @Modifying
    @Query(value = "INSERT INTO `sgpi_db`.`actividades` (`tipo_usuario`,`actividad`) VALUES (:tipoUsuario,:actividad)", nativeQuery = true)
    @Transactional
    void AddTipoUsuarioActividad(@Param("tipoUsuario") String tipoUsuario, @Param("actividad") String actividad);
    @Modifying
    @Query(value = "INSERT INTO `sgpi_db`.`usuarios` (`usuario`,`tipo_usuario`) VALUES (:cedula,:tipoUsuario)", nativeQuery = true)
    @Transactional
    void AddTipoUsuarioUsuario(@Param("tipoUsuario") String tipoUsuario, @Param("cedula") String cedula);
	@Modifying
	@Transactional
	@Query(value="UPDATE `sgpi_db`.`tipo_usuario` SET `nombre` =?1, `descripcion`=?2 WHERE (`nombre` =?3)",nativeQuery=true)
	void UpdateById(String nnombre, String ndescripcion,String nombre);
	@Modifying
	@Transactional
	@Query(value= "DELETE FROM `sgpi_db`.`usuarios` WHERE (`usuario` = ?1) and (`tipo_usuario` = ?2)", nativeQuery=true)
	void deleteUsuariosById(String cedula, String nombre);

}
