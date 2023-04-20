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
import javax.persistence.Id;
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
@Table(name = "tipo_proyecto", catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProyecto.findAll", query = "SELECT t FROM TipoProyecto t")
    , @NamedQuery(name = "TipoProyecto.findByNombre", query = "SELECT t FROM TipoProyecto t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoProyecto.findByDescripcion", query = "SELECT t FROM TipoProyecto t WHERE t.descripcion = :descripcion")})
public class TipoProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoProyecto")
    private List<Proyecto> proyectos;

    public TipoProyecto() {
    }

    public TipoProyecto(String nombre) {
        this.nombre = nombre;
    }

    public TipoProyecto(String nombre, String descripcion) {
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

    @XmlTransient
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
    public Proyecto addProyecto(Proyecto proyecto) {
    	getProyectos().add(proyecto);
    	proyecto.setTipoProyecto(this);
    	return proyecto;
    }
    public Proyecto removeProyecto(Proyecto proyecto) {
    	getProyectos().remove(proyecto);
    	proyecto.setTipoProyecto(null);
    	return proyecto;
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
        if (!(object instanceof TipoProyecto)) {
            return false;
        }
        TipoProyecto other = (TipoProyecto) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.TipoProyecto[ nombre=" + nombre + " ]";
    }
    public JSONObject toJson() {
    	JSONObject tipoProyectoJson=new JSONObject();
    	tipoProyectoJson.put("nombre",this.getNombre());
    	tipoProyectoJson.put("descripcion",this.getDescripcion());    
    	return tipoProyectoJson;
    }
}
