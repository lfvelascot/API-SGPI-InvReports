package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Participaciones;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.TipoPEstado;
import co.edu.usbbog.sgpireports.repository.IGrupoInvestigacionRepository;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;
import co.edu.usbbog.sgpireports.repository.ISemilleroRepository;

@Service
public class TiposRService {

	@Autowired
	private ISemilleroRepository semillero;
	@Autowired
	private IProyectoRepository proyecto;
	@Autowired 
	private IGrupoInvestigacionRepository grupos;


	public List<TipoPEstado> getProduccionSemillerosGI(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				for (Proyecto p : s.getProyectos()) {
					if (!p.getProductos().isEmpty()) {
						salida = sumar(salida, s.getNombre());
					}
				}
			} else {
				salida.add(new TipoPEstado(s.getNombre(), 0));
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProduccionAnioGI(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : getAllProyectosGI(semilleros)) {
			if (p.getFechaFin() != null) {
				salida = sumar(salida, String.valueOf(p.getFechaFin().getYear()));
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosEstadoGI(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : getAllProyectosGI(semilleros)) {
			salida = sumar(salida, p.getEstado());
		}
		return salida;
	}

	public List<TipoPEstado> getTiposProyectosGI(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : getAllProyectosGI(semilleros)) {
			salida = sumar(salida, p.getTipoProyecto().getNombre());
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosSemilleroPorEstado(int cc) {
		List<String> prueba = proyecto.getEstadosSemillero(cc);
		List<TipoPEstado> salida = new ArrayList<>();
		for (String i : prueba) {
			salida.add(new TipoPEstado(i, proyecto.findByEstadoS(i, cc).size()));
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosSemilleroPorTipo(int cc) {
		List<String> prueba = proyecto.getTiposSemillero(cc);
		List<TipoPEstado> salida = new ArrayList<>();
		for (String i : prueba) {
			salida.add(new TipoPEstado(i, proyecto.findByTipoProyectoS(i, cc).size()));
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosEstadosConv(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					salida = sumar(salida, pc.getIdProyecto());
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosAnioE(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : aux) {
			if (!p.getParticipaciones().isEmpty()) {
				for (Participaciones pp : p.getParticipaciones()) {
					salida = sumar(salida, String.valueOf(pp.getFechaPart().getYear()));
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosAnioConvGI(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : getAllProyectosGI(semilleros)) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					salida = sumar(salida, String.valueOf(pc.getConvocatoria().getFechaInicio().getYear()));
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosEstadosConvGI(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : getAllProyectosGI(semilleros)) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					salida = sumar(salida, pc.getIdProyecto());
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProducciónPorAnio(int cc) {
		List<TipoPEstado> salida = new ArrayList<>();
		List<Proyecto> aux = semillero.getById(cc).getProyectos();
		for (Proyecto p : aux) {
			if (p.getFechaFin() != null) {
				salida = sumar(salida, String.valueOf(p.getFechaFin().getYear()));
			}
		}
		return salida;
	}

	public List<TipoPEstado> sumar(List<TipoPEstado> salida, String valor) {
		if (salida.isEmpty()) {
			salida.add(new TipoPEstado(valor, 1));
			return salida;
		} else {
			TipoPEstado x = null;
			try {
				x = salida.stream().filter(o -> o.getTipo().equals(valor)).findFirst().get();
			} catch (NoSuchElementException e) {
				// TODO: handle exception
			}
			if (x != null) {
				int pos = salida.indexOf(x);
				x.setNumero(x.getNumero() + 1);
				salida.set(pos, x);
				return salida;
			} else {
				salida.add(new TipoPEstado(valor, 1));
				return salida;
			}
		}
	}

	public List<TipoPEstado> getProyectosAnioConvSem(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : aux) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					salida = sumar(salida, String.valueOf(pc.getConvocatoria().getFechaInicio().getYear()));
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosAnioEGI(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : getAllProyectosGI(semilleros)) {
			if (!p.getParticipaciones().isEmpty()) {
				for (Participaciones pc : p.getParticipaciones()) {
					salida = sumar(salida, String.valueOf(pc.getFechaPart().getYear()));
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosEstadosFacultad(List<Proyecto> ps) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : ps) {
			salida = sumar(salida, p.getEstado());
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosGIFacultad(List<Proyecto> ps) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : ps) {
			salida = sumar(salida, p.getSemillero().getGrupoInvestigacion().getNombre());
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosTipoFacultad(List<Proyecto> ps ) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : ps) {
			salida = sumar(salida, p.getTipoProyecto().getNombre());
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosAnioFacultad(List<Proyecto> ps ) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : ps) {
			if (p.getFechaFin() != null) {
				salida = sumar(salida, String.valueOf(p.getFechaFin().getYear()));
			}
		}
		return salida;
	}

	public List<Proyecto> getAllProyectos(List<GrupoInvestigacion> gis) {
		List<Proyecto> aux = new ArrayList<>();
		for(GrupoInvestigacion gi : gis) {
			for(Semillero s : gi.getSemilleros()) {
				aux.addAll(s.getProyectos());
			}
		}
		return aux;
	}

	public List<Proyecto> getAllProyectosGI(List<Semillero> semilleros) {
		List<Proyecto> aux = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				aux.addAll(s.getProyectos());
			}
		}
		return aux;
	}

	public List<TipoPEstado> getProyectosActProductos(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (p.getFechaFin() == null) {
				if (p.getProductos().isEmpty()) {
					salida = sumar(salida, "Sin productos");
				} else {
					salida = sumar(salida, "Con productos");
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosActTipos(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (p.getFechaFin() == null) {
				salida = sumar(salida, p.getTipoProyecto().getNombre());
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosFinProductos(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (p.getFechaFin() != null) {
				if (p.getProductos().isEmpty()) {
					salida = sumar(salida, "Sin productos");
				} else {
					salida = sumar(salida, "Con productos");
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosFinTipos(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (p.getFechaFin() != null) {
				salida = sumar(salida, p.getTipoProyecto().getNombre());
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProduccionAnioSem(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (p.getFechaFin() != null) {
				salida = sumar(salida, String.valueOf(p.getFechaFin().getYear()));
			}
		}
		return salida;
	}

}
