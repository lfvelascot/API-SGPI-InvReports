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
import co.edu.usbbog.sgpireports.repository.IGrupoInvestigacionRepository;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;
import co.edu.usbbog.sgpireports.repository.ISemilleroRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Service
public class GestionBusquedas implements IGestionBusquedas {

	@Autowired
	private IGrupoInvestigacionRepository iGrupoInvestigacionRepository;
	@Autowired
	private ISemilleroRepository iSemilleroRepository;
	@Autowired
	private IProyectoRepository iProyectoRepository;

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
	 * Busca los semilleros de un programa
	 * 
	 * @param id del programa
	 * @return lista de semilleros de un programa
	 */
	@Override
	public List<Semillero> buscarSemillerosPorPrograma(int programa) {
		return iSemilleroRepository.findByPrograma(programa);
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

	/**
	 * Busca de los proyectos con un estado
	 * 
	 * @param nombre del estado de los proyectos
	 * @return listado de los proyectos con el estado
	 */
	@Override
	public List<Proyecto> buscarProyectosPorEstado(String cc) {
		return iProyectoRepository.findAllPublicosByEstado(cc);
	}

	@Override
	public JSONArray proyectosToJSONArray(List<Proyecto> lista) {
		return getSalida(lista.stream().map(Proyecto::toJson).collect(Collectors.toList()));
	}

	@Override
	public JSONArray gruposToJSONArray(List<GrupoInvestigacion> lista) {
		return getSalida(lista.stream().map(GrupoInvestigacion::toJson).collect(Collectors.toList()));
	}

	@Override
	public JSONArray semillerosToJSONArray(List<Semillero> lista) {
		return getSalida(lista.stream().map(Semillero::toJson).collect(Collectors.toList()));
	}

	@Override
	public JSONArray facultadesToJSONArray(List<Facultad> lista) {
		return getSalida(lista.stream().map(Facultad::toJson).collect(Collectors.toList()));
	}

	@Override
	public JSONArray programasToJSONArray(List<Programa> lista) {
		return getSalida(lista.stream().map(Programa::toJson).collect(Collectors.toList()));
	}

	@Override
	public JSONArray getSalida(List<JSONObject> data) {
		salida.clear();
		salida.addAll(data);
		return salida;
	}

}
