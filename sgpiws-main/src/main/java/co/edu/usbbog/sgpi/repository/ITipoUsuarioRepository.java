package co.edu.usbbog.sgpi.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpi.model.TipoUsuario;

public interface ITipoUsuarioRepository extends JpaRepository<TipoUsuario,String> {
	/**
	 * actualiza el tipo de usuario 
	 * @param cedula
	 */
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `sgpi_db`.`usuarios` WHERE (`usuario` =?1)", nativeQuery = true)
	void desasignarRoles(String cedula);

}
