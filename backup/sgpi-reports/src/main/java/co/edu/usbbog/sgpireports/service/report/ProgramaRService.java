package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.datamodels.ProgramaR;
@Service
public class ProgramaRService {

	public List<ProgramaR> getProgramasFacultad(List<Programa> programas) {
		List<ProgramaR> salida = new ArrayList<>();
		for (Programa p : programas) {
			if(p.getDirector() != null) {
				salida.add(new ProgramaR(p.getNombre(),
						p.getDirector().getNombreCompleto(),
						p.getDirector().getCorreoEst(),
						p.getDirector().getTelefono() ));
			} else {
				salida.add(new ProgramaR(p.getNombre(),"Sin registro","Sin registro",
						"Sin registro" ));
			}
		}
		return salida;
	}

}
