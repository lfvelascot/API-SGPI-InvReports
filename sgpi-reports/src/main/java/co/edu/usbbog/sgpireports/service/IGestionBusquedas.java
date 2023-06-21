package co.edu.usbbog.sgpireports.service;

import java.util.List;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;

public interface IGestionBusquedas extends IListasJSON {


	// Buscar todos los semilleros
	public List<Semillero> buscarSemilleros();

	// Buscar semilleros por programa
	public List<Semillero> buscarSemillerosPorPrograma(int programa);

	// Buscar todos los grupos de investigación
	public List<GrupoInvestigacion> buscarGruposInv();

	// Buscar GIs por facultad
	public List<GrupoInvestigacion> buscarGruposInvPorFacultad(int cc);

	// Buscar proyectos
	public List<Proyecto> buscarProyectos();

	// Buscar proyectos por estado
	public List<Proyecto> buscarProyectosPorEstado(String cc);
	

}
