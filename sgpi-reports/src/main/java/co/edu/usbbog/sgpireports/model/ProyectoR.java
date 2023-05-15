package co.edu.usbbog.sgpireports.model;

import java.time.LocalDate;

public class ProyectoR {

	private String titulo;
	private String estado;
	private String tipo;
	private String descripcion;
	private String fechaInicio;
	private String fechaFin;
	private String metodologia;
	private String integrantes;
	private String semillero;

	public ProyectoR(String titulo, String estado, String tipo, String descripcion, String fechaInicio, LocalDate fechaFin,
			String metodologia, String integrantes, String semillero) {
		super();
		this.titulo = titulo;
		this.estado = estado;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		if(fechaFin == null) {
			this.fechaFin = "";
		} else {
			this.fechaFin = fechaFin.toString();
		}
		this.metodologia = metodologia;
		this.integrantes = integrantes;
		if(semillero == null) {
			this.semillero = "sin registro";
		} else {
			this.semillero = semillero;
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
	
	
	
	

}
