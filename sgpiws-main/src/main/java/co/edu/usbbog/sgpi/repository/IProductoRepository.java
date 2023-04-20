package co.edu.usbbog.sgpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbbog.sgpi.model.Producto;
import co.edu.usbbog.sgpi.model.Semillero;

public interface IProductoRepository extends JpaRepository<Producto, Integer>{
//metodo para lisatr productos de un proyecto
	@Query(value = "SELECT * FROM sgpi_db.producto where proyecto = ?1", nativeQuery = true)
	List<Producto> findByProyecto(int proyecto);
}
