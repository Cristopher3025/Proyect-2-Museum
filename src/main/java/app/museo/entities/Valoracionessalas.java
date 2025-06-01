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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author crist
 */
@Entity
@Table(name = "VALORACIONESSALAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Valoracionessalas.findAll", query = "SELECT v FROM Valoracionessalas v"),
    @NamedQuery(name = "Valoracionessalas.findByIdvaloracion", query = "SELECT v FROM Valoracionessalas v WHERE v.idvaloracion = :idvaloracion"),
    @NamedQuery(name = "Valoracionessalas.findByValoracion", query = "SELECT v FROM Valoracionessalas v WHERE v.valoracion = :valoracion"),
    @NamedQuery(name = "Valoracionessalas.findByObservacion", query = "SELECT v FROM Valoracionessalas v WHERE v.observacion = :observacion"),
    @NamedQuery(name = "Valoracionessalas.findByFecha", query = "SELECT v FROM Valoracionessalas v WHERE v.fecha = :fecha")})
public class Valoracionessalas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDVALORACION")
    private BigDecimal idvaloracion;
    @Column(name = "VALORACION")
    private Short valoracion;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "ENTRADAID", referencedColumnName = "IDENTRADA")
    @ManyToOne
    private Entradas entradaid;
    @JoinColumn(name = "SALAID", referencedColumnName = "IDSALA")
    @ManyToOne
    private Salas salaid;

    public Valoracionessalas() {
    }

    public Valoracionessalas(BigDecimal idvaloracion) {
        this.idvaloracion = idvaloracion;
    }

    public BigDecimal getIdvaloracion() {
        return idvaloracion;
    }

    public void setIdvaloracion(BigDecimal idvaloracion) {
        this.idvaloracion = idvaloracion;
    }

    public Short getValoracion() {
        return valoracion;
    }

    public void setValoracion(Short valoracion) {
        this.valoracion = valoracion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        hash += (idvaloracion != null ? idvaloracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valoracionessalas)) {
            return false;
        }
        Valoracionessalas other = (Valoracionessalas) object;
        if ((this.idvaloracion == null && other.idvaloracion != null) || (this.idvaloracion != null && !this.idvaloracion.equals(other.idvaloracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Valoracionessalas[ idvaloracion=" + idvaloracion + " ]";
    }
    
}
