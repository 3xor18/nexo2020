package com.nexo.app.service.impl;

import com.nexo.app.service.UnidadMedidaService;
import com.nexo.app.domain.UnidadMedida;
import com.nexo.app.repository.UnidadMedidaRepository;
import com.nexo.app.service.dto.UnidadMedidaDTO;
import com.nexo.app.service.mapper.UnidadMedidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UnidadMedida}.
 */
@Service
@Transactional
public class UnidadMedidaServiceImpl implements UnidadMedidaService {

    private final Logger log = LoggerFactory.getLogger(UnidadMedidaServiceImpl.class);

    private final UnidadMedidaRepository unidadMedidaRepository;

    private final UnidadMedidaMapper unidadMedidaMapper;

    public UnidadMedidaServiceImpl(UnidadMedidaRepository unidadMedidaRepository, UnidadMedidaMapper unidadMedidaMapper) {
        this.unidadMedidaRepository = unidadMedidaRepository;
        this.unidadMedidaMapper = unidadMedidaMapper;
    }

    /**
     * Save a unidadMedida.
     *
     * @param unidadMedidaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UnidadMedidaDTO save(UnidadMedidaDTO unidadMedidaDTO) {
        log.debug("Request to save UnidadMedida : {}", unidadMedidaDTO);
        UnidadMedida unidadMedida = unidadMedidaMapper.toEntity(unidadMedidaDTO);
        unidadMedida = unidadMedidaRepository.save(unidadMedida);
        return unidadMedidaMapper.toDto(unidadMedida);
    }

    /**
     * Get all the unidadMedidas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UnidadMedidaDTO> findAll() {
        log.debug("Request to get all UnidadMedidas");
        return unidadMedidaRepository.findAll().stream()
            .map(unidadMedidaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one unidadMedida by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UnidadMedidaDTO> findOne(Long id) {
        log.debug("Request to get UnidadMedida : {}", id);
        return unidadMedidaRepository.findById(id)
            .map(unidadMedidaMapper::toDto);
    }

    /**
     * Delete the unidadMedida by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnidadMedida : {}", id);
        unidadMedidaRepository.deleteById(id);
    }
}
