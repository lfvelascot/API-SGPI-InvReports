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
    @NamedQuery(name = "Participantes.findAll", query = "SELECT p FROM Participantes p")
    , @NamedQuery(name = "Participantes.findByUsuario", query = "SELECT p FROM Participantes p WHERE p.participantesPK.usuario = :usuario")
    , @NamedQuery(name = "Participantes.findByProyecto", query = "SELECT p FROM Participantes p WHERE p.participantesPK.proyecto = :proyecto")
    , @NamedQuery(name = "Participantes.findByFechaInicio", query = "SELECT p FROM Participantes p WHERE p.participantesPK.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Participantes.findByFechaFin", query = "SELECT p FROM Participantes p WHERE p.fechaFin = :fechaFin")
    , @NamedQuery(name = "Participantes.findByRol", query = "SELECT p FROM Participantes p WHERE p.rol = :rol")})
public class Participantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParticipantesPK participantesPK;
    @Column(name = "fecha_fin",columnDefinition = "DATE")
    private LocalDate fechaFin;
	@Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String rol;
    @JoinColumn(name = "proyecto", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "usuario", referencedColumnName = "cedula", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Participantes() { 
    }

    public Participantes(ParticipantesPK participantesPK) {
        this.participantesPK = participantesPK;
    }

    public Participantes(ParticipantesPK participantesPK, String rol) {
        this.participantesPK = participantesPK;

        this.rol = rol;
    }

    public Participantes(String usuario, int proyecto, LocalDate fechaInicio) {
        this.participantesPK = new ParticipantesPK(usuario, proyecto, fechaInicio);
    }

    public ParticipantesPK getParticipantesPK() {
        return participantesPK;
    }

    public void setParticipantesPK(ParticipantesPK participantesPK) {
        this.participantesPK = participantesPK;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }


    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (participantesPK != null ? participantesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participantes)) {
            return false;
        }
        Participantes other = (Participantes) object;
        if ((this.participantesPK == null && other.participantesPK != null) || (this.participantesPK != null && !this.participantesPK.equals(other.participantesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
    public JSONObject toJson() {
    	JSONObject paticipantesJson=new JSONObject();
    	if(this.getFechaFin()==null) {
    		paticipantesJson.put("fecha_fin","Este usuario aun participa en este proyecto");
    	}else { 
    		paticipantesJson.put("fecha_fin",this.getFechaFin());
    	}
    	//paticipantesJson.put("fecha_fin",this.getFechaInicio());
    	paticipantesJson.put("rol",this.getRol());
    	paticipantesJson.put("nombre",this.getUsuario().getNombres());
    	paticipantesJson.put("programa",this.getUsuario().getProgramaId().getNombre());
    	paticipantesJson.put("cedula",this.getUsuario().getCedula());
    	paticipantesJson.put("titulo",this.getProyecto().getTitulo());
    	paticipantesJson.put("id",this.getProyecto().getId()	);
    	return paticipantesJson;
    }


}
