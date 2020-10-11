package com.nexo.app.service.impl;

import com.nexo.app.service.CostoDeliveryService;
import com.nexo.app.domain.CostoDelivery;
import com.nexo.app.repository.CostoDeliveryRepository;
import com.nexo.app.service.dto.CostoDeliveryDTO;
import com.nexo.app.service.mapper.CostoDeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CostoDelivery}.
 */
@Service
@Transactional
public class CostoDeliveryServiceImpl implements CostoDeliveryService {

    private final Logger log = LoggerFactory.getLogger(CostoDeliveryServiceImpl.class);

    private final CostoDeliveryRepository costoDeliveryRepository;

    private final CostoDeliveryMapper costoDeliveryMapper;

    public CostoDeliveryServiceImpl(CostoDeliveryRepository costoDeliveryRepository, CostoDeliveryMapper costoDeliveryMapper) {
        this.costoDeliveryRepository = costoDeliveryRepository;
        this.costoDeliveryMapper = costoDeliveryMapper;
    }

    /**
     * Save a costoDelivery.
     *
     * @param costoDeliveryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CostoDeliveryDTO save(CostoDeliveryDTO costoDeliveryDTO) {
        log.debug("Request to save CostoDelivery : {}", costoDeliveryDTO);
        CostoDelivery costoDelivery = costoDeliveryMapper.toEntity(costoDeliveryDTO);
        costoDelivery = costoDeliveryRepository.save(costoDelivery);
        return costoDeliveryMapper.toDto(costoDelivery);
    }

    /**
     * Get all the costoDeliveries.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CostoDeliveryDTO> findAll() {
        log.debug("Request to get all CostoDeliveries");
        return costoDeliveryRepository.findAll().stream()
            .map(costoDeliveryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one costoDelivery by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CostoDeliveryDTO> findOne(Long id) {
        log.debug("Request to get CostoDelivery : {}", id);
        return costoDeliveryRepository.findById(id)
            .map(costoDeliveryMapper::toDto);
    }

    /**
     * Delete the costoDelivery by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CostoDelivery : {}", id);
        costoDeliveryRepository.deleteById(id);
    }
}
