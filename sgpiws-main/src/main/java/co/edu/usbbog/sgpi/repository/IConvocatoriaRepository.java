package co.edu.usbbog.sgpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpi.model.Convocatoria;
//metodo para obtener las convocatorias segun un estado en especifico
public interface IConvocatoriaRepository extends JpaRepository<Convocatoria, Integer>{
	/**
	 * lista de convocatorias por estado 
	 * @param estado
	 * @return
	 */
	@Query(value = "SELECT * FROM sgpi_db.convocatoria where estado = ?1", nativeQuery = true)
	List<Convocatoria> findByEstadoAbierto(String estado);
}
