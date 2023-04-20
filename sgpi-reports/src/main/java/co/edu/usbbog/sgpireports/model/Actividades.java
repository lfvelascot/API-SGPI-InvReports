/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.usbbog.sgpireports.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SIGUSBBOG
 */
@Entity
@Table(name = "actividades", catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividades.findAll", query = "SELECT a FROM Actividades a"),
    @NamedQuery(name = "Actividades.findByModulo", query = "SELECT a FROM Actividades a WHERE a.actividadesPK.modulo = :modulo"),
    @NamedQuery(name = "Actividades.findByTipoUsuario", query = "SELECT a FROM Actividades a WHERE a.actividadesPK.tipoUsuario = :tipoUsuario"),
    @NamedQuery(name = "Actividades.findByActividad", query = "SELECT a FROM Actividades a WHERE a.actividadesPK.actividad = :actividad")})
public class Actividades implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ActividadesPK actividadesPK;
    @JoinColumn(name = "actividad", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Actividad actividad1;
    @JoinColumn(name = "modulo", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Modulo modulo1;
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoUsuario tipoUsuario1;

    public Actividades() {
    }

    public Actividades(ActividadesPK actividadesPK) {
        this.actividadesPK = actividadesPK;
    }

    public Actividades(String modulo, String tipoUsuario, String actividad) {
        this.actividadesPK = new ActividadesPK(modulo, tipoUsuario, actividad);
    }

    public ActividadesPK getActividadesPK() {
        return actividadesPK;
    }

    public void setActividadesPK(ActividadesPK actividadesPK) {
        this.actividadesPK = actividadesPK;
    }

    public Actividad getActividad1() {
        return actividad1;
    }

    public void setActividad1(Actividad actividad1) {
        this.actividad1 = actividad1;
    }

    public Modulo getModulo1() {
        return modulo1;
    }

    public void setModulo1(Modulo modulo1) {
        this.modulo1 = modulo1;
    }

    public TipoUsuario getTipoUsuario1() {
        return tipoUsuario1;
    }

    public void setTipoUsuario1(TipoUsuario tipoUsuario1) {
        this.tipoUsuario1 = tipoUsuario1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actividadesPK != null ? actividadesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividades)) {
            return false;
        }
        Actividades other = (Actividades) object;
        if ((this.actividadesPK == null && other.actividadesPK != null) || (this.actividadesPK != null && !this.actividadesPK.equals(other.actividadesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.Actividades[ actividadesPK=" + actividadesPK + " ]";
    }
    
}
