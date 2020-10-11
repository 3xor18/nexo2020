package com.nexo.app.service;

import com.nexo.app.service.dto.ProductoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.Producto}.
 */
public interface ProductoService {

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoDTO save(ProductoDTO productoDTO);

    /**
     * Get all the productos.
     *
     * @return the list of entities.
     */
    List<ProductoDTO> findAll();


    /**
     * Get the "id" producto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoDTO> findOne(Long id);

    /**
     * Delete the "id" producto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
