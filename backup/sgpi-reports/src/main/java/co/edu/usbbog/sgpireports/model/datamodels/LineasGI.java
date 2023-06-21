package co.edu.usbbog.sgpireports.model.datamodels;

public class LineasGI {
	
	private String nombre,descripcion, fin, gi;

	public LineasGI(String nombre, String descripcion, String fin) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fin = fin;
	}
	
	

	public String getGi() {
		return gi;
	}



	public void setGi(String gi) {
		this.gi = gi;
	}



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}
	
	

}
