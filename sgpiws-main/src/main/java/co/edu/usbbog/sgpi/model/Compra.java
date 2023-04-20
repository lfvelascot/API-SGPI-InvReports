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
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c")
    , @NamedQuery(name = "Compra.findById", query = "SELECT c FROM Compra c WHERE c.id = :id")
    , @NamedQuery(name = "Compra.findByFechaSolicitud", query = "SELECT c FROM Compra c WHERE c.fechaSolicitud = :fechaSolicitud")
    , @NamedQuery(name = "Compra.findByNombre", query = "SELECT c FROM Compra c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Compra.findByTipo", query = "SELECT c FROM Compra c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "Compra.findByCodigoCompra", query = "SELECT c FROM Compra c WHERE c.codigoCompra = :codigoCompra")
    , @NamedQuery(name = "Compra.findByValor", query = "SELECT c FROM Compra c WHERE c.valor = :valor")
    , @NamedQuery(name = "Compra.findByFechaCompra", query = "SELECT c FROM Compra c WHERE c.fechaCompra = :fechaCompra")
    , @NamedQuery(name = "Compra.findByEstado", query = "SELECT c FROM Compra c WHERE c.estado = :estado")
    , @NamedQuery(name = "Compra.findByLink", query = "SELECT c FROM Compra c WHERE c.link = :link")
    , @NamedQuery(name = "Compra.findByDescripcion", query = "SELECT c FROM Compra c WHERE c.descripcion = :descripcion")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false , name="fecha_solicitud", columnDefinition = "DATE")
    private LocalDate fechaSolicitud;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String tipo;
    @Column(name = "codigo_compra", length = 45, nullable = true)    
    private String codigoCompra;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor",precision = 22, nullable = true)//-------------------------------------------------------------
    
    private Double valor;
    @Basic(optional = true)

    @Column(nullable = true , name="fecha_compra", columnDefinition = "DATE")//----------------------------------------------
    

    private LocalDate fechaCompra;
    @Basic(optional = false)
    @Column(nullable = false)
    private int estado;
    @Column(name = "link", length = 45, nullable = true)
    private String link;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String descripcion;
    @JoinColumn(name = "presupuesto", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Presupuesto presupuesto;

    public Compra() {
    }

    public Compra(Integer id) {
        this.id = id;
    }

    public Compra( LocalDate fechaSolicitud, String nombre, String tipo, int estado, String descripcion, String link) {
        this.fechaSolicitud = fechaSolicitud;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.descripcion = descripcion;
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigoCompra() {
        return codigoCompra;
    }

    public void setCodigoCompra(String codigoCompra) {
        this.codigoCompra = codigoCompra;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
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
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.Compra[ id=" + id + " ]";
    }
    public JSONObject toJson() {
    	JSONObject compraJson=new JSONObject();
    	compraJson.put("id",this.getId());
    	compraJson.put("fecha_solicitud",this.getFechaSolicitud());
    	compraJson.put("nombre",this.getNombre());
    	compraJson.put("tipo",this.getTipo());
    	compraJson.put("codigo_compra",this.getCodigoCompra());
    	compraJson.put("valor",this.getValor());
    	compraJson.put("fecha_compra",this.getFechaCompra());
    	compraJson.put("estado",this.getEstado());
    	compraJson.put("link",this.getLink());
    	compraJson.put("descripcion",this.getDescripcion());
    	compraJson.put("presupuesto",this.getPresupuesto().getId());
    	return compraJson;
    	
    }

	
    
}
