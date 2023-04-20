package co.edu.usbbog.sgpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpi.model.AreaConocimiento;
import net.minidev.json.JSONObject;
//metodo para obtener los proyectos de un area en especifico
public interface IAreaConocimientoRepository extends JpaRepository<AreaConocimiento, Integer> {
	/**
	 * lista de ares de conocimiento por proyectos 
	 * @param id
	 * @return
	 */
	@Query(value = "SELECT * FROM sgpi_db.areas_conocimiento where area_conocimiento = ?1", nativeQuery = true)
	List<JSONObject> findByProyecto(int id);
}
