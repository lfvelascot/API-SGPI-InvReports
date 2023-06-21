package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.LineaInvestigacion;
import co.edu.usbbog.sgpireports.model.datamodels.LineasGI;
@Service
public class LineasInvestigacionRService {
	
	private MiselaneaService extras = new MiselaneaService();
	
	public List<LineasGI> getLineasInvGI(GrupoInvestigacion g ) {
		List<LineasGI> salida = new ArrayList<>();
		for(LineaInvestigacion l : g.getLineasInvestigacion()) {
			LineasGI x = new LineasGI(l.getNombre(), l.getDescripcion(), extras.getFechaFormateada(l.getFecha()));
			x.setGi(g.getNombre());
			salida.add(x);
		}
		return salida;
	}

	public List<LineasGI> getLineasInvFacultad(List<GrupoInvestigacion> aux) {
		List<LineasGI> salida = new ArrayList<>();
		for(GrupoInvestigacion g : aux) {
			if(!g.getLineasInvestigacion().isEmpty()) {
				salida.addAll(getLineasInvGI(g));
			}
		}
		return salida;
	}

}
