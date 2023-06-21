package co.edu.usbbog.sgpireports.service.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Comentario;
import co.edu.usbbog.sgpireports.model.Producto;
import co.edu.usbbog.sgpireports.model.Proyecto;
import co.edu.usbbog.sgpireports.model.Semillero;
import co.edu.usbbog.sgpireports.model.datamodels.ComentarioR;
@Service
public class ComentarioRService {
	
	private MiselaneaService extras = new MiselaneaService();


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
							c.getNivel(), extras.getFechaFormateada(c.getFecha())));
				}
			}
		}
		return orderComments(salida);
	}

	private List<ComentarioR> orderComments(List<ComentarioR> comentarios) {
		Collections.sort(comentarios, Collections.reverseOrder());
		return comentarios;
	}


	public List<ComentarioR> getComentariosProyectos(List<Proyecto> proyectos) {
		List<ComentarioR> salida = new ArrayList<>();
		for(Proyecto p: proyectos) {
			if(!p.getProductos().isEmpty()) {
				salida.addAll(getComentariosProyecto(p.getProductos()));
			}
		}
		return orderComments(salida);
	}

	public List<ComentarioR> getProductosGI(List<Semillero> semilleros) {
		List<ComentarioR> salida = new ArrayList<>();
		for(Semillero s: semilleros) {
			if(!s.getProyectos().isEmpty()) {
				salida.addAll(getComentariosProyectos(s.getProyectos()));
			}
		}
		return orderComments(salida);
	}

	public List<ComentarioR> getProyectosActComentarios(List<Proyecto> proyectos) {
		List<ComentarioR> salida = new ArrayList<>();
		for(Proyecto p : proyectos) {
			if(p.getFechaFin() == null && !p.getProductos().isEmpty()) {
				salida.addAll(getComentariosProyecto(p.getProductos()));
			}
		}
		return orderComments(salida);
	}
	
	public List<ComentarioR> getProyectosFinComentarios(List<Proyecto> proyectos) {
		List<ComentarioR> salida = new ArrayList<>();
		for(Proyecto p : proyectos) {
			if(p.getFechaFin() != null && !p.getProductos().isEmpty()) {
				salida.addAll(getComentariosProyecto(p.getProductos()));
			}
		}
		return orderComments(salida);
	}
	
	public List<ComentarioR> getProyectosActComentariosGI(List<Semillero> semilleros) {
		List<ComentarioR> salida = new ArrayList<>();
		for(Semillero s: semilleros) {
			if(!s.getProyectos().isEmpty()) {
				salida.addAll(getProyectosActComentarios(s.getProyectos()));
			}
		}
		return orderComments(salida);
	}
	
	public List<ComentarioR> getProyectosFinComentariosGI(List<Semillero> semilleros) {
		List<ComentarioR> salida = new ArrayList<>();
		for(Semillero s: semilleros) {
			if(!s.getProyectos().isEmpty()) {
				salida.addAll(getProyectosFinComentarios(s.getProyectos()));
			}
		}
		return orderComments(salida);
	}


}
