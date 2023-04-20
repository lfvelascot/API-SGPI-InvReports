/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbbog.sgpi.model;

import java.io.Serializable;
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
@Table(name = "proyectos_convocatoria", catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProyectosConvocatoria.findAll", query = "SELECT p FROM ProyectosConvocatoria p")
    , @NamedQuery(name = "ProyectosConvocatoria.findByProyectos", query = "SELECT p FROM ProyectosConvocatoria p WHERE p.proyectosConvocatoriaPK.proyectos = :proyectos")
    , @NamedQuery(name = "ProyectosConvocatoria.findByConvocatoria", query = "SELECT p FROM ProyectosConvocatoria p WHERE p.proyectosConvocatoriaPK.convocatoria = :convocatoria")
    , @NamedQuery(name = "ProyectosConvocatoria.findByIdProyecto", query = "SELECT p FROM ProyectosConvocatoria p WHERE p.idProyecto = :idProyecto")})
public class ProyectosConvocatoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProyectosConvocatoriaPK proyectosConvocatoriaPK;
    @Basic(optional = false)
    @Column(name = "id_proyecto", nullable = false, length = 25)
    private String idProyecto;
    @JoinColumn(name = "convocatoria", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Convocatoria convocatoria;
    @JoinColumn(name = "proyectos", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;

    public ProyectosConvocatoria() {
    }

    public ProyectosConvocatoria(ProyectosConvocatoriaPK proyectosConvocatoriaPK) {
        this.proyectosConvocatoriaPK = proyectosConvocatoriaPK;
    }

    public ProyectosConvocatoria(ProyectosConvocatoriaPK proyectosConvocatoriaPK, String idProyecto) {
        this.proyectosConvocatoriaPK = proyectosConvocatoriaPK;
        this.idProyecto = idProyecto;
    }

    public ProyectosConvocatoria(int proyectos, int convocatoria) {
        this.proyectosConvocatoriaPK = new ProyectosConvocatoriaPK(proyectos, convocatoria);
    }

    public ProyectosConvocatoriaPK getProyectosConvocatoriaPK() {
        return proyectosConvocatoriaPK;
    }

    public void setProyectosConvocatoriaPK(ProyectosConvocatoriaPK proyectosConvocatoriaPK) {
        this.proyectosConvocatoriaPK = proyectosConvocatoriaPK;
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
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
        hash += (proyectosConvocatoriaPK != null ? proyectosConvocatoriaPK.hashCode() : 0);
        return hash; 
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProyectosConvocatoria)) {
            return false;
        }
        ProyectosConvocatoria other = (ProyectosConvocatoria) object;
        if ((this.proyectosConvocatoriaPK == null && other.proyectosConvocatoriaPK != null) || (this.proyectosConvocatoriaPK != null && !this.proyectosConvocatoriaPK.equals(other.proyectosConvocatoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
    public JSONObject toJson() {
    	JSONObject proyectosConovocatoriaJson=new JSONObject();
    	if(this.getProyecto()==null) {
    		proyectosConovocatoriaJson.put("Convocatoria", "esta convocatoria no tiene proyectos asociados");
    	}else {
    	proyectosConovocatoriaJson.put("id_proyecto",this.getProyecto().getId());
    	proyectosConovocatoriaJson.put("titulo_proyecto",this.getProyecto().getTitulo());
    	proyectosConovocatoriaJson.put("descripcion_proyecto",this.getProyecto().getDescripcion());
    	proyectosConovocatoriaJson.put("estado_proyecto",this.getProyecto().getEstado());
    	proyectosConovocatoriaJson.put("convocatoria",this.getConvocatoria().getNombreConvocatoria());
    	}
    	return proyectosConovocatoriaJson;
    }

}
