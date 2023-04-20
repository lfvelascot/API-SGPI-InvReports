package co.edu.usbbog.sgpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpi.model.Materia;
import co.edu.usbbog.sgpi.model.Programa;
import net.minidev.json.JSONObject;

public interface IMateriaRepository extends JpaRepository<Materia, String>{

	//metodo para para consultar las materias por Facultad
			@Query(value = "SELECT * FROM materia where programa = ?1", nativeQuery = true)
			List<Materia> findByPrograma(int programa);
			
			//metodo consultar las clases de una materia
			@Query(value = "SELECT * FROM clase where materia = ?1", nativeQuery = true)
			List<JSONObject> findByClase(String materia);
}
