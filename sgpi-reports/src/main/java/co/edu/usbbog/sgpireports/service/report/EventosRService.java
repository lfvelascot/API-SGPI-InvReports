package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Participaciones;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.EventoR;

@Service
public class EventosRService {
	
	public Logger logger = LoggerFactory.getLogger(EventosRService.class);

	public List<EventoR> getProyectosEventos(List<Proyecto> lista) {
		List<EventoR> salida = new ArrayList<>();
		for(Proyecto p : lista) {
			if(!p.getParticipaciones().isEmpty()) {
				for(Participaciones pp : p.getParticipaciones()) {
					salida.add(new EventoR(p.getTitulo(),
							pp.getEvento().getNombre(),
							getFechaFormateada(pp.getEvento().getFecha()),
							pp.getEvento().getEntidad(),
							pp.getEvento().getEstado()));
				}
			}
		}
		return salida;
	}
	
	public List<EventoR> getProyectosEventosGI(List<Semillero> semilleros) {
		List<EventoR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			if(!s.getProyectos().isEmpty()) {
				salida.addAll(getProyectosEventos(s.getProyectos()));
			}
		}
		return salida;
	}

	private String getFechaFormateada(LocalDate fecha) {
		return  fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));  //17-02-2022
	}


	public Object getParticipacionesProyecto(List<Participaciones> participaciones) {
		List<EventoR> salida = new ArrayList<>();
		for(Participaciones pp : participaciones) {
			EventoR x = new EventoR("",
					pp.getEvento().getNombre(),
					getFechaFormateada(pp.getEvento().getFecha()),
					pp.getEvento().getEntidad(),
					pp.getEvento().getEstado());
			if(pp.getReconocimientos() != null) {
				x.setSemillero(pp.getReconocimientos());
			}
			salida.add(x);
		}
		return salida;
	}

}
