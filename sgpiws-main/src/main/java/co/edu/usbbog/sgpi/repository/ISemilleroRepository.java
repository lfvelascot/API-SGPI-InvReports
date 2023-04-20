package co.edu.usbbog.sgpi.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpi.model.GrupoInvestigacion;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.Semillero;
import co.edu.usbbog.sgpi.model.Usuario;
import net.minidev.json.JSONObject;

public interface ISemilleroRepository extends JpaRepository<Semillero, Integer>{
	@Modifying
	@Transactional
	@Query(value="UPDATE `sgpi_db`.`semillero` SET `lider_semillero` = null WHERE (`id` = ?1)",nativeQuery=true)
	void deleteLiderSemilleroById(String semillero);

	//solo para consultar por grupo de investigacion
	@Query(value = "select * from semillero where grupo_investigacion= ?1", nativeQuery = true)
	List<Semillero> findByGrupoInvestigacion(int grupoInvestigacion);
	
	//solo para consultar por lider
	@Query(value = "select * from semillero where lider_semillero= ?1", nativeQuery = true)
	List<Semillero> findByLiderSemillero(String lider);
	
	//solo para consultar por linea
	@Query(value = "select * from semillero where linea_investigacion= ?1", nativeQuery = true)
	List<Semillero> findByLineaInvestigacion(String linea);
	
	//solo para consultar el programa
		@Query(value = "SELECT * FROM programas_semilleros where semillero = ?1", nativeQuery = true)
		List<JSONObject> findByPrograma(int semillero);

		
		//des asignar semillero a programa
		@Modifying
		@Transactional
		@Query(value = "DELETE FROM `sgpi_db`.`programas_semilleros` WHERE (`programa` = ?1 ) and (`semillero` = ?2 )", nativeQuery = true)
		void desAsignarPrograma( String programa, String semillero);
		
		
		//solo para consultar el programa
				@Query(value = "SELECT * FROM sgpi_db.usuario where semillero_id = ?1", nativeQuery = true)
				List<Usuario> findByUsuarios(int semillero);
				
				//solo para consultar el programa
				@Query(value = "SELECT * FROM sgpi_db.proyecto where semillero = ?1", nativeQuery = true)
				List<Proyecto> findByProyectos(int semillero);
				
				
				@Modifying
				@Transactional
				@Query(value= "UPDATE `sgpi_db`.`usuario` SET `semillero_id` = null WHERE (`cedula` = ?1)", nativeQuery=true)
				void setSemilleroNullById(String cedula);
				
				
				
				@Query(value = "select count(*) as \"semilleros_contados\" from sgpi_db.semillero", nativeQuery = true)
				List<JSONObject> contarSemilleros();
		
}
