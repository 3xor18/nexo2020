package com.nexo.app.service;

import com.nexo.app.domain.Producto;
import com.nexo.app.service.dto.ProductoDTO;
import com.nexo.app.web.rest.errors.NexoNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<ProductoDTO> findAll(Pageable pageable);

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

	/**
	 * @param pageable
	 * @return Pageable de los productos del usuario Actual
	 * @throws NexoNotFoundException si no tiene productos registrados
	 */
	Page<ProductoDTO> getMyProducts(Integer numberPage) throws NexoNotFoundException;
	
	/**
	 * @param productoDTO
	 * @return ProductoDTO
	 * @throws NexoNotFoundException 
	 */
	ProductoDTO crearProducto(ProductoDTO dto) throws NexoNotFoundException;
	
	/**
	 * @param id
	 * @return producto por id
	 */
	Optional<Producto> findById(Long id);
}
