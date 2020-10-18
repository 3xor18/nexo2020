package com.nexo.app.service.impl;

import com.nexo.app.service.PaisService;
import com.nexo.app.domain.Pais;
import com.nexo.app.repository.PaisRepository;
import com.nexo.app.service.dto.PaisDTO;
import com.nexo.app.service.mapper.PaisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Pais}.
 */
@Service
@Transactional
public class PaisServiceImpl implements PaisService {

    private final Logger log = LoggerFactory.getLogger(PaisServiceImpl.class);

    private final PaisRepository paisRepository;

    private final PaisMapper paisMapper;

    public PaisServiceImpl(PaisRepository paisRepository, PaisMapper paisMapper) {
        this.paisRepository = paisRepository;
        this.paisMapper = paisMapper;
    }

    /**
     * Save a pais.
     *
     * @param paisDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaisDTO save(PaisDTO paisDTO) {
        log.debug("Request to save Pais : {}", paisDTO);
        Pais pais = paisMapper.toEntity(paisDTO);
        pais = paisRepository.save(pais);
        return paisMapper.toDto(pais);
    }

    /**
     * Get all the pais.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaisDTO> findAll() {
        log.debug("Request to get all Pais");
        return paisRepository.findAll().stream()
            .map(paisMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pais by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaisDTO> findOne(Long id) {
        log.debug("Request to get Pais : {}", id);
        return paisRepository.findById(id)
            .map(paisMapper::toDto);
    }

    /**
     * Delete the pais by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pais : {}", id);
        paisRepository.deleteById(id);
    }

	@Override
	public Optional<Pais> findById(Long id) {
		return  paisRepository.findById(id);
	}
}
