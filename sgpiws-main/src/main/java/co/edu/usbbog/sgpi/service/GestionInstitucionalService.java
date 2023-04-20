package co.edu.usbbog.sgpi.service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usbbog.sgpi.model.AreaConocimiento;
import co.edu.usbbog.sgpi.model.Clase;
import co.edu.usbbog.sgpi.model.Convocatoria;
import co.edu.usbbog.sgpi.model.Evento;
import co.edu.usbbog.sgpi.model.Facultad;
import co.edu.usbbog.sgpi.model.GrupoInvestigacion;
import co.edu.usbbog.sgpi.model.LineaInvestigacion;
import co.edu.usbbog.sgpi.model.Materia;
import co.edu.usbbog.sgpi.model.Programa;
import co.edu.usbbog.sgpi.model.Proyecto;
import co.edu.usbbog.sgpi.model.ProyectosConvocatoria;
import co.edu.usbbog.sgpi.model.Semillero;

import co.edu.usbbog.sgpi.model.TipoUsuario;
import co.edu.usbbog.sgpi.model.Usuario;
import co.edu.usbbog.sgpi.repository.IAreaConocimientoRepository;
import co.edu.usbbog.sgpi.repository.IClaseRepository;
import co.edu.usbbog.sgpi.repository.IConvocatoriaRepository;
import co.edu.usbbog.sgpi.repository.IEventoRepository;
import co.edu.usbbog.sgpi.repository.IFacultadRepository;
import co.edu.usbbog.sgpi.repository.IGrupoInvestigacionRepository;
import co.edu.usbbog.sgpi.repository.ILineaInvestigacionRepository;
import co.edu.usbbog.sgpi.repository.IMateriaRepository;
import co.edu.usbbog.sgpi.repository.IProgramaRepository;
import co.edu.usbbog.sgpi.repository.IProyectoRepository;
import co.edu.usbbog.sgpi.repository.ISemilleroRepository;
import co.edu.usbbog.sgpi.repository.ITipoUsuarioRepository;
import co.edu.usbbog.sgpi.repository.IUsuarioRepository;
import net.minidev.json.JSONObject;

@Service
public class GestionInstitucionalService implements IGestionInstitucionalService {

	@Autowired
	private IGrupoInvestigacionRepository grupoIRepo;

	@Autowired
	private ISemilleroRepository semilleroRepo;

	@Autowired
	private IFacultadRepository facultadRepo;

	@Autowired
	private IProgramaRepository programaRepo;

	@Autowired
	private IMateriaRepository materiaRepo;

	@Autowired
	private IClaseRepository claseRepo;

	@Autowired
	private IUsuarioRepository usuarioRepo;

	@Autowired
	private IProyectoRepository proyectoRepo;

	@Autowired
	private ILineaInvestigacionRepository lineaRepo;

	@Autowired
	private IConvocatoriaRepository convocatoriaRepo;

	@Autowired
	private IAreaConocimientoRepository areaRepo;

	@Autowired
	private IEventoRepository eventoRepo;
	@Autowired
	private ITipoUsuarioRepository iTipoUsuarioRepository;

	private static Logger logger = LoggerFactory.getLogger(BibliotecaService.class);

// metodo que obtiene todos los grupos de investigacion creados
	@Override
	public List<GrupoInvestigacion> todosLosGruposInvestigacion() {
		List<GrupoInvestigacion> grupoInvestigacion = grupoIRepo.findAll();
		return grupoInvestigacion;
	}

//metodo para eliminar un grupo de investigacion
	@Override
	public boolean eliminarGrupoInvestigacion(int id) {
		JSONObject programa = grupoIRepo.findByPrograma(id);
		JSONObject linea = grupoIRepo.findByLinea(id);
		boolean grupo = grupoIRepo.existsById(id);
		if (programa == null && linea == null && grupo == true) {
			grupoIRepo.deleteById(id);
			return true;
		}

		return false;
	}

	@Override
//metodo para crear un grupo de investigacion teniendo en cuenta los diferentes roles que no pueden tener el rol de lider
	public String crearGrupoInvestigacion(GrupoInvestigacion grupoInvestigacion, String director) {

		Usuario dir = usuarioRepo.getById(director);

		TipoUsuario tipousuario = iTipoUsuarioRepository.getById("Lider grupo investigacion");

		if (!usuarioRepo.existsById(dir.getCedula())) {
			return "el usuario no existe";
		}
		List<TipoUsuario> tipo = dir.getTiposUsuario();
		int i = 0;
		// validacion de roles que no pueden tener el rol de lider
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
				return "usuario invalido 1";
			}
			if (tipoUsuario.getNombre().equals("Estudiante activo")) {
				return "usuario invalido 2";
			}
			if (tipoUsuario.getNombre().equals("Semillerista")) {
				return "usuario invalido 7";
			}
		}
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
			if (tipoUsuario2.getNombre().equals("Lider grupo investigacion")) {
				i = i + 1;

			}
		}

		if (i > 0) {
			} else {
			tipousuario.getUsuarios().add(dir);
			iTipoUsuarioRepository.save(tipousuario);
		}

		grupoInvestigacion.setDirectorGrupo(dir);
		grupoIRepo.save(grupoInvestigacion);

		return "se creo el grupo";
	}

	// metodo para modificar un grupo en especifico
	@Override
	public String modificarGrupoI(int id, String nombre, String fecha_fun, String categoria, String fecha_cat,
			String director) {

		Usuario direc = usuarioRepo.getById(director);

		TipoUsuario tipousuariouno = iTipoUsuarioRepository.getById("Lider grupo investigacion");

		GrupoInvestigacion grupo = grupoIRepo.getById(id);

		if (nombre != "") {
			grupo.setNombre(nombre);
		}
		if (fecha_fun != "") {
			grupo.setFechaFun(LocalDate.parse(fecha_fun));
		}
		if (categoria != "") {
			grupo.setCategoria(categoria);
		}
		if (fecha_cat != "") {
			grupo.setFechaCat(LocalDate.parse(fecha_cat));
		}
		if (director != "") {
			usuarioRepo.deleteTipo(grupo.getDirectorGrupo().getCedula(), "Lider grupo investigacion");

			List<TipoUsuario> tipo = direc.getTiposUsuario();
			for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
				if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
					return "usuario invalido 1";
				}
				if (tipoUsuario.getNombre().equals("Estudiante activo")) {
					return "usuario invalido 2";
				}
				if (tipoUsuario.getNombre().equals("Semillerista")) {
					return "usuario invalido 3";
				}

			}
			int i = 0;
			for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
				if (tipoUsuario2.getNombre().equals("Lider grupo investigacion")) {
					i = i + 1;

				}
			}

			if (i > 0) {
			} else {
				tipousuariouno.getUsuarios().add(direc);
				iTipoUsuarioRepository.save(tipousuariouno);
			}
			grupo.setDirectorGrupo(direc);
		}
		grupoIRepo.save(grupo);
		return "grupo actualizado";
	}

	// metodo para obtener los datos de un grupo de investigacion
	@Override
	public GrupoInvestigacion grupoiporid(int id) {
		if (grupoIRepo.existsById(id)) {
			GrupoInvestigacion grupo = grupoIRepo.getById(id);
			return grupo;
		}
		return null;

	}

	// metodo para asignar un programa aun un grupo de investigacion
	@Override
	public boolean asignarProgramaAGrupoInvestigacion(int programa, int grupo_investigacion) {
		if (!grupoIRepo.existsById(grupo_investigacion)) {
			return false;
		}
		if (!programaRepo.existsById(programa)) {
			return false;
		}
		Programa pro = programaRepo.getById(programa);
		GrupoInvestigacion grupoInvestigacion = grupoIRepo.getById(grupo_investigacion);
		// grupoInvestigacion.setProgramas(new ArrayList<>());
		grupoInvestigacion.getProgramas().add(pro);
		grupoIRepo.save(grupoInvestigacion);
		return true;
	}

	// metodo para des-asignar el programa que tiene un grupo de investigacion
	@Override
	public boolean desasignarProgramaAGrupoInvestigacion(int programa, int grupo_investigacion) {
		if (!grupoIRepo.existsById(grupo_investigacion)) {
			return false;
		}
		if (!programaRepo.existsById(programa)) {
			return false;
		}
		grupoIRepo.desAsignarPrograma(programa + "", grupo_investigacion + "");
		return true;
	}

	// metodo para listar el programa al que pertenece el grupo de investigacion
	@Override
	public List<Programa> programaDelGrupo(int grupo_investigacion) {
		GrupoInvestigacion grupo = grupoIRepo.getById(grupo_investigacion);
		List<Programa> programas = grupo.getProgramas();
		if (programas == null) {
			programas = new ArrayList<Programa>();
		}
		return programas;
	}

