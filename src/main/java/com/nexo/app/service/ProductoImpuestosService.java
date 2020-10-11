package com.nexo.app.service;

import com.nexo.app.service.dto.ProductoImpuestosDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.ProductoImpuestos}.
 */
public interface ProductoImpuestosService {

    /**
     * Save a productoImpuestos.
     *
     * @param productoImpuestosDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoImpuestosDTO save(ProductoImpuestosDTO productoImpuestosDTO);

    /**
     * Get all the productoImpuestos.
     *
     * @return the list of entities.
     */
    List<ProductoImpuestosDTO> findAll();


    /**
     * Get the "id" productoImpuestos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoImpuestosDTO> findOne(Long id);

    /**
     * Delete the "id" productoImpuestos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
