package co.edu.usbbog.sgpireports.model.datamodels;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProyectoR implements Comparable<ProyectoR> {

	private String titulo;
	private String estado;
	private String tipo;
	private String descripcion;
	private String fechaInicio;
	private String fechaFin;
	private String metodologia;
	private String integrantes;
	private String semillero;
	private String numProductos;

	public ProyectoR(String titulo, String estado, String tipo, String descripcion, LocalDate fechaInicio,
			LocalDate fechaFin, String metodologia, String integrantes, String semillero) {
		this.titulo = titulo;
		this.estado = estado;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.fechaInicio = getFechaFormateada(fechaInicio);
		this.fechaFin = getFechaFormateada(fechaFin);
		this.metodologia = metodologia;
		this.integrantes = integrantes;
		if (semillero == null) {
			this.semillero = "sin registro";
		} else {
			this.semillero = semillero;
		}
	}
	
	public ProyectoR(String titulo, String estado, String tipo, String descripcion, LocalDate fechaInicio,
			LocalDate fechaFin, String metodologia, String integrantes, String semillero, String num) {
		this.titulo = titulo;
		this.estado = estado;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.fechaInicio = getFechaFormateada(fechaInicio);
		this.fechaFin = getFechaFormateada(fechaFin);
		this.metodologia = metodologia;
		this.integrantes = integrantes;
		if (semillero == null) {
			this.semillero = "sin registro";
		} else {
			this.semillero = semillero;
		}
		this.numProductos = num;
	}


	public String getNumProductos() {
		return numProductos;
	}

	public void setNumProductos(String numProductos) {
		this.numProductos = numProductos;
	}

	private String getFechaFormateada(LocalDate fecha) {
		if (fecha != null) {
			return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else {
			return "";
		}
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getMetodologia() {
		return metodologia;
	}

	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}

	public String getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(String integrantes) {
		this.integrantes = integrantes;
	}

	public String getSemillero() {
		return semillero;
	}

	public void setSemillero(String semillero) {
		this.semillero = semillero;
	}

	public LocalDate getDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fecha = LocalDate.parse(this.getFechaInicio(), formatter);
		return fecha;
	}

	@Override
	public int compareTo(ProyectoR o) {
		return getDate().compareTo(o.getDate());
	}

}