//metodo para asignar una linea de investigacion al grupo de investigacion
	@Override
	public boolean asignarLineaAGrupoInvestigacion(String linea_investigacion, int grupo_investigacion) {
		if (!grupoIRepo.existsById(grupo_investigacion)) {
			return false;
		}
		if (!lineaRepo.existsById(linea_investigacion)) {
				return false;
		}
		LineaInvestigacion linea = lineaRepo.getById(linea_investigacion);
		GrupoInvestigacion grupoInvestigacion = grupoIRepo.getById(grupo_investigacion);
		// grupoInvestigacion.setLineasInvestigacion(new ArrayList<>());
		grupoInvestigacion.getLineasInvestigacion().add(linea);
		grupoIRepo.save(grupoInvestigacion);
		return true;
	}

	// metodo para obtener la linea de investigacion del grupo de investigacion
	@Override
	public List<LineaInvestigacion> lineaDelGrupo(int grupo_investigacion) {
		GrupoInvestigacion grupo = grupoIRepo.getById(grupo_investigacion);
		List<LineaInvestigacion> lineas = grupo.getLineasInvestigacion();

		if (lineas == null) {
			lineas = new ArrayList<LineaInvestigacion>();
		}
		return lineas;

	}

	// metodo para des-asignar la linea de investigacion del grupo de investigacion
	@Override
	public boolean desasignarLineaAGrupoInvestigacion(String linea_investigacion, int grupo_investigacion) {
		if (!grupoIRepo.existsById(grupo_investigacion)) {
				return false;
		}
		if (!lineaRepo.existsById(linea_investigacion)) {
			return false;
		}
			grupoIRepo.desAsignarLinea(linea_investigacion, grupo_investigacion + "");

		return true;
	}

	// semilleros----------------------------------------------------------------------------------------------------------------------------------------------------

	// metodo para obtener todos los semilleros creados
	@Override
	public List<Semillero> todosLosSemilleros() {
		List<Semillero> semilleros = semilleroRepo.findAll();
		return semilleros;
	}

//metodo para obtener los semilleros de un grupo de investigacion en especifico
	@Override
	public List<Semillero> todosLosSemillerosPorGrupoInvestigacion(int grupoInvestigacion) {
		GrupoInvestigacion sem = grupoIRepo.getById(grupoInvestigacion);
		List<Semillero> x = new ArrayList<>();
		if (sem != null) {
			List<Semillero> semillero = semilleroRepo.findByGrupoInvestigacion(grupoInvestigacion);
			return semillero;
		} else {
			return x;
		}
	}

//metodo para obtener todos los semilleros de un lider en especifico
	@Override
	public List<Semillero> todosLosSemillerosPorLiderSemillero(String lider) {
		Usuario usu = usuarioRepo.getById(lider);
		List<Semillero> x = new ArrayList<>();
		if (usu != null) {
			List<Semillero> semillero = semilleroRepo.findByLiderSemillero(lider);
			return semillero;
		} else {
			logger.info("El tipo no existe");
			return x;
		}
	}

//metodo para obtener los semilleros que esten asignados a una linea de investigacion en especifico
	@Override
	public List<Semillero> todosLosSemillerosPorLineaInvestigacion(String lineaInvestigacion) {
		LineaInvestigacion linea = lineaRepo.getById(lineaInvestigacion);
		List<Semillero> x = new ArrayList<>();
		if (linea != null) {
			List<Semillero> semillero = semilleroRepo.findByLineaInvestigacion(lineaInvestigacion);
			return semillero;
		} else {
			logger.info("El tipo no existe");
			return x;
		}
	}

//metodo para eliminar un semillero
	@Override
	public boolean eliminarSemillero(int id) {
		List<JSONObject> programa = semilleroRepo.findByPrograma(id);
		boolean semillero = semilleroRepo.existsById(id);
		if (programa.isEmpty() && semillero == true) {
			semilleroRepo.deleteById(id);
			return !semilleroRepo.existsById(id);
		}
		return false;

	}

