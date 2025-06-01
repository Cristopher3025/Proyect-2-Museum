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
@Table(name = "TRANSACCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transacciones.findAll", query = "SELECT t FROM Transacciones t"),
    @NamedQuery(name = "Transacciones.findByIdtransaccion", query = "SELECT t FROM Transacciones t WHERE t.idtransaccion = :idtransaccion"),
    @NamedQuery(name = "Transacciones.findByFecha", query = "SELECT t FROM Transacciones t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Transacciones.findByMonto", query = "SELECT t FROM Transacciones t WHERE t.monto = :monto"),
    @NamedQuery(name = "Transacciones.findByTipo", query = "SELECT t FROM Transacciones t WHERE t.tipo = :tipo")})
public class Transacciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDTRANSACCION")
    private BigDecimal idtransaccion;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Column(name = "TIPO")
    private String tipo;
    @OneToMany(mappedBy = "transaccionid")
    private Collection<Entradas> entradasCollection;
    @JoinColumn(name = "COMISIONID", referencedColumnName = "IDCOMISION")
    @ManyToOne
    private Comisionestarjetas comisionid;
    @JoinColumn(name = "USUARIOID", referencedColumnName = "IDUSUARIO")
    @ManyToOne
    private Usuarios usuarioid;

    public Transacciones() {
    }

    public Transacciones(BigDecimal idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public BigDecimal getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(BigDecimal idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Entradas> getEntradasCollection() {
        return entradasCollection;
    }

    public void setEntradasCollection(Collection<Entradas> entradasCollection) {
        this.entradasCollection = entradasCollection;
    }

    public Comisionestarjetas getComisionid() {
        return comisionid;
    }

    public void setComisionid(Comisionestarjetas comisionid) {
        this.comisionid = comisionid;
    }

    public Usuarios getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuarios usuarioid) {
        this.usuarioid = usuarioid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaccion != null ? idtransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transacciones)) {
            return false;
        }
        Transacciones other = (Transacciones) object;
        if ((this.idtransaccion == null && other.idtransaccion != null) || (this.idtransaccion != null && !this.idtransaccion.equals(other.idtransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Transacciones[ idtransaccion=" + idtransaccion + " ]";
    }
    
}
