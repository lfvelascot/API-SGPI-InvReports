package co.edu.usbbog.sgpireports.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbbog.sgpireports.model.Firma;

public interface IFirmaRepository extends JpaRepository<Firma, String> {

	// metodo para obtener la firma de un usuario
	@Query(value = "SELECT * FROM sgpi_db.firma where usuario = ?1", nativeQuery = true)
	Firma findByUsuario(String usuario);

	@Modifying
	@Query(value = "INSERT INTO `sgpi_db`.`firma`(`usuario`,`nombre`) VALUES (:usuario,:nombre)", nativeQuery = true)
	@Transactional
	void addFirma(@Param("usuario") String usuario, @Param("nombre") String nombre);

}
