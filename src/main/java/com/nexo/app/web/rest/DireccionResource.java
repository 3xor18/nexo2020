package com.nexo.app.web.rest;

import com.nexo.app.service.DireccionService;
import com.nexo.app.web.rest.errors.BadRequestAlertException;
import com.nexo.app.service.dto.DireccionDTO;

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
 * REST controller for managing {@link com.nexo.app.domain.Direccion}.
 */
@RestController
@RequestMapping("/api")
public class DireccionResource {

    private final Logger log = LoggerFactory.getLogger(DireccionResource.class);

    private static final String ENTITY_NAME = "direccion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DireccionService direccionService;

    public DireccionResource(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    /**
     * {@code POST  /direccions} : Create a new direccion.
     *
     * @param direccionDTO the direccionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new direccionDTO, or with status {@code 400 (Bad Request)} if the direccion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/direccions")
    public ResponseEntity<DireccionDTO> createDireccion(@RequestBody DireccionDTO direccionDTO) throws URISyntaxException {
        log.debug("REST request to save Direccion : {}", direccionDTO);
        if (direccionDTO.getId() != null) {
            throw new BadRequestAlertException("A new direccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DireccionDTO result = direccionService.save(direccionDTO);
        return ResponseEntity.created(new URI("/api/direccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /direccions} : Updates an existing direccion.
     *
     * @param direccionDTO the direccionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated direccionDTO,
     * or with status {@code 400 (Bad Request)} if the direccionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the direccionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/direccions")
    public ResponseEntity<DireccionDTO> updateDireccion(@RequestBody DireccionDTO direccionDTO) throws URISyntaxException {
        log.debug("REST request to update Direccion : {}", direccionDTO);
        if (direccionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DireccionDTO result = direccionService.save(direccionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, direccionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /direccions} : get all the direccions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of direccions in body.
     */
    @GetMapping("/direccions")
    public List<DireccionDTO> getAllDireccions() {
        log.debug("REST request to get all Direccions");
        return direccionService.findAll();
    }

    /**
     * {@code GET  /direccions/:id} : get the "id" direccion.
     *
     * @param id the id of the direccionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the direccionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/direccions/{id}")
    public ResponseEntity<DireccionDTO> getDireccion(@PathVariable Long id) {
        log.debug("REST request to get Direccion : {}", id);
        Optional<DireccionDTO> direccionDTO = direccionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(direccionDTO);
    }

    /**
     * {@code DELETE  /direccions/:id} : delete the "id" direccion.
     *
     * @param id the id of the direccionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/direccions/{id}")
    public ResponseEntity<Void> deleteDireccion(@PathVariable Long id) {
        log.debug("REST request to delete Direccion : {}", id);
        direccionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
