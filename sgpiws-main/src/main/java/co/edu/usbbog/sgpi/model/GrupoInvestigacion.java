/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbbog.sgpi.model;

import java.io.Serializable;
import java.time.LocalDate;
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

import com.fasterxml.jackson.annotation.JsonFormat;

import net.minidev.json.JSONObject;

/**
 *
 * @author 57310
 */
@Entity
@Table(name = "grupo_investigacion", catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoInvestigacion.findAll", query = "SELECT g FROM GrupoInvestigacion g")
    , @NamedQuery(name = "GrupoInvestigacion.findById", query = "SELECT g FROM GrupoInvestigacion g WHERE g.id = :id")
    , @NamedQuery(name = "GrupoInvestigacion.findByNombre", query = "SELECT g FROM GrupoInvestigacion g WHERE g.nombre = :nombre")
    , @NamedQuery(name = "GrupoInvestigacion.findByFechaFun", query = "SELECT g FROM GrupoInvestigacion g WHERE g.fechaFun = :fechaFun")
    , @NamedQuery(name = "GrupoInvestigacion.findByCategoria", query = "SELECT g FROM GrupoInvestigacion g WHERE g.categoria = :categoria")
    , @NamedQuery(name = "GrupoInvestigacion.findByFechaCat", query = "SELECT g FROM GrupoInvestigacion g WHERE g.fechaCat = :fechaCat")})
public class GrupoInvestigacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "fecha_fun", nullable = false, columnDefinition = "DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaFun;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String categoria;
    @Basic(optional = false)
    @Column(name = "fecha_cat", nullable = false, columnDefinition = "DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaCat;
    @JoinTable(name = "grupo_inv_lineas_inv", joinColumns = {
        @JoinColumn(name = "grupo_investigacion", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "linea_investigacion", referencedColumnName = "nombre", nullable = false)})
    @ManyToMany
    private List<LineaInvestigacion> lineasInvestigacion;
    @JoinTable(name = "programas_grupos_inv", joinColumns = {
        @JoinColumn(name = "grupo_investigacion", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "programa", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private List<Programa> programas;
    @JoinColumn(name = "director_grupo", referencedColumnName = "cedula", nullable = true)
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Usuario directorGrupo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupoInvestigacion")
    private List<Semillero> semilleros;

    public GrupoInvestigacion() {
    }

    public GrupoInvestigacion(Integer id) {
        this.id = id;
    }

    public GrupoInvestigacion( String nombre, LocalDate fechaFun, String categoria, LocalDate fechaCat) {
   
        this.nombre = nombre;
        this.fechaFun = fechaFun;
        this.categoria = categoria;
        this.fechaCat = fechaCat;
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

    public LocalDate getFechaFun() {
        return fechaFun;
    }

    public void setFechaFun(LocalDate fechaFun) {
        this.fechaFun = fechaFun;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getFechaCat() {
        return fechaCat;
    }

    public void setFechaCat(LocalDate fechaCat) {
        this.fechaCat = fechaCat;
    }

    @XmlTransient
    public List<LineaInvestigacion> getLineasInvestigacion() {
        return lineasInvestigacion;
    }

    public void setLineasInvestigacion(List<LineaInvestigacion> lineasInvestigacion) {
        this.lineasInvestigacion = lineasInvestigacion;
    }
    public LineaInvestigacion addLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
    	getLineasInvestigacion().add(lineaInvestigacion);
    	lineaInvestigacion.addGrupoInvestigacion(this);
    	return lineaInvestigacion;
    }
    public LineaInvestigacion removeLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
		getLineasInvestigacion().remove(lineaInvestigacion);
		lineaInvestigacion.removeGrupoInvestigacion(null);
		return lineaInvestigacion;
	}
    

    @XmlTransient
    public List<Programa> getProgramas() {
        return programas;
    }

    public void setProgramas(List<Programa> programas) {
        this.programas = programas;
    }
    public Programa addPrograma(Programa programa) {
    	getProgramas().add(programa);
    	programa.addGrupoInvestigacion(this);
    	return programa;
    }
    public Programa removePrograma(Programa programa) {
    	getProgramas().add(programa);
    	programa.removeGrupoInvestigacion(null);
    	return programa;
    }
    public Usuario getDirectorGrupo() {
        return directorGrupo;
    }

    public void setDirectorGrupo(Usuario directorGrupo) {
        this.directorGrupo = directorGrupo;
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
    	semillero.setGrupoInvestigacion(this);
    	return semillero;
    }
    public Semillero removeSemillero(Semillero semillero) {
    	getSemilleros().remove(semillero);
    	semillero.setGrupoInvestigacion(null);
    	return semillero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoInvestigacion)) {
            return false;
        }
        GrupoInvestigacion other = (GrupoInvestigacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.GrupoInvestigacion[ id=" + id + " ]";
    }
    public JSONObject toJson() {
    	JSONObject grupoInvestigacionJson=new JSONObject();
    	grupoInvestigacionJson.put("id",this.getId());
    	grupoInvestigacionJson.put("nombre",this.getNombre());
    	grupoInvestigacionJson.put("fecha_fun",this.getFechaFun());
    	grupoInvestigacionJson.put("categoria",this.getCategoria());
    	grupoInvestigacionJson.put("fecha_cat",this.getFechaCat());
    	if(this.getDirectorGrupo()==null) {
    		grupoInvestigacionJson.put("director_grupo","NO TIENE DIRECTOR ASIGNADO");
    	}else {
    		grupoInvestigacionJson.put("director_grupo",this.getDirectorGrupo().getNombres());
    	}
    	return grupoInvestigacionJson;
    	
    }



	





	
}
