package co.edu.usbbog.sgpi.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.TipoProyecto;
import co.edu.usbbog.sgpi.repository.IAreaConocimientoRepository;
import co.edu.usbbog.sgpi.repository.IProyectoRepository;
import co.edu.usbbog.sgpi.repository.ITipoProyectoRepository;
import net.minidev.json.JSONObject;


@Service
public class GestionFiltroProyectosService implements IGestionFiltroProyectosService{

	@Autowired
	private IProyectoRepository proyectoRepo;
	
	@Autowired
	private ITipoProyectoRepository tipoProRepo;
	
	@Autowired
	private IAreaConocimientoRepository areaRepo;
	@Autowired
	private ITipoProyectoRepository iTipoProyectoRepository;
	//metodo para obtener todos los proyectos
	@Override
	public List<Proyecto> todosLosProyectos() {
		List<Proyecto> proyectos = proyectoRepo.findAll();
		return proyectos;
	}
	//metodo para obtener los proyectos que se encuentren visibles y puedan mostrarse de manera publica
	@Override
	public List<JSONObject> todosLosProyectosVisibles() {
		List<JSONObject> proyectos = proyectoRepo.proyectosVisibles();
		return proyectos;
	}
//metodo para obtener proyectos segun un tipo en especifico
	@Override
	public List<Proyecto> todosLosProyectosPorTipoProyecto(String tipo_proyecto) {
		List<Proyecto> x = new ArrayList<>();
		if(!tipoProRepo.existsById(tipo_proyecto)) {
			return x;
		}
		List<Proyecto> proyectos = proyectoRepo.findByTipoProyecto(tipo_proyecto);
		return proyectos;
	}


//metodo para obtener los proyectos por un area en especifico
	@Override
	public List<Proyecto> todosLosProyectosPorAreaConocimiento(int areaConocimiento) {
		List<Proyecto> x = new ArrayList<>();
		if(!areaRepo.existsById(areaConocimiento)) {
			
			return x;
		}
		List<Proyecto> proyectos = proyectoRepo.findByAreaConocimiento(areaConocimiento);
		return proyectos;
	}
	
	//metodo para obtener un proyecto por nombre
	@Override
	public List<Proyecto> todosLosProyectosPorNombre(String titulo) {
		List<Proyecto> x = new ArrayList<>();
		if(proyectoRepo.findByTitulo(titulo) == null) {			
			return x;
		}
		List<Proyecto> proyectos = proyectoRepo.findByTitulo(titulo);
		return proyectos;
	}

//metodo para obtener los tipos de proyecto
	@Override
	public List<TipoProyecto> todosLosTiposProyecto() {
		List<TipoProyecto> tipo=iTipoProyectoRepository.findAll();
		if (tipo.equals(null)) {
			tipo = new ArrayList<TipoProyecto>();
		}
		return tipo;
	}

	

	

	
}
