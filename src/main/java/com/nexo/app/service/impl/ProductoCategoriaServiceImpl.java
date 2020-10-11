package com.nexo.app.service.impl;

import com.nexo.app.service.ProductoCategoriaService;
import com.nexo.app.domain.ProductoCategoria;
import com.nexo.app.repository.ProductoCategoriaRepository;
import com.nexo.app.service.dto.ProductoCategoriaDTO;
import com.nexo.app.service.mapper.ProductoCategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductoCategoria}.
 */
@Service
@Transactional
public class ProductoCategoriaServiceImpl implements ProductoCategoriaService {

    private final Logger log = LoggerFactory.getLogger(ProductoCategoriaServiceImpl.class);

    private final ProductoCategoriaRepository productoCategoriaRepository;

    private final ProductoCategoriaMapper productoCategoriaMapper;

    public ProductoCategoriaServiceImpl(ProductoCategoriaRepository productoCategoriaRepository, ProductoCategoriaMapper productoCategoriaMapper) {
        this.productoCategoriaRepository = productoCategoriaRepository;
        this.productoCategoriaMapper = productoCategoriaMapper;
    }

    /**
     * Save a productoCategoria.
     *
     * @param productoCategoriaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductoCategoriaDTO save(ProductoCategoriaDTO productoCategoriaDTO) {
        log.debug("Request to save ProductoCategoria : {}", productoCategoriaDTO);
        ProductoCategoria productoCategoria = productoCategoriaMapper.toEntity(productoCategoriaDTO);
        productoCategoria = productoCategoriaRepository.save(productoCategoria);
        return productoCategoriaMapper.toDto(productoCategoria);
    }

    /**
     * Get all the productoCategorias.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoCategoriaDTO> findAll() {
        log.debug("Request to get all ProductoCategorias");
        return productoCategoriaRepository.findAll().stream()
            .map(productoCategoriaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productoCategoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoCategoriaDTO> findOne(Long id) {
        log.debug("Request to get ProductoCategoria : {}", id);
        return productoCategoriaRepository.findById(id)
            .map(productoCategoriaMapper::toDto);
    }

    /**
     * Delete the productoCategoria by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoCategoria : {}", id);
        productoCategoriaRepository.deleteById(id);
    }
}