//metodo para crear un semillero en donde se tiene en cuenta los roles que no pueden ser lideres de semillero
	@Override
	public String crearSemillero(Semillero semillero, int grupo, String lider, String linea) {
		Usuario lid = usuarioRepo.getById(lider);

		TipoUsuario tipousuario = iTipoUsuarioRepository.getById("Docente lider semillero");

		GrupoInvestigacion gru = grupoIRepo.getById(grupo);
		LineaInvestigacion lin = lineaRepo.getById(linea);

		if (!usuarioRepo.existsById(lid.getCedula())) {
			return "el usuario no existe";
		}
		if (!grupoIRepo.existsById(gru.getId())) {
			return "el grupo no existe";
		}
		if (!lineaRepo.existsById(lin.getNombre())) {
			return "la linea no existe";
		}
		// seleccion de roles que no pueden ser lideres de semillero
		List<TipoUsuario> tipo = lid.getTiposUsuario();
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
				return "usuario invalido 1";
			}
			if (tipoUsuario.getNombre().equals("Estudiante activo")) {
				return "usuario invalido 2";
			}
			if (tipoUsuario.getNombre().equals("Semillerista")) {
				return "usuario invalido 3";
			}

		}
		int i = 0;
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
			if (tipoUsuario2.getNombre().equals("Docente lider semillero")) {
				i = i + 1;

			}
		}

		if (i > 0) {
		} else {
			tipousuario.getUsuarios().add(lid);
			iTipoUsuarioRepository.save(tipousuario);
		}
		lid.setSemilleroId(semillero);
		semillero.setLiderSemillero(lid);
		semillero.setGrupoInvestigacion(gru);
		semillero.setLineaInvestigacion(lin);
		semilleroRepo.save(semillero);
		return "se creo el semillero";
	}

	// metodo para modificar un semillero creado anteriormente teniendo en cuenta
	// las validaciones de los roles que no pueden ser lideres
	@Override
	public String modificarSemillero(int id, String nombre, String descripcion, String fechaFun, String grupoi,
			String lineai, String liderSemillero) {

		Usuario lider = usuarioRepo.getById(liderSemillero);

		TipoUsuario tipousuariouno = iTipoUsuarioRepository.getById("Docente lider semillero");

		Semillero semillero = semilleroRepo.getById(id);
		GrupoInvestigacion gru = grupoIRepo.getById(Integer.parseInt(grupoi));
		LineaInvestigacion lin = lineaRepo.getById(lineai);

		if (nombre != "") {
			semillero.setNombre(nombre);
		}
		if (descripcion != "") {
			semillero.setDescripcion(descripcion);
		}
		if (fechaFun != "") {
			semillero.setFechaFun(LocalDate.parse(fechaFun));
			;
		}
		if (grupoi != "") {
			semillero.setGrupoInvestigacion(gru);
		}
		if (lineai != "") {
			semillero.setLineaInvestigacion(lin);
		}
		if (liderSemillero != "") {
			usuarioRepo.deleteTipo(semillero.getLiderSemillero().getCedula(), "Docente lider semillero");
			// seleccion de roles que no pueden ser lideres de semillero
			List<TipoUsuario> tipo = lider.getTiposUsuario();
			for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
				if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
					return "usuario invalido 1";
				}
				if (tipoUsuario.getNombre().equals("Estudiante activo")) {
					return "usuario invalido 2";
				}
				if (tipoUsuario.getNombre().equals("Semillerista")) {
					return "usuario invalido 3";
				}

			}
			int i = 0;
			for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
				if (tipoUsuario2.getNombre().equals("Docente lider semillero")) {
					i = i + 1;

				}
			}

			if (i > 0) {
				} else {
					tipousuariouno.getUsuarios().add(lider);
				iTipoUsuarioRepository.save(tipousuariouno);
			}

			semillero.setLiderSemillero(lider);
		}

		semilleroRepo.save(semillero);
		return "semillero actualizado";
	}

	// metodo para obtener informacion de un semillero en especifico
	@Override
	public Semillero semilleroporid(int id) {
		if (semilleroRepo.existsById(id)) {
			JSONObject salida = new JSONObject();
			Semillero semillero = semilleroRepo.getById(id);
			LineaInvestigacion li = lineaRepo.getById(semillero.getLineaInvestigacion().getNombre());

			salida = li.toJson();

			return semillero;
		}
		return null;

	}

// metodo para asignar un semillero a un programa en especifico
	@Override
	public boolean asignarSemilleroAPrograma(int programa, int semillero) {
		if (!semilleroRepo.existsById(semillero)) {
			return false;
		}
		if (!programaRepo.existsById(programa)) {
			return false;
		}
		Programa pro = programaRepo.getById(programa);
		Semillero semi = semilleroRepo.getById(semillero);
		pro.setSemilleros(new ArrayList<>());
		pro.getSemilleros().add(semi);
		programaRepo.save(pro);
		return true;
	}

//metodo para des-asignar el programa a un semillero en especifico
	@Override
	public boolean desasignarSemilleroAPrograma(int programa, int semillero) {
		if (!semilleroRepo.existsById(semillero)) {
			return false;
		}
		if (!programaRepo.existsById(programa)) {
			return false;
		}
		semilleroRepo.desAsignarPrograma(programa + "", semillero + "");

		return true;
	}

	// metodo para obtener el programa de un semillero en especifico
	@Override
	public List<Programa> programaDelSemillero(int semillero) {
		Semillero semi = semilleroRepo.getById(semillero);
		List<Programa> programas = semi.getProgramas();
		if (programas.equals(null)) {
			programas = new ArrayList<Programa>();
		}
		return programas;
	}

	// metodo para asignar un usuario a un semillero en especifico
	@Override
	public boolean asignarSemilleroAUsuario(String cedula, int semillero) {
		Usuario usu = usuarioRepo.getById(cedula);
		Semillero semi = semilleroRepo.getById(semillero);
		TipoUsuario tipo2 = iTipoUsuarioRepository.getById("Semillerista");
		int i = 0;
		List<TipoUsuario> tipo = usu.getTiposUsuario();
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if (tipoUsuario.getNombre().contains("Semillerista")) {
				i = i + 1;
			}
		}
		usu.setSemilleroId(semi);
		if (i > 0) {
		} else {
			tipo2.getUsuarios().add(usu);
			iTipoUsuarioRepository.save(tipo2);
		}

		usuarioRepo.save(usu);
		return usuarioRepo.existsById(usu.getCedula());
	}

	// metodo para desasignar un usuario de un semillero en especifico
	@Override
	public boolean desasignarSemilleroAUsuario(String cedula) {
		Usuario usu = usuarioRepo.getById(cedula);
		semilleroRepo.setSemilleroNullById(cedula);
		return usuarioRepo.existsById(usu.getCedula());
	}

	// metodo para contar los semilleros que fueron creados en la plataforma
	@Override
	public List<JSONObject> contarSemilleros() {
		List<JSONObject> semilleros = semilleroRepo.contarSemilleros();
		return semilleros;
	}

	// facultades-------------------------------------------------------------------------------------------------------------------------------------------------------------

	// metodo para obtener todas las facultades
	@Override
	public List<Facultad> todasLasFacultades() {
		List<Facultad> facultades = facultadRepo.findAll();
		if (facultades.equals(null)) {
			facultades = new ArrayList<Facultad>();
		}

		return facultades;
	}

