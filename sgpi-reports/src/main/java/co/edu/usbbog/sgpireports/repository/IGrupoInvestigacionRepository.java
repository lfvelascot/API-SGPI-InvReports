package co.edu.usbbog.sgpireports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;

public interface IGrupoInvestigacionRepository extends JpaRepository<GrupoInvestigacion, Integer> {
	// metodo para obtener los programas de un grupo de investigacion
	@Query(value = "SELECT g.* from grupo_investigacion g inner join programas_grupos_inv p on g.id = p.grupo_investigacion WHERE p.programa =?1", nativeQuery = true)
	List<GrupoInvestigacion> findByPrograma(int grupo_investigacion);
	
	// metodo para obtener GIs de una facultad
	@Query(value = "SELECT DISTINCT g.* FROM grupo_investigacion g INNER JOIN programas_grupos_inv pg on g.id = pg.grupo_investigacion INNER JOIN programa p ON pg.programa = p.id WHERE p.facultad_id = ?1", nativeQuery = true)
	List<GrupoInvestigacion> findByFacultad(int cc);

	/**
	 * obtener datos del Coordinador de investigación de una facultad
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT nombre FROM grupo_investigacion WHERE director_grupo = ?1 LIMIT 1;", nativeQuery = true)
	String findByDirector(String cedula);

}
