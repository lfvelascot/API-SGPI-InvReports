package co.edu.usbbog.sgpireports.service.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Comentario;
import co.edu.usbbog.sgpireports.model.Producto;
import co.edu.usbbog.sgpireports.model.datamodels.ComentarioR;
@Service
public class ComentarioRService {


	public List<ComentarioR> getComentariosProyecto(List<Producto> productos) {
		List<ComentarioR> salida = new ArrayList<>();
		for (Producto p : productos) {
			if (!p.getComentarios().isEmpty()) {
				for (Comentario c : p.getComentarios()) {
					String calificacion = "N/A";
					if (c.getCalificacion() != null) {
						calificacion = String.valueOf(c.getCalificacion());
					}
					salida.add(new ComentarioR(p.getTituloProducto(), c.getComentario(), calificacion, c.getFase(),
							c.getNivel(), getFechaFormateada(c.getFecha())));
				}
			}
		}
		return salida;
	}

	private String getFechaFormateada(LocalDate fecha) {
		return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // 17-02-2022
	}

}
