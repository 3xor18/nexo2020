package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.ProductoCategoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductoCategoria} and its DTO {@link ProductoCategoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, CategoriaMapper.class})
public interface ProductoCategoriaMapper extends EntityMapper<ProductoCategoriaDTO, ProductoCategoria> {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "categoria.id", target = "categoriaId")
    ProductoCategoriaDTO toDto(ProductoCategoria productoCategoria);

    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "categoriaId", target = "categoria")
    ProductoCategoria toEntity(ProductoCategoriaDTO productoCategoriaDTO);

    default ProductoCategoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductoCategoria productoCategoria = new ProductoCategoria();
        productoCategoria.setId(id);
        return productoCategoria;
    }
}
