package com.nexo.app.service.impl;

import com.nexo.app.service.PaisService;
import com.nexo.app.service.PersonaService;
import com.nexo.app.service.ProductoService;
import com.nexo.app.service.UnidadMedidaService;
import com.nexo.app.service.UtilsService;
import com.nexo.app.config.Constants;
import com.nexo.app.domain.Pais;
import com.nexo.app.domain.Persona;
import com.nexo.app.domain.Producto;
import com.nexo.app.domain.UnidadMedida;
import com.nexo.app.repository.ProductoRepository;
import com.nexo.app.service.dto.ProductoDTO;
import com.nexo.app.service.mapper.ProductoMapper;
import com.nexo.app.web.rest.errors.NexoNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing {@link Producto}.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

	private final Integer PAGINADO_BY = 20;
	private final String FILTRADO_BY_ID = "id";

	 ModelMapper modelMapper = new ModelMapper();
	
	private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

	private final ProductoRepository productoRepository;

	private final ProductoMapper productoMapper;
	
	private final PersonaService personaService;
	
	private final UtilsService utilsService;
	
	private final UnidadMedidaService unidadmedidaService;
	
	private final PaisService paisService;

	public ProductoServiceImpl(ProductoRepository productoRepository,
			ProductoMapper productoMapper,
			PersonaService personaService,
			UtilsService utilsService,
			UnidadMedidaService unidadmedidaService,
			PaisService paisService) {
		this.productoRepository = productoRepository;
		this.productoMapper = productoMapper;
		this.personaService=personaService;
		this.utilsService=utilsService;
		this.unidadmedidaService=unidadmedidaService;
		this.paisService=paisService;
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
		// return productoRepository.findAll(pageable).map(productoMapper::toDto);
		return null;
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
	public Page<ProductoDTO> getMyProducts(Integer numberPage) throws NexoNotFoundException {
		log.debug("Request to get all my Productos");
		if (numberPage == null || numberPage < 0) {
			throw new NexoNotFoundException(Constants.NO_ENCONTRADO, "Error en el número de pagina");
		}
		 Pageable pageable = PageRequest.of(numberPage, PAGINADO_BY, Sort.by(FILTRADO_BY_ID));
		 final Page<Producto> productos = productoRepository.getAllMyProducts(pageable).orElseThrow(
				() -> new NexoNotFoundException(Constants.NO_ENCONTRADO, "No tienes Productos en tu inventario"));
		//return productos.map(productoMapper::toDto);
		List<ProductoDTO> pro=productos.stream().map(producto -> modelMapper.map(producto, ProductoDTO.class))
				.collect(Collectors.toList());
		Page<ProductoDTO> courseRes = new PageImpl<>(pro);
		return courseRes;
	}

	@Override
	public ProductoDTO crearProducto(ProductoDTO dto) throws NexoNotFoundException {
		ZonedDateTime today=utilsService.giveToday();
		Producto producto=new Producto();
		Persona vendedor=personaService.getUserActual().orElseThrow(()->new NexoNotFoundException(Constants.ERROR_INTERNO, "Error en login"));
		UnidadMedida unidad=unidadmedidaService.findById(dto.getUnidadMedidaId()).orElseThrow(()->new NexoNotFoundException(Constants.ERROR_INTERNO, "Error en la Unidad de medida"));
		Pais origen=paisService.findById(dto.getElaboradoEnId()).orElseThrow(()->new NexoNotFoundException(Constants.ERROR_INTERNO, "Error en el país"));
		producto.setFechaBd(today);
		producto.setVendedor(vendedor);
		producto.setUnidadMedida(unidad);
		producto.setElaboradoEn(origen);
		producto.setAlertaMinimo(dto.getAlertaMinimo());
		producto.setCantidadDisponible(dto.getCantidadDisponible());
		producto.setCodigo(dto.getCodigo());
		producto.setCodigoBarra(dto.getCodigoBarra());
		producto.setDescripcion(dto.getDescripcion());
		producto.setEstado(Constants.ACTIVO);
		producto.setNombre(dto.getNombre());
		producto.setPrecioAlmayorDespuesde(dto.getPrecioAlmayorDespuesde());
		producto.setPrecioCompraBruto(dto.getPrecioCompraBruto());
		producto.setPrecioVentaTotalDetal(dto.getPrecioVentaTotalDetal());
		producto.setPrecioVentaTotalMayor(dto.getPrecioVentaTotalMayor());
		producto.setUnidadMedidaVendida(dto.getUnidadMedidaVendida());
		productoRepository.save(producto);
		return productoMapper.toDto(producto);
	}

	@Override
	public Optional<Producto> findById(Long id) {
		return productoRepository.findById(id);
	}
}
