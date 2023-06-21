package co.edu.usbbog.sgpireports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.MacroProyecto;

public interface IMacroProyectoRepository extends JpaRepository<MacroProyecto, Integer>{
	/**
	 * lista de macroproyectos que estan en estado inicio 
	 * @return
	 */
	@Query(value = "SELECT * FROM sgpi_db.macro_proyecto where estado='Inicio'", nativeQuery = true)
	List<MacroProyecto> ListMacros();
}
