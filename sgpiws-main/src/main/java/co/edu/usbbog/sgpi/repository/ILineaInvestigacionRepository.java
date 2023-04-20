package co.edu.usbbog.sgpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpi.model.LineaInvestigacion;
import net.minidev.json.JSONObject;

public interface ILineaInvestigacionRepository extends JpaRepository<LineaInvestigacion, String>{

	//metodo para listar los grupos de investigacion de una linea de investigacion
	@Query(value = "SELECT * FROM sgpi_db.grupo_inv_lineas_inv where linea_investigacion = ?1", nativeQuery = true)
	List<JSONObject> findByGrupoInvestigacion(String nombre);
}
