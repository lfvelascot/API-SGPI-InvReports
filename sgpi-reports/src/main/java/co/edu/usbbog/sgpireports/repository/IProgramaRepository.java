package co.edu.usbbog.sgpireports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.Programa;

public interface IProgramaRepository extends JpaRepository<Programa, Integer> {
	// metodo para consultar los programas por Facultad
	@Query(value = "select * from programa where facultad_id= ?1", nativeQuery = true)
	List<Programa> findByFacultad(int facultad_id);

	// metodo para consultar los programas por Director
	@Query(value = "select * from programa where director= ?1", nativeQuery = true)
	List<Programa> findByDirector(String director);

}
