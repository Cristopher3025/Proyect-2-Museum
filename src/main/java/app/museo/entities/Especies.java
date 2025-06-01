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
@Table(name = "ESPECIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Especies.findAll", query = "SELECT e FROM Especies e"),
    @NamedQuery(name = "Especies.findByIdespecie", query = "SELECT e FROM Especies e WHERE e.idespecie = :idespecie"),
    @NamedQuery(name = "Especies.findByNombrecientifico", query = "SELECT e FROM Especies e WHERE e.nombrecientifico = :nombrecientifico"),
    @NamedQuery(name = "Especies.findByNombrecomun", query = "SELECT e FROM Especies e WHERE e.nombrecomun = :nombrecomun"),
    @NamedQuery(name = "Especies.findByFechaextincion", query = "SELECT e FROM Especies e WHERE e.fechaextincion = :fechaextincion"),
    @NamedQuery(name = "Especies.findByPeso", query = "SELECT e FROM Especies e WHERE e.peso = :peso"),
    @NamedQuery(name = "Especies.findByTamano", query = "SELECT e FROM Especies e WHERE e.tamano = :tamano"),
    @NamedQuery(name = "Especies.findByCaracteristicas", query = "SELECT e FROM Especies e WHERE e.caracteristicas = :caracteristicas")})
public class Especies implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDESPECIE")
    private BigDecimal idespecie;
    @Column(name = "NOMBRECIENTIFICO")
    private String nombrecientifico;
    @Column(name = "NOMBRECOMUN")
    private String nombrecomun;
    @Column(name = "FECHAEXTINCION")
    private String fechaextincion;
    @Column(name = "PESO")
    private BigDecimal peso;
    @Column(name = "TAMANO")
    private BigDecimal tamano;
    @Column(name = "CARACTERISTICAS")
    private String caracteristicas;
    @JoinColumn(name = "COLECCIONID", referencedColumnName = "IDCOLECCION")
    @ManyToOne
    private Colecciones coleccionid;
    @JoinColumn(name = "EPOCAID", referencedColumnName = "IDEPOCA")
    @ManyToOne
    private Epocas epocaid;

    public Especies() {
    }

    public Especies(BigDecimal idespecie) {
        this.idespecie = idespecie;
    }

    public BigDecimal getIdespecie() {
        return idespecie;
    }

    public void setIdespecie(BigDecimal idespecie) {
        this.idespecie = idespecie;
    }

    public String getNombrecientifico() {
        return nombrecientifico;
    }

    public void setNombrecientifico(String nombrecientifico) {
        this.nombrecientifico = nombrecientifico;
    }

    public String getNombrecomun() {
        return nombrecomun;
    }

    public void setNombrecomun(String nombrecomun) {
        this.nombrecomun = nombrecomun;
    }

    public String getFechaextincion() {
        return fechaextincion;
    }

    public void setFechaextincion(String fechaextincion) {
        this.fechaextincion = fechaextincion;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getTamano() {
        return tamano;
    }

    public void setTamano(BigDecimal tamano) {
        this.tamano = tamano;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Colecciones getColeccionid() {
        return coleccionid;
    }

    public void setColeccionid(Colecciones coleccionid) {
        this.coleccionid = coleccionid;
    }

    public Epocas getEpocaid() {
        return epocaid;
    }

    public void setEpocaid(Epocas epocaid) {
        this.epocaid = epocaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idespecie != null ? idespecie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Especies)) {
            return false;
        }
        Especies other = (Especies) object;
        if ((this.idespecie == null && other.idespecie != null) || (this.idespecie != null && !this.idespecie.equals(other.idespecie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.museo.entities.Especies[ idespecie=" + idespecie + " ]";
    }
    
}
