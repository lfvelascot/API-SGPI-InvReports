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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e")
    , @NamedQuery(name = "Evento.findById", query = "SELECT e FROM Evento e WHERE e.id = :id")
    , @NamedQuery(name = "Evento.findByNombre", query = "SELECT e FROM Evento e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Evento.findByFecha", query = "SELECT e FROM Evento e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "Evento.findByEntidad", query = "SELECT e FROM Evento e WHERE e.entidad = :entidad")
    , @NamedQuery(name = "Evento.findByEstado", query = "SELECT e FROM Evento e WHERE e.estado = :estado")
    , @NamedQuery(name = "Evento.findBySitioWeb", query = "SELECT e FROM Evento e WHERE e.sitioWeb = :sitioWeb")
    , @NamedQuery(name = "Evento.findByUrlMemoria", query = "SELECT e FROM Evento e WHERE e.urlMemoria = :urlMemoria")})
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false , name="fecha", columnDefinition = "DATE")
    private LocalDate fecha;
	@Column(length = 45)
    private String entidad;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String estado;
    @Column(name = "sitio_web", length = 45)
    private String sitioWeb;
    @Column(name = "url_memoria", length = 45)
    private String urlMemoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evento")
    private List<Participaciones> participaciones;

    public Evento() {
    }

    public Evento(Integer id) {
        this.id = id;
    }

    public Evento(String nombre, LocalDate fecha, String estado) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.estado = estado;
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

    public LocalDate getFecha() {
    	
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public String getUrlMemoria() {
        return urlMemoria;
    }

    public void setUrlMemoria(String urlMemoria) {
        this.urlMemoria = urlMemoria;
    }

    @XmlTransient
    public List<Participaciones> getParticipaciones() {
        return participaciones;
    }

    public void setParticipaciones(List<Participaciones> participaciones) {
        this.participaciones = participaciones;
    }
    public Participaciones addParticipaciones(Participaciones participaciones) {
    	getParticipaciones().add(participaciones);
    	participaciones.setEvento(this);
    	return participaciones;
    }
    public Participaciones removeParticipaciones(Participaciones participaciones) {
    	getParticipaciones().remove(participaciones);
    	participaciones.setEvento(null);
    	return participaciones;
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
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.Evento[ id=" + id + " ]";
    }
    public JSONObject toJson() {
    	JSONObject eventoJson=new JSONObject();
    	eventoJson.put("id",this.getId());
    	eventoJson.put("nombre",this.getNombre());
    	eventoJson.put("fecha",this.getFecha());
    	eventoJson.put("entidad",this.getEntidad());
    	eventoJson.put("sitio_web",this.getSitioWeb());
    	eventoJson.put("url_memoria",this.getUrlMemoria());
    	eventoJson.put("estado",this.getEstado());
    	return eventoJson;
    	
    }
}
