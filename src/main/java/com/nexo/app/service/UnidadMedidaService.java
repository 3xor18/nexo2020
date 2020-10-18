package com.nexo.app.service;

import com.nexo.app.domain.UnidadMedida;
import com.nexo.app.service.dto.UnidadMedidaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.UnidadMedida}.
 */
public interface UnidadMedidaService {

    /**
     * Save a unidadMedida.
     *
     * @param unidadMedidaDTO the entity to save.
     * @return the persisted entity.
     */
    UnidadMedidaDTO save(UnidadMedidaDTO unidadMedidaDTO);

    /**
     * Get all the unidadMedidas.
     *
     * @return the list of entities.
     */
    List<UnidadMedidaDTO> findAll();


    /**
     * Get the "id" unidadMedida.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnidadMedidaDTO> findOne(Long id);

    /**
     * Delete the "id" unidadMedida.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * @param id
     * @return la unidad de medida
     */
    Optional<UnidadMedida> findById(Long id);
}
