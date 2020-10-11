package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.CarritoProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarritoProducto} and its DTO {@link CarritoProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, CarritoMapper.class})
public interface CarritoProductoMapper extends EntityMapper<CarritoProductoDTO, CarritoProducto> {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "carrito.id", target = "carritoId")
    CarritoProductoDTO toDto(CarritoProducto carritoProducto);

    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "carritoId", target = "carrito")
    CarritoProducto toEntity(CarritoProductoDTO carritoProductoDTO);

    default CarritoProducto fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarritoProducto carritoProducto = new CarritoProducto();
        carritoProducto.setId(id);
        return carritoProducto;
    }
}
