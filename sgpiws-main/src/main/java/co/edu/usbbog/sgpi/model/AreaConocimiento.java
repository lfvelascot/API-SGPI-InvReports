/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbbog.sgpi.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.minidev.json.JSONObject;

/**
 *
 * @author 57310
 */
@Entity
@Table(name = "area_conocimiento", catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AreaConocimiento.findAll", query = "SELECT a FROM AreaConocimiento a")
    , @NamedQuery(name = "AreaConocimiento.findById", query = "SELECT a FROM AreaConocimiento a WHERE a.id = :id")
    , @NamedQuery(name = "AreaConocimiento.findByNombre", query = "SELECT a FROM AreaConocimiento a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "AreaConocimiento.findByGranArea", query = "SELECT a FROM AreaConocimiento a WHERE a.granArea = :granArea")
    , @NamedQuery(name = "AreaConocimiento.findByDescripcion", query = "SELECT a FROM AreaConocimiento a WHERE a.descripcion = :descripcion")})
public class AreaConocimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(name = "gran_area", length = 45)
    private String granArea;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String descripcion;
    @JoinTable(name = "areas_conocimiento", joinColumns = {
        @JoinColumn(name = "area_conocimiento", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "proyecto", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private List<Proyecto> proyectos;

    public AreaConocimiento() {
    }

    public AreaConocimiento(Integer id) {
        this.id = id;
    }

    public AreaConocimiento( String nombre, String descripcion) {
      
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getGranArea() {
        return granArea;
    }

    public void setGranArea(String granArea) {
        this.granArea = granArea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
    public Proyecto addProyecto(Proyecto proyecto) {
    	getProyectos().add(proyecto);    	
    	proyecto.addAreaConocimiento(this);    	
    	return proyecto;
    }
    public Proyecto removeProyecto(Proyecto proyecto) {
    	getProyectos().remove(proyecto);    	  	
    	proyecto.removeAreaConocimiento(this);    	
    	return proyecto;
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
        if (!(object instanceof AreaConocimiento)) {
            return false;
        }
        AreaConocimiento other = (AreaConocimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.AreaConocimiento[ id=" + id + " ]";
    }
    public JSONObject toJson() {
    	JSONObject areaConocimientoJson=new JSONObject();
    	areaConocimientoJson.put("id",this.getId());
    	areaConocimientoJson.put("nombre",this.getNombre());
    	areaConocimientoJson.put("gran_area",this.getGranArea());
    	areaConocimientoJson.put("descripcion",this.getDescripcion());
    	return areaConocimientoJson;
    	
    }
}