//metodo para eliminar una facultad previamente creada
	@Override
	public boolean eliminarFacultad(int id) {
		List<Programa> programas = programaRepo.findByFacultad(id);
		boolean facultad = facultadRepo.existsById(id);
		if (facultad == true && programas.isEmpty()) {
			facultadRepo.deleteById(id);
			return !facultadRepo.existsById(id);

		}
		return false;
	}

//metodo para crear una facultad teniendo en cuenta los roles que no pueden tener el rol de decano o coordinador
	@Override

	public String crearFacultad(Facultad facultad, String coordinador, String decano) {
		Usuario deca = usuarioRepo.getById(decano);
		Usuario coor = usuarioRepo.getById(coordinador);

		TipoUsuario tipousuariouno = iTipoUsuarioRepository.getById("Lider investigacion facultad");
		TipoUsuario tipousuariodos = iTipoUsuarioRepository.getById("Coordinador investigacion facultad");

		if (!usuarioRepo.existsById(deca.getCedula())) {
			return "el decano no existe";
		}
		if (!usuarioRepo.existsById(coor.getCedula())) {
			return "el coordinador no existe";
		}
		// seleccion de roles que no pueden ser decano o coordinador
		List<TipoUsuario> tipouno = deca.getTiposUsuario();
		for (Iterator iterator = tipouno.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
				return "usuario invalido 1";
			}
			if (tipoUsuario.getNombre().equals("Estudiante activo")) {
				return "usuario invalido 2";
			}
			if (tipoUsuario.getNombre().equals("Semillerista")) {
				return "usuario invalido 3";
			}
		}
		int i = 0;
		for (Iterator iterator = tipouno.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if (tipoUsuario.getNombre().equals("Lider investigacion facultad")) {
				i = i + 1;

			}
		}
		if (i > 0) {
		} else {
			tipousuariouno.getUsuarios().add(deca);
			iTipoUsuarioRepository.save(tipousuariouno);
		}

		// seleccion de roles que no pueden ser decano o coordinador
		List<TipoUsuario> tipodos = coor.getTiposUsuario();
		for (Iterator iterator = tipodos.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
				return "usuario invalido 4";
			}
			if (tipoUsuario.getNombre().equals("Estudiante activo")) {
				return "usuario invalido 5";
			}
			if (tipoUsuario.getNombre().equals("Semillerista")) {
				return "usuario invalido 6";
			}
		}
		int j = 0;
		for (Iterator iterator = tipodos.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if (tipoUsuario.getNombre().equals("Coordinador investigacion facultad")) {
				j = j + 1;

			}
		}
		if (j > 0) {
		} else {
				tipousuariodos.getUsuarios().add(coor);
			iTipoUsuarioRepository.save(tipousuariodos);
		}

		facultad.setCoorInv(coor);
		facultad.setDecano(deca);
		facultadRepo.save(facultad);

		return "se creo la facultad";
	}

//metodo para modificar una facultad creada previamente teniendo enc uenta los roles que no pueden ser coordinador o decano
	@Override
	public String modificarFacultad(int id, String nombre, String coordinador, String decano) {

		Usuario deca = usuarioRepo.getById(decano);
		Usuario coor = usuarioRepo.getById(coordinador);

		TipoUsuario tipousuariouno = iTipoUsuarioRepository.getById("Lider investigacion facultad");
		TipoUsuario tipousuariodos = iTipoUsuarioRepository.getById("Coordinador investigacion facultad");

		Facultad facultad = facultadRepo.getById(id);

		if (!nombre.equals("")) {
			facultad.setNombre(nombre);
		}
		// seleccion de roles que no pueden ser decano o coordinador
		if (!decano.equals("")) {
			usuarioRepo.deleteTipo(facultad.getDecano().getCedula(), "Lider investigacion facultad");

			List<TipoUsuario> tipouno = deca.getTiposUsuario();
			for (Iterator iterator = tipouno.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
				if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
					return "usuario invalido 1";
				}
				if (tipoUsuario.getNombre().equals("Estudiante activo")) {
					return "usuario invalido 2";
				}
				if (tipoUsuario.getNombre().equals("Semillerista")) {
					return "usuario invalido 3";
				}
			}
			int i = 0;
			for (Iterator iterator = tipouno.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
				if (tipoUsuario2.getNombre().equals("Lider investigacion facultad")) {
					i = i + 1;

				}
			}

			if (i > 0) {
			} else {
					tipousuariouno.getUsuarios().add(deca);
				iTipoUsuarioRepository.save(tipousuariouno);
			}
			facultad.setDecano(deca);
		}
		// seleccion de roles que no pueden ser decano o coordinador
		if (!coordinador.equals("")) {
			usuarioRepo.deleteTipo(facultad.getCoorInv().getCedula(), "Coordinador investigacion facultad");

			List<TipoUsuario> tipodos = coor.getTiposUsuario();
			for (Iterator iterator = tipodos.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
				if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
					return "usuario invalido 4";
				}
				if (tipoUsuario.getNombre().equals("Estudiante activo")) {
					return "usuario invalido 5";
				}
				if (tipoUsuario.getNombre().equals("Semillerista")) {
					return "usuario invalido 6";
				}
			}

			int i = 0;
			for (Iterator iterator = tipodos.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
				if (tipoUsuario2.getNombre().equals("Coordinador investigacion facultad")) {
					i = i + 1;

				}
			}

			if (i > 0) {
			} else {
				tipousuariodos.getUsuarios().add(coor);
				iTipoUsuarioRepository.save(tipousuariouno);
			}
			facultad.setCoorInv(coor);
		}

		facultadRepo.save(facultad);
		return "facultad actualizada";
	}

	// metodo para obtener la informacion de una facultad en especifico
	@Override
	public Facultad facultadporid(int id) {
		if (facultadRepo.existsById(id)) {
			Facultad facultad = facultadRepo.getById(id);

			return facultad;
		}
		return null;
	}

	// metodo para listar los programas que estan creados en el aplicativo
	@Override
	public List<Programa> todosLosProgramas() {
		List<Programa> programas = programaRepo.findAll();

		return programas;
	}

	// metodo para obtener los usuarios de un programa
	@Override
	public List<Usuario> UsuariosPrograma(int id) {

		Programa pro = programaRepo.getById(id);
		List<Usuario> usuarios = pro.getUsuarios();
		return usuarios;
	}

