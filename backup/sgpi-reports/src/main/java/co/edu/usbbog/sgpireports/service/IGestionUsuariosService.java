package co.edu.usbbog.sgpireports.service;

import co.edu.usbbog.sgpireports.model.Usuario;

public interface IGestionUsuariosService {
	
	// Cargar firma de un usuario con su cedula
	public boolean saveFirma(String cedula);
	// Obtener firma de un usuario por su cedula
	public String getFirmaUsuario(String cedula);
	// Busca los datos del usuario
	public Usuario buscarUsuario(String usuario);
}
