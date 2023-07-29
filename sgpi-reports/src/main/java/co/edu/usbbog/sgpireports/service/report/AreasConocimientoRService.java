package co.edu.usbbog.sgpireports.service.report;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.AreaConocimiento;
import co.edu.usbbog.sgpireports.model.datamodels.AreasConocimientoR;

@Service
public class AreasConocimientoRService {

	public List<AreasConocimientoR> getAreasProyecto(List<AreaConocimiento> areasConocimiento) {
		return areasConocimiento.stream()
			    .map(a -> new AreasConocimientoR(a.getNombre(), a.getGranArea(), a.getDescripcion()))
			    .collect(Collectors.toList());
	}

}
