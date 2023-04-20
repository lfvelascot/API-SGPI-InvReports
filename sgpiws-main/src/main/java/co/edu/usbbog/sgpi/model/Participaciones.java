/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbbog.sgpi.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import net.minidev.json.JSONObject;

/**
 *
 * @author 57310
 */
@Entity
@Table(catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Participaciones.findAll", query = "SELECT p FROM Participaciones p")
    , @NamedQuery(name = "Participaciones.findByEventoId", query = "SELECT p FROM Participaciones p WHERE p.participacionesPK.eventoId = :eventoId")
    , @NamedQuery(name = "Participaciones.findByProyectoIdProyecto", query = "SELECT p FROM Participaciones p WHERE p.participacionesPK.proyectoIdProyecto = :proyectoIdProyecto")
    , @NamedQuery(name = "Participaciones.findByFechaPart", query = "SELECT p FROM Participaciones p WHERE p.fechaPart = :fechaPart")
    , @NamedQuery(name = "Participaciones.findByReconocimientos", query = "SELECT p FROM Participaciones p WHERE p.reconocimientos = :reconocimientos")})
public class Participaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParticipacionesPK participacionesPK;
    @Basic(optional = false)
    @Column(name = "fecha_part", nullable = false, columnDefinition = "DATE")
    private LocalDate fechaPart;
    @Column(length = 10)
    private String reconocimientos;
    @JoinColumn(name = "evento_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Evento evento;
    @JoinColumn(name = "proyecto_id_proyecto", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Proyecto proyecto;

    public Participaciones() {
    }

    public Participaciones(ParticipacionesPK participacionesPK) {
        this.participacionesPK = participacionesPK;
    }

    public Participaciones(ParticipacionesPK participacionesPK, LocalDate fechaPart) {
        this.participacionesPK = participacionesPK;
        this.fechaPart = fechaPart;
    }

    public Participaciones(int eventoId, int proyectoIdProyecto) {
        this.participacionesPK = new ParticipacionesPK(eventoId, proyectoIdProyecto);
    }

    public ParticipacionesPK getParticipacionesPK() {
        return participacionesPK;
    }

    public void setParticipacionesPK(ParticipacionesPK participacionesPK) {
        this.participacionesPK = participacionesPK;
    }

    public LocalDate getFechaPart() {
        return fechaPart;
    }

    public void setFechaPart(LocalDate fechaPart) {
        this.fechaPart = fechaPart;
    }

    public String getReconocimientos() {
        return reconocimientos;
    }

    public void setReconocimientos(String reconocimientos) {
        this.reconocimientos = reconocimientos;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (participacionesPK != null ? participacionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participaciones)) {
            return false;
        }
        Participaciones other = (Participaciones) object;
        if ((this.participacionesPK == null && other.participacionesPK != null) || (this.participacionesPK != null && !this.participacionesPK.equals(other.participacionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
    public JSONObject toJson() {
    	JSONObject participacionesJson=new JSONObject();
    	participacionesJson.put("evento",this.getEvento().getNombre());
    	participacionesJson.put("proyecto", this.getProyecto().getTitulo());
    	participacionesJson.put("fecha_part",this.getFechaPart());
    	participacionesJson.put("reconocimientos",this.getReconocimientos());
    	
    	return participacionesJson;
    }

}
