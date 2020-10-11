package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.PersonaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Persona} and its DTO {@link PersonaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PaisMapper.class, UserMapper.class})
public interface PersonaMapper extends EntityMapper<PersonaDTO, Persona> {

    @Mapping(source = "pais.id", target = "paisId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PersonaDTO toDto(Persona persona);

    @Mapping(source = "paisId", target = "pais")
    @Mapping(source = "userId", target = "user")
    Persona toEntity(PersonaDTO personaDTO);

    default Persona fromId(Long id) {
        if (id == null) {
            return null;
        }
        Persona persona = new Persona();
        persona.setId(id);
        return persona;
    }
}
