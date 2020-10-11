package com.nexo.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Carrito.
 */
@Entity
@Table(name = "carrito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Carrito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_pedido")
    private ZonedDateTime fechaPedido;

    @Column(name = "fecha_pago")
    private ZonedDateTime fechaPago;

    @Column(name = "fecha_confirmado_pago")
    private ZonedDateTime fechaConfirmadoPago;

    @Column(name = "fecha_entrega")
    private ZonedDateTime fechaEntrega;

    @Column(name = "estado")
    private String estado;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    @Column(name = "whatsapp")
    private Boolean whatsapp;

    @Column(name = "puntaje_comprador")
    private Double puntajeComprador;

    @Column(name = "puntaje_vendedor")
    private Double puntajeVendedor;

    @Column(name = "fecha_termino")
    private ZonedDateTime fechaTermino;

    @Column(name = "razon_termino")
    private String razonTermino;

    @Column(name = "monto_total_compra")
    private Double montoTotalCompra;

    @Column(name = "monto_delivery")
    private Double montoDelivery;

    @ManyToOne
    private Direccion direccionDelivery;

    @ManyToOne
    private Persona vendedor;

    @ManyToOne
    private Persona cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaPedido() {
        return fechaPedido;
    }

    public Carrito fechaPedido(ZonedDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
        return this;
    }

    public void setFechaPedido(ZonedDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public ZonedDateTime getFechaPago() {
        return fechaPago;
    }

    public Carrito fechaPago(ZonedDateTime fechaPago) {
        this.fechaPago = fechaPago;
        return this;
    }

    public void setFechaPago(ZonedDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public ZonedDateTime getFechaConfirmadoPago() {
        return fechaConfirmadoPago;
    }

    public Carrito fechaConfirmadoPago(ZonedDateTime fechaConfirmadoPago) {
        this.fechaConfirmadoPago = fechaConfirmadoPago;
        return this;
    }

    public void setFechaConfirmadoPago(ZonedDateTime fechaConfirmadoPago) {
        this.fechaConfirmadoPago = fechaConfirmadoPago;
    }

    public ZonedDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public Carrito fechaEntrega(ZonedDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public void setFechaEntrega(ZonedDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public Carrito estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public Carrito telefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
        return this;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Boolean isWhatsapp() {
        return whatsapp;
    }

    public Carrito whatsapp(Boolean whatsapp) {
        this.whatsapp = whatsapp;
        return this;
    }

    public void setWhatsapp(Boolean whatsapp) {
        this.whatsapp = whatsapp;
    }

    public Double getPuntajeComprador() {
        return puntajeComprador;
    }

    public Carrito puntajeComprador(Double puntajeComprador) {
        this.puntajeComprador = puntajeComprador;
        return this;
    }

    public void setPuntajeComprador(Double puntajeComprador) {
        this.puntajeComprador = puntajeComprador;
    }

    public Double getPuntajeVendedor() {
        return puntajeVendedor;
    }

    public Carrito puntajeVendedor(Double puntajeVendedor) {
        this.puntajeVendedor = puntajeVendedor;
        return this;
    }

    public void setPuntajeVendedor(Double puntajeVendedor) {
        this.puntajeVendedor = puntajeVendedor;
    }

    public ZonedDateTime getFechaTermino() {
        return fechaTermino;
    }

    public Carrito fechaTermino(ZonedDateTime fechaTermino) {
        this.fechaTermino = fechaTermino;
        return this;
    }

    public void setFechaTermino(ZonedDateTime fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getRazonTermino() {
        return razonTermino;
    }

    public Carrito razonTermino(String razonTermino) {
        this.razonTermino = razonTermino;
        return this;
    }

    public void setRazonTermino(String razonTermino) {
        this.razonTermino = razonTermino;
    }

    public Double getMontoTotalCompra() {
        return montoTotalCompra;
    }

    public Carrito montoTotalCompra(Double montoTotalCompra) {
        this.montoTotalCompra = montoTotalCompra;
        return this;
    }

    public void setMontoTotalCompra(Double montoTotalCompra) {
        this.montoTotalCompra = montoTotalCompra;
    }

    public Double getMontoDelivery() {
        return montoDelivery;
    }

    public Carrito montoDelivery(Double montoDelivery) {
        this.montoDelivery = montoDelivery;
        return this;
    }

    public void setMontoDelivery(Double montoDelivery) {
        this.montoDelivery = montoDelivery;
    }

    public Direccion getDireccionDelivery() {
        return direccionDelivery;
    }

    public Carrito direccionDelivery(Direccion direccion) {
        this.direccionDelivery = direccion;
        return this;
    }

    public void setDireccionDelivery(Direccion direccion) {
        this.direccionDelivery = direccion;
    }

    public Persona getVendedor() {
        return vendedor;
    }

    public Carrito vendedor(Persona persona) {
        this.vendedor = persona;
        return this;
    }

    public void setVendedor(Persona persona) {
        this.vendedor = persona;
    }

    public Persona getCliente() {
        return cliente;
    }

    public Carrito cliente(Persona persona) {
        this.cliente = persona;
        return this;
    }

    public void setCliente(Persona persona) {
        this.cliente = persona;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carrito)) {
            return false;
        }
        return id != null && id.equals(((Carrito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Carrito{" +
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
            "}";
    }
}
