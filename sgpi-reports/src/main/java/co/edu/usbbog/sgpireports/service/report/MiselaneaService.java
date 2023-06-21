package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MiselaneaService {
	
	public String getFechaFormateada(LocalDate fecha) {
		if (fecha != null) {
			return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else {
			return "Sin dato";
		}
	}

}