//metodo para obtener los programas de la facultad
	@Override
	public List<Programa> todosLosProgramasPorFacultad(int facultad) {
		List<Programa> programas = programaRepo.findByFacultad(facultad);
		return programas;
	}

	// Programas-----------------------------------------------------------------------------------------------------------------------------------------------------
// metodo para obtener el programa del director
	@Override
	public List<Programa> todosLosProgramasPorDirector(String usuario) {

		if (existeUsuario(usuario)) {
			List<Programa> programas = programaRepo.findByDirector(usuario);
			return programas;
		} else {
			return null;
		}

	}

//metodo para eliminar un programa
	@Override
	public boolean eliminarPrograma(int id) {

		List<JSONObject> grupo = programaRepo.findByGrupo(id);
		List<JSONObject> semillero = programaRepo.findBySemillero(id);
		List<JSONObject> usuario = programaRepo.findByUsuario(id);
		boolean programa = programaRepo.existsById(id);
			if (grupo.isEmpty() && semillero.isEmpty() && usuario.isEmpty() && programa == true) {
			programaRepo.deleteById(id);
			return true;
		}

		return false;

	}

	// metodo para crear un programa teniendo en cuenta los roles que no pueden ser
	// directores
	@Override
	public String crearPrograma(Programa programa, int facultad, String director) {

		Usuario direc = usuarioRepo.getById(director);

		TipoUsuario tipousuario = iTipoUsuarioRepository.getById("Director programa");

		Facultad facul = facultadRepo.getById(facultad);

		if (!facultadRepo.existsById(facul.getId())) {
			return "la facultad no existe";
		}
		if (!usuarioRepo.existsById(direc.getCedula())) {
			return "el usuario no existe";
		}
		// seleccion de roles que no pueden directores
		List<TipoUsuario> tipo = direc.getTiposUsuario();
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
				return "usuario invalido 1";
			}
			if (tipoUsuario.getNombre().equals("Estudiante activo")) {
				return "usuario invalido 2";
			}
			if (tipoUsuario.getNombre().equals("Semillerista")) {
				return "usuario invalido 3";
			}
		}
		int i = 0;
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
			if (tipoUsuario2.getNombre().equals("Director programa")) {
				i = i + 1;

			}
		}

		if (i > 0) {
		} else {
			tipousuario.getUsuarios().add(direc);
			iTipoUsuarioRepository.save(tipousuario);
		}

		programa.setFacultadId(facul);
		programa.setDirector(direc);
		programaRepo.save(programa);
		return "se creo el programa";
	}

	// metodo para modificar un programa teniendo en cuenta los roles que no pueden
	// ser directores
	@Override
	public String modificarPrograma(int id, String nombre, String facultad, String director) {

		Usuario direc = usuarioRepo.getById(director);

		TipoUsuario tipousuariouno = iTipoUsuarioRepository.getById("Director programa");

		Programa programa = programaRepo.getById(id);
		Facultad facul = facultadRepo.getById(Integer.parseInt(facultad));

		if (!nombre.equals("")) {
			programa.setNombre(nombre);
		}
		if (!facultad.equals("")) {
			programa.setFacultadId(facul);
		}
		if (!director.equals("")) {
			usuarioRepo.deleteTipo(programa.getDirector().getCedula(), "Director programa");
			// seleccion de roles que no pueden directores
			List<TipoUsuario> tipo = direc.getTiposUsuario();
			for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
				if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
					return "usuario invalido 1";
				}
				if (tipoUsuario.getNombre().equals("Estudiante activo")) {
					return "usuario invalido 2";
				}
				if (tipoUsuario.getNombre().equals("Semillerista")) {
					return "usuario invalido 3";
				}
			}
			int i = 0;
			for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
				if (tipoUsuario2.getNombre().equals("Director programa")) {
					i = i + 1;

				}
			}

			if (i > 0) {
				} else {
				tipousuariouno.getUsuarios().add(direc);
				iTipoUsuarioRepository.save(tipousuariouno);
			}

			programa.setDirector(direc);
		}

		programaRepo.save(programa);
		return "se actualizo el programa";
	}

	// metodo para obtener los datos de un programa
	@Override
	public Programa programaporid(int id) {

		Programa programa = programaRepo.getById(id);

		return programa;

	}

	// metodo para obtener los grupos de investigacion de un programa en especifico
	@Override
	public List<GrupoInvestigacion> gruposDelPrograma(int programa) {
		Programa pro = programaRepo.getById(programa);
		List<GrupoInvestigacion> grupos = pro.getGruposInvestigacion();
		if (grupos.equals(null)) {
			grupos = new ArrayList<GrupoInvestigacion>();
		}
		return grupos;
	}

//metodo para obtener los semilleros que se encuntran en un programa en especifico
	@Override
	public List<Semillero> semillerosDelPrograma(int programa) {
		Programa pro = programaRepo.getById(programa);
		List<Semillero> semilleros = pro.getSemilleros();
		if (semilleros.equals(null)) {
			semilleros = new ArrayList<Semillero>();
		}
		return semilleros;
	}

	// MATERIAS-------------------------------------------------------------------------------------------------------------------------------------------------------
	// metodo para obtener todas las materias creadas en el aplicativo
	@Override
	public List<Materia> todasLasMaterias() {
		List<Materia> materias = materiaRepo.findAll();

		return materias;
	}

//metodo para obtener las materias de un programa
	@Override
	public List<Materia> todasLasMateriasPorPrograma(int programa) {
		List<Materia> materia = materiaRepo.findByPrograma(programa);
		return materia;
	}

