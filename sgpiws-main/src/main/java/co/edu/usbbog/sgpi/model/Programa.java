/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbbog.sgpi.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.minidev.json.JSONObject;

/**
 *
 * @author 57310
 */
@Entity
@Table(catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programa.findAll", query = "SELECT p FROM Programa p")
    , @NamedQuery(name = "Programa.findById", query = "SELECT p FROM Programa p WHERE p.id = :id")
    , @NamedQuery(name = "Programa.findByNombre", query = "SELECT p FROM Programa p WHERE p.nombre = :nombre")})
public class Programa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nombre;
    @ManyToMany(mappedBy = "programas")
    private List<GrupoInvestigacion> gruposInvestigacion;
    @JoinTable(name = "programas_semilleros", joinColumns = {
        @JoinColumn(name = "programa", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "semillero", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private List<Semillero> semilleros;
    @JoinColumn(name = "facultad_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Facultad facultadId;
    @JoinColumn(name = "director", referencedColumnName = "cedula", nullable = true)
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Usuario director;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programa")
    private List<Materia> materias;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programaId")
    private List<Usuario> usuarios;

    public Programa() {
    }

    public Programa(Integer id) {
        this.id = id;
    }

    public Programa( String nombre) {
      
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<GrupoInvestigacion> getGruposInvestigacion() {
        return gruposInvestigacion;
    }

    public void setGrupoInvestigacionList(List<GrupoInvestigacion> gruposInvestigacion) {
        this.gruposInvestigacion = gruposInvestigacion;
    }
    public GrupoInvestigacion addGrupoInvestigacion(GrupoInvestigacion grupoInvestigacion) {
		getGruposInvestigacion().add(grupoInvestigacion);
		grupoInvestigacion.addPrograma(this);
		return grupoInvestigacion;
	}

	public GrupoInvestigacion removeGrupoInvestigacion(GrupoInvestigacion grupoInvestigacion) {
		getGruposInvestigacion().remove(grupoInvestigacion);
		grupoInvestigacion.removePrograma(null);
		return grupoInvestigacion;
	}

    @XmlTransient
    public List<Semillero> getSemilleros() {
        return semilleros;
    }

    public void setSemilleros(List<Semillero> semilleros) {
        this.semilleros = semilleros;
    }
    public Semillero addSemillero(Semillero semillero) {
    	getSemilleros().add(semillero);
    	semillero.addPrograma(this);
    	return semillero;
    }
    public Semillero removeSemillero(Semillero semillero) {
    	getSemilleros().remove(semillero);
    	semillero.removePrograma(null);
    	return semillero;
    }
    public Facultad getFacultadId() {
        return facultadId;
    }

    public void setFacultadId(Facultad facultadId) {
        this.facultadId = facultadId;
    }

    public Usuario getDirector() {
        return director;
    }

    public void setDirector(Usuario director) {
        this.director = director;
    }

    @XmlTransient
    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
    public Materia addMateria(Materia materia) {
    	getMaterias().add(materia);
    	materia.setPrograma(this);
    	return materia;
    }
    public Materia removeMateria(Materia materia) {
    	getMaterias().remove(materia);
    	materia.setPrograma(null);
    	return materia;
    }

    @XmlTransient
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    public Usuario addUsuario(Usuario usuario) {

		getUsuarios().add(usuario);
		usuario.setProgramaId(this);
		return usuario;
	}
    public Usuario removeUsuario(Usuario usuario) {

		getUsuarios().remove(usuario);
		usuario.setProgramaId(null);
		return usuario;
	}


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Programa)) {
            return false;
        }
        Programa other = (Programa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

   

    public JSONObject toJson() {
    	JSONObject programaJson=new JSONObject();
    	programaJson.put("id",this.getId());
    	programaJson.put("nombre",this.getNombre());
    	if(this.getDirector()==null) {
    		programaJson.put("Director","este programa no cuenta con un director");
    	}else {
    		programaJson.put("Director",this.getDirector().getNombres());
    	}
    	if(this.getFacultadId()==null) {
    		programaJson.put("Facultad","este programa no cuenta con una facultad");
    	}else {
    		programaJson.put("Facultad",this.getFacultadId().getNombre());
    		programaJson.put("Facultad_id",this.getFacultadId().getId());
    	}
    	return programaJson;
    }
    @Override
    public String toString() {
        return toJson().toString();
    }

	
	

	
    
}
