package com.nexo.app.service;

import com.nexo.app.service.dto.ProductoCategoriaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.ProductoCategoria}.
 */
public interface ProductoCategoriaService {

    /**
     * Save a productoCategoria.
     *
     * @param productoCategoriaDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoCategoriaDTO save(ProductoCategoriaDTO productoCategoriaDTO);

    /**
     * Get all the productoCategorias.
     *
     * @return the list of entities.
     */
    List<ProductoCategoriaDTO> findAll();


    /**
     * Get the "id" productoCategoria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoCategoriaDTO> findOne(Long id);

    /**
     * Delete the "id" productoCategoria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
