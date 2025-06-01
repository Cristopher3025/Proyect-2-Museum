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
@Table(name = "HORARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horarios.findAll", query = "SELECT h FROM Horarios h"),
    @NamedQuery(name = "Horarios.findByIdhorario", query = "SELECT h FROM Horarios h WHERE h.idhorario = :idhorario"),
    @NamedQuery(name = "Horarios.findByDia", query = "SELECT h FROM Horarios h WHERE h.dia = :dia"),
    @NamedQuery(name = "Horarios.findByHoraapertura", query = "SELECT h FROM Horarios h WHERE h.horaapertura = :horaapertura"),
    @NamedQuery(name = "Horarios.findByHoracierre", query = "SELECT h FROM Horarios h WHERE h.horacierre = :horacierre")})
public class Horarios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDHORARIO")
    private BigDecimal idhorario;
    @Column(name = "DIA")
    private String dia;
    @Column(name = "HORAAPERTURA")
    private String horaapertura;
    @Column(name = "HORACIERRE")
    private String horacierre;
    @JoinColumn(name = "MUSEOID", referencedColumnName = "IDMUSEO")
    @ManyToOne
    private Museos museoid;

    public Horarios() {
    }

    public Horarios(BigDecimal idhorario) {
        this.idhorario = idhorario;
    }

    public BigDecimal getIdhorario() {
        return idhorario;
    }

    public void setIdhorario(BigDecimal idhorario) {
        this.idhorario = idhorario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraapertura() {
        return horaapertura;
    }

    public void setHoraapertura(String horaapertura) {
        this.horaapertura = horaapertura;
    }

    public String getHoracierre() {
        return horacierre;
    }

    public void setHoracierre(String horacierre) {
        this.horacierre = horacierre;
    }

    public Museos getMuseoid() {
        return museoid;
    }

    public void setMuseoid(Museos museoid) {
        this.museoid = museoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhorario != null ? idhorario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horarios)) {
            return false;
        }
        Horarios other = (Horarios) object;
        if ((this.idhorario == null && other.idhorario != null) || (this.idhorario != null && !this.idhorario.equals(other.idhorario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Horarios[ idhorario=" + idhorario + " ]";
    }
    
}
