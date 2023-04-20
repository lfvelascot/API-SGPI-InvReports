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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Convocatoria.findAll", query = "SELECT c FROM Convocatoria c")
    , @NamedQuery(name = "Convocatoria.findById", query = "SELECT c FROM Convocatoria c WHERE c.id = :id")
    , @NamedQuery(name = "Convocatoria.findByNombreConvocatoria", query = "SELECT c FROM Convocatoria c WHERE c.nombreConvocatoria = :nombreConvocatoria")
    , @NamedQuery(name = "Convocatoria.findByFechaInicio", query = "SELECT c FROM Convocatoria c WHERE c.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Convocatoria.findByFechaFinal", query = "SELECT c FROM Convocatoria c WHERE c.fechaFinal = :fechaFinal")
    , @NamedQuery(name = "Convocatoria.findByNumeroProductos", query = "SELECT c FROM Convocatoria c WHERE c.numeroProductos = :numeroProductos")
    , @NamedQuery(name = "Convocatoria.findByEstado", query = "SELECT c FROM Convocatoria c WHERE c.estado = :estado")
    , @NamedQuery(name = "Convocatoria.findByTipo", query = "SELECT c FROM Convocatoria c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "Convocatoria.findByEntidad", query = "SELECT c FROM Convocatoria c WHERE c.entidad = :entidad")})
public class Convocatoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre_convocatoria", nullable = false, length = 45)
    private String nombreConvocatoria;
    @Basic(optional = false)
    @Column(name = "fecha_inicio", nullable = false, columnDefinition = "DATE")
    private LocalDate fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_final", nullable = false, columnDefinition = "DATE")
    private LocalDate fechaFinal;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String contexto;
    @Column(name = "numero_productos", length = 45)
    private String numeroProductos;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String estado;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String tipo;
    @Column(length = 45)
    private String entidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "convocatoria")
    private List<ProyectosConvocatoria> proyectosConvocatorias;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "convocatoriaId")
    private List<DetalleConvocatoria> detallesConvocatoria;

    public Convocatoria() {
    }

    public Convocatoria(Integer id) {
        this.id = id;
    }

    public Convocatoria(String nombreConvocatoria, LocalDate fechaInicio, LocalDate fechaFinal, String contexto, String estado, String tipo) {
        this.nombreConvocatoria = nombreConvocatoria;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.contexto = contexto;
        this.estado = estado;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreConvocatoria() {
        return nombreConvocatoria;
    }

    public void setNombreConvocatoria(String nombreConvocatoria) {
        this.nombreConvocatoria = nombreConvocatoria;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public String getNumeroProductos() {
        return numeroProductos;
    }

    public void setNumeroProductos(String numeroProductos) {
        this.numeroProductos = numeroProductos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    @XmlTransient
    public List<ProyectosConvocatoria> getProyectosConvocatorias() {
        return proyectosConvocatorias;
    }

    public void setProyectosConvocatorias(List<ProyectosConvocatoria> proyectosConvocatorias) {
        this.proyectosConvocatorias = proyectosConvocatorias;
    }
    public ProyectosConvocatoria addProyectosConvocatoria(ProyectosConvocatoria proyectosConvocatoria) {
    	getProyectosConvocatorias().add(proyectosConvocatoria);
    	proyectosConvocatoria.setConvocatoria(this);
    	return proyectosConvocatoria;
    }
    public ProyectosConvocatoria removeProyectosConvocatoria(ProyectosConvocatoria proyectosConvocatoria) {
    	getProyectosConvocatorias().remove(proyectosConvocatoria);
    	proyectosConvocatoria.setConvocatoria(null);
    	return proyectosConvocatoria;
    }
    @XmlTransient
    public List<DetalleConvocatoria> getDetallesConvocatoria() {
        return detallesConvocatoria;
    }

    public void setDetallesConvocatoria(List<DetalleConvocatoria> detallesConvocatoria) {
        this.detallesConvocatoria = detallesConvocatoria;
    }
    public DetalleConvocatoria addDetalleConvocatoria(DetalleConvocatoria detalleConvocatoria) {
    	getDetallesConvocatoria().add(detalleConvocatoria);
    	detalleConvocatoria.setConvocatoriaId(this);
    	return detalleConvocatoria;
    }
    public DetalleConvocatoria removeDetalleConvocatoria(DetalleConvocatoria detalleConvocatoria) {
    	getDetallesConvocatoria().remove(detalleConvocatoria);
    	detalleConvocatoria.setConvocatoriaId(null);
    	return detalleConvocatoria;
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
        if (!(object instanceof Convocatoria)) {
            return false;
        }
        Convocatoria other = (Convocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.Convocatoria[ id=" + id + " ]";
    }
    public JSONObject toJson() {
    	JSONObject convocatoriaJson=new JSONObject();
    	convocatoriaJson.put("id",this.getId());
    	convocatoriaJson.put("nombre_convocatoria",this.getNombreConvocatoria());
    	convocatoriaJson.put("fecha_inicio",this.getFechaInicio());
    	convocatoriaJson.put("fecha_final",this.getFechaFinal());
    	convocatoriaJson.put("contexto",this.getContexto());
    	convocatoriaJson.put("numero_productos",this.getNumeroProductos());
    	convocatoriaJson.put("estado",this.getEstado());
    	convocatoriaJson.put("tipo",this.getTipo());
    	convocatoriaJson.put("entidad",this.getEntidad());
    	return convocatoriaJson;
    	
    }
}
