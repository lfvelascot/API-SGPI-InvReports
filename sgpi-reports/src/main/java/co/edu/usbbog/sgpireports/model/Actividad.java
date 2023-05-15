/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.usbbog.sgpireports.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SIGUSBBOG
 */
@Entity
@Table(catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByNombre", query = "SELECT a FROM Actividad a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Actividad.findByDescripci\u00f3n", query = "SELECT a FROM Actividad a WHERE a.descripci\u00f3n = :descripci\u00f3n"),
	@NamedQuery(name = "Actividad.findByUrl", query = "SELECT a FROM Actividad a WHERE a.url = :url")})
public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false, length = 70)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String descripción;
    @Column(length = 150)
    private String url;
    @JoinTable(name = "actividades", joinColumns = {
        @JoinColumn(name = "actividad", referencedColumnName = "nombre", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "tipo_usuario", referencedColumnName = "nombre", nullable = false)})
    @ManyToMany
    private List<TipoUsuario> tipoUsuarios;
    @JoinColumn(name = "modulo", referencedColumnName = "nombre", nullable = false)
    @ManyToOne(optional = false)
    private Modulo modulo;

    public Actividad() {
    }

    public Actividad(String nombre) {
        this.nombre = nombre;
    }

    public Actividad(String nombre, String descripción, String url) {
        this.nombre = nombre;
        this.descripción = descripción;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlTransient
    public List<TipoUsuario> getTipoUsuarios() {
        return tipoUsuarios;
    }

    public void setTipoUsuarios(List<TipoUsuario> tipoUsuarios) {
        this.tipoUsuarios = tipoUsuarios;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
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
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }
    
    
    @Override
    public String toString() {
        return "co.edu.usbbog.sgpireports.model.Actividad[ nombre=" + nombre + " ]";
    }
    
}
