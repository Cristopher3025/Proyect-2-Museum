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
@Table(name = "COMISIONESTARJETAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comisionestarjetas.findAll", query = "SELECT c FROM Comisionestarjetas c"),
    @NamedQuery(name = "Comisionestarjetas.findByIdcomision", query = "SELECT c FROM Comisionestarjetas c WHERE c.idcomision = :idcomision"),
    @NamedQuery(name = "Comisionestarjetas.findByComision", query = "SELECT c FROM Comisionestarjetas c WHERE c.comision = :comision")})
public class Comisionestarjetas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCOMISION")
    private BigDecimal idcomision;
    @Column(name = "COMISION")
    private BigDecimal comision;
    @OneToMany(mappedBy = "comisionid")
    private Collection<Transacciones> transaccionesCollection;
    @JoinColumn(name = "TIPOTARJETAID", referencedColumnName = "IDTIPOTARJETA")
    @ManyToOne
    private Tipostarjeta tipotarjetaid;

    public Comisionestarjetas() {
    }

    public Comisionestarjetas(BigDecimal idcomision) {
        this.idcomision = idcomision;
    }

    public BigDecimal getIdcomision() {
        return idcomision;
    }

    public void setIdcomision(BigDecimal idcomision) {
        this.idcomision = idcomision;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    @XmlTransient
    public Collection<Transacciones> getTransaccionesCollection() {
        return transaccionesCollection;
    }

    public void setTransaccionesCollection(Collection<Transacciones> transaccionesCollection) {
        this.transaccionesCollection = transaccionesCollection;
    }

    public Tipostarjeta getTipotarjetaid() {
        return tipotarjetaid;
    }

    public void setTipotarjetaid(Tipostarjeta tipotarjetaid) {
        this.tipotarjetaid = tipotarjetaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomision != null ? idcomision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comisionestarjetas)) {
            return false;
        }
        Comisionestarjetas other = (Comisionestarjetas) object;
        if ((this.idcomision == null && other.idcomision != null) || (this.idcomision != null && !this.idcomision.equals(other.idcomision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Comisionestarjetas[ idcomision=" + idcomision + " ]";
    }
    
}
