package co.edu.usbbog.sgpireports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbbog.sgpireports.model.Proyecto;

public interface IProyectoRepository extends JpaRepository<Proyecto, Integer> {

	/**
	 * lista de proyectos por su tipo 
	 * @param tipo_proyecto
	 * @return
	 */
	@Query(value = "SELECT * FROM proyecto WHERE tipo_proyecto = ?1 and semillero = ?2", nativeQuery = true)
	List<Proyecto> findByTipoProyectoS(String tipo_proyecto, int cc);
	
	/**
	 * lista de proyectos por su tipo 
	 * @param tipo_proyecto
	 * @return
	 */
	@Query(value = "SELECT * FROM proyecto WHERE estado = ?1 and semillero = ?2", nativeQuery = true)
	List<Proyecto> findByEstadoS(String tipo_proyecto, int cc);
	/**
	 * busqueda de proyectos por Grupo de investigación
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT p.* FROM proyecto p inner join semillero s on p.semillero = s.id WHERE s.grupo_investigacion =?1",nativeQuery = true)
	List<Proyecto> findByGI(int gi);
	/**
	 * busqueda de proyectos por Semillero
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT * FROM sgpi_db.proyecto where semillero=?1",nativeQuery = true)
	List<Proyecto> findBySemillero(int gi);
	/**
	 * busqueda de proyectos por Semillero
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT DISTINCT estado FROM proyecto",nativeQuery = true)
	List<String> getEstados();
	
	/**
	 * busqueda de proyectos por Semillero
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT DISTINCT estado FROM proyecto WHERE semillero = ?1",nativeQuery = true)
	List<String> getEstadosSemillero(int cc);
	
	/**
	 * busqueda de proyectos por Semillero
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT DISTINCT tipo_proyecto FROM proyecto WHERE semillero = ?1",nativeQuery = true)
	List<String> getTiposSemillero(int cc);
	/**
	 * busqueda de proyectos por Semillero
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT * FROM proyecto WHERE visibilidad = '1' ORDER BY fecha_inicio desc",nativeQuery = true)
	List<Proyecto> findAllPublicos();
	
	/**
	 * busqueda de proyectos por Semillero
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT * FROM proyecto WHERE visibilidad = '1' and estado = ?1 ORDER BY fecha_inicio desc",nativeQuery = true)
	List<Proyecto> findAllPublicosByEstado(String estado);

	/**
	 * busqueda de proyectos por Semilleros con datos en cierto periodo de años
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT p.* FROM proyecto p WHERE YEAR(p.fecha_inicio) >= :inicio AND YEAR(p.fecha_inicio) < :fin OR YEAR(p.fecha_fin) >= :inicio AND YEAR(p.fecha_fin) < :fin and p.semillero = :sem ORDER BY p.fecha_inicio desc;",nativeQuery = true)
	List<Proyecto> getProyectosSemAnios(@Param("sem") Integer id,@Param("inicio") Integer anioInicio,@Param("fin") Integer anioFin);
	
	/**
	 * busqueda de proyectos por Semilleros con datos en cierto periodo de años
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT p.* FROM proyecto p inner join semillero s on p.semillero = s.id WHERE YEAR(p.fecha_inicio) >=:inicio AND YEAR(p.fecha_inicio) <:fin OR YEAR(p.fecha_fin) >=:inicio AND YEAR(p.fecha_fin) <=:fin and s.grupo_investigacion = :sem ORDER BY p.fecha_inicio desc;",nativeQuery = true)
	List<Proyecto> getProyectosGIAnios(@Param("sem") Integer id,@Param("inicio") Integer anioInicio,@Param("fin") Integer anioFin);
	
	/**
	 * busqueda de proyectos por Semilleros con datos en cierto periodo de años
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT DISTINCT p.* FROM proyecto p INNER JOIN producto pr ON p.id = pr.proyecto WHERE YEAR(pr.fecha) >= :inicio AND YEAR(pr.fecha) < :fin and p.semillero = :sem ORDER BY p.fecha_inicio desc;",nativeQuery = true)
	List<Proyecto> getProyectosSemProdAnios(@Param("sem") Integer id,@Param("inicio") Integer anioInicio,@Param("fin") Integer anioFin);
	
	/**
	 * busqueda de proyectos por Semilleros con datos en cierto periodo de años
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT DISTINCT p.* FROM proyecto p INNER JOIN producto pr ON p.id = pr.proyecto inner join semillero s on p.semillero = s.id WHERE YEAR(pr.fecha) >= :inicio AND YEAR(pr.fecha) < :fin and s.grupo_investigacion = :sem ORDER BY p.fecha_inicio desc;",nativeQuery = true)
	List<Proyecto> getProyectosGIProdAnios(@Param("sem") Integer id,@Param("inicio") Integer anioInicio,@Param("fin") Integer anioFin);
	
	/**
	 * busqueda de proyectos por Semilleros con datos en cierto periodo de años
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT DISTINCT p.* FROM proyecto p INNER JOIN participaciones pe ON p.id = pe.proyecto_id_proyecto WHERE YEAR(pe.fecha_part) >= :inicio AND YEAR(pe.fecha_part) < :fin and p.semillero = :sem ORDER BY p.fecha_inicio desc;",nativeQuery = true)
	List<Proyecto> getProyectosSemEvenAnios(@Param("sem") Integer id,@Param("inicio") Integer anioInicio,@Param("fin") Integer anioFin);
	
	/**
	 * busqueda de proyectos por Semilleros con datos en cierto periodo de años
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT DISTINCT p.* FROM proyecto p INNER JOIN semillero s ON p.semillero = s.id INNER JOIN participaciones pe ON p.id = pe.proyecto_id_proyecto WHERE YEAR(pe.fecha_part) >= :inicio AND YEAR(pe.fecha_part) < :fin and s.grupo_investigacion = :sem ORDER BY p.fecha_inicio desc;",nativeQuery = true)
	List<Proyecto> getProyectosGIEvenAnios(@Param("sem") Integer id,@Param("inicio") Integer anioInicio,@Param("fin") Integer anioFin);
	
	/**
	 * busqueda de proyectos por Semilleros con datos en cierto periodo de años
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT DISTINCT p.* FROM proyecto p INNER JOIN proyectos_convocatoria pc ON p.id = pc.proyectos LEFT JOIN convocatoria c ON pc.convocatoria = c.id WHERE YEAR(c.fecha_inicio) >= :inicio AND YEAR(c.fecha_inicio) < :fin OR YEAR(c.fecha_final) >= :inicio AND YEAR(c.fecha_final) <= :fin and p.semillero = :sem ORDER BY p.fecha_inicio desc;",nativeQuery = true)
	List<Proyecto> getProyectosSemConvAnios(@Param("sem") Integer id,@Param("inicio") Integer anioInicio,@Param("fin") Integer anioFin);
	
	/**
	 * busqueda de proyectos por Semilleros con datos en cierto periodo de años
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT DISTINCT p.* FROM proyecto p INNER JOIN semillero s ON p.semillero = s.id INNER JOIN proyectos_convocatoria pc ON p.id = pc.proyectos LEFT JOIN convocatoria c ON pc.convocatoria = c.id WHERE YEAR(c.fecha_inicio) >= :inicio AND YEAR(c.fecha_inicio) < :fin OR YEAR(c.fecha_final) >= :inicio AND YEAR(c.fecha_final) <= :fin and s.grupo_investigacion = :sem ORDER BY p.fecha_inicio desc;",nativeQuery = true)
	List<Proyecto> getProyectosGIConvAnios(@Param("sem") Integer id,@Param("inicio") Integer anioInicio,@Param("fin") Integer anioFin);

	
	/**
	 * busqueda de proyectos por Semillero
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT DISTINCT p.* FROM proyecto p inner join programas_semilleros ps on p.semillero = ps.semillero inner join programa pr on ps.programa = pr.id WHERE pr.facultad_id = ?1 ORDER BY p.fecha_inicio desc",nativeQuery = true)
	List<Proyecto> findByFacultad(Integer id);

}


