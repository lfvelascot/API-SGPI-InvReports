package co.edu.usbbog.sgpi.service;

import java.util.List;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.TipoProyecto;
import net.minidev.json.JSONObject;

public interface IGestionFiltroProyectosService {
	/**
	 * todos los proyectos
	 * @return
	 */
	public List<Proyecto> todosLosProyectos();
	/**
	 * todos los proyectos visibles 
	 * @return
	 */
	public List<JSONObject> todosLosProyectosVisibles();
	/**
	 * todos los proyectos por tipo de proyecto 
	 * @param tipo_proyecto
	 * @return
	 */
	public List<Proyecto> todosLosProyectosPorTipoProyecto(String tipo_proyecto);
	/**
	 * todos los proyectos por area de conocimiento 
	 * @param areaConocimiento
	 * @return
	 */
	public List<Proyecto> todosLosProyectosPorAreaConocimiento(int areaConocimiento);
	/**
	 * todps los proyectos por titulo 
	 * @param titulo
	 * @return
	 */
	public List<Proyecto> todosLosProyectosPorNombre(String titulo);

	/**
	 * todos los tipos de proyectos 
	 * @return
	 */
	public List<TipoProyecto> todosLosTiposProyecto();
}
