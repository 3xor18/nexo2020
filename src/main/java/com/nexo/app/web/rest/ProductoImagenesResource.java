package com.nexo.app.web.rest;

import com.nexo.app.service.ProductoImagenesService;
import com.nexo.app.web.rest.errors.BadRequestAlertException;
import com.nexo.app.service.dto.ProductoImagenesDTO;

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
 * REST controller for managing {@link com.nexo.app.domain.ProductoImagenes}.
 */
@RestController
@RequestMapping("/api")
public class ProductoImagenesResource {

    private final Logger log = LoggerFactory.getLogger(ProductoImagenesResource.class);

    private static final String ENTITY_NAME = "productoImagenes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoImagenesService productoImagenesService;

    public ProductoImagenesResource(ProductoImagenesService productoImagenesService) {
        this.productoImagenesService = productoImagenesService;
    }

    /**
     * {@code POST  /producto-imagenes} : Create a new productoImagenes.
     *
     * @param productoImagenesDTO the productoImagenesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoImagenesDTO, or with status {@code 400 (Bad Request)} if the productoImagenes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-imagenes")
    public ResponseEntity<ProductoImagenesDTO> createProductoImagenes(@RequestBody ProductoImagenesDTO productoImagenesDTO) throws URISyntaxException {
        log.debug("REST request to save ProductoImagenes : {}", productoImagenesDTO);
        if (productoImagenesDTO.getId() != null) {
            throw new BadRequestAlertException("A new productoImagenes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoImagenesDTO result = productoImagenesService.save(productoImagenesDTO);
        return ResponseEntity.created(new URI("/api/producto-imagenes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-imagenes} : Updates an existing productoImagenes.
     *
     * @param productoImagenesDTO the productoImagenesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoImagenesDTO,
     * or with status {@code 400 (Bad Request)} if the productoImagenesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoImagenesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-imagenes")
    public ResponseEntity<ProductoImagenesDTO> updateProductoImagenes(@RequestBody ProductoImagenesDTO productoImagenesDTO) throws URISyntaxException {
        log.debug("REST request to update ProductoImagenes : {}", productoImagenesDTO);
        if (productoImagenesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductoImagenesDTO result = productoImagenesService.save(productoImagenesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoImagenesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /producto-imagenes} : get all the productoImagenes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoImagenes in body.
     */
    @GetMapping("/producto-imagenes")
    public List<ProductoImagenesDTO> getAllProductoImagenes() {
        log.debug("REST request to get all ProductoImagenes");
        return productoImagenesService.findAll();
    }

    /**
     * {@code GET  /producto-imagenes/:id} : get the "id" productoImagenes.
     *
     * @param id the id of the productoImagenesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoImagenesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-imagenes/{id}")
    public ResponseEntity<ProductoImagenesDTO> getProductoImagenes(@PathVariable Long id) {
        log.debug("REST request to get ProductoImagenes : {}", id);
        Optional<ProductoImagenesDTO> productoImagenesDTO = productoImagenesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoImagenesDTO);
    }

    /**
     * {@code DELETE  /producto-imagenes/:id} : delete the "id" productoImagenes.
     *
     * @param id the id of the productoImagenesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-imagenes/{id}")
    public ResponseEntity<Void> deleteProductoImagenes(@PathVariable Long id) {
        log.debug("REST request to delete ProductoImagenes : {}", id);
        productoImagenesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
