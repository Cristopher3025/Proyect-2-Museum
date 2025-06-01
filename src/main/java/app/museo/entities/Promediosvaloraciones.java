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
@Table(name = "PROMEDIOSVALORACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promediosvaloraciones.findAll", query = "SELECT p FROM Promediosvaloraciones p"),
    @NamedQuery(name = "Promediosvaloraciones.findByIdpromedio", query = "SELECT p FROM Promediosvaloraciones p WHERE p.idpromedio = :idpromedio"),
    @NamedQuery(name = "Promediosvaloraciones.findByPromedio", query = "SELECT p FROM Promediosvaloraciones p WHERE p.promedio = :promedio"),
    @NamedQuery(name = "Promediosvaloraciones.findByTotalvaloraciones", query = "SELECT p FROM Promediosvaloraciones p WHERE p.totalvaloraciones = :totalvaloraciones")})
public class Promediosvaloraciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPROMEDIO")
    private BigDecimal idpromedio;
    @Column(name = "PROMEDIO")
    private BigDecimal promedio;
    @Column(name = "TOTALVALORACIONES")
    private Integer totalvaloraciones;
    @JoinColumn(name = "SALAID", referencedColumnName = "IDSALA")
    @ManyToOne
    private Salas salaid;

    public Promediosvaloraciones() {
    }

    public Promediosvaloraciones(BigDecimal idpromedio) {
        this.idpromedio = idpromedio;
    }

    public BigDecimal getIdpromedio() {
        return idpromedio;
    }

    public void setIdpromedio(BigDecimal idpromedio) {
        this.idpromedio = idpromedio;
    }

    public BigDecimal getPromedio() {
        return promedio;
    }

    public void setPromedio(BigDecimal promedio) {
        this.promedio = promedio;
    }

    public Integer getTotalvaloraciones() {
        return totalvaloraciones;
    }

    public void setTotalvaloraciones(Integer totalvaloraciones) {
        this.totalvaloraciones = totalvaloraciones;
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
        hash += (idpromedio != null ? idpromedio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promediosvaloraciones)) {
            return false;
        }
        Promediosvaloraciones other = (Promediosvaloraciones) object;
        if ((this.idpromedio == null && other.idpromedio != null) || (this.idpromedio != null && !this.idpromedio.equals(other.idpromedio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Promediosvaloraciones[ idpromedio=" + idpromedio + " ]";
    }
    
}
