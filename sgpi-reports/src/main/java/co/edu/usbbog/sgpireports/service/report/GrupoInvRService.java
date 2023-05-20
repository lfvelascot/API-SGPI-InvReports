package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.GrupoInvestigacion;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.datamodels.GrupoInvR;
import co.edu.usbbog.sgpireports.model.datamodels.TipoPEstado;
import co.edu.usbbog.sgpireports.repository.IGrupoInvestigacionRepository;

@Service
public class GrupoInvRService {
	
	public List<GrupoInvR> getGruposInv(List<GrupoInvestigacion> aux) {
		List<GrupoInvR> salida = new ArrayList<>();
		for(GrupoInvestigacion g : aux) {
			var x = new GrupoInvR(g.getNombre(),
					getFechaFormateada(g.getFechaFun()),
					g.getCategoria(),
					getFechaFormateada(g.getFechaCat()));
			x.setNumSemilleros(String.valueOf(g.getSemilleros().size()));
			salida.add(x);
		}
		return salida;
	}
	
	private String getFechaFormateada(LocalDate fecha) {
		return  fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));  //17-02-2022
	}
	

}
