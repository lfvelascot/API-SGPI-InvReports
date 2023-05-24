package co.edu.usbbog.sgpireports.service;

import co.edu.usbbog.sgpireports.model.Usuario;

public interface IGestionUsuariosService {
	
	// Cargar firma
	public boolean saveFirma(String cedula);
	
	public String getFirmaUsuario(String cedula);

	public Usuario buscarUsuario(String usuario);
}
