package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MiselaneaService {
	
	public String getFechaFormateada(LocalDate fecha) {
		return (fecha != null) ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Sin dato";
	}
	
	public Map<String, Object> setDatosVaciosString(Map<String, Object> map, Object dato, String clave, String vacio) {
		map.put(clave, (dato == null) ? vacio : dato.toString());
		return map;
	}


}
