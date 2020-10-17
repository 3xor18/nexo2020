package com.nexo.app.service;

import com.nexo.app.service.dto.RegionDTO;
import com.nexo.app.web.rest.errors.NexoNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.Region}.
 */
public interface RegionService {

    /**
     * Save a region.
     *
     * @param regionDTO the entity to save.
     * @return the persisted entity.
     */
    RegionDTO save(RegionDTO regionDTO);

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    List<RegionDTO> findAll();


    /**
     * Get the "id" region.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegionDTO> findOne(Long id);

    /**
     * Delete the "id" region.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * @return listado de regiones del pais
     * @throws NexoNotFoundException 
     */
    List<RegionDTO> findByPais(Long id) throws NexoNotFoundException;
}
