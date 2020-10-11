package com.nexo.app.web.rest;

import com.nexo.app.service.CarritoService;
import com.nexo.app.web.rest.errors.BadRequestAlertException;
import com.nexo.app.service.dto.CarritoDTO;

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
 * REST controller for managing {@link com.nexo.app.domain.Carrito}.
 */
@RestController
@RequestMapping("/api")
public class CarritoResource {

    private final Logger log = LoggerFactory.getLogger(CarritoResource.class);

    private static final String ENTITY_NAME = "carrito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarritoService carritoService;

    public CarritoResource(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    /**
     * {@code POST  /carritos} : Create a new carrito.
     *
     * @param carritoDTO the carritoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carritoDTO, or with status {@code 400 (Bad Request)} if the carrito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carritos")
    public ResponseEntity<CarritoDTO> createCarrito(@RequestBody CarritoDTO carritoDTO) throws URISyntaxException {
        log.debug("REST request to save Carrito : {}", carritoDTO);
        if (carritoDTO.getId() != null) {
            throw new BadRequestAlertException("A new carrito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarritoDTO result = carritoService.save(carritoDTO);
        return ResponseEntity.created(new URI("/api/carritos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carritos} : Updates an existing carrito.
     *
     * @param carritoDTO the carritoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carritoDTO,
     * or with status {@code 400 (Bad Request)} if the carritoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carritoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carritos")
    public ResponseEntity<CarritoDTO> updateCarrito(@RequestBody CarritoDTO carritoDTO) throws URISyntaxException {
        log.debug("REST request to update Carrito : {}", carritoDTO);
        if (carritoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarritoDTO result = carritoService.save(carritoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carritoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carritos} : get all the carritos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carritos in body.
     */
    @GetMapping("/carritos")
    public ResponseEntity<List<CarritoDTO>> getAllCarritos(Pageable pageable) {
        log.debug("REST request to get a page of Carritos");
        Page<CarritoDTO> page = carritoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carritos/:id} : get the "id" carrito.
     *
     * @param id the id of the carritoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carritoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carritos/{id}")
    public ResponseEntity<CarritoDTO> getCarrito(@PathVariable Long id) {
        log.debug("REST request to get Carrito : {}", id);
        Optional<CarritoDTO> carritoDTO = carritoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carritoDTO);
    }

    /**
     * {@code DELETE  /carritos/:id} : delete the "id" carrito.
     *
     * @param id the id of the carritoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carritos/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Long id) {
        log.debug("REST request to delete Carrito : {}", id);
        carritoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
