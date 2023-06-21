package co.edu.usbbog.sgpireports.model.datamodels;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PresupuestoR implements Comparable<PresupuestoR>{

	private String id, monto, fecha, descripcion, titulo;
	private String totalSolicitadas, totalrealizadas, totalRechazadas, totalAceptadas, presupuestoRestante;

	public PresupuestoR(String id, String monto, String fecha, String descripcion) {
		this.id = id;
		this.monto = monto;
		this.fecha = fecha;
		if (descripcion != null) {
			this.descripcion = descripcion;
		} else {
			this.descripcion = "";
		}
	}
	
	public String getPresupuestoRestante() {
		return presupuestoRestante;
	}

	public void setPresupuestoRestante(String presupuestoRestante) {
		this.presupuestoRestante = presupuestoRestante;
	}

	public String getTotalSolicitadas() {
		return totalSolicitadas;
	}

	public void setTotalSolicitadas(String totalSolicitadas) {
		this.totalSolicitadas = totalSolicitadas;
	}

	public String getTotalrealizadas() {
		return totalrealizadas;
	}

	public void setTotalrealizadas(String totalrealizadas) {
		this.totalrealizadas = totalrealizadas;
	}

	public String getTotalRechazadas() {
		return totalRechazadas;
	}

	public void setTotalRechazadas(String totalRechazadas) {
		this.totalRechazadas = totalRechazadas;
	}

	public String getTotalAceptadas() {
		return totalAceptadas;
	}

	public void setTotalAceptadas(String totalAceptadas) {
		this.totalAceptadas = totalAceptadas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public LocalDate getDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fecha = LocalDate.parse(this.getFecha(), formatter);
		return fecha;
	}

	@Override
	public int compareTo(PresupuestoR o) {
		return getDate().compareTo(o.getDate());
	}

}
