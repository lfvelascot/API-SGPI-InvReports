package co.edu.usbbog.sgpireports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import co.edu.usbbog.sgpireports.model.Modulo;



public interface IModuloRepository extends JpaRepository<Modulo, String>{
	
	/**
	 * Busuqeda de actividades por modulo
	 * @param modulo
	 * @return List
	 */
	//@Query(value = "select * from actividades where actividades.modulo=?1", nativeQuery = true)
	// List<Actividades> getActividadesByModulo(String modulo);
	
	/**
	 * Busuqeda de actividades por tipo usuario
	 * @param tipousuario
	 * @return List
	 */
	// @Query(value = "select * from actividades where actividades.tipo_usuario=?1", nativeQuery = true)
	//List<Actividades> getActividadesByTipoUsuario(String tipousuario);
	
	/**
	 * eliminar actividades de un tipo de usuario
	 * @param tipo usuario
	 */
	@Modifying
	@Transactional
	@Query(value= "DELETE FROM `sgpi_db`.`actividades` WHERE `tipo_usuario` = ?1", nativeQuery=true)
	void deleteUsuariosById(String tipousuario);

}
