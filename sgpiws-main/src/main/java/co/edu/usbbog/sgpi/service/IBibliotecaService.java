package co.edu.usbbog.sgpi.service;

import java.util.List;
import java.util.Optional;

import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.TipoProyecto;

public interface IBibliotecaService {
	public List<Proyecto> todosLosProyectosDeGrado();
	public List<Proyecto> todosLosProyectosTerminados(String grado, String estado);
	public Proyecto proyectoporid(int id);
	
}
