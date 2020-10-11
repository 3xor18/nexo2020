package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.CostoDeliveryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CostoDelivery} and its DTO {@link CostoDeliveryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, ComunaMapper.class})
public interface CostoDeliveryMapper extends EntityMapper<CostoDeliveryDTO, CostoDelivery> {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "comuna.id", target = "comunaId")
    CostoDeliveryDTO toDto(CostoDelivery costoDelivery);

    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "comunaId", target = "comuna")
    CostoDelivery toEntity(CostoDeliveryDTO costoDeliveryDTO);

    default CostoDelivery fromId(Long id) {
        if (id == null) {
            return null;
        }
        CostoDelivery costoDelivery = new CostoDelivery();
        costoDelivery.setId(id);
        return costoDelivery;
    }
}
