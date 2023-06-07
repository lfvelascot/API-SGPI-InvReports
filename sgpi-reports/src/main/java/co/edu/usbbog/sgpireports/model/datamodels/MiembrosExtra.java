package co.edu.usbbog.sgpireports.model.datamodels;

public class MiembrosExtra  implements Comparable<MiembrosExtra>{
	
	private String miembro;
	private String rol;
	private String totalProyectos;
	private String totalEventos;
	private String totalConvocatorias;
	private String semillero;
	
	public MiembrosExtra(String miembro, String rol, String totalProyectos, String totalEventos,
			String totalConvocatorias) {
		this.miembro = miembro;
		this.rol = rol;
		this.totalProyectos = totalProyectos;
		this.totalEventos = totalEventos;
		this.totalConvocatorias = totalConvocatorias;
	}

	
	
	public String getSemillero() {
		return semillero;
	}

	public void setSemillero(String semillero) {
		this.semillero = semillero;
	}

	public String getMiembro() {
		return miembro;
	}

	public void setMiembro(String miembro) {
		this.miembro = miembro;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getTotalProyectos() {
		return totalProyectos;
	}

	public void setTotalProyectos(String totalProyectos) {
		this.totalProyectos = totalProyectos;
	}

	public String getTotalEventos() {
		return totalEventos;
	}

	public void setTotalEventos(String totalEventos) {
		this.totalEventos = totalEventos;
	}

	public String getTotalConvocatorias() {
		return totalConvocatorias;
	}

	public void setTotalConvocatorias(String totalConvocatorias) {
		this.totalConvocatorias = totalConvocatorias;
	}



	@Override
	public int compareTo(MiembrosExtra o) {
		return getMiembro().compareTo(o.getMiembro());
	}

}
