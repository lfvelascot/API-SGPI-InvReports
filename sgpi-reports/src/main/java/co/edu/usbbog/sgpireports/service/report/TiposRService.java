package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Participaciones;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.TipoPEstado;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;

@Service
public class TiposRService {

	@Autowired
	private IProyectoRepository proyecto;

	public List<TipoPEstado> getProduccionSemillerosGI(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				for (Proyecto p : s.getProyectos()) {
					if (!p.getProductos().isEmpty()) {
						salida = sumar(salida, s.getNombre(),1,0);
					}
				}
			} else {
				salida.add(new TipoPEstado(s.getNombre(), 0));
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProduccionSemillerosGI2(List<Semillero> semilleros, List<TipoPEstado> salida) {
		int i = 0;
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				for (Proyecto p : s.getProyectos()) {
					if (!p.getProductos().isEmpty()) {
						TipoPEstado x = salida.get(i);
						x.setNumero2(x.getNumero2() + 1);
						salida.set(i, x);
					}
				}
			}
			i++;
		}
		return salida;
	}

	public List<TipoPEstado> getProyFinSemillerosGI(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				for (Proyecto p : s.getProyectos()) {
					if (p.getFechaFin() != null) {
						salida = sumar(salida, s.getNombre(),1,0);
					}
				}
			} else {
				salida.add(new TipoPEstado(s.getNombre(), 0));
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProduccionAnioGI(int gi) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyecto.findByGI(gi)) {
			if (p.getFechaFin() != null) {
				salida = sumar(salida, String.valueOf(p.getFechaFin().getYear()),1,0);
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosEstadoGI(int gi) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyecto.findByGI(gi)) {
			salida = sumar(salida, p.getEstado(),1,0);
		}
		return salida;
	}

	public List<TipoPEstado> getTiposProyectosGI(int gi) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyecto.findByGI(gi)) {
			salida = sumar(salida, p.getTipoProyecto().getNombre(),1,0);
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
					salida = sumar(salida, pc.getIdProyecto(),1,0);
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosEstadosConvA(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					if (pc.getConvocatoria().getEstado().equals("ABIERTA")) {
						salida = sumar(salida, pc.getIdProyecto(),1,0);
					}
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosAnioE(List<Proyecto> lista) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getParticipaciones().isEmpty()) {
				for (Participaciones pp : p.getParticipaciones()) {
					salida = sumar(salida, String.valueOf(pp.getFechaPart().getYear()),1,0);
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getDatosporAnio(List<Proyecto> aux) {
		List<TipoPEstado> salida = getProducciónPorAnio(aux);
		for (Proyecto p : aux) {
			if (!p.getParticipaciones().isEmpty()) {
				for (Participaciones pe : p.getParticipaciones()) {
					salida = sumar(salida, String.valueOf(pe.getFechaPart().getYear()), 2,0);
				}
			}
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					salida = sumar(salida, String.valueOf(pc.getConvocatoria().getFechaFinal().getYear()), 3,0);
				}
			}
		}
		return salida;
	}

	private List<TipoPEstado> sumar(List<TipoPEstado> salida, String clave, int dato, int adicional) {
		TipoPEstado t = new TipoPEstado(clave);
		if (salida.isEmpty()) {
			if(adicional != 0) {
				salida.add(setDatos(t, dato, adicional));
			} else {
				salida.add(setDatos(t, dato, 1));
			}
			return salida;
		} else {
			TipoPEstado x = null;
			try {
				x = salida.stream().filter(o -> o.getTipo().equals(clave)).findFirst().get();
				int pos = salida.indexOf(x);
				if(adicional != 0) {
					salida.set(pos, setDatos(x, dato, adicional));
				} else {
					salida.set(pos, setDatos(x, dato, 1));
				}
				return salida;
			} catch (NoSuchElementException e) {
				if(adicional != 0) {
					salida.add(setDatos(t, dato, adicional));
				} else {
					salida.add(setDatos(t, dato, 1));
				}
				return salida;
			}
		}
	}

	private TipoPEstado setDatos(TipoPEstado t, int dato, int valor) {
		switch (dato) {
		case 1:
			t.setNumero(t.getNumero() + valor);
			break;
		case 2:
			t.setNumero2(t.getNumero2() + valor);
			break;
		case 3:
			t.setNumero3(t.getNumero3() + valor);
			break;
		default:
			break;
		}
		return t;
	}

	public List<TipoPEstado> getProyectosAnioConvGI(int gi) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyecto.findByGI(gi)) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					salida = sumar(salida, String.valueOf(pc.getConvocatoria().getFechaInicio().getYear()),1,0);
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosEstadosConvGI(int gi) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyecto.findByGI(gi)) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					salida = sumar(salida, pc.getIdProyecto(),1,0);
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosEstadosConvAGI(int gi) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyecto.findByGI(gi)) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					if (pc.getConvocatoria().getEstado().equals("ABIERTA")) {
						salida = sumar(salida, pc.getIdProyecto(),1,0);
					}
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProducciónPorAnio(List<Proyecto> lista) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() != null) {
				salida = sumar(salida, String.valueOf(p.getFechaFin().getYear()),1,0);
			}
		}
		return salida;
	}
	
	public List<TipoPEstado> getProducciónPorAnio2(List<Proyecto> lista){
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() != null) {
				salida = sumar(salida, String.valueOf(p.getFechaFin().getYear()),1,0);
			}
			if (!p.getProductos().isEmpty()) {
				salida = sumar(salida, String.valueOf(p.getFechaInicio().getYear()),2, p.getProductos().size());
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosAnioConvSem(List<Proyecto> lista) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					salida = sumar(salida, String.valueOf(pc.getConvocatoria().getFechaInicio().getYear()),1,0);
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosAnioEGI(int gi) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyecto.findByGI(gi)) {
			if (!p.getParticipaciones().isEmpty()) {
				for (Participaciones pc : p.getParticipaciones()) {
					salida = sumar(salida, String.valueOf(pc.getFechaPart().getYear()),1,0);
				}
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosEstadosFacultad(List<Proyecto> ps) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : ps) {
			salida = sumar(salida, p.getEstado(),1,0);
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosGIFacultad(List<Proyecto> ps) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : ps) {
			salida = sumar(salida, p.getSemillero().getGrupoInvestigacion().getNombre(),1,0);
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosTipoFacultad(List<Proyecto> ps) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : ps) {
			salida = sumar(salida, p.getTipoProyecto().getNombre(),1,0);
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosAnioFacultad(List<Proyecto> ps) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : ps) {
			if (p.getFechaFin() != null) {
				salida = sumar(salida, String.valueOf(p.getFechaFin().getYear()),1,0);
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosActProductos(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (p.getFechaFin() == null) {
				salida = (!p.getProductos().isEmpty()) ? sumar(salida, "Sin productos",1,0) : sumar(salida, "Con productos",1,0);
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosActTipos(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (p.getFechaFin() == null) {
				salida = sumar(salida, p.getTipoProyecto().getNombre(),1,0);
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosFinProductos(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (p.getFechaFin() != null) {
				salida = (p.getProductos().isEmpty()) ? sumar(salida, "Sin productos",1,0) : sumar(salida, "Con productos",1,0);
			}
		}
		return salida;
	}

	public List<TipoPEstado> getProyectosFinTipos(List<Proyecto> proyectos) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (p.getFechaFin() != null) {
				salida = sumar(salida, p.getTipoProyecto().getNombre(),1,0);
			}
		}
		return salida;
	}

	public List<TipoPEstado> getParticpacionesESemilleros(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (s.getProyectos().isEmpty()) {
				salida.add(new TipoPEstado(s.getNombre(), 0));
			} else {
				TipoPEstado aux = new TipoPEstado(s.getNombre(), 0);
				for (Proyecto p : s.getProyectos()) {
					if (!p.getParticipaciones().isEmpty()) {
						aux.setNumero(aux.getNumero() + p.getParticipaciones().size());
					}
				}
				salida.add(aux);
			}
		}
		return salida;
	}

	public List<TipoPEstado> getParticpacionesCASemilleros(List<Semillero> semilleros) {
		List<TipoPEstado> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (s.getProyectos().isEmpty()) {
				salida.add(new TipoPEstado(s.getNombre(), 0));
			} else {
				TipoPEstado aux = new TipoPEstado(s.getNombre(), 0);
				for (Proyecto p : s.getProyectos()) {
					if (!p.getProyectosConvocatoria().isEmpty()) {
						for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
							if (pc.getConvocatoria().getEstado().equals("ABIERTA")) {
								aux.setNumero(aux.getNumero() + 1);
							}
						}
					}
				}
				salida.add(aux);
			}
		}
		return salida;
	}

	public int countSemillerosPE(List<TipoPEstado> aux6) {
		int salida = 0;
		for (TipoPEstado t : aux6) {
			if (t.getNumero() != 0) {
				salida++;
			}
		}
		return salida;
	}

}
