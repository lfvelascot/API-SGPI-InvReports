package co.edu.usbbog.sgpireports.service;

import co.edu.usbbog.sgpireports.repository.IActividadRepository;
import co.edu.usbbog.sgpireports.repository.IFirmaRepository;
import co.edu.usbbog.sgpireports.repository.IProgramaRepository;
import co.edu.usbbog.sgpireports.repository.ITipoUsuarioRepository;
import co.edu.usbbog.sgpireports.repository.IUsuarioRepository;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usbbog.sgpireports.model.Actividad;
import co.edu.usbbog.sgpireports.model.Firma;
import co.edu.usbbog.sgpireports.model.Programa;
import co.edu.usbbog.sgpireports.model.TipoUsuario;
import co.edu.usbbog.sgpireports.model.Usuario;
import net.minidev.json.JSONObject;

@Service
public class GesionUsuariosService implements IGestionUsuariosService {

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	@Autowired
	private ITipoUsuarioRepository iTipoUsuarioRepository;
	@Autowired
	private IProgramaRepository iProgramaRepository;
	@Autowired
	private IFirmaRepository iFirmaRepository;
	@Autowired
	private IActividadRepository iActividadRepository;
	

	@Override
	public JSONObject login(String correo, String contrasena) {
		JSONObject salida = new JSONObject();
		Usuario usu = iUsuarioRepository.getByCorreo(correo);
		if (usu != null) {
			if (iUsuarioRepository.getTipoUsuario(usu.getCedula())== null) {
				salida.put("respuesta", "Sin rol asignado, comuniquese con el administrador");
			} else {
				salida = iUsuarioRepository.Login(correo, contrasena);
			}
		} else {
			salida.put("respuesta", null);
		}
		return salida;
	}

	@Override
	public boolean crearUsuario(Usuario usuario, String programa, String tipousuario) {
		if (existeUsuario(usuario.getCedula())) {
			return false;
		}
		TipoUsuario tp = iTipoUsuarioRepository.getById(tipousuario);
		Programa pro = iProgramaRepository.getById(Integer.parseInt(programa));
		if (pro == null) {
			return false;
		}
		try {
			usuario.setSemilleroId(null);
		} catch (Exception e) {
			usuario.setSemilleroId(null);
		} finally {
			usuario.setSemilleroId(null);
		}
		usuario.setProgramaId(pro);
		tp.getUsuarios().add(usuario);
		iUsuarioRepository.save(usuario);
		iTipoUsuarioRepository.save(tp);
		return iUsuarioRepository.existsById(usuario.getCedula());
	}

	@Override
	public TipoUsuario buscarTipoUsuario(String asString) {
		return iTipoUsuarioRepository.getById(asString);
	}

	@Override
	public Usuario buscarUsuario(String asString) {
		return iUsuarioRepository.getById(asString);
	}

	@Override
	public boolean existeTipoUsuario(String nombre) {
		if (iTipoUsuarioRepository.findById(nombre).isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean crearTipoUsuario(TipoUsuario tipoUsuario, List<Actividad> actividades) {
		if (!existeTipoUsuario(tipoUsuario.getNombre()) && !actividades.isEmpty()) {
			iTipoUsuarioRepository.save(tipoUsuario);
			for(Actividad a: actividades) {
				iTipoUsuarioRepository.AddTipoUsuarioActividad(tipoUsuario.getNombre(), a.getNombre());
			}
			return existeTipoUsuario(tipoUsuario.getNombre());
		} else {
			return false;
		}
	}

	@Override
	public boolean modificarTipoUsuario(TipoUsuario tipoUsuario, String nombre, List<Actividad> actividades) {
		if (existeTipoUsuario(nombre)) {
			List<Usuario> usuarios = iUsuarioRepository.getByTipoUsuario(nombre);
			iTipoUsuarioRepository.desasignarRol(nombre);
			iTipoUsuarioRepository.desasignarActividades(nombre);
			iTipoUsuarioRepository.UpdateById(tipoUsuario.getNombre(), tipoUsuario.getDescripcion(), nombre);
			if(!usuarios.isEmpty()) {
				for(Usuario a: usuarios) {
					iTipoUsuarioRepository.AddTipoUsuarioUsuario(tipoUsuario.getNombre(), a.getCedula());
				}
			}
			for(Actividad a: actividades) {
				iTipoUsuarioRepository.AddTipoUsuarioActividad(tipoUsuario.getNombre(), a.getNombre());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean modificarUsuario(String cedula, String telefono, String clave, String correoP) {
		Usuario usuario = iUsuarioRepository.getById(cedula);
		if (!correoP.equals("")) {
			usuario.setCorreoPersonal(correoP);
		}
		if (!telefono.equals("")) {
			usuario.setTelefono(telefono);
		}
		if (!clave.equals("")) {
			usuario.setContrasena(clave);
		}
		iUsuarioRepository.save(usuario);
		return iUsuarioRepository.existsById(usuario.getCedula());
	}

	@Override
	public boolean cargarFirma(String cedula, String nombrearchivo, String url) {
		Firma firma = iFirmaRepository.findByUsuario(cedula);
		if (firma.equals(null) && existeUsuario(cedula)) {
			firma = new Firma(nombrearchivo, url);
			Usuario usuario = iUsuarioRepository.getById(cedula);
			firma.setUsuario(usuario);
			iFirmaRepository.save(firma);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public List<TipoUsuario> roles(String cedula) {
		Usuario usu = iUsuarioRepository.getById(cedula);
		List<TipoUsuario> tiposUsuario = usu.getTiposUsuario();
		if (tiposUsuario.equals(null)) {
			tiposUsuario = new ArrayList<TipoUsuario>();
		}
		return tiposUsuario;
	}

	@Override
	public boolean existeUsuario(String cedula) {
		return iUsuarioRepository.existsById(cedula);
	}

	@Override
	public boolean asignarTipoUsuario(String usuario, String tipoUsuario) {
		Usuario usua = iUsuarioRepository.getById(usuario);
		TipoUsuario tipousuario = iTipoUsuarioRepository.getById(tipoUsuario);
		if (!iUsuarioRepository.existsById(usua.getCedula())) {
			return false;
		}
		if (!iTipoUsuarioRepository.existsById(tipousuario.getNombre())) {
			return false;
		}
		List<TipoUsuario> tiposUsuarios = roles(usuario);
		if (tiposUsuarios.isEmpty()) {
			tipousuario.getUsuarios().add(usua);
			iTipoUsuarioRepository.save(tipousuario);
			return true;
		} else {
			iTipoUsuarioRepository.desasignarRoles(usuario);
			tipousuario.getUsuarios().add(usua);
			iTipoUsuarioRepository.save(tipousuario);
			return true;
		}
	}

	@Override
	public Actividad buscarActividad(String nombre) {
		return iActividadRepository.getById(nombre);
	}
	
	@Override
	public boolean existsByCorreo(String correo) {
		return iUsuarioRepository.getByCorreo(correo) != null;
	}
	
	
	@Override
	public List<Usuario> todosLosUsuarios() {
		List<Usuario> usuarios = iUsuarioRepository.findAll();
		if (usuarios.equals(null)) {
			usuarios = new ArrayList<Usuario>();
		}
		return usuarios;
	}
	
	@Override
	public boolean saveFirma(String cedula, String nombre) {
		iFirmaRepository.AddFirma(cedula,nombre);
		return iFirmaRepository.existsById(cedula);
	}
}
