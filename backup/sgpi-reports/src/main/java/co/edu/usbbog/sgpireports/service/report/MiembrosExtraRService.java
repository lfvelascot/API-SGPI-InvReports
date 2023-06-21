package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.model.datamodels.MiembrosExtra;
import co.edu.usbbog.sgpireports.repository.IConvocatoriaRepository;
import co.edu.usbbog.sgpireports.repository.IParticipacionesRepository;
import co.edu.usbbog.sgpireports.repository.IParticipantesRepository;

@Service
public class MiembrosExtraRService {

	@Autowired
	private IParticipantesRepository pa;
	@Autowired
	private IParticipacionesRepository pe;
	@Autowired
	private IConvocatoriaRepository c;
	private final List<String> investigadoresF = new ArrayList<>(
			Arrays.asList("SEMILLERISTA", "ESTUDIANTE ACTIVO", "ESTUDIANTE"));

	public List<MiembrosExtra> getMiembrosSemilleroExtra(List<Usuario> aux) {
		List<MiembrosExtra> salida = new ArrayList<>();
		for (Usuario u : aux) {
			MiembrosExtra x = new MiembrosExtra(u.getNombreCompleto(), u.getTiposUsuario().get(0).getNombre(),
					String.valueOf(pa.contProyectosPorUsuario(u.getCedula())),
					String.valueOf(pe.countParticipacionesPorUsuario(u.getCedula())),
					String.valueOf(c.CountParticpacionesEnConvocatorias(u.getCedula())));
			x.setSemillero(u.getSemilleroId().getNombre());
			salida.add(x);
			
		}
		return orderSalida(salida);
	}

	private List<MiembrosExtra> orderSalida(List<MiembrosExtra> salida) {
		Collections.sort(salida, Collections.reverseOrder());
		return salida;
	}

	public List<MiembrosExtra> getMiembrosSemilleroExtraInv(List<Usuario> aux) {
		List<MiembrosExtra> salida = new ArrayList<>();
		for (Usuario u : aux) {
			if (investigadoresF.contains(u.getTiposUsuario().get(0).getNombre().toUpperCase())) {
				salida.add(new MiembrosExtra(u.getNombreCompleto(), u.getTiposUsuario().get(0).getNombre(),
						String.valueOf(pa.contProyectosPorUsuario(u.getCedula())),
						String.valueOf(pe.countParticipacionesPorUsuario(u.getCedula())),
						String.valueOf(c.CountParticpacionesEnConvocatorias(u.getCedula()))));
			}
		}
		return orderSalida(salida);
	}

	public List<MiembrosExtra> getMiembrosGIExtraInv(List<Semillero> semilleros) {
		List<MiembrosExtra> salida = new ArrayList<>();
		for (Semillero u : semilleros) {
			salida.addAll(getMiembrosSemilleroExtra(u.getUsuarios()));
		}
		return orderSalida(salida);
	}

}
