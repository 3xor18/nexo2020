package com.nexo.app.service;

import com.nexo.app.domain.Pais;
import com.nexo.app.service.dto.PaisDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.Pais}.
 */
public interface PaisService {

    /**
     * Save a pais.
     *
     * @param paisDTO the entity to save.
     * @return the persisted entity.
     */
    PaisDTO save(PaisDTO paisDTO);

    /**
     * Get all the pais.
     *
     * @return the list of entities.
     */
    List<PaisDTO> findAll();


    /**
     * Get the "id" pais.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaisDTO> findOne(Long id);

    /**
     * Delete the "id" pais.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * @param id del pais
     * @return pais
     */
    Optional<Pais> findById(Long id);
}
