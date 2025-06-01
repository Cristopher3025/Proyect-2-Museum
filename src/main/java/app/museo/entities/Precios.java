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
@Table(name = "PRECIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Precios.findAll", query = "SELECT p FROM Precios p"),
    @NamedQuery(name = "Precios.findByIdprecio", query = "SELECT p FROM Precios p WHERE p.idprecio = :idprecio"),
    @NamedQuery(name = "Precios.findByPreciolunesasabado", query = "SELECT p FROM Precios p WHERE p.preciolunesasabado = :preciolunesasabado"),
    @NamedQuery(name = "Precios.findByPreciodomingo", query = "SELECT p FROM Precios p WHERE p.preciodomingo = :preciodomingo")})
public class Precios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPRECIO")
    private BigDecimal idprecio;
    @Column(name = "PRECIOLUNESASABADO")
    private BigDecimal preciolunesasabado;
    @Column(name = "PRECIODOMINGO")
    private BigDecimal preciodomingo;
    @JoinColumn(name = "SALAID", referencedColumnName = "IDSALA")
    @ManyToOne
    private Salas salaid;

    public Precios() {
    }

    public Precios(BigDecimal idprecio) {
        this.idprecio = idprecio;
    }

    public BigDecimal getIdprecio() {
        return idprecio;
    }

    public void setIdprecio(BigDecimal idprecio) {
        this.idprecio = idprecio;
    }

    public BigDecimal getPreciolunesasabado() {
        return preciolunesasabado;
    }

    public void setPreciolunesasabado(BigDecimal preciolunesasabado) {
        this.preciolunesasabado = preciolunesasabado;
    }

    public BigDecimal getPreciodomingo() {
        return preciodomingo;
    }

    public void setPreciodomingo(BigDecimal preciodomingo) {
        this.preciodomingo = preciodomingo;
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
        hash += (idprecio != null ? idprecio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Precios)) {
            return false;
        }
        Precios other = (Precios) object;
        if ((this.idprecio == null && other.idprecio != null) || (this.idprecio != null && !this.idprecio.equals(other.idprecio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Precios[ idprecio=" + idprecio + " ]";
    }
    
}
