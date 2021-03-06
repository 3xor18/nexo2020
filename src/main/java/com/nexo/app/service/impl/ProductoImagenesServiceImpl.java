package com.nexo.app.service.impl;

import com.nexo.app.service.ProductoImagenesService;
import com.nexo.app.service.ProductoService;
import com.nexo.app.service.UtilsService;
import com.nexo.app.config.Constants;
import com.nexo.app.domain.Producto;
import com.nexo.app.domain.ProductoImagenes;
import com.nexo.app.repository.ProductoImagenesRepository;
import com.nexo.app.service.dto.ProductoImagenesDTO;
import com.nexo.app.service.mapper.ProductoImagenesMapper;
import com.nexo.app.web.rest.errors.NexoNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductoImagenes}.
 */
@Service
@Transactional
public class ProductoImagenesServiceImpl implements ProductoImagenesService {

    private final Logger log = LoggerFactory.getLogger(ProductoImagenesServiceImpl.class);

    private final ProductoImagenesRepository productoImagenesRepository;

    private final ProductoImagenesMapper productoImagenesMapper;
    
    private final UtilsService utilsService;
    
    private final ProductoService productoService;

    public ProductoImagenesServiceImpl(ProductoImagenesRepository productoImagenesRepository, ProductoImagenesMapper productoImagenesMapper,
    		UtilsService utilsService,ProductoService productoService) {
        this.productoImagenesRepository = productoImagenesRepository;
        this.productoImagenesMapper = productoImagenesMapper;
        this.utilsService=utilsService;
        this.productoService=productoService;
    }

    /**
     * Save a productoImagenes.
     *
     * @param productoImagenesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductoImagenesDTO save(ProductoImagenesDTO productoImagenesDTO) {
        log.debug("Request to save ProductoImagenes : {}", productoImagenesDTO);
        ProductoImagenes productoImagenes = productoImagenesMapper.toEntity(productoImagenesDTO);
        productoImagenes = productoImagenesRepository.save(productoImagenes);
        return productoImagenesMapper.toDto(productoImagenes);
    }

    /**
     * Get all the productoImagenes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoImagenesDTO> findAll() {
        log.debug("Request to get all ProductoImagenes");
        return productoImagenesRepository.findAll().stream()
            .map(productoImagenesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productoImagenes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoImagenesDTO> findOne(Long id) {
        log.debug("Request to get ProductoImagenes : {}", id);
        return productoImagenesRepository.findById(id)
            .map(productoImagenesMapper::toDto);
    }

    /**
     * Delete the productoImagenes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoImagenes : {}", id);
        productoImagenesRepository.deleteById(id);
    }

	@Override
	public ProductoImagenesDTO agregarImagen(ProductoImagenesDTO dtoIn) throws NexoNotFoundException {
		ProductoImagenes imagen=new ProductoImagenes();
		Producto producto=productoService.findById(dtoIn.getProductoId()).orElseThrow(()->new NexoNotFoundException(Constants.NO_ENCONTRADO,"Producto no encontrado"));
		imagen.setEstado(Constants.ACTIVO);
		imagen.setFechaBd(utilsService.giveToday());
		imagen.setPath(dtoIn.getPath());
		imagen.setProducto(producto);
		ProductoImagenes newImagen= productoImagenesRepository.save(imagen);
		 return productoImagenesMapper.toDto(newImagen);
	}
}
