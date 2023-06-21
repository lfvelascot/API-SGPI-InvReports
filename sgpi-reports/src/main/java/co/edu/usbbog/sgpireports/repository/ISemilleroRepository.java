package co.edu.usbbog.sgpireports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.Semillero;

public interface ISemilleroRepository extends JpaRepository<Semillero, Integer> {

	// solo para consultar por grupo de investigacion
	@Query(value = "select * from semillero where grupo_investigacion= ?1", nativeQuery = true)
	List<Semillero> findByGrupoInvestigacion(int grupoInvestigacion);

	// solo para consultar el programa
	@Query(value = "SELECT g.* from semillero g inner join programas_semilleros p on g.id = p.semillero WHERE p.programa =?1", nativeQuery = true)
	List<Semillero> findByPrograma(int semillero);

	/**
	 * obtener datos del Coordinador de investigación de una facultad
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT nombre FROM semillero WHERE lider_semillero = ?1 LIMIT 1;", nativeQuery = true)
	String findByLider(String cedula);

}
