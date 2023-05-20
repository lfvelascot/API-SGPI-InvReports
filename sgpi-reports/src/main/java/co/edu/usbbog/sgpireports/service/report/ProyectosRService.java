package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Producto;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.ProyectoR;
import co.edu.usbbog.sgpireports.repository.IGrupoInvestigacionRepository;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;
import co.edu.usbbog.sgpireports.repository.ISemilleroRepository;

@Service
public class ProyectosRService {
	
	@Autowired
	private IProyectoRepository proyecto;
	@Autowired
	private ISemilleroRepository semillero;
	@Autowired
	private SemillerosRService semilleros;
	@Autowired 
	private IGrupoInvestigacionRepository grupos;
	

	public List<ProyectoR> getProyectosGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			salida.addAll(getProyectosSemillero(s.getId()));
		}
		return salida;
	}
	
	public List<ProyectoR> getProyectosSemillero(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ProyectoR> salida = new ArrayList<>();
		for(Proyecto p: aux) {
			salida.add(new ProyectoR(
					p.getTitulo(),
					p.getEstado(), 
					p.getTipoProyecto().getNombre(),
					p.getDescripcion(),
					p.getFechaInicio(),
					p.getFechaFin(),
					p.getMetodologia(),
					"",
					p.getSemillero().getNombre()));
		}
		return salida;
	}
	
	public List<ProyectoR> getProyectosSemilleroE(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ProyectoR> salida = new ArrayList<>();
		for(Proyecto p: aux) {
			if(!p.getParticipaciones().isEmpty()) {
				salida.add(new ProyectoR(
						p.getTitulo(),
						p.getEstado(), 
						p.getTipoProyecto().getNombre(),
						p.getDescripcion(),
						p.getFechaInicio(),
						p.getFechaFin(),
						p.getMetodologia(),
						"",
						p.getSemillero().getNombre()));
			}
		}
		return salida;
	}

	public List<ProyectoR> getProyectosSemilleroC(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ProyectoR> salida = new ArrayList<>();
		for(Proyecto p: aux) {
			if(!p.getProyectosConvocatoria().isEmpty()) {
				salida.add(new ProyectoR(
						p.getTitulo(),
						p.getEstado(), 
						p.getTipoProyecto().getNombre(),
						p.getDescripcion(),
						p.getFechaInicio(),
						p.getFechaFin(),
						p.getMetodologia(),
						"",
						p.getSemillero().getNombre()));
			}
		}
		return salida;
	}
	
	public List<ProyectoR> getProyectosGIC(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			salida.addAll(getProyectosSemilleroC(s.getId()));
		}
		return salida;
	}
	
	public int numProyectosSinProductoSemillero(int cc) {
		List<Proyecto> lista = semillero.getById(cc).getProyectos();
		int salida = 0;
		for(Proyecto p : lista) {
			List<Producto> aux = p.getProductos();
			if (aux.isEmpty()) {
				salida += 1;
			}
		}
		return salida;
	}
	
	public int countProyectosConProductoGI(List<Semillero> semilleros) {
		int salida = 0;
		for(Semillero s : semilleros) {
			if(!s.getProyectos().isEmpty()) {
				for(Proyecto p : s.getProyectos()) {
					if(!p.getProductos().isEmpty()) {
						salida += 1;
					}
				}
			}
		}
		return salida;
	}
	
	
	public int countProyectosFinalizadosGI(GrupoInvestigacion s) {
		int i = 0;
		for(Semillero p : s.getSemilleros()) {
				i += semilleros.numProyectosFinalizados(p.getProyectos());
		}
		return i;
	}
	
	public int countProyectosGI(GrupoInvestigacion s) {
		int i = 0;
		for(Semillero p : s.getSemilleros()) {
				i += p.getProyectos().size();
		}
		return i;
	}
	
	public List<ProyectoR> getProyectosEGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			salida.addAll(getProyectosSemilleroE(s.getId()));
		}
		return salida;
	}

	public List<ProyectoR> getProyectosActivosSemillero(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ProyectoR> salida = new ArrayList<>();
		for(Proyecto p: aux) {
			if(p.getFechaFin() == null) {
				salida.add(new ProyectoR(
						p.getTitulo(),
						p.getEstado(), 
						p.getTipoProyecto().getNombre(),
						p.getDescripcion(),
						p.getFechaInicio(),
						p.getFechaFin(),
						p.getMetodologia(),
						"",
						p.getSemillero().getNombre()));
			}
		}
		return salida;
	}


	public List<ProyectoR> getProyectosActivosGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			salida.addAll(getProyectosActivosSemillero(s.getId()));
		}
		return salida;
	}


	public List<ProyectoR> getProyectosFinGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			salida.addAll(getProyectosFinSemillero(s.getId()));
		}
		return salida;
	}

	public List<ProyectoR> getProyectosFinSemillero(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ProyectoR> salida = new ArrayList<>();
		for(Proyecto p: aux) {
			if(p.getFechaFin() != null) {
				salida.add(new ProyectoR(
						p.getTitulo(),
						p.getEstado(), 
						p.getTipoProyecto().getNombre(),
						p.getDescripcion(),
						p.getFechaInicio(),
						p.getFechaFin(),
						p.getMetodologia(),
						"",
						p.getSemillero().getNombre()));
			}
		}
		return salida;
	}

	public int countProyectosFacultad(int cc) {
		int salida = 0;
		List<GrupoInvestigacion> aux = grupos.findByFacultad(cc);
		for(GrupoInvestigacion g : aux) {
			for(Semillero s : g.getSemilleros()) {
				salida += s.countProyectos();
			}
		}
		return salida;
	}

	public int countProyectosFacultadFin(int cc) {
		int salida = 0;
		List<GrupoInvestigacion> aux = grupos.findByFacultad(cc);
		for(GrupoInvestigacion g : aux) {
			for(Semillero s : g.getSemilleros()) {
					for(Proyecto p : s.getProyectos()) {
						if(p.getFechaFin() != null) {
							salida += 1;
						}
					}
			}
		}
		return salida;
	}

	public int countProyectosConProductoFacultad(int cc) {
		int salida = 0;
		List<GrupoInvestigacion> aux = grupos.findByFacultad(cc);
		for(GrupoInvestigacion g : aux) {
			for(Semillero s : g.getSemilleros()) {
					for(Proyecto p : s.getProyectos()) {
						if(!p.getProductos().isEmpty()) {
							salida += 1;
						}
					}
			}
		}
		return salida;
	}

	public List<ProyectoR> getProyectosFacultad(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
					for(Proyecto p : lista) {
						salida.add(new ProyectoR(
								p.getTitulo(),
								p.getEstado(), 
								p.getTipoProyecto().getNombre(),
								p.getDescripcion(),
								p.getFechaInicio(),
								p.getFechaFin(),
								p.getMetodologia(),
								"",
								p.getSemillero().getNombre()));
					}
		return salida;
	}

}
