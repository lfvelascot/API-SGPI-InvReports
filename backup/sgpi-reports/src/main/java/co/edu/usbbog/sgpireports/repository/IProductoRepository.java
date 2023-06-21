package co.edu.usbbog.sgpireports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpireports.model.Producto;

public interface IProductoRepository extends JpaRepository<Producto, Integer>{
//metodo para lisatr productos de un proyecto
	@Query(value = "SELECT * FROM sgpi_db.producto where proyecto = ?1", nativeQuery = true)
	List<Producto> findByProyecto(int proyecto);
}