//metodo para eliminar una materia
	@Override
	public boolean eliminarMateria(String catalogo) {
		List<JSONObject> clase = materiaRepo.findByClase(catalogo);
		boolean materia = materiaRepo.existsById(catalogo);
		if (clase.isEmpty() && materia == true) {
			materiaRepo.deleteById(catalogo);
			return true;
		}
		return false;
	}

//metodo para crear una materia
	@Override
	public boolean crearMateria(Materia materia, int programa) {
		Programa pro = programaRepo.getById(programa);
		if (!programaRepo.existsById(pro.getId())) {
			return false;
		}
		materia.setPrograma(pro);
		materiaRepo.save(materia);
		return materiaRepo.existsById(materia.getCatalogo());
	}

	// metodo para modificar una materia previamente creada
	@Override
	public boolean modificarMateria(String catalogo, String nombre, String programa) {
		Programa pro = programaRepo.getById(Integer.parseInt(programa));

		Materia materia = materiaRepo.getById(catalogo);

		if (!catalogo.equals("")) {
			materia.setCatalogo(catalogo);
		}

		if (!nombre.equals("")) {
			materia.setNombre(nombre);
		}

		if (!programa.equals("")) {
			materia.setPrograma(pro);
		}
		materiaRepo.save(materia);
		return !materiaRepo.existsById(catalogo);
	}

//metodo para obtener la informacion de una materia en especifico
	@Override
	public Materia materiaporid(String catalogo) {

		Materia materia = materiaRepo.getById(catalogo);
		return materia;
	}

	// CLASES-----------------------------------------------------------------------------------------------------------------------------------------------------------
	// metodo para obtener las clases creadas en el aplicativo
	@Override
	public List<Clase> todasLasClases() {

		List<Clase> clases = claseRepo.findAll();
		return clases;
	}

//metodo para obtener todas las clases de un profesor
	@Override
	public List<Clase> clasesPorProfesor(String profesor) {
		if (existeUsuario(profesor)) {
			List<Clase> clases = claseRepo.findByProfesor(profesor);
			return clases;
		} else {
			return null;
		}

	}

//metodo para obtener las clases de una materia
	@Override
	public List<Clase> clasesPorMateria(String materia) {

		if (materiaRepo.findById(materia) != null) {
			List<Clase> clases = claseRepo.findByMateria(materia);
			return clases;
		} else {
			return null;
		}
	}

//metodo para eliminar una clase
	@Override
	public boolean eliminarClase(int numero) {
		List<JSONObject> profesor = claseRepo.findByProyecto(numero);
		boolean clase = claseRepo.existsById(numero);
		if (profesor.isEmpty() && clase == true) {
			claseRepo.deleteById(numero);
			return true;
		}
		return false;
	}

	// metodo para crear una clase teniendo en cuenta los roles que no pueden ser
	// profesores
	@Override
	public String crearClase(Clase clase, String materia, String profesor) {
		Usuario profe = usuarioRepo.getById(profesor);

		TipoUsuario tipousuario = iTipoUsuarioRepository.getById("Docente");

		Materia mate = materiaRepo.getById(materia);
		if (!usuarioRepo.existsById(profe.getCedula())) {
			return "el usuario no existe";
		}
		if (!materiaRepo.existsById(mate.getCatalogo())) {
			return "la materia no existe";
		}
		if (claseRepo.existsById(clase.getNumero())) {
			return "la clase ya existe";
		}
		// seleccion de roles que no puede ser un profesor
		List<TipoUsuario> tipo = profe.getTiposUsuario();
		String rol = "";
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
			if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
				rol = "Estudiante inactivo";
			}
			if (tipoUsuario.getNombre().equals("Estudiante activo")) {
				rol = "Estudiante activo";
			}
			if (tipoUsuario.getNombre().equals("Semillerista")) {
				rol = "Semillerista";
			}

		}
		int i = 0;
		for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
			TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
			if (tipoUsuario2.getNombre().equals("Docente")) {
				i = i + 1;

			}
		}

		if (i > 0) {
		} else {
			tipousuario.getUsuarios().add(profe);
			iTipoUsuarioRepository.save(tipousuario);
		}

		if (rol.equals("Estudiante inactivo")) {
			return "esta persona es usuario inactivo";
		} else if (rol.equals("Estudiante activo")) {
			return "esta persona es usuario activo";
		} else if (rol.equals("Semillerista")) {
			return "esta persona es usuario semillerista";
		} else {

			clase.setMateria(mate);
			clase.setProfesor(profe);
			claseRepo.save(clase);

			return "se creo la clase";
		}

	}

	// metodo para mosdificar la clase teniendo en cuenta los roles que no pueden
	// ser profesores
	public String modificarClase(int numero, String nombre, String semestre, String materia, String profesor) {

		Usuario profe = usuarioRepo.getById(profesor);

		TipoUsuario tipousuariouno = iTipoUsuarioRepository.getById("Docente");

		Clase clase = claseRepo.getById(numero);
		Materia mate = materiaRepo.getById(materia);

		if (!nombre.equals("")) {
			clase.setNombre(nombre);
		}

		if (!semestre.equals("")) {
			clase.setSemestre(semestre);
		}

		if (!materia.equals("")) {
			clase.setMateria(mate);
		}

		if (!profesor.equals("")) {
			// seleccion de roles que no pueden ser profesores
			List<TipoUsuario> tipo = profe.getTiposUsuario();
			for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario = (TipoUsuario) iterator.next();
				if (tipoUsuario.getNombre().equals("Estudiante inactivo")) {
					return "usuario invalido 1";
				}
				if (tipoUsuario.getNombre().equals("Estudiante activo")) {
					return "usuario invalido 2";
				}
				if (tipoUsuario.getNombre().equals("Semillerista")) {
					return "usuario invalido 3";
				}

			}

			int i = 0;
			for (Iterator iterator = tipo.iterator(); iterator.hasNext();) {
				TipoUsuario tipoUsuario2 = (TipoUsuario) iterator.next();
				if (tipoUsuario2.getNombre().equals("Docente")) {
					i = i + 1;

				}
			}

			if (i > 0) {
			} else {
				tipousuariouno.getUsuarios().add(profe);
				iTipoUsuarioRepository.save(tipousuariouno);
			}
			clase.setProfesor(profe);
		}
		claseRepo.save(clase);
		return "clase actualizada";

	}

	// metodo para obtener toda la infromacion de una clase
	@Override
	public Clase claseporid(int id) {
		Clase clase = claseRepo.getById(id);

		return clase;

	}

	// metodo para signar proyectos a una clase en especifico
	@Override
	public boolean asignarProyectosAClase(int proyecto, int clase) {
		if (!proyectoRepo.existsById(proyecto)) {
			return false;
		}
		if (!claseRepo.existsById(clase)) {
			return false;
		}
		Proyecto pro = proyectoRepo.getById(proyecto);
		Clase cla = claseRepo.getById(clase);
		cla.setProyectos(new ArrayList<>());
		cla.getProyectos().add(pro);
		claseRepo.save(cla);
		pro.setClases(new ArrayList<>());
		pro.getClases().add(cla);
		proyectoRepo.save(pro);
		return true;
	}

