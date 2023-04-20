/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbbog.sgpi.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import net.minidev.json.JSONObject;

/**
 *
 * @author 57310
 */
@Embeddable
public class ProyectosConvocatoriaPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(nullable = false)
    private int proyectos;
    @Basic(optional = false)
    @Column(nullable = false)
    private int convocatoria;

    public ProyectosConvocatoriaPK() {
    }

    public ProyectosConvocatoriaPK(int proyectos, int convocatoria) {
        this.proyectos = proyectos;
        this.convocatoria = convocatoria;
    }

    public int getProyectos() {
        return proyectos;
    }

    public void setProyectos(int proyectos) {
        this.proyectos = proyectos;
    }

    public int getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(int convocatoria) {
        this.convocatoria = convocatoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) proyectos;
        hash += (int) convocatoria;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProyectosConvocatoriaPK)) {
            return false;
        }
        ProyectosConvocatoriaPK other = (ProyectosConvocatoriaPK) object;
        if (this.proyectos != other.proyectos) {
            return false;
        }
        if (this.convocatoria != other.convocatoria) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.ProyectosConvocatoriaPK[ proyectos=" + proyectos + ", convocatoria=" + convocatoria + " ]";
    }
    public JSONObject toJson() {
    	JSONObject proyectosConovocatoriaPKJson=new JSONObject();
    	proyectosConovocatoriaPKJson.put("proyectos",this.getProyectos());
    	proyectosConovocatoriaPKJson.put("convocatoria",this.getConvocatoria());
    	return proyectosConovocatoriaPKJson;
    }
    
}
