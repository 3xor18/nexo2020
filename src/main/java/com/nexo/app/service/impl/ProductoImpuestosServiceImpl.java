package com.nexo.app.service.impl;

import com.nexo.app.service.ProductoImpuestosService;
import com.nexo.app.domain.ProductoImpuestos;
import com.nexo.app.repository.ProductoImpuestosRepository;
import com.nexo.app.service.dto.ProductoImpuestosDTO;
import com.nexo.app.service.mapper.ProductoImpuestosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductoImpuestos}.
 */
@Service
@Transactional
public class ProductoImpuestosServiceImpl implements ProductoImpuestosService {

    private final Logger log = LoggerFactory.getLogger(ProductoImpuestosServiceImpl.class);

    private final ProductoImpuestosRepository productoImpuestosRepository;

    private final ProductoImpuestosMapper productoImpuestosMapper;

    public ProductoImpuestosServiceImpl(ProductoImpuestosRepository productoImpuestosRepository, ProductoImpuestosMapper productoImpuestosMapper) {
        this.productoImpuestosRepository = productoImpuestosRepository;
        this.productoImpuestosMapper = productoImpuestosMapper;
    }

    /**
     * Save a productoImpuestos.
     *
     * @param productoImpuestosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductoImpuestosDTO save(ProductoImpuestosDTO productoImpuestosDTO) {
        log.debug("Request to save ProductoImpuestos : {}", productoImpuestosDTO);
        ProductoImpuestos productoImpuestos = productoImpuestosMapper.toEntity(productoImpuestosDTO);
        productoImpuestos = productoImpuestosRepository.save(productoImpuestos);
        return productoImpuestosMapper.toDto(productoImpuestos);
    }

    /**
     * Get all the productoImpuestos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoImpuestosDTO> findAll() {
        log.debug("Request to get all ProductoImpuestos");
        return productoImpuestosRepository.findAll().stream()
            .map(productoImpuestosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productoImpuestos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoImpuestosDTO> findOne(Long id) {
        log.debug("Request to get ProductoImpuestos : {}", id);
        return productoImpuestosRepository.findById(id)
            .map(productoImpuestosMapper::toDto);
    }

    /**
     * Delete the productoImpuestos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoImpuestos : {}", id);
        productoImpuestosRepository.deleteById(id);
    }
}
