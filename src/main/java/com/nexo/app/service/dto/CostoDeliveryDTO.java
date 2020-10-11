package com.nexo.app.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nexo.app.domain.CostoDelivery} entity.
 */
public class CostoDeliveryDTO implements Serializable {

    private Long id;

    private String estado;

    private Double montoIndividual;

    private Double montoMayor;

    private Double cantidadMayor;

    private ZonedDateTime fechaBd;


    private Long productoId;

    private Long comunaId;

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

    public Double getMontoIndividual() {
        return montoIndividual;
    }

    public void setMontoIndividual(Double montoIndividual) {
        this.montoIndividual = montoIndividual;
    }

    public Double getMontoMayor() {
        return montoMayor;
    }

    public void setMontoMayor(Double montoMayor) {
        this.montoMayor = montoMayor;
    }

    public Double getCantidadMayor() {
        return cantidadMayor;
    }

    public void setCantidadMayor(Double cantidadMayor) {
        this.cantidadMayor = cantidadMayor;
    }

    public ZonedDateTime getFechaBd() {
        return fechaBd;
    }

    public void setFechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getComunaId() {
        return comunaId;
    }

    public void setComunaId(Long comunaId) {
        this.comunaId = comunaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CostoDeliveryDTO costoDeliveryDTO = (CostoDeliveryDTO) o;
        if (costoDeliveryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), costoDeliveryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CostoDeliveryDTO{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", montoIndividual=" + getMontoIndividual() +
            ", montoMayor=" + getMontoMayor() +
            ", cantidadMayor=" + getCantidadMayor() +
            ", fechaBd='" + getFechaBd() + "'" +
            ", producto=" + getProductoId() +
            ", comuna=" + getComunaId() +
            "}";
    }
}
