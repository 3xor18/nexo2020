package com.nexo.app.web.rest;

import com.nexo.app.service.ProductoImpuestosService;
import com.nexo.app.web.rest.errors.BadRequestAlertException;
import com.nexo.app.service.dto.ProductoImpuestosDTO;

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
 * REST controller for managing {@link com.nexo.app.domain.ProductoImpuestos}.
 */
@RestController
@RequestMapping("/api")
public class ProductoImpuestosResource {

    private final Logger log = LoggerFactory.getLogger(ProductoImpuestosResource.class);

    private static final String ENTITY_NAME = "productoImpuestos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoImpuestosService productoImpuestosService;

    public ProductoImpuestosResource(ProductoImpuestosService productoImpuestosService) {
        this.productoImpuestosService = productoImpuestosService;
    }

    /**
     * {@code POST  /producto-impuestos} : Create a new productoImpuestos.
     *
     * @param productoImpuestosDTO the productoImpuestosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoImpuestosDTO, or with status {@code 400 (Bad Request)} if the productoImpuestos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-impuestos")
    public ResponseEntity<ProductoImpuestosDTO> createProductoImpuestos(@RequestBody ProductoImpuestosDTO productoImpuestosDTO) throws URISyntaxException {
        log.debug("REST request to save ProductoImpuestos : {}", productoImpuestosDTO);
        if (productoImpuestosDTO.getId() != null) {
            throw new BadRequestAlertException("A new productoImpuestos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoImpuestosDTO result = productoImpuestosService.save(productoImpuestosDTO);
        return ResponseEntity.created(new URI("/api/producto-impuestos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-impuestos} : Updates an existing productoImpuestos.
     *
     * @param productoImpuestosDTO the productoImpuestosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoImpuestosDTO,
     * or with status {@code 400 (Bad Request)} if the productoImpuestosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoImpuestosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-impuestos")
    public ResponseEntity<ProductoImpuestosDTO> updateProductoImpuestos(@RequestBody ProductoImpuestosDTO productoImpuestosDTO) throws URISyntaxException {
        log.debug("REST request to update ProductoImpuestos : {}", productoImpuestosDTO);
        if (productoImpuestosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductoImpuestosDTO result = productoImpuestosService.save(productoImpuestosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoImpuestosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /producto-impuestos} : get all the productoImpuestos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoImpuestos in body.
     */
    @GetMapping("/producto-impuestos")
    public List<ProductoImpuestosDTO> getAllProductoImpuestos() {
        log.debug("REST request to get all ProductoImpuestos");
        return productoImpuestosService.findAll();
    }

    /**
     * {@code GET  /producto-impuestos/:id} : get the "id" productoImpuestos.
     *
     * @param id the id of the productoImpuestosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoImpuestosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-impuestos/{id}")
    public ResponseEntity<ProductoImpuestosDTO> getProductoImpuestos(@PathVariable Long id) {
        log.debug("REST request to get ProductoImpuestos : {}", id);
        Optional<ProductoImpuestosDTO> productoImpuestosDTO = productoImpuestosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoImpuestosDTO);
    }

    /**
     * {@code DELETE  /producto-impuestos/:id} : delete the "id" productoImpuestos.
     *
     * @param id the id of the productoImpuestosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-impuestos/{id}")
    public ResponseEntity<Void> deleteProductoImpuestos(@PathVariable Long id) {
        log.debug("REST request to delete ProductoImpuestos : {}", id);
        productoImpuestosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
