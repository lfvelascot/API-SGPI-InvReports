package co.edu.usbbog.sgpi.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.repository.IProyectoRepository;

@Service
public class BibliotecaService implements IBibliotecaService {

	@Autowired
	private IProyectoRepository proyectoRepo;

	private static Logger logger = LoggerFactory.getLogger(BibliotecaService.class);

	// metodo para obtener todos lo trabajos de grado
	@Override
	public List<Proyecto> todosLosProyectosDeGrado() {
		List<Proyecto> proyectos = proyectoRepo.findByTipoProyectoGrado();
		return proyectos;
	}

	// metodo para obtener todos los proyectos que se encuentren en estado terminado
	@Override
	public List<Proyecto> todosLosProyectosTerminados(String grado, String estado) {
		List<Proyecto> x = new ArrayList<>();
		if (grado != null) {
			
			if (estado != null) {
				List<Proyecto> proyectoTerminado = proyectoRepo.findByTipoProyectoAndEstado(grado, estado);
				return proyectoTerminado;
			} else {
				logger.info("El estado no existe");
				return x;
			}
		} else {
			logger.info("no existe");
			return x;
		}
	}

	// metodo para poder acceder a la informacion de un proyecto en especifico
	@Override
	public Proyecto proyectoporid(int id) {
		if (proyectoRepo.existsById(id)) {
			Proyecto proyectos = proyectoRepo.getById(id);
			return proyectos;
		}
		return null;
	}

}