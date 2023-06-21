package co.edu.usbbog.sgpireports.model.datamodels;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventoR implements Comparable<EventoR>{
	
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
	
	public LocalDate getDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fecha = LocalDate.parse(this.getFecha(), formatter);
		return fecha;
	}

	@Override
	public int compareTo(EventoR o) {
		return getDate().compareTo(o.getDate());
	}
}
