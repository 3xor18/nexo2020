package com.nexo.app.service;

import com.nexo.app.service.dto.CarritoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.Carrito}.
 */
public interface CarritoService {

    /**
     * Save a carrito.
     *
     * @param carritoDTO the entity to save.
     * @return the persisted entity.
     */
    CarritoDTO save(CarritoDTO carritoDTO);

    /**
     * Get all the carritos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarritoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" carrito.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarritoDTO> findOne(Long id);

    /**
     * Delete the "id" carrito.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
