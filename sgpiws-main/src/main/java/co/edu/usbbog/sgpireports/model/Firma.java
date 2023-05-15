/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.usbbog.sgpireports.model;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import co.edu.usbbog.sgpi.model.Usuario;

/**
 *
 * @author SIGUSBBOG
 */
@Entity
@Table(catalog = "sgpi_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firma.findAll", query = "SELECT f FROM Firma f"),
    @NamedQuery(name = "Firma.findByUsuario", query = "SELECT f FROM Firma f WHERE f.usuario = :usuario"),
    @NamedQuery(name = "Firma.findByNombre", query = "SELECT f FROM Firma f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "Firma.findByUrl", query = "SELECT f FROM Firma f WHERE f.url = :url")})
public class Firma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false, length = 20)
    private String usuario;
    @Basic(optional = false)
    @Column(nullable = false, length = 350)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String url;
    @JoinColumn(name = "usuario", referencedColumnName = "cedula", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario1;

    public Firma() {
    }

    public Firma(String usuario) {
        this.usuario = usuario;
    }

    public Firma(String usuario, String nombre, String url) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Firma)) {
            return false;
        }
        Firma other = (Firma) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbbog.sgpireports.model.Firma[ usuario=" + usuario + " ]";
    }
}
