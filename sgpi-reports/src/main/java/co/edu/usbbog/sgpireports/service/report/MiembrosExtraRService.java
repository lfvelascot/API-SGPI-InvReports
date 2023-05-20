package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.model.datamodels.MiembrosExtra;
import co.edu.usbbog.sgpireports.repository.IConvocatoriaRepository;
import co.edu.usbbog.sgpireports.repository.IParticipacionesRepository;
import co.edu.usbbog.sgpireports.repository.IParticipantesRepository;
import co.edu.usbbog.sgpireports.repository.IUsuarioRepository;
@Service
public class MiembrosExtraRService {

	@Autowired
	private IParticipantesRepository pa;
	@Autowired
	private IParticipacionesRepository pe;
	@Autowired
	private IConvocatoriaRepository c;
	@Autowired
	private IUsuarioRepository usuarios;


	public List<MiembrosExtra> getMiembrosSemilleroExtra(int cc) {
		List<Usuario> aux = usuarios.getMiembrosSemillero(cc);
		List<MiembrosExtra> salida = new ArrayList<>();
		for (Usuario u : aux) {
			salida.add(new MiembrosExtra(u.getNombreCompleto(), u.getTiposUsuario().get(0).getNombre(),
					String.valueOf(pa.contProyectosPorUsuario(u.getCedula())),
					String.valueOf(pe.countParticipacionesPorUsuario(u.getCedula())),
					String.valueOf(c.CountParticpacionesEnConvocatorias(u.getCedula()))));
		}
		return salida;
	}

}
