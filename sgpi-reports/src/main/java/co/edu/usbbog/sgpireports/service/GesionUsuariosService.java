package co.edu.usbbog.sgpireports.service;

import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.repository.IFirmaRepository;
import co.edu.usbbog.sgpireports.repository.IUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class GesionUsuariosService implements IGestionUsuariosService {

	@Autowired
	private IFirmaRepository iFirmaRepository;
	@Autowired
	private IUsuarioRepository usuario;
	
	
	@Override
	public boolean saveFirma(String cedula) {
		String nombre = "Firma-"+cedula+".png";
		iFirmaRepository.AddFirma(cedula,nombre);
		return iFirmaRepository.existsById(cedula);
	}


	@Override
	public String getFirmaUsuario(String cedula) {
		var user = iFirmaRepository.getById(cedula);
		if(user != null) {
			return user.getNombre();
		} else {
			return null;
		}
	}


	@Override
	public Usuario buscarUsuario(String cedula) {
		return usuario.getById(cedula);
	}
}
