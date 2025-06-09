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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "COLECCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Colecciones.findAll", query = "SELECT c FROM Colecciones c"),
    @NamedQuery(name = "Colecciones.findByIdcoleccion", query = "SELECT c FROM Colecciones c WHERE c.idcoleccion = :idcoleccion"),
    @NamedQuery(name = "Colecciones.findByNombre", query = "SELECT c FROM Colecciones c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Colecciones.findBySiglo", query = "SELECT c FROM Colecciones c WHERE c.siglo = :siglo"),
    @NamedQuery(name = "Colecciones.findByObservacion", query = "SELECT c FROM Colecciones c WHERE c.observacion = :observacion")})
public class Colecciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCOLECCION")
    private BigDecimal idcoleccion;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "SIGLO")
    private String siglo;
    @Column(name = "OBSERVACION")
    private String observacion;
    @OneToMany(mappedBy = "coleccionid")
    private Collection<Especies> especiesCollection;
    @JoinColumn(name = "SALAID", referencedColumnName = "IDSALA")
    @ManyToOne
    private Salas salaid;

    public Colecciones() {
    }

    public Colecciones(BigDecimal idcoleccion) {
        this.idcoleccion = idcoleccion;
    }

    public BigDecimal getIdcoleccion() {
        return idcoleccion;
    }

    public void setIdcoleccion(BigDecimal idcoleccion) {
        this.idcoleccion = idcoleccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglo() {
        return siglo;
    }

    public void setSiglo(String siglo) {
        this.siglo = siglo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @XmlTransient
    public Collection<Especies> getEspeciesCollection() {
        return especiesCollection;
    }

    public void setEspeciesCollection(Collection<Especies> especiesCollection) {
        this.especiesCollection = especiesCollection;
    }

    public Salas getSalaid() {
        return salaid;
    }

    public void setSalaid(Salas salaid) {
        this.salaid = salaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcoleccion != null ? idcoleccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colecciones)) {
            return false;
        }
        Colecciones other = (Colecciones) object;
        if ((this.idcoleccion == null && other.idcoleccion != null) || (this.idcoleccion != null && !this.idcoleccion.equals(other.idcoleccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    
}
