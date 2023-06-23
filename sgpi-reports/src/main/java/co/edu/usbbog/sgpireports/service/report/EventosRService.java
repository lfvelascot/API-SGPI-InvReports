package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Participaciones;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.EventoR;

@Service
public class EventosRService {
	
	private MiselaneaService extras = new MiselaneaService();

	public List<EventoR> getProyectosEventos(List<Proyecto> lista) {
		List<EventoR> salida = new ArrayList<>();
		for(Proyecto p : lista) {
			if(!p.getParticipaciones().isEmpty()) {
				for(Participaciones pp : p.getParticipaciones()) {
					EventoR x = new EventoR(p.getTitulo(),
							pp.getEvento().getNombre(),
							extras.getFechaFormateada(pp.getEvento().getFecha()),
							pp.getEvento().getEntidad(),
							pp.getEvento().getEstado());
					x.setSemillero((pp.getReconocimientos() != null) ? pp.getReconocimientos() : "");
				}
			}
		}
		return orderParticipaciones(salida);
	}
	
	private List<EventoR> orderParticipaciones(List<EventoR> participaciones) {
		Collections.sort(participaciones, Collections.reverseOrder());
		return participaciones;
	}

	public List<EventoR> getProyectosEventosGI(List<Semillero> semilleros) {
		List<EventoR> salida = new ArrayList<>();
		for(Semillero s : semilleros) {
			if(!s.getProyectos().isEmpty()) {
				salida.addAll(getProyectosEventos(s.getProyectos()));
			}
		}
		return orderParticipaciones(salida);
	}


	public List<EventoR> getParticipacionesProyecto(List<Participaciones> participaciones) {
		List<EventoR> salida = new ArrayList<>();
		for(Participaciones pp : participaciones) {
			EventoR x = new EventoR("",
					pp.getEvento().getNombre(),
					extras.getFechaFormateada(pp.getEvento().getFecha()),
					pp.getEvento().getEntidad(),
					pp.getEvento().getEstado());
			x.setSemillero((pp.getReconocimientos() != null) ? pp.getReconocimientos() : "");
			salida.add(x);
		}
		return orderParticipaciones(salida);
	}

}
