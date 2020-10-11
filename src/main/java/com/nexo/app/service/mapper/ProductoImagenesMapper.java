package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.ProductoImagenesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductoImagenes} and its DTO {@link ProductoImagenesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface ProductoImagenesMapper extends EntityMapper<ProductoImagenesDTO, ProductoImagenes> {

    @Mapping(source = "producto.id", target = "productoId")
    ProductoImagenesDTO toDto(ProductoImagenes productoImagenes);

    @Mapping(source = "productoId", target = "producto")
    ProductoImagenes toEntity(ProductoImagenesDTO productoImagenesDTO);

    default ProductoImagenes fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductoImagenes productoImagenes = new ProductoImagenes();
        productoImagenes.setId(id);
        return productoImagenes;
    }
}
