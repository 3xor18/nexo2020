package com.nexo.app.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nexo.app.domain.ProductoImagenes} entity.
 */
public class ProductoImagenesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String estado;

	private ZonedDateTime fechaBd;

	private String path;

	private String nombre;

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

	public ZonedDateTime getFechaBd() {
		return fechaBd;
	}

	public void setFechaBd(ZonedDateTime fechaBd) {
		this.fechaBd = fechaBd;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

		ProductoImagenesDTO productoImagenesDTO = (ProductoImagenesDTO) o;
		if (productoImagenesDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), productoImagenesDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "ProductoImagenesDTO{" + "id=" + getId() + ", estado='" + getEstado() + "'" + ", fechaBd='"
				+ getFechaBd() + "'" + ", path='" + getPath() + "'" + ", nombre='" + getNombre() + "'" + ", producto="
				+ getProductoId() + "}";
	}
}
