/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbbog.sgpi.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @NamedQuery(name = "Clase.findAll", query = "SELECT c FROM Clase c")
    , @NamedQuery(name = "Clase.findByNumero", query = "SELECT c FROM Clase c WHERE c.numero = :numero")
    , @NamedQuery(name = "Clase.findByNombre", query = "SELECT c FROM Clase c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Clase.findBySemestre", query = "SELECT c FROM Clase c WHERE c.semestre = :semestre")})
public class Clase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer numero;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String semestre;
    @JoinTable(name = "proyectos_clase", joinColumns = {
        @JoinColumn(name = "clase", referencedColumnName = "numero", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "proyecto", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Proyecto> proyectos;
    @JoinColumn(name = "materia", referencedColumnName = "catalogo", nullable = false)
    @ManyToOne(optional = false)
    private Materia materia;
    @JoinColumn(name = "profesor", referencedColumnName = "cedula", nullable = true)
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Usuario profesor;

    public Clase() {
    }

    public Clase(Integer numero) {
        this.numero = numero;
    }

    public Clase(Integer numero, String nombre, String semestre) {
        this.numero = numero;
        this.nombre = nombre;
        this.semestre = semestre;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    @XmlTransient
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
    public Proyecto addProyectos(Proyecto proyecto) {
    	getProyectos().add(proyecto);    	
    	proyecto.addClases(this);    	
    	return proyecto;
    }
    public Proyecto removeProyectos(Proyecto proyecto) {
    	getProyectos().remove(proyecto);    	  	
    	proyecto.removeClases(null);    	
    	return proyecto;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clase)) {
            return false;
        }
        Clase other = (Clase) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.Clase[ numero=" + numero + " ]";
    }
    public JSONObject toJson() {
    	JSONObject claseJson=new JSONObject();
    	claseJson.put("numero",this.getNumero());
    	claseJson.put("nombre",this.getNombre());
    	claseJson.put("semestre",this.getSemestre());
    	claseJson.put("materia",this.getMateria().getNombre());  
    	claseJson.put("materia_id",this.getMateria().getCatalogo());  
    	if(this.getProfesor() == null) { 		
    		claseJson.put("profesor","");
    	}else {
    		claseJson.put("profesor",this.getProfesor().getNombres());
    	}
       	return claseJson;  	
    }
    
}
