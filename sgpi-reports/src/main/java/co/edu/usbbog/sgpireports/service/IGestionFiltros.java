package co.edu.usbbog.sgpireports.service;

import java.util.List;

import co.edu.usbbog.sgpireports.model.Facultad;
import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;

public interface IGestionFiltros extends IListasJSON {

	// Buscar todas las facultades
	public List<Facultad> buscarFacultades();

	// Buscar programas por facultad
	public List<Programa> buscarProgramasPorFacultad(int facultad);

	// Buscar todos los programas
	public List<Programa> buscarProgramas();

	// Buscar todos los semilleros
	public List<Semillero> buscarSemilleros();

	// Buscar todos los grupos de investigación
	public List<GrupoInvestigacion> buscarGruposInv();

	// Buscar Semilleros de un grupo de investigación
	public List<Semillero> buscarSemillerosPorGrupoInv(int grupo);

	// buscar proyectos por grupo de investigación
	public List<Proyecto> buscarProyectosPorGI(int gi);

	// buscar proyectos por grupo de investigación
	public List<Proyecto> buscarProyectosPorSemillero(int gi);

	// Buscar GIs por facultad
	public List<GrupoInvestigacion> buscarGruposInvPorFacultad(int cc);

	// Buscar proyectos
	public List<Proyecto> buscarProyectos();

}
