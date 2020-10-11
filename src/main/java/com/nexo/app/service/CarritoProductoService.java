package com.nexo.app.service;

import com.nexo.app.service.dto.CarritoProductoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.CarritoProducto}.
 */
public interface CarritoProductoService {

    /**
     * Save a carritoProducto.
     *
     * @param carritoProductoDTO the entity to save.
     * @return the persisted entity.
     */
    CarritoProductoDTO save(CarritoProductoDTO carritoProductoDTO);

    /**
     * Get all the carritoProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarritoProductoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" carritoProducto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarritoProductoDTO> findOne(Long id);

    /**
     * Delete the "id" carritoProducto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
