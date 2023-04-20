/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbbog.sgpi.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @NamedQuery(name = "Materia.findAll", query = "SELECT m FROM Materia m")
    , @NamedQuery(name = "Materia.findByCatalogo", query = "SELECT m FROM Materia m WHERE m.catalogo = :catalogo")
    , @NamedQuery(name = "Materia.findByNombre", query = "SELECT m FROM Materia m WHERE m.nombre = :nombre")})
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String catalogo;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<Clase> clases;
    @JoinColumn(name = "programa", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Programa programa;

    public Materia() {
    }

    public Materia(String catalogo) {
        this.catalogo = catalogo;
    }

    public Materia(String catalogo, String nombre) {
        this.catalogo = catalogo;
        this.nombre = nombre;
    }

    public String getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(String catalogo) {
        this.catalogo = catalogo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Clase> getClases() {
        return clases;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }
    public Clase addClase(Clase clase) {
    	getClases().add(clase);
    	clase.setMateria(this);
    	return clase;
    }
    public Clase removeClase(Clase clase) {
    	getClases().remove(clase);
    	clase.setMateria(null);
    	return clase;
    }
    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catalogo != null ? catalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.catalogo == null && other.catalogo != null) || (this.catalogo != null && !this.catalogo.equals(other.catalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.Materia[ catalogo=" + catalogo + " ]";
    }
    public JSONObject toJson() {
    	JSONObject materiaJson=new JSONObject();
    	materiaJson.put("catalogo",this.getCatalogo());
    	materiaJson.put("nombre",this.getNombre());

    	if (this.getPrograma()==null) {
    		materiaJson.put("programa","esta materia no cuenta con un programa");
		}else {
			materiaJson.put("programa",this.getPrograma().getNombre());
			materiaJson.put("programa_id",this.getPrograma().getId());
		}

    	return materiaJson;
    }
}
