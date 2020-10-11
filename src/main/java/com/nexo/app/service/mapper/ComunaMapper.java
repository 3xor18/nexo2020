package com.nexo.app.service.mapper;

import com.nexo.app.domain.*;
import com.nexo.app.service.dto.ComunaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comuna} and its DTO {@link ComunaDTO}.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class})
public interface ComunaMapper extends EntityMapper<ComunaDTO, Comuna> {

    @Mapping(source = "region.id", target = "regionId")
    ComunaDTO toDto(Comuna comuna);

    @Mapping(source = "regionId", target = "region")
    Comuna toEntity(ComunaDTO comunaDTO);

    default Comuna fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comuna comuna = new Comuna();
        comuna.setId(id);
        return comuna;
    }
}
