package co.edu.usbbog.sgpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpi.model.MacroProyecto;
import co.edu.usbbog.sgpi.model.Materia;

public interface IMacroProyectoRepository extends JpaRepository<MacroProyecto, Integer>{
	/**
	 * lista de macroproyectos que estan en estado inicio 
	 * @return
	 */
	@Query(value = "SELECT * FROM sgpi_db.macro_proyecto where estado='Inicio'", nativeQuery = true)
	List<MacroProyecto> ListMacros();
}
