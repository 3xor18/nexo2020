package com.nexo.app.service.impl;

import com.nexo.app.service.RegionService;
import com.nexo.app.domain.Pais;
import com.nexo.app.domain.Region;
import com.nexo.app.repository.PaisRepository;
import com.nexo.app.repository.RegionRepository;
import com.nexo.app.service.dto.RegionDTO;
import com.nexo.app.service.mapper.RegionMapper;
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
 * Service Implementation for managing {@link Region}.
 */
@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    private final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);

    private final RegionRepository regionRepository;

    private final RegionMapper regionMapper;
    
    @Autowired
    PaisRepository paisRepository;

    public RegionServiceImpl(RegionRepository regionRepository, RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    /**
     * Save a region.
     *
     * @param regionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RegionDTO save(RegionDTO regionDTO) {
        log.debug("Request to save Region : {}", regionDTO);
        Region region = regionMapper.toEntity(regionDTO);
        region = regionRepository.save(region);
        return regionMapper.toDto(region);
    }

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegionDTO> findAll() {
        log.debug("Request to get all Regions");
        return regionRepository.findAll().stream()
            .map(regionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one region by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegionDTO> findOne(Long id) {
        log.debug("Request to get Region : {}", id);
        return regionRepository.findById(id)
            .map(regionMapper::toDto);
    }

    /**
     * Delete the region by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Region : {}", id);
        regionRepository.deleteById(id);
    }

	@Override
	public List<RegionDTO> findByPais(Long id) throws NexoNotFoundException {
		List<Region> regiones=new ArrayList<>();
		List<RegionDTO> regionesDto=new ArrayList<>();
		Pais pais=paisRepository.findById(id).orElseThrow(()->new NexoNotFoundException("404","No se encuenta el país"));
		regiones=regionRepository.getByPais(pais).orElse(null);
		if(regiones==null) {
			return regionesDto;
		}
		regionesDto= regiones.stream()
        .map(regionMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
		return regionesDto;
	}
}