//metodo para obtener los proyectos por una clase en especifico
	@Override
	public List<Proyecto> proyectosPorClase(int clase) {
		Clase cla = claseRepo.getById(clase);
		List<Proyecto> proyectos = cla.getProyectos();
		if (proyectos.equals(null)) {
			proyectos = new ArrayList<Proyecto>();
		}
		return proyectos;
	}

	// metodo para de asignar proyectos a una clase en especifico
	@Override
	public boolean desasignarProyectosAClase(int proyecto, int clase) {

		if (!claseRepo.existsById(clase)) {
			return false;
		}
		if (!proyectoRepo.existsById(proyecto)) {
			return false;
		}
		claseRepo.desAsignarProyecto(clase + "", proyecto + "");
		return true;
	}

	// metodo que obtiene todas las convocatorias que se encuentrene en estado
	// abierto
	@Override
	public List<Convocatoria> todasLasConvocatoriasAbiertas(String estado) {
		List<Convocatoria> con = convocatoriaRepo.findByEstadoAbierto(estado);
		return con;
	}

	// metodo que obtiene la informacion de una convocatoria en especifico
	@Override
	public Convocatoria convocatoriaPorID(int id) {
		if (convocatoriaRepo.existsById(id)) {
			Convocatoria convocatoria = convocatoriaRepo.getById(id);
			return convocatoria;
		}
		return null;
	}

	// lineas-----------------------------------------------------------------------------------------------------------------------------------------------------------
	// metodo que obtiene todas las lineas creadas en el aplicativo
	@Override
	public List<LineaInvestigacion> todasLasLineas() {
		List<LineaInvestigacion> lineas = lineaRepo.findAll();

		return lineas;
	}

	// metodo para crear una linea
	@Override
	public boolean crearLinea(LineaInvestigacion linea, LocalDate fecha) {
		linea.setFecha(fecha);
		lineaRepo.save(linea);
		return lineaRepo.existsById(linea.getNombre());
	}

	// metodo para modificar una linea
	public boolean modificarLinea(String nombre, String descripcion, String fecha) {
		LineaInvestigacion linea = lineaRepo.getById(nombre);

		if (!descripcion.equals("")) {
			linea.setDescripcion(descripcion);
		}

		if (!fecha.equals("")) {
			linea.setFecha(LocalDate.parse(fecha));
		}
		lineaRepo.save(linea);
		return !lineaRepo.existsById(linea.getNombre());
	}

	// metodo que obtiene la informacion de una linea especifica
	@Override
	public LineaInvestigacion lineaporid(String id) {
		LineaInvestigacion linea = lineaRepo.getById(id);
		return linea;
	}

//metodo para eliminar una linea previamente creada
	@Override
	public boolean eliminarLinea(String nombre) {
		List<Semillero> semilleros = semilleroRepo.findByLineaInvestigacion(nombre);
		List<JSONObject> grupos = lineaRepo.findByGrupoInvestigacion(nombre);
		boolean linea = lineaRepo.existsById(nombre);
		if (semilleros.isEmpty() && linea == true && grupos.isEmpty()) {
			lineaRepo.deleteById(nombre);
			return true;
		}
		return false;
	}

	// Areas de
	// conocimiento--------------------------------------------------------------------------------------------------------------------------------------------
	// metodo para obtener todas las areas de conocimiento
	@Override
	public List<AreaConocimiento> todasLasAreasConocimiento() {
		List<AreaConocimiento> areas = areaRepo.findAll();
		return areas;
	}

	// metodo para crear un area de conocimiento
	@Override
	public boolean crearArea(AreaConocimiento area, String gran_area) {

		area.setGranArea(gran_area);
		areaRepo.save(area);
		return true;
	}

	// metodo para actualizar una area de conocimiento
	@Override
	public boolean modificarArea(int id, String nombre, String gran_area, String descripcion) {
		AreaConocimiento area = areaRepo.getById(id);
		if (!nombre.equals("")) {
			area.setNombre(nombre);
		}

		if (!gran_area.equals("")) {
			area.setGranArea(gran_area);
		}

		if (!descripcion.equals("")) {
			area.setDescripcion(descripcion);
		}
		areaRepo.save(area);
		return !areaRepo.existsById(area.getId());
	}

	// metodo para obtener informacion de un area en especifico
	@Override
	public AreaConocimiento areaporid(int id) {
		AreaConocimiento area = areaRepo.getById(id);
		return area;
	}

	// metodo para eliminar un area
	@Override
	public boolean eliminarArea(int id) {
		List<JSONObject> proyectos = areaRepo.findByProyecto(id);
		boolean area = areaRepo.existsById(id);
		if (proyectos.isEmpty() && area == true) {
			areaRepo.deleteById(id);
			return true;
		}
		return false;
	}

	// EVENTOS-----------------------------------------------------------------------------------------------------------------------------------------------------------
	// metodo para listar todos los eventos creados en el aplicativo
	@Override
	public List<Evento> todosLosEventos() {
		List<Evento> eventos = eventoRepo.findAll();
		return eventos;
	}
	@Override
	public List<Evento> todosLosEventosInternos() {
		List<Evento> eventos = eventoRepo.listarEventoInternos();
		return eventos;
	}

	// metodo que cuenta los eventos creados en el aplicativo
	@Override
	public List<JSONObject> contarEventos() {
		List<JSONObject> eventos = eventoRepo.contarEventos();
		return eventos;
	}

	// metodo para crear un evento
	@Override
	public boolean crearEvento(Evento evento, String entidad, String sitio_web, String url_memoria) {
		evento.setEntidad(entidad);
		evento.setSitioWeb(sitio_web);
		evento.setUrlMemoria(url_memoria);
		eventoRepo.save(evento);
		return eventoRepo.existsById(evento.getId());
	}

