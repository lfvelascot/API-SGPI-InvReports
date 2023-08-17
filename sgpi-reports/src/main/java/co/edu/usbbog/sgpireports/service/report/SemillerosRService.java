package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Participaciones;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.SemillerosR;
import co.edu.usbbog.sgpireports.repository.IConvocatoriaRepository;
import co.edu.usbbog.sgpireports.repository.IParticipacionesRepository;

@Service
public class SemillerosRService {

	@Autowired
	private IParticipacionesRepository pe;
	@Autowired
	private IConvocatoriaRepository c;
	@Autowired
	private MiembrosSemilleroRService miembros;
	@Autowired
	private ProyectosRService proyectos;
	private MiselaneaService extras = new MiselaneaService();

	public List<SemillerosR> getSemillerosGI(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.add(createSemilleroR(s));
		}
		return salida;
	}

	public List<SemillerosR> getSemillerosConGI(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				boolean flag = false;
				for (Proyecto p : s.getProyectos()) {
					if (!p.getProyectosConvocatoria().isEmpty()) {
						flag = true;
						break;
					}
				}
				if (flag) {
					salida.add(createSemilleroR(s));
				}
			}
		}
		return salida;
	}

	public List<SemillerosR> getSemillerosConAGI(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				boolean flag = false;
				for (Proyecto p : s.getProyectos()) {
					if (!p.getProyectosConvocatoria().isEmpty()) {
						for (ProyectosConvocatoria pc : p.getProyectosConvocatoria())
							if (pc.getConvocatoria().getEstado().equals("ABIERTA")) {
								flag = true;
								break;
							}
						if (flag) {
							break;
						}
					}
				}
				if (flag) {
					salida.add(createSemilleroR(s));
				}
			}
		}
		return salida;
	}

	public int numProyectosFinalizados(List<Proyecto> aux2) {
		Long salida = aux2.stream().filter(c -> c.getFechaFin() != null).count();
		return salida.intValue();
	}

	public List<SemillerosR> getSemillerosEGI(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				boolean flag = false;
				for (Proyecto p : s.getProyectos()) {
					if (!p.getParticipaciones().isEmpty()) {
						flag = true;
						break;
					}
				}
				if (flag) {
					salida.add(createSemilleroR(s));
				}
			}
		}
		return salida;
	}

	private SemillerosR createSemilleroR(Semillero s) {
		SemillerosR x = basicoSemillero(s);
		x.setTotalEventos(pe.countParticpacionesSemillero(s.getId()));
		x.setTotalConvocatorias(c.countParticipacionesSemillero(s.getId()));
		int y = s.getProyectos().size();
		int i = numProyectosFinalizados(s.getProyectos());
		x.setProyectosActivos(y - i);
		x.setProyectosFinalizados(i);
		x.setTotalProyectos(y);
		return x;
	}

	private SemillerosR basicoSemillero(Semillero s) {
		String lider = "sin lider registrado";
		if (s.getLiderSemillero() != null) {
			lider = s.getLiderSemillero().getNombreCompleto();
		}
		SemillerosR x = new SemillerosR(s.getNombre(), extras.getFechaFormateada(s.getFechaFun()), lider,
				s.getLineaInvestigacion().getNombre(), miembros.countMiembrosSemillero(s.getId()));
		return x;
	}

	public int numProyectosActivos(List<Proyecto> list) {
		Long salida = list.stream().filter(c -> c.getFechaFin() != null).count();
		return salida.intValue();
	}

	public List<SemillerosR> getSemillerosGIProyectosAct(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				for (Proyecto p : s.getProyectos()) {
					if (p.getFechaFin() == null) {
						salida.add(createSemilleroR(s));
						break;
					}
				}
			}
		}
		return salida;
	}

	public List<SemillerosR> getSemillerosGIProyectosFin(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			if (!s.getProyectos().isEmpty()) {
				for (Proyecto p : s.getProyectos()) {
					if (p.getFechaFin() != null) {
						salida.add(createSemilleroR(s));
						break;
					}
				}
			}
		}
		return salida;
	}

	public List<SemillerosR> getSemillerosGIAnio(List<Proyecto> aux, List<Semillero> semilleros, int anioInicio,
			int anioFin) {
		List<SemillerosR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			SemillerosR ss = basicoSemillero(s);
			for (Proyecto p : aux) {
				if (p.getSemillero().getId() == s.getId()) {
					ss.setTotalEventos(ss.getTotalEventos() + getParticipacionesEventosAnio(p, anioInicio, anioFin));
					ss.setTotalConvocatorias(
							ss.getTotalConvocatorias() + getParticipacionesConvAnio(p, anioInicio, anioFin));
					if (p.getFechaFin() != null) {
						ss.setProyectosFinalizados(ss.getProyectosFinalizados() + 1);
					} else {
						ss.setProyectosActivos(ss.getProyectosActivos() + 1);
					}
					ss.setTotalProyectos(ss.getProyectosActivos() + ss.getProyectosFinalizados());
				}
			}
			salida.add(ss);
		}
		return salida;
	}

	public Map<String, Object> getSemilleroAnio(List<Proyecto> aux, int anioInicio, int anioFin,
			Map<String, Object> map) {
		Integer[] lista = getTotales(aux, anioInicio, anioFin);
		map.put("totalParticipacionesEventos", String.valueOf(lista[0]) + " participacion(es) en eventos");
		map.put("totalPartcipacionesConv", String.valueOf(lista[1]) + " participacion(es) en convocatorias");
		map.put("totalProductos", String.valueOf(lista[2]) + " producto(s)");
		map.put("totalProyectosFinalizados", String.valueOf(lista[3]) + " proyecto(s) finalizado(s)");
		map.put("TotalProyectosActivos", String.valueOf(lista[4]) + " proyecto(s) sin terminar");
		return map;
	}

	public Integer[] getTotales(List<Proyecto> aux, int anioInicio, int anioFin) {
		int totalProyectosFinalizados = proyectos.countProyectosFin(aux), totalParticipacionesConv = 0,
				totalPartciipacionesEventos = 0, totalProductos = 0;
		for (Proyecto p : aux) {
			totalPartciipacionesEventos += getParticipacionesEventosAnio(p, anioInicio, anioFin);
			totalParticipacionesConv += getParticipacionesConvAnio(p, anioInicio, anioFin);
			totalProductos += p.getProductos().size();
		}
		return new Integer[] { totalPartciipacionesEventos, totalParticipacionesConv, totalProductos,
				totalProyectosFinalizados, aux.size() - totalProyectosFinalizados };
	}

	private int getParticipacionesEventosAnio(Proyecto p, int i, int j) {
		if (p.getParticipaciones().isEmpty()) {
			return 0;
		} else {
			int salida = 0;
			for (Participaciones pe : p.getParticipaciones()) {
				if (pe.getFechaPart().getYear() >= i && pe.getFechaPart().getYear() < j) {
					salida += 1;
				}
			}
			return salida;
		}
	}

	private int getParticipacionesConvAnio(Proyecto p, int i, int j) {
		if (p.getProyectosConvocatoria().isEmpty()) {
			return 0;
		} else {
			int salida = 0;
			for (ProyectosConvocatoria pe : p.getProyectosConvocatoria()) {
				if ((pe.getConvocatoria().getFechaInicio().getYear() >= i
						&& pe.getConvocatoria().getFechaInicio().getYear() < j)
						|| (pe.getConvocatoria().getFechaFinal().getYear() >= i
								&& pe.getConvocatoria().getFechaFinal().getYear() < j)) {
					salida += 1;
				}
			}
			return salida;
		}
	}

}
