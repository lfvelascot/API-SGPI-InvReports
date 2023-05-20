package co.edu.usbbog.sgpireports.model.datamodels;

public class EventoR {
	
	private String titulo, nombre,fecha,entidad, estado, semillero;

	public EventoR(String titulo, String nombre, String fecha, String entidad, String estado) {
		this.titulo = titulo;
		this.nombre = nombre;
		this.fecha = fecha;
		this.entidad = entidad;
		this.estado = estado;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String string) {
		this.fecha = string;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getSemillero() {
		return semillero;
	}

	public void setSemillero(String semillero) {
		this.semillero = semillero;
	}
}
