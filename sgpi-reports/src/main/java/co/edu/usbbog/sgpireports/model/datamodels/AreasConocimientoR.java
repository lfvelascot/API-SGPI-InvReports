package co.edu.usbbog.sgpireports.model.datamodels;

public class AreasConocimientoR {
	
	private String nombre, granArea, descripcion;

	public AreasConocimientoR(String nombre, String granArea, String descripcion) {
		this.nombre = nombre;
		this.granArea = granArea;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGranArea() {
		return granArea;
	}

	public void setGranArea(String granArea) {
		this.granArea = granArea;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
