package com.nexo.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "codigo_barra")
    private String codigoBarra;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "cantidad_disponible")
    private Double cantidadDisponible;

    @Column(name = "alerta_minimo")
    private Double alertaMinimo;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_bd")
    private ZonedDateTime fechaBd;

    @Column(name = "estado")
    private String estado;

    @Column(name = "precio_compra_bruto")
    private Double precioCompraBruto;

    @Column(name = "precio_venta_total_detal")
    private Double precioVentaTotalDetal;

    @Column(name = "precio_venta_total_mayor")
    private Double precioVentaTotalMayor;

    @Column(name = "unidad_medida_vendida")
    private Double unidadMedidaVendida;

    @Column(name = "precio_almayor_despuesde")
    private Double precioAlmayorDespuesde;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Persona vendedor;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Pais elaboradoEn;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private ProductoImpuestos productoImpuestos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public Producto codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public Producto codigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
        return this;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public Producto cantidadDisponible(Double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
        return this;
    }

    public void setCantidadDisponible(Double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Double getAlertaMinimo() {
        return alertaMinimo;
    }

    public Producto alertaMinimo(Double alertaMinimo) {
        this.alertaMinimo = alertaMinimo;
        return this;
    }

    public void setAlertaMinimo(Double alertaMinimo) {
        this.alertaMinimo = alertaMinimo;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public Producto fechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
        return this;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public ZonedDateTime getFechaBd() {
        return fechaBd;
    }

    public Producto fechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
        return this;
    }

    public void setFechaBd(ZonedDateTime fechaBd) {
        this.fechaBd = fechaBd;
    }

    public String getEstado() {
        return estado;
    }

    public Producto estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getPrecioCompraBruto() {
        return precioCompraBruto;
    }

    public Producto precioCompraBruto(Double precioCompraBruto) {
        this.precioCompraBruto = precioCompraBruto;
        return this;
    }

    public void setPrecioCompraBruto(Double precioCompraBruto) {
        this.precioCompraBruto = precioCompraBruto;
    }

    public Double getPrecioVentaTotalDetal() {
        return precioVentaTotalDetal;
    }

    public Producto precioVentaTotalDetal(Double precioVentaTotalDetal) {
        this.precioVentaTotalDetal = precioVentaTotalDetal;
        return this;
    }

    public void setPrecioVentaTotalDetal(Double precioVentaTotalDetal) {
        this.precioVentaTotalDetal = precioVentaTotalDetal;
    }

    public Double getPrecioVentaTotalMayor() {
        return precioVentaTotalMayor;
    }

    public Producto precioVentaTotalMayor(Double precioVentaTotalMayor) {
        this.precioVentaTotalMayor = precioVentaTotalMayor;
        return this;
    }

    public void setPrecioVentaTotalMayor(Double precioVentaTotalMayor) {
        this.precioVentaTotalMayor = precioVentaTotalMayor;
    }

    public Double getUnidadMedidaVendida() {
        return unidadMedidaVendida;
    }

    public Producto unidadMedidaVendida(Double unidadMedidaVendida) {
        this.unidadMedidaVendida = unidadMedidaVendida;
        return this;
    }

    public void setUnidadMedidaVendida(Double unidadMedidaVendida) {
        this.unidadMedidaVendida = unidadMedidaVendida;
    }

    public Double getPrecioAlmayorDespuesde() {
        return precioAlmayorDespuesde;
    }

    public Producto precioAlmayorDespuesde(Double precioAlmayorDespuesde) {
        this.precioAlmayorDespuesde = precioAlmayorDespuesde;
        return this;
    }

    public void setPrecioAlmayorDespuesde(Double precioAlmayorDespuesde) {
        this.precioAlmayorDespuesde = precioAlmayorDespuesde;
    }

    public Persona getVendedor() {
        return vendedor;
    }

    public Producto vendedor(Persona persona) {
        this.vendedor = persona;
        return this;
    }

    public void setVendedor(Persona persona) {
        this.vendedor = persona;
    }

    public Pais getElaboradoEn() {
        return elaboradoEn;
    }

    public Producto elaboradoEn(Pais pais) {
        this.elaboradoEn = pais;
        return this;
    }

    public void setElaboradoEn(Pais pais) {
        this.elaboradoEn = pais;
    }

    public ProductoImpuestos getProductoImpuestos() {
        return productoImpuestos;
    }

    public Producto productoImpuestos(ProductoImpuestos productoImpuestos) {
        this.productoImpuestos = productoImpuestos;
        return this;
    }

    public void setProductoImpuestos(ProductoImpuestos productoImpuestos) {
        this.productoImpuestos = productoImpuestos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Producto{" +
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
            "}";
    }
}
