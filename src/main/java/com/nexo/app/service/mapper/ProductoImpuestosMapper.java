package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.ProductoImpuestosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductoImpuestos} and its DTO {@link ProductoImpuestosDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface ProductoImpuestosMapper extends EntityMapper<ProductoImpuestosDTO, ProductoImpuestos> {

    @Mapping(source = "producto.id", target = "productoId")
    ProductoImpuestosDTO toDto(ProductoImpuestos productoImpuestos);

    @Mapping(source = "productoId", target = "producto")
    ProductoImpuestos toEntity(ProductoImpuestosDTO productoImpuestosDTO);

    default ProductoImpuestos fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductoImpuestos productoImpuestos = new ProductoImpuestos();
        productoImpuestos.setId(id);
        return productoImpuestos;
    }
}
