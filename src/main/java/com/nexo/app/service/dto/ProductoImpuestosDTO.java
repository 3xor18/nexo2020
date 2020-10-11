package com.nexo.app.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nexo.app.domain.ProductoImpuestos} entity.
 */
public class ProductoImpuestosDTO implements Serializable {

    private Long id;

    private String estado;

    private String nombre;

    private ZonedDateTime fechaBd;

    private Boolean impuestoMontoFijo;

    private Double montoOTasa;

    private Boolean impuestoNacional;

    private ZonedDateTime fechaAplicable;


    private Long productoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ZonedDateTime getFechaBd() {
        return fechaBd;
    }

    public void setFechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
    }

    public Boolean isImpuestoMontoFijo() {
        return impuestoMontoFijo;
    }

    public void setImpuestoMontoFijo(Boolean impuestoMontoFijo) {
        this.impuestoMontoFijo = impuestoMontoFijo;
    }

    public Double getMontoOTasa() {
        return montoOTasa;
    }

    public void setMontoOTasa(Double montoOTasa) {
        this.montoOTasa = montoOTasa;
    }

    public Boolean isImpuestoNacional() {
        return impuestoNacional;
    }

    public void setImpuestoNacional(Boolean impuestoNacional) {
        this.impuestoNacional = impuestoNacional;
    }

    public ZonedDateTime getFechaAplicable() {
        return fechaAplicable;
    }

    public void setFechaAplicable(ZonedDateTime fechaAplicable) {
        this.fechaAplicable = fechaAplicable;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoImpuestosDTO productoImpuestosDTO = (ProductoImpuestosDTO) o;
        if (productoImpuestosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoImpuestosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoImpuestosDTO{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", fechaBd='" + getFechaBd() + "'" +
            ", impuestoMontoFijo='" + isImpuestoMontoFijo() + "'" +
            ", montoOTasa=" + getMontoOTasa() +
            ", impuestoNacional='" + isImpuestoNacional() + "'" +
            ", fechaAplicable='" + getFechaAplicable() + "'" +
            ", producto=" + getProductoId() +
            "}";
    }
}
