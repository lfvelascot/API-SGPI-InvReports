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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
    @NamedQuery(name = "Comentario.findAll", query = "SELECT c FROM Comentario c")
    , @NamedQuery(name = "Comentario.findById", query = "SELECT c FROM Comentario c WHERE c.id = :id")
    , @NamedQuery(name = "Comentario.findByCalificacion", query = "SELECT c FROM Comentario c WHERE c.calificacion = :calificacion")
    , @NamedQuery(name = "Comentario.findByFase", query = "SELECT c FROM Comentario c WHERE c.fase = :fase")
    , @NamedQuery(name = "Comentario.findByNivel", query = "SELECT c FROM Comentario c WHERE c.nivel = :nivel")
    , @NamedQuery(name = "Comentario.findByFecha", query = "SELECT c FROM Comentario c WHERE c.fecha = :fecha")})
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String comentario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double calificacion;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String fase;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nivel;
    @Basic(optional = false)
    @Column(nullable = false, name="fecha", columnDefinition = "DATE")
    private LocalDate fecha;
    @JoinColumn(name = "producto_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Producto productoId;

    public Comentario() {
    }

    public Comentario(Integer id) {
        this.id = id;
    }

    public Comentario(String comentario, String fase, String nivel, LocalDate fecha) {
        this.comentario = comentario;
        this.fase = fase;
        this.nivel = nivel;
        this.fecha = fecha;
    }
    public Comentario(Integer id, String comentario, String fase, String nivel,Double calificacion, LocalDate fecha,Producto producto) {
        this.id = id;
        this.comentario = comentario;
        this.fase = fase;
        this.nivel = nivel;
        this.calificacion=calificacion;
        this.fecha = fecha;
        this.productoId=producto;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Producto getProductoId() {
        return productoId;
    }

    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
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
        if (!(object instanceof Comentario)) {
            return false;
        }
        Comentario other = (Comentario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
    public JSONObject toJson() {
    	JSONObject comentarioJson=new JSONObject();
    	comentarioJson.put("id",this.getId());
    	comentarioJson.put("comentario",this.getComentario());
    	comentarioJson.put("calificacion",this.getCalificacion());
    	comentarioJson.put("fase",this.getFase());
    	comentarioJson.put("nivel",this.getNivel());
    	comentarioJson.put("fecha",this.getFecha());
    	comentarioJson.put("producto_id",this.getProductoId().getId());
    	return comentarioJson;
    	
    }
}
