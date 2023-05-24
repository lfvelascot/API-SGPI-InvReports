package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Logger logger = LoggerFactory.getLogger(SemillerosRService.class);
	
	public List<SemillerosR> getSemillerosGI(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			salida.add(createSemilleroR(s));
		}
		return salida;
	}
	
	public List<SemillerosR> getSemillerosConGI(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			if(!s.getProyectos().isEmpty()) {
				boolean flag = false;
				for(Proyecto p : s.getProyectos()) {
					if(!p.getProyectosConvocatoria().isEmpty()) {
						flag = true;
						break;
					}
				}
				if(flag) {
					salida.add(createSemilleroR(s));
				}
			}
		}
		return salida;
	}
	
	public List<SemillerosR> getSemillerosConAGI(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			if(!s.getProyectos().isEmpty()) {
				boolean flag = false;
				for(Proyecto p : s.getProyectos()) {
					if(!p.getProyectosConvocatoria().isEmpty()) {
						for(ProyectosConvocatoria pc :  p.getProyectosConvocatoria())
							if(pc.getConvocatoria().getEstado().equals("ABIERTA")) {
								flag = true;
								break;
							}
						if(flag) {
							break;
						}
					}
				}
				if(flag) {
					salida.add(createSemilleroR(s));
				}
			}
		}
		return salida;
	}
	
	public int numProyectosFinalizados(List<Proyecto> aux2) {
		int i = 0;
		for(Proyecto t : aux2) {
			if(t.getFechaFin() != null) {
				i += 1;
			}
		}
		return i;
	}
	
	public List<SemillerosR> getSemillerosEGI(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			if(!s.getProyectos().isEmpty()) {
				boolean flag = false;
				for(Proyecto p : s.getProyectos()) {
					if(!p.getParticipaciones().isEmpty()) {
						flag = true;
						break;
					}
				}
				if(flag) {
					salida.add(createSemilleroR(s));
				}
			}
		}
		return salida;
	}
	
	private SemillerosR createSemilleroR(Semillero s) {
		String lider = "sin lider registrado";
		if(s.getLiderSemillero() != null) {
			lider = s.getLiderSemillero().getNombreCompleto();
		}
		SemillerosR x = new SemillerosR(s.getNombre(),
				getFechaFormateada(s.getFechaFun()), 
				lider,
				s.getLineaInvestigacion().getNombre(),
				miembros.countMiembrosSemillero(s.getId()));
		x.setTotalEventos(pe.countParticpacionesSemillero(s.getId()));
		x.setTotalConvocatorias(c.countParticipacionesSemillero(s.getId()));
		int y = s.getProyectos().size();
		int i = numProyectosFinalizados(s.getProyectos());
		x.setProyectosActivos(y-i);
		x.setProyectosFinalizados(i);
		x.setTotalProyectos(y);
		return x;
	}
	
	private String getFechaFormateada(LocalDate fecha) {
		return  fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));  //17-02-2022
	}

	public int numProyectosActivos(List<Proyecto> list) {
		int salida = 0;
				for(Proyecto p : list) {
					if(p.getFechaFin() == null) {
						salida += 1;
					}
				}
		return salida;
	}

	public List<SemillerosR> getSemillerosGIProyectosAct(List<Semillero> semilleros) {
		List<SemillerosR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			if(!s.getProyectos().isEmpty()) {
				for(Proyecto p : s.getProyectos()) {
					if(p.getFechaFin() == null) {
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
		for(Semillero s : semilleros) {
			if(!s.getProyectos().isEmpty()) {
				for(Proyecto p : s.getProyectos()) {
					if(p.getFechaFin() != null) {
						salida.add(createSemilleroR(s));
						break;
					}
				}
			}
		}
		return salida;
	}

}
