package com.nexo.app.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nexo.app.domain.ProductoCategoria} entity.
 */
public class ProductoCategoriaDTO implements Serializable {

    private Long id;

    private String estado;

    private ZonedDateTime fechaBD;


    private Long productoId;

    private Long categoriaId;

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

    public ZonedDateTime getFechaBD() {
        return fechaBD;
    }

    public void setFechaBD(ZonedDateTime fechaBD) {
        this.fechaBD = fechaBD;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoCategoriaDTO productoCategoriaDTO = (ProductoCategoriaDTO) o;
        if (productoCategoriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoCategoriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoCategoriaDTO{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", fechaBD='" + getFechaBD() + "'" +
            ", producto=" + getProductoId() +
            ", categoria=" + getCategoriaId() +
            "}";
    }
}
