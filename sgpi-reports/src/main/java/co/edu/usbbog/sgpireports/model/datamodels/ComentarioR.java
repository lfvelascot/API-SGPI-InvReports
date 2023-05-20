package co.edu.usbbog.sgpireports.model.datamodels;

public class ComentarioR {
	
	String producto, comentario, calificacion, fase, nivel, fecha;

	public ComentarioR(String producto, String comentario, String calificacion, String fase, String nivel,
			String fecha) {
		super();
		this.producto = producto;
		this.comentario = comentario;
		this.calificacion = calificacion;
		this.fase = fase;
		this.nivel = nivel;
		this.fecha = fecha;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	

}
