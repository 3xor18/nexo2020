package com.nexo.app.service;

import com.nexo.app.service.dto.CostoDeliveryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.CostoDelivery}.
 */
public interface CostoDeliveryService {

    /**
     * Save a costoDelivery.
     *
     * @param costoDeliveryDTO the entity to save.
     * @return the persisted entity.
     */
    CostoDeliveryDTO save(CostoDeliveryDTO costoDeliveryDTO);

    /**
     * Get all the costoDeliveries.
     *
     * @return the list of entities.
     */
    List<CostoDeliveryDTO> findAll();


    /**
     * Get the "id" costoDelivery.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CostoDeliveryDTO> findOne(Long id);

    /**
     * Delete the "id" costoDelivery.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
