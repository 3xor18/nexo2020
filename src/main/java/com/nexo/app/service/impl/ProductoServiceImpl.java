package com.nexo.app.service.impl;

import com.nexo.app.service.PersonaService;
import com.nexo.app.service.ProductoService;
import com.nexo.app.service.UserService;
import com.nexo.app.config.Constants;
import com.nexo.app.domain.Producto;
import com.nexo.app.domain.User;
import com.nexo.app.repository.ProductoRepository;
import com.nexo.app.service.dto.ProductoDTO;
import com.nexo.app.service.mapper.ProductoMapper;
import com.nexo.app.web.rest.errors.NexoNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Producto}.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

	private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

	private final ProductoRepository productoRepository;

	private final ProductoMapper productoMapper;
	
	private final UserService userService;
	
	private final PersonaService personaService;
	
	public ProductoServiceImpl(
			ProductoRepository productoRepository, 
			ProductoMapper productoMapper,
			UserService userService,
			PersonaService personaService) {
		this.productoRepository = productoRepository;
		this.productoMapper = productoMapper;
		this.userService=userService;
		this.personaService=personaService;
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
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ProductoDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Productos");
		return productoRepository.findAll(pageable).map(productoMapper::toDto);
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
		return productoRepository.findById(id).map(productoMapper::toDto);
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
	public Page<ProductoDTO> getMyProducts(Pageable pageable) throws NexoNotFoundException {
	    log.debug("Request to get all my Productos");
	    User usuarioActual=userService.getUserWithAuthorities().orElseThrow(()->new NexoNotFoundException(Constants.NO_ENCONTRADO, "No puedes acceder al recurso"));
		Persona vendedorActual=personaService.
	    for (int i = 0; i < 20; i++) {
			log.debug(">>>"+usuarioActual.getId());
		}
	    Page<Producto> productos=productoRepository.getAllMyProducts(usuarioActual.getId(),pageable).orElseThrow(()->new NexoNotFoundException(Constants.NO_ENCONTRADO, "No tienes Productos en tu inventario"));
        return productos .map(productoMapper::toDto);
	}
}
