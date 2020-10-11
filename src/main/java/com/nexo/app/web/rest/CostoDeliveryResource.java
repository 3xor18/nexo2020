package com.nexo.app.web.rest;

import com.nexo.app.service.CostoDeliveryService;
import com.nexo.app.web.rest.errors.BadRequestAlertException;
import com.nexo.app.service.dto.CostoDeliveryDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nexo.app.domain.CostoDelivery}.
 */
@RestController
@RequestMapping("/api")
public class CostoDeliveryResource {

    private final Logger log = LoggerFactory.getLogger(CostoDeliveryResource.class);

    private static final String ENTITY_NAME = "costoDelivery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CostoDeliveryService costoDeliveryService;

    public CostoDeliveryResource(CostoDeliveryService costoDeliveryService) {
        this.costoDeliveryService = costoDeliveryService;
    }

    /**
     * {@code POST  /costo-deliveries} : Create a new costoDelivery.
     *
     * @param costoDeliveryDTO the costoDeliveryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new costoDeliveryDTO, or with status {@code 400 (Bad Request)} if the costoDelivery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/costo-deliveries")
    public ResponseEntity<CostoDeliveryDTO> createCostoDelivery(@RequestBody CostoDeliveryDTO costoDeliveryDTO) throws URISyntaxException {
        log.debug("REST request to save CostoDelivery : {}", costoDeliveryDTO);
        if (costoDeliveryDTO.getId() != null) {
            throw new BadRequestAlertException("A new costoDelivery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CostoDeliveryDTO result = costoDeliveryService.save(costoDeliveryDTO);
        return ResponseEntity.created(new URI("/api/costo-deliveries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /costo-deliveries} : Updates an existing costoDelivery.
     *
     * @param costoDeliveryDTO the costoDeliveryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated costoDeliveryDTO,
     * or with status {@code 400 (Bad Request)} if the costoDeliveryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the costoDeliveryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/costo-deliveries")
    public ResponseEntity<CostoDeliveryDTO> updateCostoDelivery(@RequestBody CostoDeliveryDTO costoDeliveryDTO) throws URISyntaxException {
        log.debug("REST request to update CostoDelivery : {}", costoDeliveryDTO);
        if (costoDeliveryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CostoDeliveryDTO result = costoDeliveryService.save(costoDeliveryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, costoDeliveryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /costo-deliveries} : get all the costoDeliveries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of costoDeliveries in body.
     */
    @GetMapping("/costo-deliveries")
    public List<CostoDeliveryDTO> getAllCostoDeliveries() {
        log.debug("REST request to get all CostoDeliveries");
        return costoDeliveryService.findAll();
    }

    /**
     * {@code GET  /costo-deliveries/:id} : get the "id" costoDelivery.
     *
     * @param id the id of the costoDeliveryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the costoDeliveryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/costo-deliveries/{id}")
    public ResponseEntity<CostoDeliveryDTO> getCostoDelivery(@PathVariable Long id) {
        log.debug("REST request to get CostoDelivery : {}", id);
        Optional<CostoDeliveryDTO> costoDeliveryDTO = costoDeliveryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(costoDeliveryDTO);
    }

    /**
     * {@code DELETE  /costo-deliveries/:id} : delete the "id" costoDelivery.
     *
     * @param id the id of the costoDeliveryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/costo-deliveries/{id}")
    public ResponseEntity<Void> deleteCostoDelivery(@PathVariable Long id) {
        log.debug("REST request to delete CostoDelivery : {}", id);
        costoDeliveryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
