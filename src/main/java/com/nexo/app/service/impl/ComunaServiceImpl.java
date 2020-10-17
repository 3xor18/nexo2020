package com.nexo.app.service.impl;

import com.nexo.app.service.ComunaService;
import com.nexo.app.domain.Comuna;
import com.nexo.app.domain.Region;
import com.nexo.app.repository.ComunaRepository;
import com.nexo.app.repository.RegionRepository;
import com.nexo.app.service.dto.ComunaDTO;
import com.nexo.app.service.mapper.ComunaMapper;
import com.nexo.app.web.rest.errors.NexoNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Comuna}.
 */
@Service
@Transactional
public class ComunaServiceImpl implements ComunaService {

	private final Logger log = LoggerFactory.getLogger(ComunaServiceImpl.class);

	private final ComunaRepository comunaRepository;

	private final ComunaMapper comunaMapper;

	@Autowired
	RegionRepository regionRepository;

	public ComunaServiceImpl(ComunaRepository comunaRepository, ComunaMapper comunaMapper) {
		this.comunaRepository = comunaRepository;
		this.comunaMapper = comunaMapper;
	}

	/**
	 * Save a comuna.
	 *
	 * @param comunaDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public ComunaDTO save(ComunaDTO comunaDTO) {
		log.debug("Request to save Comuna : {}", comunaDTO);
		Comuna comuna = comunaMapper.toEntity(comunaDTO);
		comuna = comunaRepository.save(comuna);
		return comunaMapper.toDto(comuna);
	}

	/**
	 * Get all the comunas.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ComunaDTO> findAll() {
		log.debug("Request to get all Comunas");
		return comunaRepository.findAll().stream().map(comunaMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one comuna by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ComunaDTO> findOne(Long id) {
		log.debug("Request to get Comuna : {}", id);
		return comunaRepository.findById(id).map(comunaMapper::toDto);
	}

	/**
	 * Delete the comuna by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Comuna : {}", id);
		comunaRepository.deleteById(id);
	}

	@Override
	public List<ComunaDTO> findByRegion(Long idRegion) throws NexoNotFoundException {
		List<Comuna> comunas = new ArrayList<>();
		List<ComunaDTO> dto = new ArrayList<>();
		Region region = regionRepository.findById(idRegion)
				.orElseThrow(() -> new NexoNotFoundException("404", "No se encuenta data"));
		comunas = comunaRepository.getByRegion(region).orElse(null);
		if (comunas == null) {
			return dto;
		}
		dto = comunas.stream().map(comunaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
		return dto;
	}
}
