package co.edu.usbbog.sgpireports.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpireports.model.Firma;
import co.edu.usbbog.sgpireports.model.Usuario;
import co.edu.usbbog.sgpireports.repository.IFirmaRepository;
import co.edu.usbbog.sgpireports.repository.IUsuarioRepository;

@Service
public class GesionUsuariosService implements IGestionUsuariosService {

	@Autowired
	private IFirmaRepository iFirmaRepository;
	@Autowired
	private IUsuarioRepository usuario;

	/**
	 * Guarda la firma de un usuario
	 * @param String con la cedula del usuario
	 * @return boolean indicando si se guardo o no la firma
	 */
	@Override
	public boolean saveFirma(String cedula) {
		String nombre = "Firma-" + cedula + ".png";
		iFirmaRepository.AddFirma(cedula, nombre);
		return iFirmaRepository.existsById(cedula);
	}

	/**
	 * Busca la firma de un usuario
	 * @param String con la cedula del usuario
	 * @return nombre del archivo de la firma
	 */
	@Override
	public String getFirmaUsuario(String cedula) {
		Firma user = iFirmaRepository.getById(cedula);
		if (user != null) {
			return user.getNombre();
		} else {
			return null;
		}
	}

	/**
	 * Busca los usuarios por su cedula
	 * @param String con la cedula del usuario
	 * @return el usuario asociado a la cedula ingresada
	 */
	@Override
	public Usuario buscarUsuario(String cedula) {
		return usuario.getById(cedula);
	}
}
