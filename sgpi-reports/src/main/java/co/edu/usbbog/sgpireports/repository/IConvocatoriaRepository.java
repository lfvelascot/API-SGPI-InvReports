package co.edu.usbbog.sgpireports.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.Convocatoria;
//metodo para obtener las convocatorias segun un estado en especifico
public interface IConvocatoriaRepository extends JpaRepository<Convocatoria, Integer>{
	/**
	 * lista de convocatorias por estado 
	 * @param estado
	 * @return
	 */
	@Query(value = "SELECT * FROM sgpi_db.convocatoria where estado = ?1", nativeQuery = true)
	List<Convocatoria> findByEstadoAbierto(String estado);
	
	/**
	 * lista de convocatorias por estado 
	 * @param estado
	 * @return
	 */
	@Query(value = "SELECT COUNT(*) FROM proyectos_convocatoria p INNER JOIN participantes pp on p.proyectos = pp.proyecto WHERE pp.usuario =?1", nativeQuery = true)
	Integer CountParticpacionesEnConvocatorias(String estado);

	/**
	 * obtener nombre con la cedula de un usuario 
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT COUNT(*) FROM proyectos_convocatoria p INNER JOIN proyecto pp on p.proyectos = pp.id WHERE pp.semillero =?1", nativeQuery = true)
	Integer countParticipacionesSemillero(Integer id);
}
