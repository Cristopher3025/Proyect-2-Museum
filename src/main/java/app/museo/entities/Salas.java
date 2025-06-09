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
@Table(name = "SALAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salas.findAll", query = "SELECT s FROM Salas s"),
    @NamedQuery(name = "Salas.findByIdsala", query = "SELECT s FROM Salas s WHERE s.idsala = :idsala"),
    @NamedQuery(name = "Salas.findByNombre", query = "SELECT s FROM Salas s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Salas.findByDescripcion", query = "SELECT s FROM Salas s WHERE s.descripcion = :descripcion")})
public class Salas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDSALA")
    private BigDecimal idsala;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "MUSEOID", referencedColumnName = "IDMUSEO")
    @ManyToOne
    private Museos museoid;
    @OneToMany(mappedBy = "salaid")
    private Collection<Promediosvaloraciones> promediosvaloracionesCollection;
    @OneToMany(mappedBy = "salaid")
    private Collection<Precios> preciosCollection;
    @OneToMany(mappedBy = "salaid")
    private Collection<Valoracionessalas> valoracionessalasCollection;
    @OneToMany(mappedBy = "salaid")
    private Collection<Tematicas> tematicasCollection;
    @OneToMany(mappedBy = "salaid")
    private Collection<Entradassalas> entradassalasCollection;
    @OneToMany(mappedBy = "salaid")
    private Collection<Colecciones> coleccionesCollection;
    @OneToMany(mappedBy = "salaid")
    private Collection<Validacionesentradas> validacionesentradasCollection;

    public Salas() {
    }

    public Salas(BigDecimal idsala) {
        this.idsala = idsala;
    }

    public BigDecimal getIdsala() {
        return idsala;
    }

    public void setIdsala(BigDecimal idsala) {
        this.idsala = idsala;
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

    public Museos getMuseoid() {
        return museoid;
    }

    public void setMuseoid(Museos museoid) {
        this.museoid = museoid;
    }

    @XmlTransient
    public Collection<Promediosvaloraciones> getPromediosvaloracionesCollection() {
        return promediosvaloracionesCollection;
    }

    public void setPromediosvaloracionesCollection(Collection<Promediosvaloraciones> promediosvaloracionesCollection) {
        this.promediosvaloracionesCollection = promediosvaloracionesCollection;
    }

    @XmlTransient
    public Collection<Precios> getPreciosCollection() {
        return preciosCollection;
    }

    public void setPreciosCollection(Collection<Precios> preciosCollection) {
        this.preciosCollection = preciosCollection;
    }

    @XmlTransient
    public Collection<Valoracionessalas> getValoracionessalasCollection() {
        return valoracionessalasCollection;
    }

    public void setValoracionessalasCollection(Collection<Valoracionessalas> valoracionessalasCollection) {
        this.valoracionessalasCollection = valoracionessalasCollection;
    }

    @XmlTransient
    public Collection<Tematicas> getTematicasCollection() {
        return tematicasCollection;
    }

    public void setTematicasCollection(Collection<Tematicas> tematicasCollection) {
        this.tematicasCollection = tematicasCollection;
    }

    @XmlTransient
    public Collection<Entradassalas> getEntradassalasCollection() {
        return entradassalasCollection;
    }

    public void setEntradassalasCollection(Collection<Entradassalas> entradassalasCollection) {
        this.entradassalasCollection = entradassalasCollection;
    }

    @XmlTransient
    public Collection<Colecciones> getColeccionesCollection() {
        return coleccionesCollection;
    }

    public void setColeccionesCollection(Collection<Colecciones> coleccionesCollection) {
        this.coleccionesCollection = coleccionesCollection;
    }

    @XmlTransient
    public Collection<Validacionesentradas> getValidacionesentradasCollection() {
        return validacionesentradasCollection;
    }

    public void setValidacionesentradasCollection(Collection<Validacionesentradas> validacionesentradasCollection) {
        this.validacionesentradasCollection = validacionesentradasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsala != null ? idsala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salas)) {
            return false;
        }
        Salas other = (Salas) object;
        if ((this.idsala == null && other.idsala != null) || (this.idsala != null && !this.idsala.equals(other.idsala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    
}
