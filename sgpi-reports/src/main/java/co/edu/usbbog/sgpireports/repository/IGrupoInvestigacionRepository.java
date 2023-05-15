package co.edu.usbbog.sgpireports.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Usuario;
import net.minidev.json.JSONObject;

public interface IGrupoInvestigacionRepository extends JpaRepository<GrupoInvestigacion, Integer> {
	// metodo para obtener los programas de un grupo de investigacion
	@Query(value = "SELECT g.* from grupo_investigacion g inner join programas_grupos_inv p on g.id = p.grupo_investigacion WHERE p.programa =?1", nativeQuery = true)
	List<GrupoInvestigacion> findByPrograma(int grupo_investigacion);

	// metodo para obtener las lindeas de investigacion de un grupo de investigacion
	@Query(value = "SELECT * FROM sgpi_db.grupo_inv_lineas_inv where grupo_investigacion = ?1", nativeQuery = true)
	JSONObject findByLinea(int grupo_investigacion);

	// metodo para des-asignar linea
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `sgpi_db`.`grupo_inv_lineas_inv` WHERE (`grupo_investigacion` = ?2) and (`linea_investigacion` = ?1)", nativeQuery = true)
	void desAsignarLinea(String linea_investigacion, String grupo_investigacion);

	// metodo para des asignar programa
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `sgpi_db`.`programas_grupos_inv` WHERE (`programa` = ?1) and (`grupo_investigacion` = ?2)", nativeQuery = true)
	void desAsignarPrograma(String programa, String grupo_investigacion);

	// metodo para obtener GIs de una facultad
	@Query(value = "SELECT DISTINCT g.* FROM grupo_investigacion g INNER JOIN programas_grupos_inv pg on g.id = pg.grupo_investigacion INNER JOIN programa p ON pg.programa = p.id WHERE p.facultad_id = ?1", nativeQuery = true)
	List<GrupoInvestigacion> findByFacultad(int cc);
}
