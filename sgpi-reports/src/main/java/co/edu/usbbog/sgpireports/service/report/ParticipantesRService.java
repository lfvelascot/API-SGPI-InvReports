package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Participantes;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.ParticipantesR;
import co.edu.usbbog.sgpireports.repository.IProyectoRepository;
import co.edu.usbbog.sgpireports.repository.ISemilleroRepository;

@Service
public class ParticipantesRService {

	@Autowired
	private IProyectoRepository proyecto;
	@Autowired
	private ISemilleroRepository semillero;

	public List<ParticipantesR> getIntegrantes(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ParticipantesR> salida = new ArrayList<>();
		for (Proyecto p : aux) {
			List<Participantes> participantes = p.getParticipantes();
			for (Participantes pa : participantes) {
				salida.add(new ParticipantesR(p.getTitulo(), pa.getUsuario().getNombreCompleto(), pa.getRol(),
						getFechaFormateada(pa.getParticipantesPK().getFechaInicio()),
						getFechaFormateada(pa.getFechaFin()), p.getSemillero().getNombre()));
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

	public List<ParticipantesR> getIntegrantesE(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ParticipantesR> salida = new ArrayList<>();
		for (Proyecto p : aux) {
			if (!p.getParticipaciones().isEmpty()) {
				List<Participantes> participantes = p.getParticipantes();
				for (Participantes pa : participantes) {
					salida.add(new ParticipantesR(p.getTitulo(), pa.getUsuario().getNombreCompleto(), pa.getRol(),
							getFechaFormateada(pa.getParticipantesPK().getFechaInicio()),
							getFechaFormateada(pa.getFechaFin()), p.getSemillero().getNombre()));
				}
			}
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesCGI(List<Semillero> semilleros) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getIntegrantesC(s.getId()));
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesC(int cc) {
		List<Proyecto> aux = proyecto.findBySemillero(cc);
		List<ParticipantesR> salida = new ArrayList<>();
		for (Proyecto p : aux) {
			if (!p.getProyectosConvocatoria().isEmpty()) {
				List<Participantes> participantes = p.getParticipantes();
				for (Participantes pa : participantes) {
					salida.add(new ParticipantesR(p.getTitulo(), pa.getUsuario().getNombreCompleto(), pa.getRol(),
							getFechaFormateada(pa.getParticipantesPK().getFechaInicio()),
							getFechaFormateada(pa.getFechaFin()), p.getSemillero().getNombre()));
				}
			}
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesEGI(List<Semillero> semilleros) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getIntegrantesE(s.getId()));
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesProyActivos(int cc) {
		List<ParticipantesR> salida = new ArrayList<>();
		Semillero s = semillero.getById(cc);
		for (Proyecto p : s.getProyectos()) {
			if (p.getFechaFin() == null && !p.getParticipantes().isEmpty()) {
				for (Participantes pp : p.getParticipantes()) {
					salida.add(new ParticipantesR(p.getTitulo(), pp.getUsuario().getNombreCompleto(), pp.getRol(),
							getFechaFormateada(pp.getParticipantesPK().getFechaInicio()),
							getFechaFormateada(pp.getFechaFin()), p.getSemillero().getNombre()));
				}
			}
		}
		return salida;
	}

	public int countParticipantesProyectosActSemillero(int cc) {
		List<String> salida = new ArrayList<>();
		Semillero s = semillero.getById(cc);
		for (Proyecto p : s.getProyectos()) {
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

	public int countParticipantesProyectosFinSemillero(int cc) {
		List<String> salida = new ArrayList<>();
		Semillero s = semillero.getById(cc);
		for (Proyecto p : s.getProyectos()) {
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
			salida.addAll(getIntegrantesProyActivos(s.getId()));
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesProyFin(int cc) {
		List<ParticipantesR> salida = new ArrayList<>();
		Semillero s = semillero.getById(cc);
		for (Proyecto p : s.getProyectos()) {
			if (p.getFechaFin() != null && !p.getParticipantes().isEmpty()) {
				for (Participantes pp : p.getParticipantes()) {
					salida.add(new ParticipantesR(p.getTitulo(), pp.getUsuario().getNombreCompleto(), pp.getRol(),
							getFechaFormateada(pp.getParticipantesPK().getFechaInicio()),
							getFechaFormateada(pp.getFechaFin()), p.getSemillero().getNombre()));
				}
			}
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesProyFinGI(List<Semillero> semilleros) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Semillero s : semilleros) {
			salida.addAll(getIntegrantesProyFin(s.getId()));
		}
		return salida;
	}

	public List<ParticipantesR> getIntegrantesProyecto(List<Participantes> participantes) {
		List<ParticipantesR> salida = new ArrayList<>();
		for (Participantes p : participantes) {
			salida.add(new ParticipantesR(p.getProyecto().getTitulo(), p.getUsuario().getNombreCompleto(), p.getRol(),
					getFechaFormateada(p.getParticipantesPK().getFechaInicio()), getFechaFormateada(p.getFechaFin()),
					p.getProyecto().getSemillero().getNombre()));
		}
		return salida;
	}

}
