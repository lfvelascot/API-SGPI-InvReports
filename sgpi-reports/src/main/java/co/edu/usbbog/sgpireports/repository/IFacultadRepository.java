package co.edu.usbbog.sgpireports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.Facultad;

public interface IFacultadRepository extends JpaRepository<Facultad, Integer>{
	/**
	 * obtener datos del Coordinador de investigación de una facultad
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT nombre FROM facultad WHERE decano = ?1 LIMIT 1;", nativeQuery = true)
	String findByDecano(String cedula);
	
	/**
	 * obtener datos del Coordinador de investigación de una facultad
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT nombre FROM facultad WHERE coor_inv = ?1 LIMIT 1;", nativeQuery = true)
	String findByCoord(String cedula);
}
