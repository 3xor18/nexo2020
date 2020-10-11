package com.nexo.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Persona.
 */
@Entity
@Table(name = "persona")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "doc_identidad")
    private String docIdentidad;

    @Column(name = "fecha_nac")
    private LocalDate fechaNac;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "estado")
    private String estado;

    @Column(name = "jhi_natural")
    private Boolean natural;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Column(name = "score_comprador")
    private Double scoreComprador;

    @Column(name = "score_vendedor")
    private Double scoreVendedor;

    @ManyToOne
    @JsonIgnoreProperties("personas")
    private Pais pais;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Persona nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public Persona apellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
        return this;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public Persona apellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
        return this;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDocIdentidad() {
        return docIdentidad;
    }

    public Persona docIdentidad(String docIdentidad) {
        this.docIdentidad = docIdentidad;
        return this;
    }

    public void setDocIdentidad(String docIdentidad) {
        this.docIdentidad = docIdentidad;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public Persona fechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
        return this;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public Persona sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public Persona nacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
        return this;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEmail() {
        return email;
    }

    public Persona email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Persona telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public Persona estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean isNatural() {
        return natural;
    }

    public Persona natural(Boolean natural) {
        this.natural = natural;
        return this;
    }

    public void setNatural(Boolean natural) {
        this.natural = natural;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public Persona nombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
        return this;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public Double getScoreComprador() {
        return scoreComprador;
    }

    public Persona scoreComprador(Double scoreComprador) {
        this.scoreComprador = scoreComprador;
        return this;
    }

    public void setScoreComprador(Double scoreComprador) {
        this.scoreComprador = scoreComprador;
    }

    public Double getScoreVendedor() {
        return scoreVendedor;
    }

    public Persona scoreVendedor(Double scoreVendedor) {
        this.scoreVendedor = scoreVendedor;
        return this;
    }

    public void setScoreVendedor(Double scoreVendedor) {
        this.scoreVendedor = scoreVendedor;
    }

    public Pais getPais() {
        return pais;
    }

    public Persona pais(Pais pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public User getUser() {
        return user;
    }

    public Persona user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Persona)) {
            return false;
        }
        return id != null && id.equals(((Persona) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Persona{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellidoPaterno='" + getApellidoPaterno() + "'" +
            ", apellidoMaterno='" + getApellidoMaterno() + "'" +
            ", docIdentidad='" + getDocIdentidad() + "'" +
            ", fechaNac='" + getFechaNac() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", nacionalidad='" + getNacionalidad() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", estado='" + getEstado() + "'" +
            ", natural='" + isNatural() + "'" +
            ", nombreComercial='" + getNombreComercial() + "'" +
            ", scoreComprador=" + getScoreComprador() +
            ", scoreVendedor=" + getScoreVendedor() +
            "}";
    }
}
