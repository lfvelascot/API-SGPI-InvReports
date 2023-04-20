package co.edu.usbbog.sgpi.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usbbog.sgpi.model.Facultad;
import co.edu.usbbog.sgpi.model.GrupoInvestigacion;
import co.edu.usbbog.sgpi.model.Programa;
import co.edu.usbbog.sgpi.model.Semillero;
import co.edu.usbbog.sgpi.model.TipoUsuario;
import co.edu.usbbog.sgpi.model.Usuario;
import co.edu.usbbog.sgpi.repository.IFacultadRepository;
import co.edu.usbbog.sgpi.repository.IGrupoInvestigacionRepository;
import co.edu.usbbog.sgpi.repository.IProgramaRepository;
import co.edu.usbbog.sgpi.repository.ISemilleroRepository;
import co.edu.usbbog.sgpi.repository.ITipoUsuarioRepository;
import co.edu.usbbog.sgpi.repository.IUsuarioRepository;
import net.minidev.json.JSONObject;

@Service
public class GestionUsuariosService implements IGestionUsuariosService {
	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private ISemilleroRepository iSemilleroRepository;
	@Autowired
	private ITipoUsuarioRepository iTipoUsuarioRepository;
	@Autowired
	private IFacultadRepository iFacultadRepository;
	@Autowired
	private IProgramaRepository iProgramaRepository;
	@Autowired
	private IGrupoInvestigacionRepository iGrupoInvestigacionRepository;

	@Override
	public List<Usuario> todosLosUsuarios() {
		List<Usuario> usuarios = iUsuarioRepository.findAll();
		if (usuarios.equals(null)) {
			usuarios = new ArrayList<Usuario>();
		}
		return usuarios;
	}

	@Override
	public Usuario buscarUsuario(String cedula) {
		return iUsuarioRepository.getById(cedula);
	}

	@Override
	public Facultad buscarFacultad(int id) {
		return iFacultadRepository.getById(id);
	}

	@Override
	public boolean existeUsuario(String cedula) {

		return iUsuarioRepository.existsById(cedula);
	}

	@Override
	public Programa buscarPrograma(int id) {
		return iProgramaRepository.getById(id);
	}

