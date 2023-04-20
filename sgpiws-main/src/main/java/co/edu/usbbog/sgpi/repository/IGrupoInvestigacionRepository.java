package co.edu.usbbog.sgpi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpi.model.GrupoInvestigacion;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.Usuario;
import net.minidev.json.JSONObject;

public interface IGrupoInvestigacionRepository extends JpaRepository<GrupoInvestigacion, Integer>{
	//metodo para obtener los programas de un grupo de investigacion
	@Query(value = "SELECT * FROM programas_grupos_inv where grupo_investigacion = ?1", nativeQuery = true)
	JSONObject findByPrograma(int grupo_investigacion);

	
	//metodo para obtener las lindeas de investigacion de un grupo de investigacion
	@Query(value = "SELECT * FROM sgpi_db.grupo_inv_lineas_inv where grupo_investigacion = ?1", nativeQuery = true)
	JSONObject findByLinea(int grupo_investigacion);
	
	//metodo para des-asignar linea
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `sgpi_db`.`grupo_inv_lineas_inv` WHERE (`grupo_investigacion` = ?2) and (`linea_investigacion` = ?1)", nativeQuery = true)
	void desAsignarLinea( String linea_investigacion, String grupo_investigacion);
	
	//metodo para des asignar programa
			@Modifying
			@Transactional
			@Query(value = "DELETE FROM `sgpi_db`.`programas_grupos_inv` WHERE (`programa` = ?1) and (`grupo_investigacion` = ?2)", nativeQuery = true)
			void desAsignarPrograma( String programa, String grupo_investigacion);
}
