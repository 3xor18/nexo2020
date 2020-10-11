package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.ProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonaMapper.class, PaisMapper.class, ComunaMapper.class})
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {

    @Mapping(source = "vendedor.id", target = "vendedorId")
    @Mapping(source = "elaboradoEn.id", target = "elaboradoEnId")
    @Mapping(source = "comunaVenta.id", target = "comunaVentaId")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "vendedorId", target = "vendedor")
    @Mapping(source = "elaboradoEnId", target = "elaboradoEn")
    @Mapping(source = "comunaVentaId", target = "comunaVenta")
    Producto toEntity(ProductoDTO productoDTO);

    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
