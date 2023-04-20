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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findById", query = "SELECT p FROM Producto p WHERE p.id = :id")
    , @NamedQuery(name = "Producto.findByTituloProducto", query = "SELECT p FROM Producto p WHERE p.tituloProducto = :tituloProducto")
    , @NamedQuery(name = "Producto.findByTipoProducto", query = "SELECT p FROM Producto p WHERE p.tipoProducto = :tipoProducto")
    , @NamedQuery(name = "Producto.findByUrlRepo", query = "SELECT p FROM Producto p WHERE p.urlRepo = :urlRepo")
	/*, @NamedQuery(name = "Producto.findByFecha", query = "SELECT p FROM Producto p WHERE p.fecha = :fecha")*/})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "titulo_producto", nullable = false, length = 100)
    private String tituloProducto;
    @Basic(optional = false)
    @Column(name = "tipo_producto", nullable = false, length = 100)
    private String tipoProducto;
    @Basic(optional = false)
    @Column(name = "url_repo", nullable = false, length = 150)
    private String urlRepo;
    @Column(name = "fecha", nullable = false, columnDefinition = "date")
    private LocalDate fecha;
	@JoinColumn(name = "proyecto", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoId")
    private List<Comentario> comentarios;

    public Producto() {
    }

    public Producto(Integer id) {
        this.id = id;
    }

    public Producto(String tituloProducto, String tipoProducto, String urlRepo,LocalDate fecha) {
        
        this.tituloProducto = tituloProducto;
        this.tipoProducto = tipoProducto;
        this.urlRepo = urlRepo;
        this.fecha=fecha;
    
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTituloProducto() {
        return tituloProducto;
    }

    public void setTituloProducto(String tituloProducto) {
        this.tituloProducto = tituloProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getUrlRepo() {
        return urlRepo;
    }

    public void setUrlRepo(String urlRepo) {
        this.urlRepo = urlRepo;
    }

    public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @XmlTransient
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
   
    public Comentario addComentario(Comentario comentario) {
    	getComentarios().add(comentario);
    	comentario.setProductoId(this);
    	return comentario;
    }
    public Comentario removeComentario(Comentario comentario) {
    	getComentarios().remove(comentario);
    	comentario.setProductoId(null);
    	return comentario;
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
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
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
    	JSONObject productoJson=new JSONObject();
    	productoJson.put("id",this.getId());
    	productoJson.put("titulo_producto",this.getTituloProducto());
    	productoJson.put("tipo_producto",this.getTipoProducto());
    	productoJson.put("url_repo",this.getUrlRepo());
    	productoJson.put("fecha",this.getFecha());
    	productoJson.put("proyecto",this.getProyecto().getId());
    	return productoJson;
    }
}
