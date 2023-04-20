/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbbog.sgpi.model;

import java.io.Serializable;
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
@Table(name = "detalle_convocatoria", catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleConvocatoria.findAll", query = "SELECT d FROM DetalleConvocatoria d")
    , @NamedQuery(name = "DetalleConvocatoria.findById", query = "SELECT d FROM DetalleConvocatoria d WHERE d.id = :id")
    , @NamedQuery(name = "DetalleConvocatoria.findByObjetivosConvocatoria", query = "SELECT d FROM DetalleConvocatoria d WHERE d.objetivosConvocatoria = :objetivosConvocatoria")
    , @NamedQuery(name = "DetalleConvocatoria.findByRequisitos", query = "SELECT d FROM DetalleConvocatoria d WHERE d.requisitos = :requisitos")
    , @NamedQuery(name = "DetalleConvocatoria.findByModalidade", query = "SELECT d FROM DetalleConvocatoria d WHERE d.modalidade = :modalidade")})
public class DetalleConvocatoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "objetivos_convocatoria", nullable = false, length = 45)
    private String objetivosConvocatoria;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String requisitos;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String modalidade;
    @JoinColumn(name = "convocatoria_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Convocatoria convocatoriaId;

    public DetalleConvocatoria() {
    }

    public DetalleConvocatoria(Integer id) {
        this.id = id;
    }

    public DetalleConvocatoria(String objetivosConvocatoria, String requisitos, String modalidade) {
        this.objetivosConvocatoria = objetivosConvocatoria;
        this.requisitos = requisitos;
        this.modalidade = modalidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjetivosConvocatoria() {
        return objetivosConvocatoria;
    }

    public void setObjetivosConvocatoria(String objetivosConvocatoria) {
        this.objetivosConvocatoria = objetivosConvocatoria;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public Convocatoria getConvocatoriaId() {
        return convocatoriaId;
    }

    public void setConvocatoriaId(Convocatoria convocatoriaId) {
        this.convocatoriaId = convocatoriaId;
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
        if (!(object instanceof DetalleConvocatoria)) {
            return false;
        }
        DetalleConvocatoria other = (DetalleConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.DetalleConvocatoria[ id=" + id + " ]";
    }
    public JSONObject toJson() {
    	JSONObject detalleConvocatoriaJson=new JSONObject();
    	detalleConvocatoriaJson.put("id",this.getId());
    	detalleConvocatoriaJson.put("objetivos_convocatoria",this.getObjetivosConvocatoria());
    	detalleConvocatoriaJson.put("requisitos",this.getRequisitos());
    	detalleConvocatoriaJson.put("modalidade",this.getModalidade());
    	detalleConvocatoriaJson.put("convocatoria_id",this.getConvocatoriaId());
    	
    	return detalleConvocatoriaJson;
    	
    }

	
	
	

	
    
}