	@Override
	public boolean eliminarUsuario(String cedula) {
		if (iUsuarioRepository.findById(cedula).isPresent()) {
			iUsuarioRepository.deleteById(cedula);
			return true;
		}
		return false;
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
		}
		finally {
			usuario.setSemilleroId(null);
		}
		usuario.setProgramaId(pro);
		tp.getUsuarios().add(usuario);
		iUsuarioRepository.save(usuario);
		iTipoUsuarioRepository.save(tp);
		return iUsuarioRepository.existsById(usuario.getCedula());
	}

	@Override
	public boolean existeSemillero(Integer id) {
		if (iSemilleroRepository.findById(id).isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean asignarSemillero(String cedula,int semillero) {
		Usuario usu=iUsuarioRepository.getById(cedula);
		Semillero semi=iSemilleroRepository.getById(semillero);
		TipoUsuario tipo2 = iTipoUsuarioRepository.getById("Semillerista");
		int i=0;
		List<TipoUsuario> tipo=usu.getTiposUsuario();
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if(tipoUsuario.getNombre().contains("Semillerista")) {
				i=i+1;
			}
		}
		usu.setSemilleroId(semi);
		if(i>0) {
		}else{
			tipo2.getUsuarios().add(usu);
			iTipoUsuarioRepository.save(tipo2);
		}
		
		iUsuarioRepository.save(usu);
		return iUsuarioRepository.existsById(usu.getCedula());
		}

	public boolean eliminarUsuarioSemillero(String cedula) {

		if (iUsuarioRepository.existsById(cedula)) {
			iUsuarioRepository.setSemilleroById(cedula);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean existeFacultad(Integer id) {
		if (iFacultadRepository.findById(id).isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public TipoUsuario buscarTipoUsuario(String asString) {
		return iTipoUsuarioRepository.getById(asString);
	}

	@Override
	public List<TipoUsuario> todosLosTipoUsuario() {
		List<TipoUsuario> tiposUsuario = iTipoUsuarioRepository.findAll();
		if (tiposUsuario.equals(null)) {
			tiposUsuario = new ArrayList<TipoUsuario>();
		}
		return tiposUsuario;
	}

	@Override
	public boolean existeTipoUsuario(String nombre) {
		if (iTipoUsuarioRepository.findById(nombre).isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean eliminarTipoUsuario(String nombre) {

		if (iTipoUsuarioRepository.findById(nombre).isPresent()) {
			iTipoUsuarioRepository.deleteById(nombre);
			return true;
		}
		return false;
	}

	@Override
	public boolean eliminarTipoUsuarioAUsuario(String cedula, String nombre) {
		if (iUsuarioRepository.existsById(cedula)) {
			iUsuarioRepository.deleteUsuariosById(cedula, nombre);
			return true;
		}
		return false;
	}

	@Override
	public boolean crearTipoUsuario(TipoUsuario tipoUsuario) {
		iTipoUsuarioRepository.save(tipoUsuario);
		return true;

	}

	@Override
	public boolean asignarDecano(Facultad facultad, String decano) {
		Usuario deca = iUsuarioRepository.getById(decano);
		List<TipoUsuario> tipo = deca.getTiposUsuario();
		TipoUsuario profesor = iTipoUsuarioRepository.getById("profesor");
		TipoUsuario administrador = iTipoUsuarioRepository.getById("administrativo");
		if (tipo.contains(profesor) || tipo.contains(administrador)) {
			if (deca != null && facultad != null) {
				iFacultadRepository.setDecanoById(deca.getCedula(), facultad.getId() + "");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean eliminarDecanoFacultad(String cedula, String facultad) {
		if (iUsuarioRepository.existsById(cedula) && iFacultadRepository.existsById(Integer.parseInt(facultad))) {
			iFacultadRepository.deleteDecanoById(facultad);
			return true;
		}
		return false;
	}

	@Override
	public boolean asignarCoorInv(Facultad facultad, String coorInv) {
		Usuario coor = iUsuarioRepository.getById(coorInv);
		List<TipoUsuario> tipo = coor.getTiposUsuario();
		TipoUsuario profesor = iTipoUsuarioRepository.getById("profesor");
		TipoUsuario administrador = iTipoUsuarioRepository.getById("administrativo");
		if (tipo.contains(profesor) || tipo.contains(administrador)) {
			if (coor != null && facultad != null) {
				iFacultadRepository.setCoorInvById(coor.getCedula(), facultad.getId() + "");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean eliminarCoorInvFacultad(String cedula, String facultad) {
		if (iUsuarioRepository.existsById(cedula) && iFacultadRepository.existsById(Integer.parseInt(facultad))) {
			iFacultadRepository.deleteCoorInvById(facultad);
			return true;
		}
		return false;
	}

	@Override
	public boolean eliminarDirectorGrupo(String cedula, String grupo) {
		if (iUsuarioRepository.existsById(cedula)
				&& iGrupoInvestigacionRepository.existsById(Integer.parseInt(grupo))) {
			iUsuarioRepository.deleteDirectorById(grupo);
			return true;
		}
		return false;
	}

	@Override
	public JSONObject login(String correo, String contrasena,String tipo) {
		JSONObject salida = new JSONObject();
		Usuario usu = iUsuarioRepository.getByCorreo(correo);
		if(usu!=null) {
			if(usu.getTiposUsuario().equals(null)) {
				salida.put("respuesta","el usuario o la contraseña son incorrectos o el tipo e rol son incorrectos");
			}else {
				salida = iUsuarioRepository.Login(correo, contrasena,tipo);
			}	
			}else {
				salida.put("respuesta",null);
			}
		return salida;
	}
	@Override
	public boolean asignarDirectorPrograma(Programa programa, String direct) {
		Usuario usu = iUsuarioRepository.getById(direct);
		programa.setDirector(usu);
		iProgramaRepository.save(programa);
		return iProgramaRepository.existsById(programa.getId());
	}
	@Override
	public GrupoInvestigacion buscarGrpo(int director) {
		return iGrupoInvestigacionRepository.getById(director);
	}

	@Override
	public boolean asignarDirectorGrupo(GrupoInvestigacion grupo, String director) {
		Usuario usu = iUsuarioRepository.getById(director);
		grupo.setDirectorGrupo(usu);
		iGrupoInvestigacionRepository.save(grupo);
		return iGrupoInvestigacionRepository.existsById(grupo.getId());
	}

	@Override
	public Semillero buscarSemillero(int lider) {
		return iSemilleroRepository.getById(lider);
	}

	@Override
	public boolean asignarLiderSemillero(Semillero semillero, String lider) {
		Usuario usu = iUsuarioRepository.getById(lider);
		semillero.setLiderSemillero(usu);
		iSemilleroRepository.save(semillero);
		return iSemilleroRepository.existsById(semillero.getId());
	}

	@Override
	public boolean eliminarLiderSemillero(String cedula, String semillero) {
		if (iUsuarioRepository.existsById(cedula) && iSemilleroRepository.existsById(Integer.parseInt(semillero))) {
			iSemilleroRepository.deleteLiderSemilleroById(semillero);
			return true;
		}
		return false;
	}

	@Override
	public boolean asignarTipoUsuario(Usuario usuario, TipoUsuario tipoUsuario) {
		if(iUsuarioRepository.existsById(usuario.getCedula())) {
			
		if(tipoUsuario.getNombre().equals("Egresado")||tipoUsuario.getNombre().equals("Estudiante inactivo")){
			iTipoUsuarioRepository.desasignarRoles(usuario.getCedula());
			tipoUsuario.getUsuarios().add(usuario);
			iTipoUsuarioRepository.save(tipoUsuario);
			return true;
		}
		tipoUsuario.getUsuarios().add(usuario);
		usuario.getTiposUsuario().add(tipoUsuario);
		iUsuarioRepository.save(usuario);
		iTipoUsuarioRepository.save(tipoUsuario);
		List<Usuario> usuarios = iTipoUsuarioRepository.getById(tipoUsuario.getNombre()).getUsuarios();
		return usuarios.contains(usuario);
	}else {
				return false;
				}
		
	}
	@Override
	public String asignarRolUsuario(String usuario, String tipoUsuario) {
		Usuario usua = iUsuarioRepository.getById(usuario);
		TipoUsuario tipousuario = iTipoUsuarioRepository.getById(tipoUsuario);
		if(!iUsuarioRepository.existsById(usua.getCedula())) {
			return "el usuario no existe";
		}
		if(!iTipoUsuarioRepository.existsById(tipousuario.getNombre())) {
			return "el rol no existe";
		}
		int i =0;
		if(tipoUsuario.equals("Egresado")||tipoUsuario.equals("Estudiante inactivo")){
			//iTipoUsuarioRepository.desasignarRoles(usuario);
			tipousuario.getUsuarios().add(usua);
			iTipoUsuarioRepository.save(tipousuario);
			return "Se asigno el rol "+ tipoUsuario + "";
		}else {
		
		List<TipoUsuario> tipo = usua.getTiposUsuario();
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
			if(tipoUsuario2.getNombre().equals(tipousuario.getNombre())) {
				i=i+1;
			}
		}
		}
			if(i>0) {
				return "ya tiene ese rol";
			}
			else {
				
				tipousuario.getUsuarios().add(usua);
				iTipoUsuarioRepository.save(tipousuario);
			}
			return "se agrego el rol";	
		
	}
	@Override
	public boolean validarCuenta(String cedula, String visibilidad) {

		return false;
	}

	@Override
	public boolean cambiarContrasenaUsuario(String contrasena) {
		// TODO Auto-generated method stub
		return false;
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
	public boolean modificarUsuario(String cedula, String telefono, String clave, String correoP) {
		Usuario usuario=iUsuarioRepository.getById(cedula);
		if(correoP.equals("")){
		usuario.setCorreoPersonal(correoP);
		}
		if(telefono.equals("")) {
			usuario.setTelefono(telefono);
		}
		if(clave.equals("")) {
			usuario.setContrasena(clave);
		}
		iUsuarioRepository.save(usuario);	
		return iUsuarioRepository.existsById(usuario.getCedula());
	}

	@Override
	public List<TipoUsuario> todosLosRoles() {
		List<TipoUsuario> tipo = iTipoUsuarioRepository.findAll();
		if (tipo.equals(null)) {
			tipo = new ArrayList<TipoUsuario>();
		}
		return tipo;
		
	}

	@Override
	public List<Usuario> todosPorRol(String tipo) {
		TipoUsuario tipoU= iTipoUsuarioRepository.getById(tipo);
		List<Usuario> usu= tipoU.getUsuarios();
		if (usu.equals(null)) {
			usu = new ArrayList<Usuario>();
		}
		return usu;
	}

	@Override
	public boolean desasignarRol(String cedula) {
		Usuario usuario=iUsuarioRepository.getById(cedula);
		if(iUsuarioRepository.existsById(cedula)) {
		iTipoUsuarioRepository.desasignarRoles(usuario.getCedula());
		return true;
		}else {
			return false;
		}
		}



}
