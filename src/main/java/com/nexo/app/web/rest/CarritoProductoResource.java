package com.nexo.app.web.rest;

import com.nexo.app.service.CarritoProductoService;
import com.nexo.app.web.rest.errors.BadRequestAlertException;
import com.nexo.app.service.dto.CarritoProductoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nexo.app.domain.CarritoProducto}.
 */
@RestController
@RequestMapping("/api")
public class CarritoProductoResource {

    private final Logger log = LoggerFactory.getLogger(CarritoProductoResource.class);

    private static final String ENTITY_NAME = "carritoProducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarritoProductoService carritoProductoService;

    public CarritoProductoResource(CarritoProductoService carritoProductoService) {
        this.carritoProductoService = carritoProductoService;
    }

    /**
     * {@code POST  /carrito-productos} : Create a new carritoProducto.
     *
     * @param carritoProductoDTO the carritoProductoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carritoProductoDTO, or with status {@code 400 (Bad Request)} if the carritoProducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carrito-productos")
    public ResponseEntity<CarritoProductoDTO> createCarritoProducto(@RequestBody CarritoProductoDTO carritoProductoDTO) throws URISyntaxException {
        log.debug("REST request to save CarritoProducto : {}", carritoProductoDTO);
        if (carritoProductoDTO.getId() != null) {
            throw new BadRequestAlertException("A new carritoProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarritoProductoDTO result = carritoProductoService.save(carritoProductoDTO);
        return ResponseEntity.created(new URI("/api/carrito-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrito-productos} : Updates an existing carritoProducto.
     *
     * @param carritoProductoDTO the carritoProductoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carritoProductoDTO,
     * or with status {@code 400 (Bad Request)} if the carritoProductoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carritoProductoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carrito-productos")
    public ResponseEntity<CarritoProductoDTO> updateCarritoProducto(@RequestBody CarritoProductoDTO carritoProductoDTO) throws URISyntaxException {
        log.debug("REST request to update CarritoProducto : {}", carritoProductoDTO);
        if (carritoProductoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarritoProductoDTO result = carritoProductoService.save(carritoProductoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carritoProductoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carrito-productos} : get all the carritoProductos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carritoProductos in body.
     */
    @GetMapping("/carrito-productos")
    public ResponseEntity<List<CarritoProductoDTO>> getAllCarritoProductos(Pageable pageable) {
        log.debug("REST request to get a page of CarritoProductos");
        Page<CarritoProductoDTO> page = carritoProductoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carrito-productos/:id} : get the "id" carritoProducto.
     *
     * @param id the id of the carritoProductoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carritoProductoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carrito-productos/{id}")
    public ResponseEntity<CarritoProductoDTO> getCarritoProducto(@PathVariable Long id) {
        log.debug("REST request to get CarritoProducto : {}", id);
        Optional<CarritoProductoDTO> carritoProductoDTO = carritoProductoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carritoProductoDTO);
    }

    /**
     * {@code DELETE  /carrito-productos/:id} : delete the "id" carritoProducto.
     *
     * @param id the id of the carritoProductoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carrito-productos/{id}")
    public ResponseEntity<Void> deleteCarritoProducto(@PathVariable Long id) {
        log.debug("REST request to delete CarritoProducto : {}", id);
        carritoProductoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
