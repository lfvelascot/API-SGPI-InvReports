package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.ProyectoR;

@Service
public class ProyectosRService {

	@Autowired
	private SemillerosRService semilleros;

	public List<ProyectoR> getProyectosGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosSemillero(s.getProyectos()));
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosSemillero(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if(p.getSemillero() != null) {
				salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(), p.getDescripcion(),
					p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "", p.getSemillero().getNombre(),
					String.valueOf(p.getProductos().size())));
			}
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosSemilleroE(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getParticipaciones().isEmpty()) {
				if(p.getSemillero() != null) {
					salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
						p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
						p.getSemillero().getNombre()));
				}
			}
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosSemilleroC(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				if(p.getSemillero() != null) {
					salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
						p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
						p.getSemillero().getNombre()));
				}
			}
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosSemilleroCA(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					if (pc.getConvocatoria().getEstado().equals("ABIERTA") && p.getSemillero() != null) {
						salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
								p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
								p.getSemillero().getNombre()));
					}
				}
			}
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosGIC(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosSemilleroC(s.getProyectos()));
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosGICA(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosSemilleroCA(s.getProyectos()));
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosEGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosSemilleroE(s.getProyectos()));
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosActivosSemillero(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() == null && p.getSemillero() != null) {
				salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
						p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
						p.getSemillero().getNombre()));
			}
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosActivosGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosActivosSemillero(s.getProyectos()));
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosFinGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosFinSemillero(s.getProyectos()));
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosFinSemillero(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() != null && p.getSemillero() != null) {
				salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
						p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
						p.getSemillero().getNombre()));
			}
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosFacultad(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if(p.getSemillero() != null) {
				salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(), p.getDescripcion(),
					p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
					p.getSemillero().getGrupoInvestigacion().getNombre()));
			}
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosPresSem(List<Proyecto> proyectos) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : proyectos) {
			if (!p.getPresupuestos().isEmpty() && p.getSemillero() != null) {
				salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
						p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
						p.getSemillero().getNombre()));
			}
		}
		return orderSalida(salida);
	}

	public List<ProyectoR> getProyectosPresGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero p : semilleros) {
			if (!p.getProyectos().isEmpty()) {
				salida.addAll(getProyectosPresSem(p.getProyectos()));
			}
		}
		return orderSalida(salida);
	}

	public int countProyectosFin(List<Proyecto> lista) {
		int salida = 0;
		for (Proyecto p : lista) {
			salida = (p.getFechaFin() != null) ? salida+1 : salida;
		}
		return salida;
	}

	public int countProyectosConProductoFacultad(List<Proyecto> lista) {
		int salida = 0;
		for (Proyecto p : lista) {
			salida = (!p.getProductos().isEmpty()) ? salida+1 : salida;
		}
		return salida;
	}

	public int numProyectosSinProductoSemillero(List<Proyecto> lista) {
		int salida = 0;
		for (Proyecto p : lista) {
			salida = (p.getProductos().isEmpty()) ? salida+1 : salida;
		}
		return salida;
	}

	public int countProyectosConProductoGI(List<Semillero> semilleros) {
		int salida = 0;
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				for (Proyecto p : s.getProyectos()) {
					if (!p.getProductos().isEmpty()) {
						salida = (!p.getProductos().isEmpty()) ? salida+1 : salida;
					}
				}
			}
		}
		return salida;
	}

	public int countProyectosFinalizadosGI(GrupoInvestigacion s) {
		int i = 0;
		for (Semillero p : s.getSemilleros()) {
			i += semilleros.numProyectosFinalizados(p.getProyectos());
		}
		return i;
	}

	public int countProyectosGI(GrupoInvestigacion s) {
		int i = 0;
		for (Semillero p : s.getSemilleros()) {
			i += p.getTotalProyectos();
		}
		return i;
	}

	private List<ProyectoR> orderSalida(List<ProyectoR> salida) {
		Collections.sort(salida, Collections.reverseOrder());
		return salida;
	}

	public List<Proyecto> unificar(List<Proyecto> general, List<Proyecto> prod, List<Proyecto> even,
			List<Proyecto> conv) {
		general.addAll(conv);
		general.addAll(even);
		general.addAll(prod);
		Set<Proyecto> aux = new HashSet<>(general);
		general.clear();
		for(Proyecto p : aux) {
			if(p.getSemillero() != null) {
				general.add(p);
			}
		}
		return general;
	}

}
