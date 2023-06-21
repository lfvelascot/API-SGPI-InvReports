package co.edu.usbbog.sgpireports.model.datamodels;

public class ParticipantesR {
	private String proyecto;
	private String participante;
	private String rol;
	private String inicio;
	private String fin;
	private String semillero;

	public ParticipantesR(String proyecto, String participante, String rol, String inicio, String fin,
			String semillero) {
		this.proyecto = proyecto;
		this.participante = participante;
		this.rol = rol;
		this.inicio = inicio;
		if (fin == null) {
			this.fin = "";
		} else {
			this.fin = fin;
		}
		this.semillero = semillero;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public String getParticipante() {
		return participante;
	}

	public void setParticipante(String participante) {
		this.participante = participante;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public String getSemillero() {
		return semillero;
	}

	public void setSemillero(String semillero) {
		this.semillero = semillero;
	}

}
