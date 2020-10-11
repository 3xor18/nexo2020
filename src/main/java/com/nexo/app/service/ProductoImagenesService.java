package com.nexo.app.service;

import com.nexo.app.service.dto.ProductoImagenesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.ProductoImagenes}.
 */
public interface ProductoImagenesService {

    /**
     * Save a productoImagenes.
     *
     * @param productoImagenesDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoImagenesDTO save(ProductoImagenesDTO productoImagenesDTO);

    /**
     * Get all the productoImagenes.
     *
     * @return the list of entities.
     */
    List<ProductoImagenesDTO> findAll();


    /**
     * Get the "id" productoImagenes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoImagenesDTO> findOne(Long id);

    /**
     * Delete the "id" productoImagenes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
