package co.edu.usbbog.sgpireports.model.datamodels;

import co.edu.usbbog.sgpireports.model.Programa;

public class MiembrosSemillero {
	
	private String codigo, nombre, telefono, correo, programa, semillero;
	
	

	public MiembrosSemillero(String codigo, String nombre, String telefono, String correo, Programa programa) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		if(programa != null) {
			this.programa = programa.getNombre();
		} else {
			this.programa = "";
		}
	}

	public MiembrosSemillero(String codigo, String nombre, String telefono, String correo,
			String nombre2) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		this.programa = nombre2;
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

	public String getSemillero() {
		return semillero;
	}

	public void setSemillero(String semillero) {
		this.semillero = semillero;
	}
	
	

}
