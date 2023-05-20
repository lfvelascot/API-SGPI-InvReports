package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.AreaConocimiento;
import co.edu.usbbog.sgpireports.model.datamodels.AreasConocimientoR;

@Service
public class AreasConocimientoRService {

	public List<AreasConocimientoR> getAreasProyecto(List<AreaConocimiento> areasConocimiento) {
		List<AreasConocimientoR> salida = new ArrayList<>();
		for (AreaConocimiento a : areasConocimiento) {
			salida.add(new AreasConocimientoR(a.getNombre(), a.getGranArea(), a.getDescripcion()));
		}
		return salida;
	}

}
