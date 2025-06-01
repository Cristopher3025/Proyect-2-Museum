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
@Table(name = "TIPOSMUSEO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiposmuseo.findAll", query = "SELECT t FROM Tiposmuseo t"),
    @NamedQuery(name = "Tiposmuseo.findByIdtipomuseo", query = "SELECT t FROM Tiposmuseo t WHERE t.idtipomuseo = :idtipomuseo"),
    @NamedQuery(name = "Tiposmuseo.findByNombre", query = "SELECT t FROM Tiposmuseo t WHERE t.nombre = :nombre")})
public class Tiposmuseo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDTIPOMUSEO")
    private BigDecimal idtipomuseo;
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(mappedBy = "tipomuseoid")
    private Collection<Museos> museosCollection;

    public Tiposmuseo() {
    }

    public Tiposmuseo(BigDecimal idtipomuseo) {
        this.idtipomuseo = idtipomuseo;
    }

    public BigDecimal getIdtipomuseo() {
        return idtipomuseo;
    }

    public void setIdtipomuseo(BigDecimal idtipomuseo) {
        this.idtipomuseo = idtipomuseo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Museos> getMuseosCollection() {
        return museosCollection;
    }

    public void setMuseosCollection(Collection<Museos> museosCollection) {
        this.museosCollection = museosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipomuseo != null ? idtipomuseo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiposmuseo)) {
            return false;
        }
        Tiposmuseo other = (Tiposmuseo) object;
        if ((this.idtipomuseo == null && other.idtipomuseo != null) || (this.idtipomuseo != null && !this.idtipomuseo.equals(other.idtipomuseo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    
}
