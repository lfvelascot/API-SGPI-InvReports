package co.edu.usbbog.sgpireports.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Facultad;
import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.repository.IFacultadRepository;
import co.edu.usbbog.sgpireports.repository.IGrupoInvestigacionRepository;
import co.edu.usbbog.sgpireports.repository.IProgramaRepository;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;
import co.edu.usbbog.sgpireports.repository.ISemilleroRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Service
public class GestionFiltrosService implements IGestionFiltros {

	@Autowired
	private IFacultadRepository iFacultadRepository;
	@Autowired
	private IProgramaRepository iProgramaRepository;
	@Autowired
	private IGrupoInvestigacionRepository iGrupoInvestigacionRepository;
	@Autowired
	private ISemilleroRepository iSemilleroRepository;
	@Autowired
	private IProyectoRepository iProyectoRepository;

	/**
	 * Busca todos los datos de las facultades
	 * 
	 * @return lista con las facultades
	 */
	@Override
	public List<Facultad> buscarFacultades() {
		return iFacultadRepository.findAll();
	}

	/**
	 * Busca los programas de una facultad
	 * 
	 * @param id de la facultad
	 * @return listado de los programas de la facultad
	 */
	@Override
	public List<Programa> buscarProgramasPorFacultad(int facultad) {
		return iProgramaRepository.findByFacultad(facultad);
	}

	/**
	 * Busca los programas
	 * 
	 * @return lista con todos los programas
	 */
	@Override
	public List<Programa> buscarProgramas() {
		return iProgramaRepository.findAll();
	}

	/**
	 * Busca los semilleros
	 * 
	 * @return lista con todos los semilleros
	 */
	@Override
	public List<Semillero> buscarSemilleros() {
		return iSemilleroRepository.findAll();
	}

	/**
	 * Busca los grupos de investigación
	 * 
	 * @return lista con todos los grupos de investigación
	 */
	@Override
	public List<GrupoInvestigacion> buscarGruposInv() {
		return iGrupoInvestigacionRepository.findAll();
	}

	/**
	 * Busca los semilleros de un grupo de investigación
	 * 
	 * @param id del grupo de investigación
	 * @return lista de semilleros del grupo de investigación
	 */
	@Override
	public List<Semillero> buscarSemillerosPorGrupoInv(int grupo) {
		return iSemilleroRepository.findByGrupoInvestigacion(grupo);
	}

	/**
	 * Busca los proyecto de un grupo de investigación
	 * 
	 * @param id del grupo de investigación
	 * @return listado de proyectos del grupo de investigación
	 */
	@Override
	public List<Proyecto> buscarProyectosPorGI(int gi) {
		return iProyectoRepository.findByGI(gi);
	}

	/**
	 * Busca los proyecto de un semillero
	 * 
	 * @param id del semillero
	 * @return listado de proyectos del semillero
	 */
	@Override
	public List<Proyecto> buscarProyectosPorSemillero(int gi) {
		return iProyectoRepository.findBySemillero(gi);
	}

	/**
	 * Busca los grupos de investigación de una facultad
	 * 
	 * @param id de la facultad
	 * @return listado de los grupos de investigación de la facultad
	 */
	@Override
	public List<GrupoInvestigacion> buscarGruposInvPorFacultad(int cc) {
		return iGrupoInvestigacionRepository.findByFacultad(cc);
	}

	/**
	 * Busca los proyecto publicos
	 * 
	 * @return listado de proyectos publicos
	 */
	@Override
	public List<Proyecto> buscarProyectos() {
		return iProyectoRepository.findAllPublicos();
	}

	@Override
	public JSONArray proyectosToJSONArray(List<Proyecto> lista) {
		return getSalida(lista.stream().map(Proyecto::toJsonF).collect(Collectors.toList()));
	}

	@Override
	public JSONArray gruposToJSONArray(List<GrupoInvestigacion> lista) {
		return getSalida(lista.stream().map(GrupoInvestigacion::toJsonF).collect(Collectors.toList()));
	}

	@Override
	public JSONArray semillerosToJSONArray(List<Semillero> lista) {
		return getSalida(lista.stream().map(Semillero::toJsonF).collect(Collectors.toList()));
	}

	@Override
	public JSONArray facultadesToJSONArray(List<Facultad> lista) {
		return getSalida(lista.stream().map(Facultad::toJsonF).collect(Collectors.toList()));
	}

	@Override
	public JSONArray programasToJSONArray(List<Programa> lista) {
		return getSalida(lista.stream().map(Programa::toJsonF).collect(Collectors.toList()));
	}

	@Override
	public JSONArray getSalida(List<JSONObject> data) {
		salida.clear();
		salida.addAll(data);
		return salida;
	}
}
