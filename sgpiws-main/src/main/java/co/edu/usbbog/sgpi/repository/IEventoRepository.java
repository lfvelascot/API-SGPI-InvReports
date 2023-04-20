package co.edu.usbbog.sgpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpi.model.Evento;
import net.minidev.json.JSONObject;

public interface IEventoRepository extends JpaRepository<Evento, Integer>{
	//metodo para obtener las participaciones de un evento en especifico
	@Query(value = "SELECT * FROM sgpi_db.participaciones where evento_id = ?1", nativeQuery = true)
	List<JSONObject> findByParticipaciones(int id);
//metodo para obtener los eventos que se encuentren en proceso
	@Query(value = "SELECT * FROM sgpi_db.evento where estado='Abierto'", nativeQuery = true)
	List<JSONObject> listarEventos();
//metodo para contar el numero de eventos totales creados en el aplicativo
	@Query(value = "select count(*) as \"eventos_contados\" from sgpi_db.evento", nativeQuery = true)
	List<JSONObject> contarEventos();
	@Query(value = "SELECT * FROM sgpi_db.evento where estado='Abierto' and entidad='Interno'", nativeQuery = true)
	List<Evento> listarEventoInternos();
}
