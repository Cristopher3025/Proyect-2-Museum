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
@Table(name = "TIPOSTARJETA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipostarjeta.findAll", query = "SELECT t FROM Tipostarjeta t"),
    @NamedQuery(name = "Tipostarjeta.findByIdtipotarjeta", query = "SELECT t FROM Tipostarjeta t WHERE t.idtipotarjeta = :idtipotarjeta"),
    @NamedQuery(name = "Tipostarjeta.findByNombre", query = "SELECT t FROM Tipostarjeta t WHERE t.nombre = :nombre")})
public class Tipostarjeta implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDTIPOTARJETA")
    private BigDecimal idtipotarjeta;
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(mappedBy = "tipotarjetaid")
    private Collection<Comisionestarjetas> comisionestarjetasCollection;

    public Tipostarjeta() {
    }

    public Tipostarjeta(BigDecimal idtipotarjeta) {
        this.idtipotarjeta = idtipotarjeta;
    }

    public BigDecimal getIdtipotarjeta() {
        return idtipotarjeta;
    }

    public void setIdtipotarjeta(BigDecimal idtipotarjeta) {
        this.idtipotarjeta = idtipotarjeta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Comisionestarjetas> getComisionestarjetasCollection() {
        return comisionestarjetasCollection;
    }

    public void setComisionestarjetasCollection(Collection<Comisionestarjetas> comisionestarjetasCollection) {
        this.comisionestarjetasCollection = comisionestarjetasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipotarjeta != null ? idtipotarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipostarjeta)) {
            return false;
        }
        Tipostarjeta other = (Tipostarjeta) object;
        if ((this.idtipotarjeta == null && other.idtipotarjeta != null) || (this.idtipotarjeta != null && !this.idtipotarjeta.equals(other.idtipotarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    
}
