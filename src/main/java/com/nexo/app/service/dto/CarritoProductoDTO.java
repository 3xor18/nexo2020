package com.nexo.app.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nexo.app.domain.CarritoProducto} entity.
 */
public class CarritoProductoDTO implements Serializable {

    private Long id;

    private Double cantidad;

    private Double precioCompra;

    private String estado;

    private Double costoDelivery;


    private Long productoId;

    private Long carritoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getCostoDelivery() {
        return costoDelivery;
    }

    public void setCostoDelivery(Double costoDelivery) {
        this.costoDelivery = costoDelivery;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(Long carritoId) {
        this.carritoId = carritoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarritoProductoDTO carritoProductoDTO = (CarritoProductoDTO) o;
        if (carritoProductoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carritoProductoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarritoProductoDTO{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", precioCompra=" + getPrecioCompra() +
            ", estado='" + getEstado() + "'" +
            ", costoDelivery=" + getCostoDelivery() +
            ", producto=" + getProductoId() +
            ", carrito=" + getCarritoId() +
            "}";
    }
}
