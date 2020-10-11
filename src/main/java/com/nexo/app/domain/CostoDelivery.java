package com.nexo.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A CostoDelivery.
 */
@Entity
@Table(name = "costo_delivery")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CostoDelivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado")
    private String estado;

    @Column(name = "monto_individual")
    private Double montoIndividual;

    @Column(name = "monto_mayor")
    private Double montoMayor;

    @Column(name = "cantidad_mayor")
    private Double cantidadMayor;

    @Column(name = "fecha_bd")
    private ZonedDateTime fechaBd;

    @ManyToOne
    @JsonIgnoreProperties("costoDeliveries")
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties("costoDeliveries")
    private Comuna comuna;

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

    public CostoDelivery estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMontoIndividual() {
        return montoIndividual;
    }

    public CostoDelivery montoIndividual(Double montoIndividual) {
        this.montoIndividual = montoIndividual;
        return this;
    }

    public void setMontoIndividual(Double montoIndividual) {
        this.montoIndividual = montoIndividual;
    }

    public Double getMontoMayor() {
        return montoMayor;
    }

    public CostoDelivery montoMayor(Double montoMayor) {
        this.montoMayor = montoMayor;
        return this;
    }

    public void setMontoMayor(Double montoMayor) {
        this.montoMayor = montoMayor;
    }

    public Double getCantidadMayor() {
        return cantidadMayor;
    }

    public CostoDelivery cantidadMayor(Double cantidadMayor) {
        this.cantidadMayor = cantidadMayor;
        return this;
    }

    public void setCantidadMayor(Double cantidadMayor) {
        this.cantidadMayor = cantidadMayor;
    }

    public ZonedDateTime getFechaBd() {
        return fechaBd;
    }

    public CostoDelivery fechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
        return this;
    }

    public void setFechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
    }

    public Producto getProducto() {
        return producto;
    }

    public CostoDelivery producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public CostoDelivery comuna(Comuna comuna) {
        this.comuna = comuna;
        return this;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CostoDelivery)) {
            return false;
        }
        return id != null && id.equals(((CostoDelivery) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CostoDelivery{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", montoIndividual=" + getMontoIndividual() +
            ", montoMayor=" + getMontoMayor() +
            ", cantidadMayor=" + getCantidadMayor() +
            ", fechaBd='" + getFechaBd() + "'" +
            "}";
    }
}
