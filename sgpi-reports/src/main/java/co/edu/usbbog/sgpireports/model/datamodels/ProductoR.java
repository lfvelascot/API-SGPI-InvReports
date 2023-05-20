package co.edu.usbbog.sgpireports.model.datamodels;

public class ProductoR {
	
	private String proyecto, titulo, tipo, url, fecha, semillero;

	public ProductoR(String proyecto, String titulo, String tipo, String url, String fecha) {
		this.proyecto = proyecto;
		this.titulo = titulo;
		this.tipo = tipo;
		this.url = url;
		this.fecha = fecha;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getSemillero() {
		return semillero;
	}

	public void setSemillero(String semillero) {
		this.semillero = semillero;
	}
	
	
	
	
	
	
}
