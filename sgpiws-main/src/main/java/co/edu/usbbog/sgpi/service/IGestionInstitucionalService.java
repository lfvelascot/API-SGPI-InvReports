package co.edu.usbbog.sgpi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
import co.edu.usbbog.sgpi.model.TipoProyecto;
import co.edu.usbbog.sgpi.model.Usuario;
import net.minidev.json.JSONObject;

 

public interface IGestionInstitucionalService {
	
	public boolean existeUsuario (String cedula);

	
	public List<GrupoInvestigacion> todosLosGruposInvestigacion();
	public boolean eliminarGrupoInvestigacion(int id);

	public String crearGrupoInvestigacion(GrupoInvestigacion grupoInvestigacion, String director);
	public GrupoInvestigacion grupoiporid(int id);
	public String modificarGrupoI(int id, String nombre, String fecha_fun, String categoria, String fecha_cat, String director);

	public boolean asignarProgramaAGrupoInvestigacion(int programa, int grupo_investigacion);
	public List<Programa> programaDelGrupo(int grupo_investigacion);
	public boolean desasignarProgramaAGrupoInvestigacion(int programa, int grupo_investigacion);
	public boolean asignarLineaAGrupoInvestigacion (String linea_investigacion, int grupo_investigacion);
	public List<LineaInvestigacion> lineaDelGrupo(int grupo_investigacion);
	public boolean desasignarLineaAGrupoInvestigacion (String linea_investigacion, int grupo_investigacion);
	//actualizar grupoInvestigacion
	
	public List<Semillero> todosLosSemilleros();
	public List<Semillero> todosLosSemillerosPorGrupoInvestigacion(int grupoInvestigacion);
	public List<Semillero> todosLosSemillerosPorLiderSemillero(String lider);
	public List<Semillero> todosLosSemillerosPorLineaInvestigacion(String lineaInvestigacion);
	public boolean eliminarSemillero(int id);
	public String crearSemillero(Semillero semillero, int grupo, String lider, String linea);
	public String modificarSemillero(int id, String nombre, String descripcion, String fechaFun, String grupoi, String lineai, String liderSemillero);
	public Semillero semilleroporid(int id);
	public boolean asignarSemilleroAPrograma(int programa, int semillero);
	public List<Programa> programaDelSemillero(int semillero);
	public boolean desasignarSemilleroAPrograma(int programa, int semillero);
	public boolean asignarSemilleroAUsuario(String cedula,int semillero);
	public boolean desasignarSemilleroAUsuario(String cedula);
	public List<JSONObject> contarSemilleros();
	//actualizar semillero
	
	public List<Facultad> todasLasFacultades();
	public boolean eliminarFacultad(int id);

	public String crearFacultad(Facultad facultad, String coordinador, String decano);
	public String modificarFacultad(int id, String nombre, String deca, String coor);
	public Facultad facultadporid(int id);

	//actualizarFacultad
	
	public List<Programa> todosLosProgramas();
	public List<Programa> todosLosProgramasPorFacultad(int facultad);
	public List<Programa> todosLosProgramasPorDirector(String usuario);
	public boolean eliminarPrograma(int id);
	public String crearPrograma(Programa programa, int facultad, String director);
	public String modificarPrograma(int id, String nombre, String facultad, String director);
	public Programa programaporid(int id);
	public List<Usuario> UsuariosPrograma(int id);
	
	public List<GrupoInvestigacion> gruposDelPrograma(int programa);
	public List<Semillero> semillerosDelPrograma(int programa);
	//actualizarPrograma
	
	public List<Materia> todasLasMaterias();
	public List<Materia> todasLasMateriasPorPrograma(int programa);
	public boolean eliminarMateria(String catalogo);
	public boolean crearMateria(Materia materia, int programa);
	public boolean modificarMateria(String catalogo, String nombre, String programa);
	public Materia materiaporid(String catalogo);
	//actualizar materia
	
	public List<Clase> todasLasClases();
	public List<Clase> clasesPorProfesor(String profesor);
	public List<Clase> clasesPorMateria(String materia);
	public boolean eliminarClase(int numero);
	public String crearClase(Clase clase, String materia, String profesor);
	public String modificarClase(int numero, String nombre, String semestre, String materia, String profesor);
	public Clase claseporid(int id);
	
	public boolean asignarProyectosAClase(int proyecto, int clase);
	public boolean desasignarProyectosAClase(int proyecto, int clase);
	public List<Proyecto> proyectosPorClase(int clase);
	//actualizar clase
	
	
	public List<LineaInvestigacion> todasLasLineas();
	public boolean crearLinea(LineaInvestigacion linea, LocalDate fecha);
	public boolean modificarLinea(String nombre, String descripcion, String fecha);
	public LineaInvestigacion lineaporid(String id);
	public boolean eliminarLinea(String nombre);
	//lineas investigacion
	
	
	
	
	public List<AreaConocimiento> todasLasAreasConocimiento();
	public boolean crearArea(AreaConocimiento area, String gran_area);
	public boolean modificarArea(int id, String nombre, String gran_area, String descripcion);
	public AreaConocimiento areaporid(int id);
	public boolean eliminarArea(int id);
	//areas de conocimiento
	
	
	
	
	
	public List<JSONObject> contarEventos();
	public List<Evento> todosLosEventos();
	public boolean crearEvento(Evento evento, String entidad, String sitio_web, String url_memoria);
	public boolean modificarEvento(int id, String nombre, String fecha, String entidad, String estado, String sitio_web, String url_memoria);
	public Evento eventoporid(int id);
	public boolean eliminarEvento(int id);
	
	
	
	//Consultar convocatorias 
	public List<Convocatoria> todasLasConvocatoriasAbiertas(String estado);
	public Convocatoria convocatoriaPorID(int id);
	public List<ProyectosConvocatoria> proyectosPorConvocatoria(int convocatoria);
	public List<Convocatoria> todasLasConvocatorias();
	public boolean crearConvocatoria(Convocatoria convocatoria, String numero_productos, String entidad);
	public Convocatoria convocatoriaporid(int id);
	public boolean modificarConvocatoria(int id, String nombre_convocatoria, String fecha_inicio, String fecha_final, String contexto, String numero_productos, String estado, String tipo, String entidad);
	public boolean eliminarConvocatoria(int id);
	
	
	
	
	//Listar usuarios de un semillero
	public List<Usuario> usuariosPorSemillero(int semillero);

	//proyectos por semillero
	public List<Proyecto> proyectosPorSemillero(int semillero);


	public boolean crearLinea2(String nombre, String descripcion, LocalDate fecha);


	public List<JSONObject> ProyectosPostuladosConvocatorias(String estado);


	public List<JSONObject> datosProyectoConvocatoria(int id);


	public List<Evento> todosLosEventosInternos();

	
}
