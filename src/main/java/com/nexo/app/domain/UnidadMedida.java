package com.nexo.app.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A UnidadMedida.
 */
@Entity
@Table(name = "unidad_medida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnidadMedida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado")
    private String estado;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_bd")
    private ZonedDateTime fechaBd;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public UnidadMedida estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public UnidadMedida nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ZonedDateTime getFechaBd() {
        return fechaBd;
    }

    public UnidadMedida fechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
        return this;
    }

    public void setFechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadMedida)) {
            return false;
        }
        return id != null && id.equals(((UnidadMedida) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UnidadMedida{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", fechaBd='" + getFechaBd() + "'" +
            "}";
    }
}
