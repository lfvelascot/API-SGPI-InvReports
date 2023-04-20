/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.usbbog.sgpireports.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author SIGUSBBOG
 */
@Embeddable
public class ActividadesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "modulo")
    private String modulo;
    @Basic(optional = false)
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    @Basic(optional = false)
    @Column(name = "actividad")
    private String actividad;

    public ActividadesPK() {
    }

    public ActividadesPK(String modulo, String tipoUsuario, String actividad) {
        this.modulo = modulo;
        this.tipoUsuario = tipoUsuario;
        this.actividad = actividad;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modulo != null ? modulo.hashCode() : 0);
        hash += (tipoUsuario != null ? tipoUsuario.hashCode() : 0);
        hash += (actividad != null ? actividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadesPK)) {
            return false;
        }
        ActividadesPK other = (ActividadesPK) object;
        if ((this.modulo == null && other.modulo != null) || (this.modulo != null && !this.modulo.equals(other.modulo))) {
            return false;
        }
        if ((this.tipoUsuario == null && other.tipoUsuario != null) || (this.tipoUsuario != null && !this.tipoUsuario.equals(other.tipoUsuario))) {
            return false;
        }
        if ((this.actividad == null && other.actividad != null) || (this.actividad != null && !this.actividad.equals(other.actividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.ActividadesPK[ modulo=" + modulo + ", tipoUsuario=" + tipoUsuario + ", actividad=" + actividad + " ]";
    }
    
}
