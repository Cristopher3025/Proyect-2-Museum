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
@Table(name = "ENTRADASSALAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entradassalas.findAll", query = "SELECT e FROM Entradassalas e"),
    @NamedQuery(name = "Entradassalas.findByIdentradasala", query = "SELECT e FROM Entradassalas e WHERE e.identradasala = :identradasala")})
public class Entradassalas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDENTRADASALA")
    private BigDecimal identradasala;
    @JoinColumn(name = "ENTRADAID", referencedColumnName = "IDENTRADA")
    @ManyToOne
    private Entradas entradaid;
    @JoinColumn(name = "SALAID", referencedColumnName = "IDSALA")
    @ManyToOne
    private Salas salaid;

    public Entradassalas() {
    }

    public Entradassalas(BigDecimal identradasala) {
        this.identradasala = identradasala;
    }

    public BigDecimal getIdentradasala() {
        return identradasala;
    }

    public void setIdentradasala(BigDecimal identradasala) {
        this.identradasala = identradasala;
    }

    public Entradas getEntradaid() {
        return entradaid;
    }

    public void setEntradaid(Entradas entradaid) {
        this.entradaid = entradaid;
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
        hash += (identradasala != null ? identradasala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entradassalas)) {
            return false;
        }
        Entradassalas other = (Entradassalas) object;
        if ((this.identradasala == null && other.identradasala != null) || (this.identradasala != null && !this.identradasala.equals(other.identradasala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Entradassalas[ identradasala=" + identradasala + " ]";
    }
    
}
