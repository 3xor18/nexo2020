package com.nexo.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ProductoCategoria.
 */
@Entity
@Table(name = "producto_categoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductoCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_bd")
    private ZonedDateTime fechaBD;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Categoria categoria;

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

    public ProductoCategoria estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ZonedDateTime getFechaBD() {
        return fechaBD;
    }

    public ProductoCategoria fechaBD(ZonedDateTime fechaBD) {
        this.fechaBD = fechaBD;
        return this;
    }

    public void setFechaBD(ZonedDateTime fechaBD) {
        this.fechaBD = fechaBD;
    }

    public Producto getProducto() {
        return producto;
    }

    public ProductoCategoria producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public ProductoCategoria categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoCategoria)) {
            return false;
        }
        return id != null && id.equals(((ProductoCategoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductoCategoria{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", fechaBD='" + getFechaBD() + "'" +
            "}";
    }
}
