package co.edu.usbbog.sgpireports.service;

import java.util.List;

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
	
	@Override
	public List<Facultad> buscarFacultades() {
		return iFacultadRepository.findAll();
	}

	@Override
	public List<Programa> buscarProgramasPorFacultad(int facultad) {
		return iProgramaRepository.findByFacultad(facultad);
	}

	@Override
	public List<Programa> buscarProgramas() {
		return iProgramaRepository.findAll();
	}

	@Override
	public List<Semillero> buscarSemilleros() {
		return iSemilleroRepository.findAll();
	}

	@Override
	public List<Semillero> buscarSemillerosPorPrograma(int programa) {
		return iSemilleroRepository.findByPrograma(programa);
	}

	@Override
	public List<GrupoInvestigacion> buscarGruposInv() {
		return iGrupoInvestigacionRepository.findAll();
	}

	@Override
	public List<GrupoInvestigacion> buscarGruposInvPorPrograma(int programa) {
		return iGrupoInvestigacionRepository.findByPrograma(programa);
	}

	@Override
	public List<Semillero> buscarSemillerosPorGrupoInv(int grupo) {
		return iSemilleroRepository.findByGrupoInvestigacion(grupo);
	}

	@Override
	public List<Proyecto> buscarProyectosPorGI(int gi) {
		return iProyectoRepository.findByGI(gi);
	}

	@Override
	public List<Proyecto> buscarProyectosPorSemillero(int gi) {
		return iProyectoRepository.findBySemillero(gi);
	}

	@Override
	public List<GrupoInvestigacion> buscarGruposInvPorFacultad(int cc) {
		return iGrupoInvestigacionRepository.findByFacultad(cc);
	}

	@Override
	public List<Proyecto> buscarProyectos() {
		return iProyectoRepository.findAll();
	}

	@Override
	public List<String> buscarEstadoProyectos() {
		return iProyectoRepository.getEstados();
	}

	@Override
	public List<Semillero> buscarSemillerosPorGI(int cc) {
		return iSemilleroRepository.findByGrupoInvestigacion(cc);
	}

	@Override
	public List<Proyecto> buscarProyectosPorEstado(String cc) {
		return iProyectoRepository.findByEstado(cc);
	}



}
