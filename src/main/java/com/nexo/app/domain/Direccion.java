package com.nexo.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Direccion.
 */
@Entity
@Table(name = "direccion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "principal")
    private Boolean principal;

    @ManyToOne
    @JsonIgnoreProperties("direccions")
    private Comuna comuna;

    @ManyToOne
    @JsonIgnoreProperties("direccions")
    private Persona persona;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public Direccion direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public Direccion estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean isPrincipal() {
        return principal;
    }

    public Direccion principal(Boolean principal) {
        this.principal = principal;
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public Direccion comuna(Comuna comuna) {
        this.comuna = comuna;
        return this;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    public Persona getPersona() {
        return persona;
    }

    public Direccion persona(Persona persona) {
        this.persona = persona;
        return this;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Direccion)) {
            return false;
        }
        return id != null && id.equals(((Direccion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Direccion{" +
            "id=" + getId() +
            ", direccion='" + getDireccion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", principal='" + isPrincipal() + "'" +
            "}";
    }
}
