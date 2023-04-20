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
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "linea_investigacion", catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LineaInvestigacion.findAll", query = "SELECT l FROM LineaInvestigacion l")
    , @NamedQuery(name = "LineaInvestigacion.findByNombre", query = "SELECT l FROM LineaInvestigacion l WHERE l.nombre = :nombre")
    , @NamedQuery(name = "LineaInvestigacion.findByDescripcion", query = "SELECT l FROM LineaInvestigacion l WHERE l.descripcion = :descripcion")
    , @NamedQuery(name = "LineaInvestigacion.findByFecha", query = "SELECT l FROM LineaInvestigacion l WHERE l.fecha = :fecha")})
public class LineaInvestigacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 150)
    private String descripcion;
    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;
    @ManyToMany(mappedBy = "lineasInvestigacion")
    private List<GrupoInvestigacion> gruposInvestigacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lineaInvestigacion")
    private List<Semillero> semilleros;

    public LineaInvestigacion() {
    }

    public LineaInvestigacion(String nombre) {
        this.nombre = nombre;
    }

    public LineaInvestigacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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
		grupoInvestigacion.addLineaInvestigacion(this);
		return grupoInvestigacion;
		
	}
	public GrupoInvestigacion removeGrupoInvestigacion(GrupoInvestigacion grupoInvestigacion) {
		getGruposInvestigacion().remove(grupoInvestigacion);
		grupoInvestigacion.removeLineaInvestigacion(this);
		return grupoInvestigacion;
		
	}

    @XmlTransient
    public List<Semillero> getSemilleros() {
        return semilleros;
    }

    public void setSemilleros(List<Semillero> semillero) {
        this.semilleros = semillero;
    }
    public Semillero addSemillero(Semillero semillero) {
    	getSemilleros().add(semillero);
    	semillero.setLineaInvestigacion(this);
    	return semillero;
    }
    public Semillero removeSemillero(Semillero semillero) {
    	getSemilleros().remove(semillero);
    	semillero.setLineaInvestigacion(null);
    	return semillero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LineaInvestigacion)) {
            return false;
        }
        LineaInvestigacion other = (LineaInvestigacion) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.LineaInvestigacion[ nombre=" + nombre + " ]";
    }
    public JSONObject toJson() {
    	JSONObject lineaInvestigacionJson=new JSONObject();
    	lineaInvestigacionJson.put("nombre",this.getNombre());
    	lineaInvestigacionJson.put("descripcion",this.getDescripcion());
    	lineaInvestigacionJson.put("fecha",this.getFecha());
    	return lineaInvestigacionJson;
    	
    }
	
}
