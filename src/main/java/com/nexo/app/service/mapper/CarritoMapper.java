package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.CarritoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Carrito} and its DTO {@link CarritoDTO}.
 */
@Mapper(componentModel = "spring", uses = {DireccionMapper.class, PersonaMapper.class})
public interface CarritoMapper extends EntityMapper<CarritoDTO, Carrito> {

    @Mapping(source = "direccionDelivery.id", target = "direccionDeliveryId")
    @Mapping(source = "vendedor.id", target = "vendedorId")
    @Mapping(source = "cliente.id", target = "clienteId")
    CarritoDTO toDto(Carrito carrito);

    @Mapping(source = "direccionDeliveryId", target = "direccionDelivery")
    @Mapping(source = "vendedorId", target = "vendedor")
    @Mapping(source = "clienteId", target = "cliente")
    Carrito toEntity(CarritoDTO carritoDTO);

    default Carrito fromId(Long id) {
        if (id == null) {
            return null;
        }
        Carrito carrito = new Carrito();
        carrito.setId(id);
        return carrito;
    }
}
