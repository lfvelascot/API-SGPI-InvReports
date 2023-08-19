/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbbog.sgpireports.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.minidev.json.JSONObject;

/**
 *
 * @author 57310
 */
@Entity
@Table(catalog = "ingusb_sgpi_bd", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cod_universitario"}),
    @UniqueConstraint(columnNames = {"correo_est"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByCedula", query = "SELECT u FROM Usuario u WHERE u.cedula = :cedula"),
    @NamedQuery(name = "Usuario.findByCodUniversitario", query = "SELECT u FROM Usuario u WHERE u.codUniversitario = :codUniversitario"),
    @NamedQuery(name = "Usuario.findByCorreoEst", query = "SELECT u FROM Usuario u WHERE u.correoEst = :correoEst"),
    @NamedQuery(name = "Usuario.findByContrasena", query = "SELECT u FROM Usuario u WHERE u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuario.findByNombres", query = "SELECT u FROM Usuario u WHERE u.nombres = :nombres"),
    @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Usuario.findByVisibilidad", query = "SELECT u FROM Usuario u WHERE u.visibilidad = :visibilidad"),
    @NamedQuery(name = "Usuario.findByCorreoPersonal", query = "SELECT u FROM Usuario u WHERE u.correoPersonal = :correoPersonal")})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(nullable = false, length = 20)
	private String cedula;
	@Basic(optional = false)
    @Column(name = "cod_universitario", nullable = false)
    private long codUniversitario;
	@Basic(optional = false, fetch = FetchType.LAZY)
	@Column(name = "correo_est", nullable = true, length = 45)
	private String correoEst;
	@Basic(optional = false)
	@Column(nullable = false, length = 100)
	private String contrasena;
	@Basic(optional = false)
	@Column(nullable = false, length = 100)
	private String nombres;
	@Basic(optional = false)
	@Column(nullable = false, length = 100)
	private String apellidos;
	@Column(length = 45)
	private String telefono;
	@Basic(optional = false)
	@Column(name = "visibilidad")
	private short visibilidad;
	@Column(name = "correo_personal", length = 45)
	private String correoPersonal;
	@ManyToMany(mappedBy = "usuarios")
	private List<TipoUsuario> tiposUsuario;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "directorGrupo")
	private List<GrupoInvestigacion> gruposInvestigacion;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "director")
	private List<Programa> programas;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "liderSemillero")
	private List<Semillero> semilleros;
	@JoinColumn(name = "programa_id", referencedColumnName = "id", nullable = true)
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Programa programaId;
	@JoinColumn(name = "semillero_id", referencedColumnName = "id", nullable = true)
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Semillero semilleroId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "coorInv")
	private List<Facultad> coorIncFacultad;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "decano")
	private List<Facultad> decanoFacultad;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
	private List<Participantes> participantes;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
	private Firma firma;

	public Usuario() {
	}

	public Usuario(String cedula) {
		this.cedula = cedula;
	}

	public Usuario(String cedula, Long codUniversitario, String correoEst, String contrasena, String nombres,
			String apellidos, short visiblidad) {
		this.cedula = cedula;
		this.codUniversitario = codUniversitario;
		this.correoEst = correoEst;
		this.contrasena = contrasena;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.visibilidad = visiblidad;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

    public long getCodUniversitario() {
        return codUniversitario;
    }

    public void setCodUniversitario(long codUniversitario) {
        this.codUniversitario = codUniversitario;
    }

	public String getCorreoEst() {
		return correoEst;
	}

	public void setCorreoEst(String correoEst) {
		this.correoEst = correoEst;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		if (this.telefono == null) {
			return "sin numero registrado";
		} else {
			return this.telefono;
		}
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public short getVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(short visibilidad) {
		this.visibilidad = visibilidad;
	}

	public String getCorreoPersonal() {
		return correoPersonal;
	}

	public void setCorreoPersonal(String correoPersonal) {
		this.correoPersonal = correoPersonal;
	}

	@XmlTransient
	public List<TipoUsuario> getTiposUsuario() {
		return tiposUsuario;
	}

	public void setTiposUsuario(List<TipoUsuario> tiposUsuario) {
		this.tiposUsuario = tiposUsuario;
	}

	@XmlTransient
	public List<GrupoInvestigacion> getGruposInvestigacion() {
		return gruposInvestigacion;
	}

	public void setGruposInvestigacion(List<GrupoInvestigacion> grupoInvestigacion) {
		this.gruposInvestigacion = grupoInvestigacion;
	}


	@XmlTransient
	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programa) {
		this.programas = programa;
	}

	@XmlTransient
	public List<Semillero> getSemilleros() {
		return semilleros;
	}

	public void setSemilleros(List<Semillero> semillero) {
		this.semilleros = semillero;
	}

	public Programa getProgramaId() {
		return programaId;
	}

	public void setProgramaId(Programa programaId) {
		this.programaId = programaId;
	}

	public Semillero getSemilleroId() {
		return semilleroId;
	}

	public void setSemilleroId(Semillero semilleroId) {
		this.semilleroId = semilleroId;
	}

	@XmlTransient
	public List<Facultad> getCoorIncFacultad() {
		return coorIncFacultad;
	}

	public void setCoorInvFacultad(List<Facultad> coorInvFacultad) {
		this.coorIncFacultad = coorInvFacultad;
	}

	@XmlTransient
	public List<Facultad> getDecanoFacultad() {
		return decanoFacultad;
	}

	public void setDecanoFacultad(List<Facultad> deFacultad) {
		this.decanoFacultad = deFacultad;
	}

	@XmlTransient
	public List<Participantes> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Participantes> participantes) {
		this.participantes = participantes;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cedula != null ? cedula.hashCode() : 0);
		return hash;
	}

	public Firma getFirma() {
		return firma;
	}

	public void setFirma(Firma firma) {
		this.firma = firma;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) object;
		if ((this.cedula == null && other.cedula != null)
				|| (this.cedula != null && !this.cedula.equals(other.cedula))) {
			return false;
		}
		return true;
	}

	public JSONObject toJson() {
		JSONObject usuarioJson = new JSONObject();
		usuarioJson.put("cedula", this.getCedula());
		usuarioJson.put("cod_Universitario", this.getCodUniversitario());
		usuarioJson.put("correo_est", this.getCorreoEst());
		usuarioJson.put("contrasena", this.getCedula());
		usuarioJson.put("nombres", this.getNombres());
		usuarioJson.put("apellidos", this.getApellidos());
		if (this.getTelefono() == null) {
			usuarioJson.put("telefono", "Sin registro");
		} else {
			usuarioJson.put("telefono", this.getTelefono());
		}
		usuarioJson.put("visbilidad", this.getVisibilidad());
		usuarioJson.put("correo_personal", this.getCorreoPersonal());

		if (this.getTiposUsuario().isEmpty()) {
			usuarioJson.put("rol", "");
		} else {
			usuarioJson.put("rol", this.getTiposUsuario().get(0).getNombre());
		}
		if (this.getSemilleroId() == null) {
			usuarioJson.put("semillero_id", "");
		} else {
			usuarioJson.put("semillero", this.getSemilleroId().getNombre());
			usuarioJson.put("semillero_id", this.getSemilleroId().getId());
		}
		if (this.getProgramaId() == null) {
			usuarioJson.put("programa", "");
		} else {
			usuarioJson.put("programa", this.getProgramaId().getNombre());
		}
		return usuarioJson;

	}

	@Override
	public String toString() {
		return toJson().toString();
	}

	public String getNombreCompleto() {
		return this.getNombres() + " " + this.getApellidos();
	}

}
