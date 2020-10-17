package com.nexo.app.service;

import com.nexo.app.service.dto.ComunaDTO;
import com.nexo.app.web.rest.errors.NexoNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.Comuna}.
 */
public interface ComunaService {

    /**
     * Save a comuna.
     *
     * @param comunaDTO the entity to save.
     * @return the persisted entity.
     */
    ComunaDTO save(ComunaDTO comunaDTO);

    /**
     * Get all the comunas.
     *
     * @return the list of entities.
     */
    List<ComunaDTO> findAll();


    /**
     * Get the "id" comuna.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComunaDTO> findOne(Long id);

    /**
     * Delete the "id" comuna.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * @param idRegion
     * @return las comunas de la region
     * @throws NexoNotFoundException 
     */
    List<ComunaDTO> findByRegion(Long idRegion) throws NexoNotFoundException;
}
