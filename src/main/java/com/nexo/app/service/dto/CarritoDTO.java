package com.nexo.app.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nexo.app.domain.Carrito} entity.
 */
public class CarritoDTO implements Serializable {

    private Long id;

    private ZonedDateTime fechaPedido;

    private ZonedDateTime fechaPago;

    private ZonedDateTime fechaConfirmadoPago;

    private ZonedDateTime fechaEntrega;

    private String estado;

    private String telefonoContacto;

    private Boolean whatsapp;

    private Double puntajeComprador;

    private Double puntajeVendedor;

    private ZonedDateTime fechaTermino;

    private String razonTermino;

    private Double montoTotalCompra;

    private Double montoDelivery;


    private Long direccionDeliveryId;

    private Long vendedorId;

    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(ZonedDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public ZonedDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(ZonedDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public ZonedDateTime getFechaConfirmadoPago() {
        return fechaConfirmadoPago;
    }

    public void setFechaConfirmadoPago(ZonedDateTime fechaConfirmadoPago) {
        this.fechaConfirmadoPago = fechaConfirmadoPago;
    }

    public ZonedDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(ZonedDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Boolean isWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(Boolean whatsapp) {
        this.whatsapp = whatsapp;
    }

    public Double getPuntajeComprador() {
        return puntajeComprador;
    }

    public void setPuntajeComprador(Double puntajeComprador) {
        this.puntajeComprador = puntajeComprador;
    }

    public Double getPuntajeVendedor() {
        return puntajeVendedor;
    }

    public void setPuntajeVendedor(Double puntajeVendedor) {
        this.puntajeVendedor = puntajeVendedor;
    }

    public ZonedDateTime getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(ZonedDateTime fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getRazonTermino() {
        return razonTermino;
    }

    public void setRazonTermino(String razonTermino) {
        this.razonTermino = razonTermino;
    }

    public Double getMontoTotalCompra() {
        return montoTotalCompra;
    }

    public void setMontoTotalCompra(Double montoTotalCompra) {
        this.montoTotalCompra = montoTotalCompra;
    }

    public Double getMontoDelivery() {
        return montoDelivery;
    }

    public void setMontoDelivery(Double montoDelivery) {
        this.montoDelivery = montoDelivery;
    }

    public Long getDireccionDeliveryId() {
        return direccionDeliveryId;
    }

    public void setDireccionDeliveryId(Long direccionId) {
        this.direccionDeliveryId = direccionId;
    }

    public Long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Long personaId) {
        this.vendedorId = personaId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long personaId) {
        this.clienteId = personaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarritoDTO carritoDTO = (CarritoDTO) o;
        if (carritoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carritoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarritoDTO{" +
            "id=" + getId() +
            ", fechaPedido='" + getFechaPedido() + "'" +
            ", fechaPago='" + getFechaPago() + "'" +
            ", fechaConfirmadoPago='" + getFechaConfirmadoPago() + "'" +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            ", estado='" + getEstado() + "'" +
            ", telefonoContacto='" + getTelefonoContacto() + "'" +
            ", whatsapp='" + isWhatsapp() + "'" +
            ", puntajeComprador=" + getPuntajeComprador() +
            ", puntajeVendedor=" + getPuntajeVendedor() +
            ", fechaTermino='" + getFechaTermino() + "'" +
            ", razonTermino='" + getRazonTermino() + "'" +
            ", montoTotalCompra=" + getMontoTotalCompra() +
            ", montoDelivery=" + getMontoDelivery() +
            ", direccionDelivery=" + getDireccionDeliveryId() +
            ", vendedor=" + getVendedorId() +
            ", cliente=" + getClienteId() +
            "}";
    }
}
