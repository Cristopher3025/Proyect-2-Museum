/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.museo.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 *
 * @author crist
 */
@Entity
@Table(name = "EPOCAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Epocas.findAll", query = "SELECT e FROM Epocas e"),
    @NamedQuery(name = "Epocas.findByIdepoca", query = "SELECT e FROM Epocas e WHERE e.idepoca = :idepoca"),
    @NamedQuery(name = "Epocas.findByNombre", query = "SELECT e FROM Epocas e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Epocas.findByDescripcion", query = "SELECT e FROM Epocas e WHERE e.descripcion = :descripcion")})
public class Epocas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDEPOCA")
    private BigDecimal idepoca;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "epocaid")
    private Collection<Tematicas> tematicasCollection;
    @OneToMany(mappedBy = "epocaid")
    private Collection<Especies> especiesCollection;

    public Epocas() {
    }

    public Epocas(BigDecimal idepoca) {
        this.idepoca = idepoca;
    }

    public BigDecimal getIdepoca() {
        return idepoca;
    }

    public void setIdepoca(BigDecimal idepoca) {
        this.idepoca = idepoca;
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

    @XmlTransient
    public Collection<Tematicas> getTematicasCollection() {
        return tematicasCollection;
    }

    public void setTematicasCollection(Collection<Tematicas> tematicasCollection) {
        this.tematicasCollection = tematicasCollection;
    }

    @XmlTransient
    public Collection<Especies> getEspeciesCollection() {
        return especiesCollection;
    }

    public void setEspeciesCollection(Collection<Especies> especiesCollection) {
        this.especiesCollection = especiesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idepoca != null ? idepoca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Epocas)) {
            return false;
        }
        Epocas other = (Epocas) object;
        if ((this.idepoca == null && other.idepoca != null) || (this.idepoca != null && !this.idepoca.equals(other.idepoca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Epocas[ idepoca=" + idepoca + " ]";
    }
    
}
