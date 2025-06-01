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
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author crist
 */
@Entity
@Table(name = "TEMATICAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tematicas.findAll", query = "SELECT t FROM Tematicas t"),
    @NamedQuery(name = "Tematicas.findByIdtematica", query = "SELECT t FROM Tematicas t WHERE t.idtematica = :idtematica"),
    @NamedQuery(name = "Tematicas.findByNombre", query = "SELECT t FROM Tematicas t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tematicas.findByCaracteristicas", query = "SELECT t FROM Tematicas t WHERE t.caracteristicas = :caracteristicas")})
public class Tematicas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDTEMATICA")
    private BigDecimal idtematica;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CARACTERISTICAS")
    private String caracteristicas;
    @JoinColumn(name = "EPOCAID", referencedColumnName = "IDEPOCA")
    @ManyToOne
    private Epocas epocaid;
    @JoinColumn(name = "SALAID", referencedColumnName = "IDSALA")
    @ManyToOne
    private Salas salaid;

    public Tematicas() {
    }

    public Tematicas(BigDecimal idtematica) {
        this.idtematica = idtematica;
    }

    public BigDecimal getIdtematica() {
        return idtematica;
    }

    public void setIdtematica(BigDecimal idtematica) {
        this.idtematica = idtematica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Epocas getEpocaid() {
        return epocaid;
    }

    public void setEpocaid(Epocas epocaid) {
        this.epocaid = epocaid;
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
        hash += (idtematica != null ? idtematica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tematicas)) {
            return false;
        }
        Tematicas other = (Tematicas) object;
        if ((this.idtematica == null && other.idtematica != null) || (this.idtematica != null && !this.idtematica.equals(other.idtematica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Tematicas[ idtematica=" + idtematica + " ]";
    }
    
}
