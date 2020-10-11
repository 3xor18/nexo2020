package com.nexo.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ProductoImpuestos.
 */
@Entity
@Table(name = "producto_impuestos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductoImpuestos implements Serializable {

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

    @Column(name = "impuesto_monto_fijo")
    private Boolean impuestoMontoFijo;

    @Column(name = "monto_o_tasa")
    private Double montoOTasa;

    @Column(name = "impuesto_nacional")
    private Boolean impuestoNacional;

    @Column(name = "fecha_aplicable")
    private ZonedDateTime fechaAplicable;

    @ManyToOne
    @JsonIgnoreProperties("productoImpuestos")
    private Producto producto;

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

    public ProductoImpuestos estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public ProductoImpuestos nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ZonedDateTime getFechaBd() {
        return fechaBd;
    }

    public ProductoImpuestos fechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
        return this;
    }

    public void setFechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
    }

    public Boolean isImpuestoMontoFijo() {
        return impuestoMontoFijo;
    }

    public ProductoImpuestos impuestoMontoFijo(Boolean impuestoMontoFijo) {
        this.impuestoMontoFijo = impuestoMontoFijo;
        return this;
    }

    public void setImpuestoMontoFijo(Boolean impuestoMontoFijo) {
        this.impuestoMontoFijo = impuestoMontoFijo;
    }

    public Double getMontoOTasa() {
        return montoOTasa;
    }

    public ProductoImpuestos montoOTasa(Double montoOTasa) {
        this.montoOTasa = montoOTasa;
        return this;
    }

    public void setMontoOTasa(Double montoOTasa) {
        this.montoOTasa = montoOTasa;
    }

    public Boolean isImpuestoNacional() {
        return impuestoNacional;
    }

    public ProductoImpuestos impuestoNacional(Boolean impuestoNacional) {
        this.impuestoNacional = impuestoNacional;
        return this;
    }

    public void setImpuestoNacional(Boolean impuestoNacional) {
        this.impuestoNacional = impuestoNacional;
    }

    public ZonedDateTime getFechaAplicable() {
        return fechaAplicable;
    }

    public ProductoImpuestos fechaAplicable(ZonedDateTime fechaAplicable) {
        this.fechaAplicable = fechaAplicable;
        return this;
    }

    public void setFechaAplicable(ZonedDateTime fechaAplicable) {
        this.fechaAplicable = fechaAplicable;
    }

    public Producto getProducto() {
        return producto;
    }

    public ProductoImpuestos producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoImpuestos)) {
            return false;
        }
        return id != null && id.equals(((ProductoImpuestos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductoImpuestos{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", fechaBd='" + getFechaBd() + "'" +
            ", impuestoMontoFijo='" + isImpuestoMontoFijo() + "'" +
            ", montoOTasa=" + getMontoOTasa() +
            ", impuestoNacional='" + isImpuestoNacional() + "'" +
            ", fechaAplicable='" + getFechaAplicable() + "'" +
            "}";
    }
}
