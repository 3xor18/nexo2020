package com.nexo.app.service.impl;

import com.nexo.app.service.CarritoService;
import com.nexo.app.domain.Carrito;
import com.nexo.app.repository.CarritoRepository;
import com.nexo.app.service.dto.CarritoDTO;
import com.nexo.app.service.mapper.CarritoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Carrito}.
 */
@Service
@Transactional
public class CarritoServiceImpl implements CarritoService {

    private final Logger log = LoggerFactory.getLogger(CarritoServiceImpl.class);

    private final CarritoRepository carritoRepository;

    private final CarritoMapper carritoMapper;

    public CarritoServiceImpl(CarritoRepository carritoRepository, CarritoMapper carritoMapper) {
        this.carritoRepository = carritoRepository;
        this.carritoMapper = carritoMapper;
    }
    /**
     * Save a carrito. 
     *
     * @param carritoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CarritoDTO save(CarritoDTO carritoDTO) {
        log.debug("Request to save Carrito : {}", carritoDTO);
        Carrito carrito = carritoMapper.toEntity(carritoDTO);
        carrito = carritoRepository.save(carrito);
        return carritoMapper.toDto(carrito);
    }

    /**
     * Get all the carritos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CarritoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Carritos");
        return carritoRepository.findAll(pageable)
            .map(carritoMapper::toDto);
    }


    /**
     * Get one carrito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarritoDTO> findOne(Long id) {
        log.debug("Request to get Carrito : {}", id);
        return carritoRepository.findById(id)
            .map(carritoMapper::toDto);
    }

    /**
     * Delete the carrito by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Carrito : {}", id);
        carritoRepository.deleteById(id);
    }
}
