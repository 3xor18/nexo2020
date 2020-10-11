package com.nexo.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ProductoImagenes.
 */
@Entity
@Table(name = "producto_imagenes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductoImagenes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_bd")
    private ZonedDateTime fechaBd;

    @Column(name = "path")
    private String path;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JsonIgnoreProperties("productoImagenes")
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

    public ProductoImagenes estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ZonedDateTime getFechaBd() {
        return fechaBd;
    }

    public ProductoImagenes fechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
        return this;
    }

    public void setFechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
    }

    public String getPath() {
        return path;
    }

    public ProductoImagenes path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNombre() {
        return nombre;
    }

    public ProductoImagenes nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Producto getProducto() {
        return producto;
    }

    public ProductoImagenes producto(Producto producto) {
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
        if (!(o instanceof ProductoImagenes)) {
            return false;
        }
        return id != null && id.equals(((ProductoImagenes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductoImagenes{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", fechaBd='" + getFechaBd() + "'" +
            ", path='" + getPath() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
