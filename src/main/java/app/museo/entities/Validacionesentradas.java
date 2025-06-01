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
@Table(name = "VALIDACIONESENTRADAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Validacionesentradas.findAll", query = "SELECT v FROM Validacionesentradas v"),
    @NamedQuery(name = "Validacionesentradas.findByIdvalidacion", query = "SELECT v FROM Validacionesentradas v WHERE v.idvalidacion = :idvalidacion"),
    @NamedQuery(name = "Validacionesentradas.findByFechavalidacion", query = "SELECT v FROM Validacionesentradas v WHERE v.fechavalidacion = :fechavalidacion"),
    @NamedQuery(name = "Validacionesentradas.findByValido", query = "SELECT v FROM Validacionesentradas v WHERE v.valido = :valido")})
public class Validacionesentradas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDVALIDACION")
    private BigDecimal idvalidacion;
    @Column(name = "FECHAVALIDACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavalidacion;
    @Column(name = "VALIDO")
    private Character valido;
    @JoinColumn(name = "ENTRADAID", referencedColumnName = "IDENTRADA")
    @ManyToOne
    private Entradas entradaid;
    @JoinColumn(name = "SALAID", referencedColumnName = "IDSALA")
    @ManyToOne
    private Salas salaid;

    public Validacionesentradas() {
    }

    public Validacionesentradas(BigDecimal idvalidacion) {
        this.idvalidacion = idvalidacion;
    }

    public BigDecimal getIdvalidacion() {
        return idvalidacion;
    }

    public void setIdvalidacion(BigDecimal idvalidacion) {
        this.idvalidacion = idvalidacion;
    }

    public Date getFechavalidacion() {
        return fechavalidacion;
    }

    public void setFechavalidacion(Date fechavalidacion) {
        this.fechavalidacion = fechavalidacion;
    }

    public Character getValido() {
        return valido;
    }

    public void setValido(Character valido) {
        this.valido = valido;
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
        hash += (idvalidacion != null ? idvalidacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Validacionesentradas)) {
            return false;
        }
        Validacionesentradas other = (Validacionesentradas) object;
        if ((this.idvalidacion == null && other.idvalidacion != null) || (this.idvalidacion != null && !this.idvalidacion.equals(other.idvalidacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Validacionesentradas[ idvalidacion=" + idvalidacion + " ]";
    }
    
}
