package co.edu.usbbog.sgpireports.model;

public class MiembrosSemillero {
	
	private String codigo;
	private String nombre;
	private String telefono;
	private String correo;
	private String programa;
	private String Semillero;
	
	public MiembrosSemillero(String codigo, String nombre, String telefono, String correo, String programa) {
		this.codigo = codigo;
		this.nombre = nombre;
		if(telefono == null) {
			this.telefono = "sin telefono registrado";
		} else {
			this.telefono = telefono;
		}
		this.correo = correo;
		this.programa = programa;
	}
	
	public String getSemillero() {
		return Semillero;
	}
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public void setSemillero(String semillero) {
		Semillero = semillero;
	}
	
}
