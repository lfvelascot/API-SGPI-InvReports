package co.edu.usbbog.sgpireports.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SIGUSBBOG
 */
@Entity
@Table(catalog = "ingusb_sgpi_bd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l"),
    @NamedQuery(name = "Log.findById", query = "SELECT l FROM Log l WHERE l.id = :id"),
    @NamedQuery(name = "Log.findByUsuario", query = "SELECT l FROM Log l WHERE l.usuario = :usuario"),
    @NamedQuery(name = "Log.findByFecha", query = "SELECT l FROM Log l WHERE l.fecha = :fecha"),
    @NamedQuery(name = "Log.findByAccion", query = "SELECT l FROM Log l WHERE l.accion = :accion")})
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(length = 20)
    private String usuario;
    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime fecha;
    @Basic(optional = false)
    @Column(nullable = false, length = 60)
    private String accion;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String comentario;

    public Log() {
    }

    public Log(Integer id) {
        this.id = id;
    }

    public Log(Integer id, LocalDateTime fecha, String accion, String comentario) {
        this.id = id;
        this.fecha = fecha;
        this.accion = accion;
        this.comentario = comentario;
    }

    public Log(LocalDateTime fecha, String accion, String comentario) {
    	this.fecha = fecha;
        this.accion = accion;
        this.comentario = comentario;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Log[ id=" + id + " ]";
    }
    
}
