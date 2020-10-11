package com.nexo.app.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nexo.app.domain.UnidadMedida} entity.
 */
public class UnidadMedidaDTO implements Serializable {

    private Long id;

    private String estado;

    private String nombre;

    private ZonedDateTime fechaBd;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnidadMedidaDTO unidadMedidaDTO = (UnidadMedidaDTO) o;
        if (unidadMedidaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unidadMedidaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnidadMedidaDTO{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", fechaBd='" + getFechaBd() + "'" +
            "}";
    }
}
