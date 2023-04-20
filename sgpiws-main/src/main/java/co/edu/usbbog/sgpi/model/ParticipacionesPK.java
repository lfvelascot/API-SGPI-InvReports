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
public class ParticipacionesPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "evento_id", nullable = false)
    private int eventoId;
    @Basic(optional = false)
    @Column(name = "proyecto_id_proyecto", nullable = false)
    private int proyectoIdProyecto;

    public ParticipacionesPK() {
    }

    public ParticipacionesPK(int eventoId, int proyectoIdProyecto) {
        this.eventoId = eventoId;
        this.proyectoIdProyecto = proyectoIdProyecto;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public int getProyectoIdProyecto() {
        return proyectoIdProyecto;
    }

    public void setProyectoIdProyecto(int proyectoIdProyecto) {
        this.proyectoIdProyecto = proyectoIdProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eventoId;
        hash += (int) proyectoIdProyecto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParticipacionesPK)) {
            return false;
        }
        ParticipacionesPK other = (ParticipacionesPK) object;
        if (this.eventoId != other.eventoId) {
            return false;
        }
        if (this.proyectoIdProyecto != other.proyectoIdProyecto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.ParticipacionesPK[ eventoId=" + eventoId + ", proyectoIdProyecto=" + proyectoIdProyecto + " ]";
    }
    public JSONObject toJson() {
    	JSONObject participacionesPKJson=new JSONObject();
    	participacionesPKJson.put("evento_id",this.getEventoId());
    	participacionesPKJson.put("proyecto_id_proyecto",this.getProyectoIdProyecto());
    	return participacionesPKJson;
    }
    
}