//metodo para modificar un evento
	@Override
	public boolean modificarEvento(int id, String nombre, String fecha, String entidad, String estado, String sitio_web,
			String url_memoria) {
		Evento evento = eventoRepo.getById(id);
		if (!nombre.equals("")) {
			evento.setNombre(nombre);
		}
		if (!fecha.equals("")) {
			evento.setFecha(LocalDate.parse(fecha));
			;
		}
		if (!entidad.equals("")) {
			evento.setEntidad(entidad);
		}
		if (!estado.equals("")) {
			evento.setEstado(estado);
		}
		if (!sitio_web.equals("")) {
			evento.setSitioWeb(sitio_web);
		}
		if (!url_memoria.equals("")) {
			evento.setUrlMemoria(url_memoria);
		}
		eventoRepo.save(evento);
		return !eventoRepo.existsById(evento.getId());
	}

	// metodo para obtener la informacion de un evento en especifico
	@Override
	public Evento eventoporid(int id) {
		Evento evento = eventoRepo.getById(id);
		return evento;
	}

	// metodo para eliminar un evento
	@Override
	public boolean eliminarEvento(int id) {
		List<JSONObject> participaciones = eventoRepo.findByParticipaciones(id);
		boolean evento = eventoRepo.existsById(id);
		if (!participaciones.isEmpty() || evento == false) {
			
			return false;
		}
		eventoRepo.deleteById(id);
		return true;
	}

	// METODOS DE SI EXISTE
	@Override
	// metodo para validar si un usuario existe
	public boolean existeUsuario(String cedula) {
		if (usuarioRepo.findById(cedula).isPresent()) {
			return true;
		} else {
			return false;
		}
	}

//metodo para listar los usuarios por semillero
	@Override
	public List<Usuario> usuariosPorSemillero(int semillero) {
		Semillero semi = semilleroRepo.getById(semillero);
		List<Usuario> usuarios = semi.getUsuarios();
		if (usuarios.equals(null)) {
			usuarios = new ArrayList<Usuario>();
		}
		return usuarios;
	}

//metodo para listar los proyectos de un semillero
	@Override
	public List<Proyecto> proyectosPorSemillero(int semillero) {
		Semillero semi = semilleroRepo.getById(semillero);
		List<Proyecto> proyectos = semi.getProyectos();
		if (proyectos.equals(null)) {
			proyectos = new ArrayList<Proyecto>();
		}

		return proyectos;
	}

//metodo para listar los proyectos de una convocatoria
	@Override
	public List<ProyectosConvocatoria> proyectosPorConvocatoria(int convocatoria) {
		Convocatoria con = convocatoriaRepo.getById(convocatoria);
		List<ProyectosConvocatoria> proyectos = con.getProyectosConvocatorias();

		if (proyectos.equals(null)) {
			proyectos = new ArrayList<ProyectosConvocatoria>();
		}
		return proyectos;
	}

	@Override
	public List<Convocatoria> todasLasConvocatorias() {
		List<Convocatoria> convocatorias = convocatoriaRepo.findAll();
		return convocatorias;
	}

	// metodo para crear una convocatoria
	@Override
	public boolean crearConvocatoria(Convocatoria convocatoria, String numero_productos, String entidad) {
		convocatoria.setNumeroProductos(numero_productos);
		convocatoria.setEntidad(entidad);
		convocatoriaRepo.save(convocatoria);
		if (convocatoria.getFechaFinal().isBefore(convocatoria.getFechaInicio())) {
			return false;
		}
		return true;
	}

//metodo para modificar una convocatoria
	@Override
	public boolean modificarConvocatoria(int id, String nombre_convocatoria, String fecha_inicio, String fecha_final,
			String contexto, String numero_productos, String estado, String tipo, String entidad) {

		Convocatoria convocatoria = convocatoriaRepo.getById(id);
		if (!nombre_convocatoria.equals("")) {
			convocatoria.setNombreConvocatoria(nombre_convocatoria);
		}
		if (!fecha_inicio.equals("")) {
			convocatoria.setFechaInicio(LocalDate.parse(fecha_inicio));
		}
		if (!fecha_final.equals("")) {
			convocatoria.setFechaFinal(LocalDate.parse(fecha_final));
		}
		if (!contexto.equals("")) {
			convocatoria.setContexto(contexto);
		}
		if (!numero_productos.equals("")) {
			convocatoria.setNumeroProductos(numero_productos);
		}
		if (!estado.equals("")) {
			convocatoria.setEstado(estado);
		}
		if (!tipo.equals("")) {
			convocatoria.setTipo(tipo);
		}
		if (!entidad.equals("")) {
			convocatoria.setEntidad(entidad);
		}
		convocatoriaRepo.save(convocatoria);
		return convocatoriaRepo.existsById(convocatoria.getId());
	}

	// metodo para obtener la informacion de una convocatoria
	@Override
	public Convocatoria convocatoriaporid(int id) {
		Convocatoria convocatoria = convocatoriaRepo.getById(id);

		return convocatoria;
	}

	// metodo para eliminar una convocatoria
	@Override
	public boolean eliminarConvocatoria(int id) {
		boolean convo = convocatoriaRepo.existsById(id);
		if (convo == true) {
			convocatoriaRepo.deleteById(id);
			;
			return true;
		}
		return false;
	}

//metodo para crear una linea de investigacion
	@Override
	public boolean crearLinea2(String nombre, String descripcion, LocalDate fecha) {
		LineaInvestigacion linea = new LineaInvestigacion();
		linea.setNombre(nombre);
		linea.setDescripcion(descripcion);

		lineaRepo.save(linea);
		return lineaRepo.existsById(nombre);
	}

//metodo de proyectos postulados a convocatorias 
	@Override
	public List<JSONObject> ProyectosPostuladosConvocatorias(String estado) {
		List<JSONObject> PPC = proyectoRepo.ProyectosPostuladosConvocatorias(estado);
		return PPC;
	}

//meto para obtener la informacion de un proyecto de convocatoria
	@Override
	public List<JSONObject> datosProyectoConvocatoria(int id) {
		List<JSONObject> PPC = proyectoRepo.datosProyectoConvocatoria(id);
		return PPC;
	}

}