package co.edu.usbbog.sgpi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.TipoProyecto;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public interface IProyectoRepository extends JpaRepository<Proyecto, Integer> {

	// solo para consultar por tipo y estado
	
	@Query(value = "SELECT * FROM proyecto WHERE tipo_proyecto = ?1 and estado = ?2", nativeQuery = true)
	List<Proyecto> findByTipoProyectoAndEstado(String tipoProyecto, String estado);

	// solo para consultar por estado
	@Transactional(readOnly = true)
	List<Proyecto> findByEstado(String estado);
	/**
	 * lista de proyectos que sean tipo grado
	 * @return
	 */
	@Query(value = "SELECT * FROM proyecto WHERE tipo_proyecto = 'grado'", nativeQuery = true)
	List<Proyecto> findByTipoProyectoGrado();
	/**
	 * lista de proyectos por su tipo 
	 * @param tipo_proyecto
	 * @return
	 */
	@Query(value = "SELECT * FROM proyecto WHERE tipo_proyecto = ?1", nativeQuery = true)
	List<Proyecto> findByTipoProyecto(String tipo_proyecto);
	/**
	 * lista de proyectos por area de conocimiento 
	 * @param area_conocimiento
	 * @return
	 */
	
	@Query(value = "SELECT * FROM sgpi_db.proyecto JOIN areas_conocimiento ON sgpi_db.proyecto.id=areas_conocimiento.proyecto WHERE areas_conocimiento.area_conocimiento= ?1", nativeQuery = true)
	List<Proyecto> findByAreaConocimiento(int area_conocimiento);
	/**
	 * datos de un proyecto en especifico 
	 * @param titulo
	 * @return
	 */
	@Query(value = "SELECT * FROM sgpi_db.proyecto where titulo = ?1", nativeQuery = true)
	List<Proyecto> findByTitulo(String titulo);
	/**
	 * proyectos de clase de un usuario 
	 * @param cedula
	 * @return
	 */
	@Query(value="SELECT usuario.cedula as \"cedula\", proyecto.id, proyecto.titulo, proyecto.estado, proyecto.descripcion, proyecto.fecha_inicio, proyecto.fecha_fin, proyecto.metodologia, proyecto.visibilidad, proyecto.tipo_proyecto from participantes inner join usuario on participantes.usuario =  usuario.cedula inner join proyecto on proyecto.id = participantes.proyecto where participantes.usuario=?1 and (proyecto.tipo_proyecto='Aula' or proyecto.tipo_proyecto='Grado') ",nativeQuery = true)
	List<JSONObject> proyectosParticipaClase(String cedula);
	/**
	 * datos de un proyecto de semillero 
	 * @param semillero
	 * @return
	 */
	@Query(value="select * from proyecto where proyecto.semillero=?1",nativeQuery = true)
	List<JSONObject> proyectosParticipaSemillero(int semillero);
	/**
	 * lista de proyectos de grado en los que participa un usuario 
	 * @param cedula
	 * @return
	 */
	@Query(value="select id, titulo,descripcion,estado  from proyecto, participantes where proyecto.tipo_proyecto=\"Grado\" and proyecto.id=participantes.proyecto and participantes.usuario=?1",nativeQuery = true)
	List<JSONObject> proyectosParticipaGrado(String cedula);
	/**
	 * lista de proyectos visibles 
	 * @return
	 */
	@Query(value="select area_conocimiento.nombre, proyecto.id, proyecto.titulo, proyecto.estado, proyecto.descripcion, proyecto.fecha_inicio, proyecto.fecha_fin, proyecto.metodologia, proyecto.visibilidad from areas_conocimiento inner join area_conocimiento on areas_conocimiento.area_conocimiento = area_conocimiento.id inner join proyecto on areas_conocimiento.proyecto = proyecto.id where proyecto.visibilidad = 1",nativeQuery = true)
	List<JSONObject> proyectosVisibles();
	/**
	 * metodo para eliminar un area de conocimiento de un proyecto 
	 * @param area
	 * @param pro
	 */
	@Modifying
	@Transactional
	@Query(value="DELETE FROM sgpi_db.areas_conocimiento WHERE (proyecto = ?2) and (area_conocimiento = ?1)",nativeQuery = true)
	void eliminarArea(int area,int pro);
	/**
	 * lista de proyectos de convocatoria 
	 * @param cedula
	 * @return
	 */
	@Query(value="select proyecto.id,proyecto.descripcion,proyecto.titulo,proyecto.estado, proyectos_convocatoria.convocatoria from proyecto,proyectos_convocatoria,participantes where proyecto.id=proyectos_convocatoria.proyectos and  participantes.proyecto=proyecto.id and participantes.usuario=?1",nativeQuery = true)
	List<JSONObject> tusProyectoConvocatoria(String cedula);
	/**
	 * lista de proyectos de semillero 
	 * @param cedula
	 * @return
	 */
	@Query(value="select proyecto.id,proyecto.descripcion,proyecto.titulo,proyecto.estado, proyecto.semillero from proyecto,participantes where proyecto.tipo_proyecto=\"Semillero\" and  participantes.proyecto=proyecto.id and participantes.usuario=?1 and proyecto.id=participantes.proyecto",nativeQuery = true)
	List<JSONObject> tusProyectoSemillero(String cedula);
	/**
	 * lista de participaciones en una convocatoria 
	 * @param proyecto
	 * @return
	 */
	@Query(value="Select convocatoria.nombre_convocatoria,convocatoria.id from convocatoria, proyectos_convocatoria where proyectos_convocatoria.proyectos=?1 and proyectos_convocatoria.convocatoria=convocatoria.id",nativeQuery = true)
	List<JSONObject> paticipacionesConvocatoria(int proyecto);
	/**
	 * lista de proyectos de grado
	 * @return
	 */
	@Query(value="select * from proyecto where tipo_proyecto=\"Grado\"",nativeQuery = true)
	List<JSONObject> proyectosGrado();
	/**
	 * lista de proeyctos de clase
	 * @param clase
	 * @return
	 */
	@Query(value="select * from proyecto, proyectos_clase where proyecto.id=proyectos_clase.proyecto and proyecto.estado=\"Propuesta\" and proyectos_clase.clase=?1",nativeQuery = true)
	List<JSONObject> proyectosPropuestaClase(int clase);
	/**
	 * lista de proyectos de clase en desarrollo 
	 * @param curso
	 * @return
	 */
	@Query(value="select * from proyecto, proyectos_clase where proyecto.id=proyectos_clase.proyecto and proyecto.estado=\"Desarrollo\" and proyectos_clase.clase=?1",nativeQuery = true)
	List<JSONObject> proyectosDesarrolloClase(int curso);
	/**
	 * lista de proyectos de grado finalizados 
	 * @param curso
	 * @return
	 */
	@Query(value="select * from proyecto, proyectos_clase where proyecto.id=proyectos_clase.proyecto and proyecto.estado=\"Finalizado\" and proyectos_clase.clase=?1",nativeQuery = true)
	List<JSONObject> proyectosFinalizadosClase(int curso);
	/**
	 * lista de proyectos de convocatoria 
	 * @param convocatoria
	 * @param id
	 * @return
	 */
	@Query(value="select distinct proyecto.id, proyecto.titulo, proyecto.descripcion,proyectos_convocatoria.id_proyecto ,\r\n"
			+ " convocatoria.nombre_convocatoria from proyectos_convocatoria,proyecto, \r\n"
			+ "participantes,convocatoria where proyectos_convocatoria.proyectos=proyecto.id and convocatoria.id=?1 and\r\n"
			+ "convocatoria.id=proyectos_convocatoria.convocatoria and participantes.usuario=?2",nativeQuery = true)
	List<JSONObject> tusProyectosConvocatoria(int convocatoria,int id);
	/**
	 * lista de proyectos de convocatoria en postulacion 
	 * @param estado
	 * @return
	 */
	@Query(value="select proyecto.id,proyecto.titulo,proyecto.descripcion,convocatoria.nombre_convocatoria, proyectos_convocatoria.id_proyecto from proyecto, proyectos_convocatoria,convocatoria where "
			+ "proyecto.id=proyectos_convocatoria.proyectos and proyectos_convocatoria.convocatoria=convocatoria.id and proyectos_convocatoria.id_proyecto=?1 and convocatoria.estado='Abierto'",nativeQuery = true)
	List<JSONObject> ProyectosPostuladosConvocatorias(String estado);
	/**
	 * datps de un proyecto de convocatoria 
	 * @param id
	 * @return
	 */
	@Query(value= "select proyecto.id,proyecto.titulo,proyecto.descripcion,convocatoria.nombre_convocatoria, proyectos_convocatoria.id_proyecto ,convocatoria.id as id_convocatoria from proyecto, proyectos_convocatoria,convocatoria where proyecto.id=proyectos_convocatoria.proyectos and proyectos_convocatoria.convocatoria=convocatoria.id and proyecto.id=?1 ",nativeQuery = true)
	List<JSONObject> datosProyectoConvocatoria(int id);
	/**
	 * lista de proyectos de grado en estado de inicio 
	 * @param cedula
	 * @return
	 */
	@Query(value= "select proyecto.id,titulo,proyecto.tipo_proyecto,proyecto.estado,descripcion from proyecto, participantes,usuario where proyecto.estado='Inicio' and proyecto.id=participantes.proyecto and usuario.cedula=participantes.usuario and usuario.cedula=?1 and proyecto.tipo_proyecto='Grado'",nativeQuery = true)
	List<JSONObject> trabajoGradoInicio(String cedula);
	/**
	 * lista de proyectos de grado en desarrollo 
	 * @param cedula
	 * @return
	 */
	@Query(value= "select proyecto.id,titulo,proyecto.tipo_proyecto,proyecto.estado,descripcion from proyecto, participantes,usuario where proyecto.estado='Desarrollo' and proyecto.id=participantes.proyecto and usuario.cedula=participantes.usuario and usuario.cedula=?1 and proyecto.tipo_proyecto='Grado'",nativeQuery = true)
	List<JSONObject> trabajoGradoDesarrollo(String cedula);
	/**
	 * lista de proyectos de grado en estado de correcciones 
	 * @param cedula
	 * @return
	 */
	@Query(value= "select proyecto.id,titulo,proyecto.tipo_proyecto,proyecto.estado,descripcion from proyecto, participantes,usuario where proyecto.estado='Correcciones' and proyecto.id=participantes.proyecto and usuario.cedula=participantes.usuario and usuario.cedula=?1 and proyecto.tipo_proyecto='Grado'",nativeQuery = true)
	List<JSONObject> trabajoGradoJurado(String cedula);
	/**
	 * lista de proyectos de grado en estado finalizados 
	 * @param cedula
	 * @return
	 */
	@Query(value= "select proyecto.id,titulo,proyecto.tipo_proyecto,proyecto.estado,descripcion from proyecto, participantes,usuario where proyecto.estado='Finalizado' and proyecto.id=participantes.proyecto and usuario.cedula=participantes.usuario and usuario.cedula=?1 and proyecto.tipo_proyecto='Grado'",nativeQuery = true)
	List<JSONObject> trabajoGradoFinalizados(String cedula);
	/**
	 * lista de proyectos de grado en estado de rechazados 
	 * @param cedula
	 * @return
	 */
	@Query(value= "select proyecto.id,titulo,proyecto.tipo_proyecto,proyecto.estado,descripcion from proyecto, participantes,usuario where proyecto.estado='Rechazado' and proyecto.id=participantes.proyecto and usuario.cedula=participantes.usuario and usuario.cedula=?1 and proyecto.tipo_proyecto='Grado'",nativeQuery = true)
	List<JSONObject> trabajoGradoRechazados(String cedula);
	/**
	 * busqueda de un proyecto en especifico 
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT * FROM sgpi_db.proyecto where id=?1",nativeQuery = true)
	JSONObject buscarProyecto(int proyectoId);
	/**
	 * busqueda de proyectos finalizados
	 * @param proyectoId
	 * @return
	 */
	@Query(value= "SELECT * FROM sgpi_db.proyecto where estado='Finalizado'",nativeQuery = true)
	List<JSONObject> proyectosFinalizados();
	@Query(value= "select distinct proyecto.id, proyecto.titulo, proyecto.descripcion,proyectos_convocatoria.id_proyecto ,convocatoria.nombre_convocatoria from proyectos_convocatoria,proyecto, \r\n"
			+ "			participantes,convocatoria where proyectos_convocatoria.proyectos=proyecto.id and \r\n"
			+ "            convocatoria.id=proyectos_convocatoria.convocatoria and participantes.usuario=?1",nativeQuery = true)
	List<JSONObject> proyectosConvocatoriaUsuario(String cedula);
}
