package com.nexo.app.service.impl;

import com.nexo.app.service.ProductoService;
import com.nexo.app.domain.Producto;
import com.nexo.app.repository.ProductoRepository;
import com.nexo.app.service.dto.ProductoDTO;
import com.nexo.app.service.mapper.ProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Producto}.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {
	
	private final int PAGINADO_BY=20;

    private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);
        Producto producto = productoMapper.toEntity(productoDTO);
        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

    /**
     * Get all the productos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAll() {
        log.debug("Request to get all Productos");
        return productoRepository.findAll().stream()
            .map(productoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one producto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        return productoRepository.findById(id)
            .map(productoMapper::toDto);
    }

    /**
     * Delete the producto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.deleteById(id);
    }

	@Override
	public Page<ProductoDTO> getMyProductsPage(Integer page) {
		Pageable pageable=new PageRequest(page, PAGINADO_BY);
		Page<Producto> productos=productoRepository.findByCurrentUset()
		return null;
	}
}
