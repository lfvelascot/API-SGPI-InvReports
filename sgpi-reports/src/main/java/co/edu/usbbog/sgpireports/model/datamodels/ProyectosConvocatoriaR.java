package co.edu.usbbog.sgpireports.model.datamodels;

public class ProyectosConvocatoriaR {
	private String proyecto, convocatoria, fechaInicio, fechaFin, entidad, semillero, estado;

	public ProyectosConvocatoriaR(String proyecto, String convocatoria, String fechaInicio, String fechaFin,
			String entidad, String semillero, String estado) {
		super();
		this.proyecto = proyecto;
		this.convocatoria = convocatoria;
		this.fechaInicio = fechaInicio;
		if (fechaFin == null) {
			this.fechaFin = "";
		} else {
			this.fechaFin = fechaFin;
		}
		if (entidad == null) {
			this.entidad = "Sin entidad registrada";
		} else {
			this.entidad = entidad;
		}
		this.semillero = semillero;
		this.estado = estado;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public String getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
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

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getSemillero() {
		return semillero;
	}

	public void setSemillero(String semillero) {
		this.semillero = semillero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
