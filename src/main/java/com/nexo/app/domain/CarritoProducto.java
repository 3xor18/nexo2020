package com.nexo.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CarritoProducto.
 */
@Entity
@Table(name = "carrito_producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CarritoProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "precio_compra")
    private Double precioCompra;

    @Column(name = "estado")
    private String estado;

    @Column(name = "costo_delivery")
    private Double costoDelivery;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Carrito carrito;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public CarritoProducto cantidad(Double cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public CarritoProducto precioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
        return this;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getEstado() {
        return estado;
    }

    public CarritoProducto estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getCostoDelivery() {
        return costoDelivery;
    }

    public CarritoProducto costoDelivery(Double costoDelivery) {
        this.costoDelivery = costoDelivery;
        return this;
    }

    public void setCostoDelivery(Double costoDelivery) {
        this.costoDelivery = costoDelivery;
    }

    public Producto getProducto() {
        return producto;
    }

    public CarritoProducto producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public CarritoProducto carrito(Carrito carrito) {
        this.carrito = carrito;
        return this;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarritoProducto)) {
            return false;
        }
        return id != null && id.equals(((CarritoProducto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CarritoProducto{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", precioCompra=" + getPrecioCompra() +
            ", estado='" + getEstado() + "'" +
            ", costoDelivery=" + getCostoDelivery() +
            "}";
    }
}
