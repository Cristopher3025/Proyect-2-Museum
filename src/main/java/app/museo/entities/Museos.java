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
import jakarta.persistence.Transient;
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
@Table(name = "MUSEOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Museos.findAll", query = "SELECT m FROM Museos m"),
    @NamedQuery(name = "Museos.findByIdmuseo", query = "SELECT m FROM Museos m WHERE m.idmuseo = :idmuseo"),
    @NamedQuery(name = "Museos.findByNombre", query = "SELECT m FROM Museos m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Museos.findByUbicacion", query = "SELECT m FROM Museos m WHERE m.ubicacion = :ubicacion"),
    @NamedQuery(name = "Museos.findByFechafundacion", query = "SELECT m FROM Museos m WHERE m.fechafundacion = :fechafundacion"),
    @NamedQuery(name = "Museos.findByNombredirector", query = "SELECT m FROM Museos m WHERE m.nombredirector = :nombredirector"),
    @NamedQuery(name = "Museos.findBySitioweb", query = "SELECT m FROM Museos m WHERE m.sitioweb = :sitioweb")
})
public class Museos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDMUSEO")
    private BigDecimal idmuseo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "UBICACION")
    private String ubicacion;

    @Column(name = "FECHAFUNDACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafundacion;

    @Column(name = "NOMBREDIRECTOR")
    private String nombredirector;

    @Column(name = "SITIOWEB")
    private String sitioweb;
    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "TELEFONO")
    private String telefono;


    

    @JoinColumn(name = "TIPOMUSEOID", referencedColumnName = "IDTIPOMUSEO")
    @ManyToOne
    private Tiposmuseo tipomuseoid;

    @OneToMany(mappedBy = "museoid")
    private Collection<Salas> salasCollection;

    @OneToMany(mappedBy = "museoid")
    private Collection<Horarios> horariosCollection;

    public Museos() { }

    public Museos(BigDecimal idmuseo) {
        this.idmuseo = idmuseo;
    }

    // Getters y setters ↓↓↓

    public BigDecimal getIdmuseo() {
        return idmuseo;
    }

    public void setIdmuseo(BigDecimal idmuseo) {
        this.idmuseo = idmuseo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechafundacion() {
        return fechafundacion;
    }

    public void setFechafundacion(Date fechafundacion) {
        this.fechafundacion = fechafundacion;
    }

    public String getNombredirector() {
        return nombredirector;
    }

    public void setNombredirector(String nombredirector) {
        this.nombredirector = nombredirector;
    }

    public String getSitioweb() {
        return sitioweb;
    }

    public void setSitioweb(String sitioweb) {
        this.sitioweb = sitioweb;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Tiposmuseo getTipomuseoid() {
        return tipomuseoid;
    }

    public void setTipomuseoid(Tiposmuseo tipomuseoid) {
        this.tipomuseoid = tipomuseoid;
    }

    @XmlTransient
    public Collection<Salas> getSalasCollection() {
        return salasCollection;
    }

    public void setSalasCollection(Collection<Salas> salasCollection) {
        this.salasCollection = salasCollection;
    }

    @XmlTransient
    public Collection<Horarios> getHorariosCollection() {
        return horariosCollection;
    }

    public void setHorariosCollection(Collection<Horarios> horariosCollection) {
        this.horariosCollection = horariosCollection;
    }

    @Override
    public int hashCode() {
        return (idmuseo != null ? idmuseo.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Museos)) return false;
        Museos other = (Museos) object;
        return !((this.idmuseo == null && other.idmuseo != null) || (this.idmuseo != null && !this.idmuseo.equals(other.idmuseo)));
    }

    @Override
    public String toString() {
        return "app.museo.entities.Museos[ idmuseo=" + idmuseo + " ]";
    }
}

