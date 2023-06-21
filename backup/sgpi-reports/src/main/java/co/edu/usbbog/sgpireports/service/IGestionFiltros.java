package co.edu.usbbog.sgpireports.service;

import java.util.List;

import co.edu.usbbog.sgpireports.model.Facultad;
import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;

public interface IGestionFiltros {

	// Buscar todas las facultades
	public List<Facultad> buscarFacultades();

	// Buscar programas por facultad
	public List<Programa> buscarProgramasPorFacultad(int facultad);

	// Buscar todos los programas
	public List<Programa> buscarProgramas();

	// Buscar todos los semilleros
	public List<Semillero> buscarSemilleros();

	// Buscar semilleros por programa
	public List<Semillero> buscarSemillerosPorPrograma(int programa);

	// Buscar todos los grupos de investigación
	public List<GrupoInvestigacion> buscarGruposInv();

	// Buscar grupos de investigación por prgrama
	public List<GrupoInvestigacion> buscarGruposInvPorPrograma(int programa);

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

	// Obtener listado de estados de proyectos
	public List<String> buscarEstadoProyectos();

	// Buscar Semilleros por GI
	public List<Semillero> buscarSemillerosPorGI(int cc);

	// Buscar proyectos por estado
	public List<Proyecto> buscarProyectosPorEstado(String cc);

}
