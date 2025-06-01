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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author crist
 */
@Entity
@Table(name = "ENTRADAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entradas.findAll", query = "SELECT e FROM Entradas e"),
    @NamedQuery(name = "Entradas.findByIdentrada", query = "SELECT e FROM Entradas e WHERE e.identrada = :identrada"),
    @NamedQuery(name = "Entradas.findByFechavisita", query = "SELECT e FROM Entradas e WHERE e.fechavisita = :fechavisita"),
    @NamedQuery(name = "Entradas.findByPreciototal", query = "SELECT e FROM Entradas e WHERE e.preciototal = :preciototal"),
    @NamedQuery(name = "Entradas.findByCodigoqr", query = "SELECT e FROM Entradas e WHERE e.codigoqr = :codigoqr")})
public class Entradas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDENTRADA")
    private BigDecimal identrada;
    @Column(name = "FECHAVISITA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavisita;
    @Column(name = "PRECIOTOTAL")
    private BigDecimal preciototal;
    @Column(name = "CODIGOQR")
    private String codigoqr;
    @OneToMany(mappedBy = "entradaid")
    private Collection<Valoracionessalas> valoracionessalasCollection;
    @JoinColumn(name = "TRANSACCIONID", referencedColumnName = "IDTRANSACCION")
    @ManyToOne
    private Transacciones transaccionid;
    @JoinColumn(name = "USUARIOID", referencedColumnName = "IDUSUARIO")
    @ManyToOne
    private Usuarios usuarioid;
    @OneToMany(mappedBy = "entradaid")
    private Collection<Entradassalas> entradassalasCollection;
    @OneToMany(mappedBy = "entradaid")
    private Collection<Validacionesentradas> validacionesentradasCollection;

    public Entradas() {
    }

    public Entradas(BigDecimal identrada) {
        this.identrada = identrada;
    }

    public BigDecimal getIdentrada() {
        return identrada;
    }

    public void setIdentrada(BigDecimal identrada) {
        this.identrada = identrada;
    }

    public Date getFechavisita() {
        return fechavisita;
    }

    public void setFechavisita(Date fechavisita) {
        this.fechavisita = fechavisita;
    }

    public BigDecimal getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(BigDecimal preciototal) {
        this.preciototal = preciototal;
    }

    public String getCodigoqr() {
        return codigoqr;
    }

    public void setCodigoqr(String codigoqr) {
        this.codigoqr = codigoqr;
    }

    @XmlTransient
    public Collection<Valoracionessalas> getValoracionessalasCollection() {
        return valoracionessalasCollection;
    }

    public void setValoracionessalasCollection(Collection<Valoracionessalas> valoracionessalasCollection) {
        this.valoracionessalasCollection = valoracionessalasCollection;
    }

    public Transacciones getTransaccionid() {
        return transaccionid;
    }

    public void setTransaccionid(Transacciones transaccionid) {
        this.transaccionid = transaccionid;
    }

    public Usuarios getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuarios usuarioid) {
        this.usuarioid = usuarioid;
    }

    @XmlTransient
    public Collection<Entradassalas> getEntradassalasCollection() {
        return entradassalasCollection;
    }

    public void setEntradassalasCollection(Collection<Entradassalas> entradassalasCollection) {
        this.entradassalasCollection = entradassalasCollection;
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
        hash += (identrada != null ? identrada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entradas)) {
            return false;
        }
        Entradas other = (Entradas) object;
        if ((this.identrada == null && other.identrada != null) || (this.identrada != null && !this.identrada.equals(other.identrada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Entradas[ identrada=" + identrada + " ]";
    }
    
}
