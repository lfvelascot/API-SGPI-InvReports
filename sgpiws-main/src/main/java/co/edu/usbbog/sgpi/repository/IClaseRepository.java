package co.edu.usbbog.sgpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbbog.sgpi.model.Clase;
import net.minidev.json.JSONObject;

public interface IClaseRepository extends JpaRepository<Clase, Integer>{
 //metodo para obtener las clases de un profesor
	@Query(value = "SELECT * FROM clase WHERE profesor = ?1", nativeQuery = true)
	List<Clase> findByProfesor(String profesor);
	//metodo para obtener las clases de una materia
	@Query(value = "SELECT * FROM clase WHERE materia = ?1", nativeQuery = true)
	List<Clase> findByMateria(String materia);
	
	//metodo para obtener los proyectos de una clase
	@Query(value = "SELECT * FROM proyectos_clase where clase = ?1", nativeQuery = true)
	List<JSONObject> findByProyecto(int clase);
	
	//des asignar proyecto a una clase
			@Modifying
			@Transactional
			@Query(value = "DELETE FROM `sgpi_db`.`proyectos_clase` WHERE (`clase` = ?1) and (`proyecto` = ?2)", nativeQuery = true)
			void desAsignarProyecto( String clase, String proyecto);
}
