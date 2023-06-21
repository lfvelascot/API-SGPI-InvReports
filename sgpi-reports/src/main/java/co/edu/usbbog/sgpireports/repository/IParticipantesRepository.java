package co.edu.usbbog.sgpireports.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.Participantes;
import co.edu.usbbog.sgpireports.model.ParticipantesPK;

public interface IParticipantesRepository extends JpaRepository<Participantes, ParticipantesPK>{

	
	/**
	 * obtener nombre con la cedula de un usuario 
	 * @param cedula
	 * @return
	 */
	@Query(value = "SELECT COUNT(*) FROM participantes p WHERE p.usuario =?1", nativeQuery = true)
	Integer contProyectosPorUsuario(String cedula);


}
