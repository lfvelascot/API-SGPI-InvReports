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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Semillero.findAll", query = "SELECT s FROM Semillero s")
    , @NamedQuery(name = "Semillero.findById", query = "SELECT s FROM Semillero s WHERE s.id = :id")
    , @NamedQuery(name = "Semillero.findByNombre", query = "SELECT s FROM Semillero s WHERE s.nombre = :nombre")
    , @NamedQuery(name = "Semillero.findByDescripcion", query = "SELECT s FROM Semillero s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "Semillero.findByFechaFun", query = "SELECT s FROM Semillero s WHERE s.fechaFun = :fechaFun")})
public class Semillero implements Serializable {

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
    @Column(nullable = false, length = 45)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fecha_fun", nullable = false, columnDefinition = "DATE")
    private LocalDate fechaFun;
    @ManyToMany(mappedBy = "semilleros")
    private List<Programa> programas;
    @OneToMany(mappedBy = "semillero")
    private List<Proyecto> proyectos;
    @JoinColumn(name = "grupo_investigacion", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private GrupoInvestigacion grupoInvestigacion;
    @JoinColumn(name = "linea_investigacion", referencedColumnName = "nombre", nullable = false)
    @ManyToOne(optional = false)
    private LineaInvestigacion lineaInvestigacion;
    @JoinColumn(name = "lider_semillero", referencedColumnName = "cedula", nullable = true)
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Usuario liderSemillero;
    @OneToMany(mappedBy = "semilleroId")
    private List<Usuario> usuarios;

    public Semillero() {
    }

    public Semillero(Integer id) {
        this.id = id;
    }

    public Semillero( String nombre, String descripcion, LocalDate fechaFun) {
    
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaFun = fechaFun;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaFun() {
        return fechaFun;
    }

    public void setFechaFun(LocalDate fechaFun) {
        this.fechaFun = fechaFun;
    }

    @XmlTransient
    public List<Programa> getProgramas() {
        return programas;
    }

    public void setProgramas(List<Programa> programas) {
        this.programas = programas;
    }
    public Programa addPrograma(Programa programa) {
		getProgramas().add(programa);
		programa.addSemillero(this);	
		return programa;
	}

	public Programa removePrograma(Programa programa) {
		getProgramas().remove(programa);
		programa.removeSemillero(this);
		return programa;
		
	}
    @XmlTransient
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public GrupoInvestigacion getGrupoInvestigacion() {
        return grupoInvestigacion;
    }

    public void setGrupoInvestigacion(GrupoInvestigacion grupoInvestigacion) {
        this.grupoInvestigacion = grupoInvestigacion;
    }
    

    public LineaInvestigacion getLineaInvestigacion() {
        return lineaInvestigacion;
    }

    public void setLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
        this.lineaInvestigacion = lineaInvestigacion;
    }

    public Usuario getLiderSemillero() {
        return liderSemillero;
    }

    public void setLiderSemillero(Usuario liderSemillero) {
        this.liderSemillero = liderSemillero;
    }

    @XmlTransient
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    public Usuario addUsuario(Usuario usuario) {
    	getUsuarios().add(usuario);
    	usuario.addsemillero(this);
    	return usuario;
    }
    public Usuario removeUsuario(Usuario usuario) {
    	getUsuarios().remove(usuario);
    	usuario.addsemillero(null);
    	return usuario;
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
        if (!(object instanceof Semillero)) {
            return false;
        }
        Semillero other = (Semillero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpi.model.Semillero[ id=" + id + " ]";
    }
    public JSONObject toJson() {
    	JSONObject semilleroJson=new JSONObject();
    	semilleroJson.put("id",this.getId());
    	semilleroJson.put("nombre",this.getNombre());
    	semilleroJson.put("descripcion",this.getDescripcion());
    	semilleroJson.put("fecha_fun",this.getFechaFun());
    	if(this.getGrupoInvestigacion()==null) {
    		semilleroJson.put("grupo_investigacion","NO TIENE GRUPO DE INVESTIGACIÓN");
    	}else {
    		semilleroJson.put("grupo_investigacion",this.getGrupoInvestigacion().getNombre());
    		semilleroJson.put("grupo_investigacion_id",this.getGrupoInvestigacion().getId());
    	}
    	if(this.getLineaInvestigacion()==null) {
    		semilleroJson.put("linea_investigacion","NO TIENE LINEA DE INVESTIGACIÓN");
    	}else {
    		semilleroJson.put("linea_investigacion",this.getLineaInvestigacion().getNombre());
    		
    	}

    	if(this.getLiderSemillero()==null) {
    		semilleroJson.put("lider_semillero","NO TIENE LIDER DE SEMILLERO");
    	}else {
    		semilleroJson.put("lider_semillero",this.getLiderSemillero().getNombres());
    	}
    	
    	
    	return semilleroJson;
    }

	

	

	
    
}
