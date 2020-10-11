package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.DireccionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Direccion} and its DTO {@link DireccionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ComunaMapper.class, PersonaMapper.class})
public interface DireccionMapper extends EntityMapper<DireccionDTO, Direccion> {

    @Mapping(source = "comuna.id", target = "comunaId")
    @Mapping(source = "persona.id", target = "personaId")
    DireccionDTO toDto(Direccion direccion);

    @Mapping(source = "comunaId", target = "comuna")
    @Mapping(source = "personaId", target = "persona")
    Direccion toEntity(DireccionDTO direccionDTO);

    default Direccion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Direccion direccion = new Direccion();
        direccion.setId(id);
        return direccion;
    }
}
