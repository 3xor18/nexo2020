package com.nexo.app.service.impl;

import com.nexo.app.service.CarritoProductoService;
import com.nexo.app.domain.CarritoProducto;
import com.nexo.app.repository.CarritoProductoRepository;
import com.nexo.app.service.dto.CarritoProductoDTO;
import com.nexo.app.service.mapper.CarritoProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CarritoProducto}.
 */
@Service
@Transactional
public class CarritoProductoServiceImpl implements CarritoProductoService {

    private final Logger log = LoggerFactory.getLogger(CarritoProductoServiceImpl.class);

    private final CarritoProductoRepository carritoProductoRepository;

    private final CarritoProductoMapper carritoProductoMapper;

    public CarritoProductoServiceImpl(CarritoProductoRepository carritoProductoRepository, CarritoProductoMapper carritoProductoMapper) {
        this.carritoProductoRepository = carritoProductoRepository;
        this.carritoProductoMapper = carritoProductoMapper;
    }

    /**
     * Save a carritoProducto.
     *
     * @param carritoProductoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CarritoProductoDTO save(CarritoProductoDTO carritoProductoDTO) {
        log.debug("Request to save CarritoProducto : {}", carritoProductoDTO);
        CarritoProducto carritoProducto = carritoProductoMapper.toEntity(carritoProductoDTO);
        carritoProducto = carritoProductoRepository.save(carritoProducto);
        return carritoProductoMapper.toDto(carritoProducto);
    }

    /**
     * Get all the carritoProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CarritoProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarritoProductos");
        return carritoProductoRepository.findAll(pageable)
            .map(carritoProductoMapper::toDto);
    }


    /**
     * Get one carritoProducto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarritoProductoDTO> findOne(Long id) {
        log.debug("Request to get CarritoProducto : {}", id);
        return carritoProductoRepository.findById(id)
            .map(carritoProductoMapper::toDto);
    }

    /**
     * Delete the carritoProducto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarritoProducto : {}", id);
        carritoProductoRepository.deleteById(id);
    }
}
