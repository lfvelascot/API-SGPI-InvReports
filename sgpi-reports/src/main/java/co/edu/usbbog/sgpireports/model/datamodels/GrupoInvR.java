package co.edu.usbbog.sgpireports.model.datamodels;

public class GrupoInvR {
	
	private String nombre, fechaFun, categoria, fechaCat;
	private String numSemilleros;

	public GrupoInvR(String nombre, String fechaFun, String categoria, String fechaCat) {
		this.nombre = nombre;
		this.fechaFun = fechaFun;
		this.categoria = categoria;
		this.fechaCat = fechaCat;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaFun() {
		return fechaFun;
	}

	public void setFechaFun(String fechaFun) {
		this.fechaFun = fechaFun;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getFechaCat() {
		return fechaCat;
	}

	public void setFechaCat(String fechaCat) {
		this.fechaCat = fechaCat;
	}

	public String getNumSemilleros() {
		return numSemilleros;
	}

	public void setNumSemilleros(String numSemilleros) {
		this.numSemilleros = numSemilleros;
	}

}
