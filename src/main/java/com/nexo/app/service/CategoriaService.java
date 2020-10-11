package com.nexo.app.service;

import com.nexo.app.service.dto.CategoriaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nexo.app.domain.Categoria}.
 */
public interface CategoriaService {

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save.
     * @return the persisted entity.
     */
    CategoriaDTO save(CategoriaDTO categoriaDTO);

    /**
     * Get all the categorias.
     *
     * @return the list of entities.
     */
    List<CategoriaDTO> findAll();


    /**
     * Get the "id" categoria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriaDTO> findOne(Long id);

    /**
     * Delete the "id" categoria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
