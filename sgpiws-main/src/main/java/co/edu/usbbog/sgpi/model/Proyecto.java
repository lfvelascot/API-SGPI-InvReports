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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
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
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
    , @NamedQuery(name = "Proyecto.findById", query = "SELECT p FROM Proyecto p WHERE p.id = :id")
    , @NamedQuery(name = "Proyecto.findByTitulo", query = "SELECT p FROM Proyecto p WHERE p.titulo = :titulo")
    , @NamedQuery(name = "Proyecto.findByEstado", query = "SELECT p FROM Proyecto p WHERE p.estado = :estado")
    , @NamedQuery(name = "Proyecto.findByFechaInicio", query = "SELECT p FROM Proyecto p WHERE p.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Proyecto.findByFechaFin", query = "SELECT p FROM Proyecto p WHERE p.fechaFin = :fechaFin")
    , @NamedQuery(name = "Proyecto.findByVisibilidad", query = "SELECT p FROM Proyecto p WHERE p.visibilidad = :visibilidad")
    , @NamedQuery(name = "Proyecto.findByCiudad", query = "SELECT p FROM Proyecto p WHERE p.ciudad = :ciudad")})
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String titulo;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String estado;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fecha_inicio", nullable = false, columnDefinition = "DATE")
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin", columnDefinition = "DATE")
    private LocalDate fechaFin;
    @Lob
    @Column(name = "retroalimentacion_final", length = 2147483647)
    private String retroalimentacionFinal;
    @Basic(optional = false)
    @Column(nullable = false)
    private short visibilidad;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String ciudad;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String metodologia;
    @Lob
    @Column(length = 2147483647)
    private String conclusiones;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String justificacion;
    @ManyToMany(mappedBy = "proyectos")
    private List<AreaConocimiento> areasConocimiento;
    @ManyToMany(mappedBy = "proyectos")
    private List<Clase> clases;
    @JoinTable(name = "antecedentes", joinColumns = {
        @JoinColumn(name = "proyecto", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "ancedente", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private List<Proyecto> antecedentes;
    @ManyToMany(mappedBy = "antecedentes")
    private List<Proyecto> proyectos;
    @JoinColumn(name = "macro_proyecto", referencedColumnName = "id", nullable = true)
    @ManyToOne
    private MacroProyecto macroProyecto;
    @JoinColumn(name = "semillero", referencedColumnName = "id")
    @ManyToOne
    private Semillero semillero;
    @JoinColumn(name = "tipo_proyecto", referencedColumnName = "nombre", nullable = false)
    @ManyToOne(optional = false)
    private TipoProyecto tipoProyecto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    private List<ProyectosConvocatoria> proyectosConvocatoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    private List<Producto> productos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    private List<Participaciones> participaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    private List<Presupuesto> presupuestos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    private List<Participantes> participantes;

    public Proyecto() {
    }

    public Proyecto(Integer id) {
        this.id = id;
    }

    public Proyecto( String titulo, String estado, String descripcion, LocalDate fechaInicio, short visibilidad, String ciudad, String metodologia, String justificacion) {
        this.titulo = titulo;
        this.estado = estado;
        this.descripcion = descripcion; 
        this.fechaInicio = fechaInicio;
        this.visibilidad = visibilidad;
        this.ciudad = ciudad;
        this.metodologia = metodologia;
        this.justificacion = justificacion;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getRetroalimentacionFinal() {
        return retroalimentacionFinal;
    }

    public void setRetroalimentacionFinal(String retroalimentacionFinal) {
        this.retroalimentacionFinal = retroalimentacionFinal;
    }

    public short getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(short visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public String getConclusiones() {
        return conclusiones;
    }

    public void setConclusiones(String conclusiones) {
        this.conclusiones = conclusiones;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    @XmlTransient
    public List<AreaConocimiento> getAreasConocimiento() {
        return areasConocimiento;
    }

    public void setAreasConocimiento(List<AreaConocimiento> areasConocimiento) {
        this.areasConocimiento = areasConocimiento;
    }
    public AreaConocimiento addAreaConocimiento(AreaConocimiento areaConocimiento) {
		getAreasConocimiento().add(areaConocimiento);
		areaConocimiento.addProyecto(this);		
		return areaConocimiento;
	}
    public AreaConocimiento removeAreaConocimiento(AreaConocimiento areaConocimiento) {
		getAreasConocimiento().remove(areaConocimiento);
		areaConocimiento.removeProyecto(null);		
		return areaConocimiento;
	}

	
    @XmlTransient
    public List<Clase> getClases() {
        return clases;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }

	public Clase addClases(Clase clase) {
		getClases().add(clase);
		clase.addProyectos(this);
		return clase;
	}

	public Clase removeClases(Clase clase) {
		getClases().remove(clase);
		clase.removeProyectos(this);
		return clase;
		
	}
    @XmlTransient
    public List<Proyecto> getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(List<Proyecto> antecedentes) {
        this.antecedentes = antecedentes;
    }

    @XmlTransient
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
    
   

    public MacroProyecto getMacroProyecto() {
        return macroProyecto;
    }

    public void setMacroProyecto(MacroProyecto macroProyecto) {
        this.macroProyecto = macroProyecto;
    }

    public Semillero getSemillero() {
        return semillero;
    }

    public void setSemillero(Semillero semillero) {
        this.semillero = semillero;
    }

    public TipoProyecto getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(TipoProyecto tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    @XmlTransient
    public List<ProyectosConvocatoria> getProyectosConvocatoria() {
        return proyectosConvocatoria;
    }

    public void setProyectosConvocatoriaList(List<ProyectosConvocatoria> proyectosConvocatoria) {
        this.proyectosConvocatoria = proyectosConvocatoria;
    }
   
    @XmlTransient
    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    public Producto addProducto(Producto producto) {
    	getProductos().add(producto);
    	producto.setProyecto(this);
    	return producto;
    }
    public Producto removeProducto(Producto producto) {
    	getProductos().remove(producto);
    	producto.setProyecto(null);
    	return producto;
    }

    @XmlTransient
    public List<Participaciones> getParticipaciones() {
        return participaciones;
    }

    public void setParticipaciones(List<Participaciones> participaciones) {
        this.participaciones = participaciones;
    }
    public Participaciones addParticipaciones(Participaciones participaciones) {
    	getParticipaciones().add(participaciones);
    	participaciones.setProyecto(this);
    	return participaciones;
    }
    public Participaciones removeParticipaciones(Participaciones participaciones) {
    	getParticipaciones().remove(participaciones);
    	participaciones.setProyecto(null);
    	return participaciones;
    }

    @XmlTransient
    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(List<Presupuesto> presupuesto) {
        this.presupuestos = presupuesto;
    }
    public Presupuesto addPresupuesto (Presupuesto presupuesto) {
    	getPresupuestos().add(presupuesto);
    	presupuesto.setProyecto(this);
    	return presupuesto;
    }
    public Presupuesto removePresupuesto (Presupuesto presupuesto) {
    	getPresupuestos().remove(presupuesto);
    	presupuesto.setProyecto(null);
    	return presupuesto;
    }


    @XmlTransient
    public List<Participantes> getParticipantes() {
        return participantes;
    }
    
    public void setParticipantes(List<Participantes> participantes) {
        this.participantes = participantes;
    }
    public Participantes addParticipantes (Participantes participantes) {
    	getParticipantes().add(participantes);
    	participantes.setProyecto(this);
    	return participantes;
    }
    public Participantes removeParticipantes (Participantes participantes) {
    	getParticipantes().remove(participantes);
    	participantes.setProyecto(null);
    	return participantes;
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
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
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
    	JSONObject proyectoJson=new JSONObject();
    	proyectoJson.put("id",this.getId());
    	proyectoJson.put("titulo",this.getTitulo());
    	proyectoJson.put("estado",this.getEstado());
    	proyectoJson.put("descripcion",this.getDescripcion());
    	proyectoJson.put("fecha_inicio",this.getFechaInicio());
    	proyectoJson.put("fecha_fin",this.getFechaFin());
    	proyectoJson.put("retroalimentacion_final",this.getRetroalimentacionFinal());
    	proyectoJson.put("visibilidad",this.getVisibilidad());
    	proyectoJson.put("ciudad",this.getCiudad());
    	proyectoJson.put("metodologia",this.getMetodologia());
    	proyectoJson.put("conclusiones",this.getConclusiones());
    	proyectoJson.put("justificacion",this.getJustificacion());
    	if(this.getSemillero()==null) {
    	proyectoJson.put("semillero","este proyecto no tiene asociado ningun semillero");
    	}else {
    		proyectoJson.put("semillero", this.getSemillero().getNombre());
    	}
    	return proyectoJson;
    }


}
