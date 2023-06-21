package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.model.datamodels.MiembrosSemillero;
import co.edu.usbbog.sgpireports.model.datamodels.ParticipantesR;
import co.edu.usbbog.sgpireports.repository.IUsuarioRepository;

@Service
public class MiembrosSemilleroRService {
	
	@Autowired
	private IUsuarioRepository usuarios;
	private final List<String> investigadoresF = new ArrayList<>(Arrays.asList("SEMILLERISTA", "ESTUDIANTE ACTIVO", "ESTUDIANTE") );

	
	public List<MiembrosSemillero> getLideresSemillerosDetallado(GrupoInvestigacion s) {
		List<Semillero> aux = s.getSemilleros();
		return getLideresSemillerosDetalladoPrograma(aux);
	}
	
	public List<MiembrosSemillero> getMiembrosSemillero(List<Usuario> aux) {
		List<MiembrosSemillero> salida = new ArrayList<>();
		for(Usuario u: aux) {
			MiembrosSemillero us = new MiembrosSemillero(u.getCodUniversitario().toString(),
					u.getNombreCompleto(),
					u.getTelefono(),
					u.getCorreoEst(),
					u.getProgramaId());
			if(u.getSemilleroId() != null) {
				us.setSemillero(u.getSemilleroId().getNombre());
			} else {
				us.setSemillero("");
			}
			salida.add(us);
		}
		return salida;
	}

	public List<MiembrosSemillero> getDirectoresGI(List<GrupoInvestigacion> aux) {
		List<MiembrosSemillero> salida = new ArrayList<>();
		for(GrupoInvestigacion g : aux) {
			if(g.getDirectorGrupo() != null) {
				salida.add(new MiembrosSemillero(String.valueOf(g.getDirectorGrupo().getCodUniversitario()),
						g.getDirectorGrupo().getNombreCompleto(),
						g.getDirectorGrupo().getTelefono(),
						g.getDirectorGrupo().getCorreoEst(),
						g.getNombre()));
			}
		}
		return salida;
	}

	public List<MiembrosSemillero> getLideresSemillerosDetalladoPrograma(List<Semillero> semilleros) {
		List<MiembrosSemillero> salida = new ArrayList<>();
		for(Semillero p: semilleros) {
			Usuario u = p.getLiderSemillero();
			if(u != null){
				String program = "N/A";
				if(u.getProgramaId() != null) {
					program = u.getProgramaId().getNombre();
				}
				MiembrosSemillero m = new MiembrosSemillero(u.getCodUniversitario().toString(),
						u.getNombreCompleto(),
						u.getTelefono(),
						u.getCorreoEst(),
						program);
				m.setSemillero(p.getNombre());
				salida.add(m);
			}
		}
		return salida;
	}

	public List<MiembrosSemillero> getMiembrosSemilleros(int programa) {
		List<MiembrosSemillero> salida = new ArrayList<>();
		for(Usuario u : usuarios.getMiembrosSemillerosPrograma(programa)) {
			String program = "N/A";
			if(u.getProgramaId() != null) {
				program = u.getProgramaId().getNombre();
			}
			MiembrosSemillero m = new MiembrosSemillero(u.getCodUniversitario().toString(),
					u.getNombreCompleto(),
					u.getTelefono(),
					u.getCorreoEst(),
					program);
			m.setSemillero(u.getSemilleroId().getNombre());
			salida.add(m);
		}
		return salida;
	}
	

	public List<MiembrosSemillero> getMiembrosSemilleroInv(List<Usuario> aux) {
		List<MiembrosSemillero> salida = new ArrayList<>();
		for(Usuario u: aux) {
			if(investigadoresF.contains(u.getTiposUsuario().get(0).getNombre().toUpperCase())) {
				MiembrosSemillero us = new MiembrosSemillero(u.getCodUniversitario().toString(),
						u.getNombreCompleto(),
						u.getTelefono(),
						u.getCorreoEst(),
						u.getProgramaId().getNombre());
				us.setSemillero(u.getSemilleroId().getNombre());
				salida.add(us);
			}
		}
		return salida;
	}

	public List<MiembrosSemillero> getMiembrosGIInv(List<Semillero> semilleros) {
		List<MiembrosSemillero> salida = new ArrayList<>();
		for(Semillero u: semilleros) {
			salida.addAll(getMiembrosSemilleroInv(u.getUsuarios()));
		}
		return salida;
	}

	public int countParticipantesProyectoA(List<ParticipantesR> aux3) {
		int i = 0;
		for(ParticipantesR p : aux3) {
			if(p.getFin() == "") {
				i++;
			}
		}
		return i;
	}
	public int countMiembrosSemillero(int cc) {
		return usuarios.getMiembrosSemillero(cc).size();
	}
	
	public int countParticipantesProyectos(List<ParticipantesR> l) {
		List<String> codigos = new ArrayList<>();
		for(ParticipantesR m : l) {
			if(!codigos.contains(m.getParticipante())) {
				codigos.add(m.getParticipante());
			}
		}
		return codigos.size();
	}
}
