package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.PaisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pais} and its DTO {@link PaisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaisMapper extends EntityMapper<PaisDTO, Pais> {



    default Pais fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pais pais = new Pais();
        pais.setId(id);
        return pais;
    }
}
