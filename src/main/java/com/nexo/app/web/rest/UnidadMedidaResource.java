package com.nexo.app.web.rest;

import com.nexo.app.service.UnidadMedidaService;
import com.nexo.app.web.rest.errors.BadRequestAlertException;
import com.nexo.app.service.dto.UnidadMedidaDTO;

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
 * REST controller for managing {@link com.nexo.app.domain.UnidadMedida}.
 */
@RestController
@RequestMapping("/api")
public class UnidadMedidaResource {

    private final Logger log = LoggerFactory.getLogger(UnidadMedidaResource.class);

    private static final String ENTITY_NAME = "unidadMedida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadMedidaService unidadMedidaService;

    public UnidadMedidaResource(UnidadMedidaService unidadMedidaService) {
        this.unidadMedidaService = unidadMedidaService;
    }

    /**
     * {@code POST  /unidad-medidas} : Create a new unidadMedida.
     *
     * @param unidadMedidaDTO the unidadMedidaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadMedidaDTO, or with status {@code 400 (Bad Request)} if the unidadMedida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidad-medidas")
    public ResponseEntity<UnidadMedidaDTO> createUnidadMedida(@RequestBody UnidadMedidaDTO unidadMedidaDTO) throws URISyntaxException {
        log.debug("REST request to save UnidadMedida : {}", unidadMedidaDTO);
        if (unidadMedidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidadMedida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadMedidaDTO result = unidadMedidaService.save(unidadMedidaDTO);
        return ResponseEntity.created(new URI("/api/unidad-medidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidad-medidas} : Updates an existing unidadMedida.
     *
     * @param unidadMedidaDTO the unidadMedidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadMedidaDTO,
     * or with status {@code 400 (Bad Request)} if the unidadMedidaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadMedidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidad-medidas")
    public ResponseEntity<UnidadMedidaDTO> updateUnidadMedida(@RequestBody UnidadMedidaDTO unidadMedidaDTO) throws URISyntaxException {
        log.debug("REST request to update UnidadMedida : {}", unidadMedidaDTO);
        if (unidadMedidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadMedidaDTO result = unidadMedidaService.save(unidadMedidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadMedidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidad-medidas} : get all the unidadMedidas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadMedidas in body.
     */
    @GetMapping("/unidad-medidas")
    public List<UnidadMedidaDTO> getAllUnidadMedidas() {
        log.debug("REST request to get all UnidadMedidas");
        return unidadMedidaService.findAll();
    }

    /**
     * {@code GET  /unidad-medidas/:id} : get the "id" unidadMedida.
     *
     * @param id the id of the unidadMedidaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadMedidaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidad-medidas/{id}")
    public ResponseEntity<UnidadMedidaDTO> getUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to get UnidadMedida : {}", id);
        Optional<UnidadMedidaDTO> unidadMedidaDTO = unidadMedidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadMedidaDTO);
    }

    /**
     * {@code DELETE  /unidad-medidas/:id} : delete the "id" unidadMedida.
     *
     * @param id the id of the unidadMedidaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidad-medidas/{id}")
    public ResponseEntity<Void> deleteUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to delete UnidadMedida : {}", id);
        unidadMedidaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
