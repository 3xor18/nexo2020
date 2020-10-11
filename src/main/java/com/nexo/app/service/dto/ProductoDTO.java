package com.nexo.app.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nexo.app.domain.Producto} entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    private String nombre;

    private String codigo;

    private String codigoBarra;

    private String descripcion;

    private Double cantidadDisponible;

    private Double alertaMinimo;

    private LocalDate fechaVencimiento;

    private ZonedDateTime fechaBd;

    private String estado;

    private Double precioCompraBruto;

    private Double precioVentaTotalDetal;

    private Double precioVentaTotalMayor;

    private Double unidadMedidaVendida;

    private Double precioAlmayorDespuesde;


    private Long vendedorId;

    private Long elaboradoEnId;

    private Long comunaVentaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Double getAlertaMinimo() {
        return alertaMinimo;
    }

    public void setAlertaMinimo(Double alertaMinimo) {
        this.alertaMinimo = alertaMinimo;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public ZonedDateTime getFechaBd() {
        return fechaBd;
    }

    public void setFechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getPrecioCompraBruto() {
        return precioCompraBruto;
    }

    public void setPrecioCompraBruto(Double precioCompraBruto) {
        this.precioCompraBruto = precioCompraBruto;
    }

    public Double getPrecioVentaTotalDetal() {
        return precioVentaTotalDetal;
    }

    public void setPrecioVentaTotalDetal(Double precioVentaTotalDetal) {
        this.precioVentaTotalDetal = precioVentaTotalDetal;
    }

    public Double getPrecioVentaTotalMayor() {
        return precioVentaTotalMayor;
    }

    public void setPrecioVentaTotalMayor(Double precioVentaTotalMayor) {
        this.precioVentaTotalMayor = precioVentaTotalMayor;
    }

    public Double getUnidadMedidaVendida() {
        return unidadMedidaVendida;
    }

    public void setUnidadMedidaVendida(Double unidadMedidaVendida) {
        this.unidadMedidaVendida = unidadMedidaVendida;
    }

    public Double getPrecioAlmayorDespuesde() {
        return precioAlmayorDespuesde;
    }

    public void setPrecioAlmayorDespuesde(Double precioAlmayorDespuesde) {
        this.precioAlmayorDespuesde = precioAlmayorDespuesde;
    }

    public Long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Long personaId) {
        this.vendedorId = personaId;
    }

    public Long getElaboradoEnId() {
        return elaboradoEnId;
    }

    public void setElaboradoEnId(Long paisId) {
        this.elaboradoEnId = paisId;
    }

    public Long getComunaVentaId() {
        return comunaVentaId;
    }

    public void setComunaVentaId(Long comunaId) {
        this.comunaVentaId = comunaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (productoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", codigoBarra='" + getCodigoBarra() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidadDisponible=" + getCantidadDisponible() +
            ", alertaMinimo=" + getAlertaMinimo() +
            ", fechaVencimiento='" + getFechaVencimiento() + "'" +
            ", fechaBd='" + getFechaBd() + "'" +
            ", estado='" + getEstado() + "'" +
            ", precioCompraBruto=" + getPrecioCompraBruto() +
            ", precioVentaTotalDetal=" + getPrecioVentaTotalDetal() +
            ", precioVentaTotalMayor=" + getPrecioVentaTotalMayor() +
            ", unidadMedidaVendida=" + getUnidadMedidaVendida() +
            ", precioAlmayorDespuesde=" + getPrecioAlmayorDespuesde() +
            ", vendedor=" + getVendedorId() +
            ", elaboradoEn=" + getElaboradoEnId() +
            ", comunaVenta=" + getComunaVentaId() +
            "}";
    }
}
