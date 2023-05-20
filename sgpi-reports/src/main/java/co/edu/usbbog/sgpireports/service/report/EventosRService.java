package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Participaciones;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.EventoR;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;

@Service
public class EventosRService {
	
	@Autowired
	private IProyectoRepository proyecto;

	public List<EventoR> getProyectosEventos(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<EventoR> salida = new ArrayList<>();
		for(Proyecto p : aux) {
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
				salida.addAll(getProyectosEventos(s.getId()));
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
			salida.add(new EventoR("",
					pp.getEvento().getNombre(),
					getFechaFormateada(pp.getEvento().getFecha()),
					pp.getEvento().getEntidad(),
					pp.getEvento().getEstado()));
		}
		return null;
	}

}
