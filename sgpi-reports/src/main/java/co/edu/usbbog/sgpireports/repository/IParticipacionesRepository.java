package co.edu.usbbog.sgpireports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.Participaciones;
import co.edu.usbbog.sgpireports.model.ParticipacionesPK;

public interface IParticipacionesRepository extends JpaRepository<Participaciones, ParticipacionesPK>{
	/**
	 * obtener nombre con la cedula de un usuario 
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT COUNT(*) FROM evento e INNER JOIN participaciones p ON e.id = p.evento_id INNER JOIN participantes pp ON p.proyecto_id_proyecto = pp.proyecto WHERE pp.usuario =?1", nativeQuery = true)
	int countParticipacionesPorUsuario(String cedula);

	/**
	 * obtener nombre con la cedula de un usuario 
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT COUNT(*) FROM participaciones p INNER JOIN proyecto pp on p.proyecto_id_proyecto = pp.id WHERE pp.semillero = ?1", nativeQuery = true)
	int countParticpacionesSemillero(Integer id);

}
