package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Participantes;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.ParticipantesR;


@Service
public class ParticipantesRService {



	public List<ParticipantesR> getIntegrantes(List<Proyecto> lista) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if(!p.getParticipantes().isEmpty()) {
				salida.addAll(getIntegrantesProyecto(p.getParticipantes()));
			}
		}
		return salida;
	}

	private String getFechaFormateada(LocalDate fecha) {
		if (fecha != null) {
			return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else {
			return "";
		}
	}

	public List<ParticipantesR> getIntegrantesE(List<Proyecto> lista) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getParticipaciones().isEmpty() && !p.getParticipantes().isEmpty()) {
				salida.addAll(getIntegrantesProyecto(p.getParticipantes()));
			}
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesCGI(List<Semillero> semilleros) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getIntegrantesC(s.getProyectos()));
		}
		return salida;
	}
	
	public List<ParticipantesR> getIntegrantesCGIA(List<Semillero> semilleros) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getIntegrantesCA(s.getProyectos()));
		}
		return salida;
	}


	public List<ParticipantesR> getIntegrantesC(List<Proyecto> lista) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getProyectosConvocatoria().isEmpty() && !p.getParticipantes().isEmpty()) {
				salida.addAll(getIntegrantesProyecto(p.getParticipantes()));
			}
		}
		return salida;
	}
	
	public List<ParticipantesR> getIntegrantesCA(List<Proyecto> lista) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				for(ProyectosConvocatoria pc : p.getProyectosConvocatoria()) {
					if(pc.getConvocatoria().getEstado().equals("ABIERTA") && !p.getParticipantes().isEmpty()) {
						salida.addAll(getIntegrantesProyecto(p.getParticipantes()));
						break;
					}
				}
			}
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesEGI(List<Semillero> semilleros) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getIntegrantesE(s.getProyectos()));
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesProyActivos(List<Proyecto> lista) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() == null && !p.getParticipantes().isEmpty()) {
				salida.addAll(getIntegrantesProyecto(p.getParticipantes()));
			}
		}
		return salida;
	}

	public int countParticipantesProyectosActSemillero(List<Proyecto> lista) {
		List<String> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() == null && !p.getParticipantes().isEmpty()) {
				for (Participantes pp : p.getParticipantes()) {
					if (!salida.contains(pp.getUsuario().getCedula())) {
						salida.add(pp.getUsuario().getCedula());
					}
				}
			}
		}
		return salida.size();
	}

	public int countParticipantesProyectosFinSemillero(List<Proyecto> lista) {
		List<String> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() != null && !p.getParticipantes().isEmpty()) {
				for (Participantes pp : p.getParticipantes()) {
					if (!salida.contains(pp.getUsuario().getCedula())) {
						salida.add(pp.getUsuario().getCedula());
					}
				}
			}
		}
		return salida.size();
	}

	public List<ParticipantesR> getIntegrantesProyActivosGI(List<Semillero> semilleros) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getIntegrantesProyActivos(s.getProyectos()));
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesProyFin(List<Proyecto> lista) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Proyecto p : lista) {
			if (p.getFechaFin() != null && !p.getParticipantes().isEmpty()) {
				salida.addAll(getIntegrantesProyecto(p.getParticipantes()));
			}
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesProyFinGI(List<Semillero> semilleros) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getIntegrantesProyFin(s.getProyectos()));
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesProyecto(List<Participantes> participantes) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Participantes p : orderParticipantes(participantes)) {
			salida.add(new ParticipantesR(p.getProyecto().getTitulo(),
					p.getUsuario().getNombreCompleto(), p.getRol(),
					getFechaFormateada(p.getParticipantesPK().getFechaInicio()),
					getFechaFormateada(p.getFechaFin()),
					p.getProyecto().getSemillero().getNombre()));
		}
		return salida;
	}

	private List<Participantes> orderParticipantes(List<Participantes> participantes) {
		Collections.sort(participantes, Collections.reverseOrder());
		return participantes;
	}

}
