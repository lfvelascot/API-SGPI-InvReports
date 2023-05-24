package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Producto;
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
		return salida;
	}

	public List<ProyectoR> getProyectosSemillero(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(), p.getDescripcion(),
					p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "", p.getSemillero().getNombre()));
		}
		return salida;
	}

	public List<ProyectoR> getProyectosSemilleroE(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getParticipaciones().isEmpty()) {
				salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
						p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
						p.getSemillero().getNombre()));
			}
		}
		return salida;
	}

	public List<ProyectoR> getProyectosSemilleroC(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
						p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
						p.getSemillero().getNombre()));
			}
		}
		return salida;
	}

	public List<ProyectoR> getProyectosSemilleroCA(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for (ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					if (pc.getConvocatoria().getEstado().equals("ABIERTA")) {
						salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
								p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
								p.getSemillero().getNombre()));
					}
				}
			}
		}
		return salida;
	}

	public List<ProyectoR> getProyectosGIC(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosSemilleroC(s.getProyectos()));
		}
		return salida;
	}

	public List<ProyectoR> getProyectosGICA(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosSemilleroCA(s.getProyectos()));
		}
		return salida;
	}

	public int numProyectosSinProductoSemillero(List<Proyecto> lista) {
		int salida = 0;
		for (Proyecto p : lista) {
			List<Producto> aux = p.getProductos();
			if (aux.isEmpty()) {
				salida += 1;
			}
		}
		return salida;
	}

	public int countProyectosConProductoGI(List<Semillero> semilleros) {
		int salida = 0;
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				for (Proyecto p : s.getProyectos()) {
					if (!p.getProductos().isEmpty()) {
						salida += 1;
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
			i += p.getProyectos().size();
		}
		return i;
	}

	public List<ProyectoR> getProyectosEGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosSemilleroE(s.getProyectos()));
		}
		return salida;
	}

	public List<ProyectoR> getProyectosActivosSemillero(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() == null) {
				salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
						p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
						p.getSemillero().getNombre()));
			}
		}
		return salida;
	}

	public List<ProyectoR> getProyectosActivosGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosActivosSemillero(s.getProyectos()));
		}
		return salida;
	}

	public List<ProyectoR> getProyectosFinGI(List<Semillero> semilleros) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getProyectosFinSemillero(s.getProyectos()));
		}
		return salida;
	}

	public List<ProyectoR> getProyectosFinSemillero(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() != null) {
				salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(),
						p.getDescripcion(), p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "",
						p.getSemillero().getNombre()));
			}
		}
		return salida;
	}

	public int countProyectosFacultadFin(List<Proyecto> lista) {
		int salida = 0;
				for (Proyecto p : lista) {
					if (p.getFechaFin() != null) {
						salida += 1;
					}
				}
		return salida;
	}

	public int countProyectosConProductoFacultad(List<Proyecto> lista) {
		int salida = 0;
		for (Proyecto p : lista) {
			if (!p.getProductos().isEmpty()) {
				salida += 1;
			}
		}
		return salida;
	}

	public List<ProyectoR> getProyectosFacultad(List<Proyecto> lista) {
		List<ProyectoR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			salida.add(new ProyectoR(p.getTitulo(), p.getEstado(), p.getTipoProyecto().getNombre(), p.getDescripcion(),
					p.getFechaInicio(), p.getFechaFin(), p.getMetodologia(), "", p.getSemillero().getNombre()));
		}
		return salida;
	}


}
