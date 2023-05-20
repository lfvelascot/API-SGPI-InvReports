package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.LineaInvestigacion;
import co.edu.usbbog.sgpireports.model.MiembrosSemillero;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.model.datamodels.LineasGI;
import co.edu.usbbog.sgpireports.model.datamodels.ParticipantesR;
import co.edu.usbbog.sgpireports.repository.IGrupoInvestigacionRepository;
import co.edu.usbbog.sgpireports.repository.IUsuarioRepository;

@Service
public class MiembrosSemilleroRService {
	
	@Autowired
	private IUsuarioRepository usuarios;

	
	public List<MiembrosSemillero> getLideresSemillerosDetallado(GrupoInvestigacion s) {
		List<Semillero> aux = s.getSemilleros();
		return getLideresSemillerosDetalladoPrograma(aux);
	}
	
	public List<MiembrosSemillero> getMiembrosSemillero(int cc) {
		List<Usuario> aux = usuarios.getMiembrosSemillero(cc);
		List<MiembrosSemillero> salida = new ArrayList<>();
		for(Usuario u: aux) {
			salida.add(new MiembrosSemillero(u.getCodUniversitario().toString(),
					u.getNombreCompleto(),
					u.getTelefono(),
					u.getCorreoEst(),
					u.getProgramaId().getNombre()));
		}
		return salida;
	}
	
	public int countMiembrosSemillero(int cc) {
		return usuarios.getMiembrosSemillero(cc).size();
	}
	
	public int countParticipantesProyectos(List<ParticipantesR> l) {
		List<String> codigos = new ArrayList<>();
		for(ParticipantesR m : l) {
			if(!codigos.stream().filter(o -> o.equals(m.getParticipante())).findFirst().isPresent()) {
				codigos.add(m.getParticipante());
			}
		}
		return codigos.size();
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
				MiembrosSemillero m = new MiembrosSemillero(u.getCodUniversitario().toString(),
						u.getNombreCompleto(),
						u.getTelefono(),
						u.getCorreoEst(),
						u.getProgramaId().getNombre());
				m.setSemillero(p.getNombre());
				salida.add(m);
			}
		}
		return salida;
	}
	
	

}
