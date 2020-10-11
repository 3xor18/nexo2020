package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.UnidadMedidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnidadMedida} and its DTO {@link UnidadMedidaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnidadMedidaMapper extends EntityMapper<UnidadMedidaDTO, UnidadMedida> {



    default UnidadMedida fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadMedida unidadMedida = new UnidadMedida();
        unidadMedida.setId(id);
        return unidadMedida;
    }
}
