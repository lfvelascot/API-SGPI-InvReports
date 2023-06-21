package co.edu.usbbog.sgpireports.model.datamodels;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompraR implements Comparable<CompraR>{

	private String fecha_solicitud, nombre, tipo, codigo_compra, valor,  fecha_compra, estado, link, descripcion,
			presupuesto;

	public CompraR(String fecha_solicitud, String nombre, String tipo, String codigo_compra, double valor,
			String fecha_compra, String estado, String link, String descripcion, String presupuesto) {
		this.fecha_solicitud = fecha_solicitud;
		this.nombre = nombre;
		this.tipo = tipo;
		if (valor != 0.0) {
			this.valor = "$ "+String.valueOf(valor);
		} else {
			this.valor = "sin valor";
		}
		if (fecha_compra != null) {
			this.fecha_compra = fecha_compra;
		} else {
			this.fecha_compra = "";
		}
		if (codigo_compra != null) {
			this.codigo_compra = codigo_compra;
		} else {
			this.codigo_compra = "";
		}
		this.estado = estado;
		if (link != null) {
			this.link = link;
		} else {
			this.link = "sin URL";
		}
		this.descripcion = descripcion;
		this.presupuesto = presupuesto;
	}


	public String getFecha_solicitud() {
		return fecha_solicitud;
	}

	public void setFecha_solicitud(String fecha_solicitud) {
		this.fecha_solicitud = fecha_solicitud;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodigo_compra() {
		return codigo_compra;
	}

	public void setCodigo_compra(String codigo_compra) {
		this.codigo_compra = codigo_compra;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getFecha_compra() {
		return fecha_compra;
	}

	public void setFecha_compra(String fecha_compra) {
		this.fecha_compra = fecha_compra;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}


	public LocalDate getDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fecha = LocalDate.parse(this.getFecha_solicitud(), formatter);
		return fecha;
	}

	@Override
	public int compareTo(CompraR o) {
		return getDate().compareTo(o.getDate());
	}

}
