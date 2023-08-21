package co.edu.usbbog.sgpireports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.Log;

public interface ILogRepository  extends JpaRepository<Log, Integer>{

	@Query(value = "select * from log where usuario=?1" , nativeQuery = true)
	List<Log> findLogsByUser(String cc);
	
	@Query(value = "select * from log where accion=?1" , nativeQuery = true)
	List<Log> findLogsByAccion(String accion);

}
