package com.nexo.app.web.rest;

import com.nexo.app.service.ComunaService;
import com.nexo.app.web.rest.errors.BadRequestAlertException;
import com.nexo.app.web.rest.errors.NexoNotFoundException;
import com.nexo.app.service.dto.ComunaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nexo.app.domain.Comuna}.
 */
@RestController
@RequestMapping("/api")
public class ComunaResource {

    private final Logger log = LoggerFactory.getLogger(ComunaResource.class);

    private static final String ENTITY_NAME = "comuna";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComunaService comunaService;

    public ComunaResource(ComunaService comunaService) {
        this.comunaService = comunaService;
    }

    /**
     * {@code POST  /comunas} : Create a new comuna.
     *
     * @param comunaDTO the comunaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comunaDTO, or with status {@code 400 (Bad Request)} if the comuna has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comunas")
    public ResponseEntity<ComunaDTO> createComuna(@RequestBody ComunaDTO comunaDTO) throws URISyntaxException {
        log.debug("REST request to save Comuna : {}", comunaDTO);
        if (comunaDTO.getId() != null) {
            throw new BadRequestAlertException("A new comuna cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComunaDTO result = comunaService.save(comunaDTO);
        return ResponseEntity.created(new URI("/api/comunas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comunas} : Updates an existing comuna.
     *
     * @param comunaDTO the comunaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comunaDTO,
     * or with status {@code 400 (Bad Request)} if the comunaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comunaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comunas")
    public ResponseEntity<ComunaDTO> updateComuna(@RequestBody ComunaDTO comunaDTO) throws URISyntaxException {
        log.debug("REST request to update Comuna : {}", comunaDTO);
        if (comunaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComunaDTO result = comunaService.save(comunaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comunaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comunas} : get all the comunas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comunas in body.
     */
    @GetMapping("/comunas")
    public List<ComunaDTO> getAllComunas() {
        log.debug("REST request to get all Comunas");
        return comunaService.findAll();
    }

    /**
     * {@code GET  /comunas/:id} : get the "id" comuna.
     *
     * @param id the id of the comunaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comunaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comunas/{id}")
    public ResponseEntity<ComunaDTO> getComuna(@PathVariable Long id) {
        log.debug("REST request to get Comuna : {}", id);
        Optional<ComunaDTO> comunaDTO = comunaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comunaDTO);
    }

    /**
     * {@code DELETE  /comunas/:id} : delete the "id" comuna.
     *
     * @param id the id of the comunaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comunas/{id}")
    public ResponseEntity<Void> deleteComuna(@PathVariable Long id) {
        log.debug("REST request to delete Comuna : {}", id);
        comunaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * @param idRegion
     * @return las comunas de la region
     */
    @GetMapping("/comunas/porregion/{idRegion}")
	public ResponseEntity<?> getMyProductos(@PathVariable Long idRegion) {
		log.debug("REST request to get comunas of region");
		Map<String, Object> response = new HashMap<>();
		List<ComunaDTO> comunas = null;
		try {
			comunas = comunaService.findByRegion(idRegion);
		} catch (NexoNotFoundException e) {
			e.printStackTrace();
			response.put("mensaje", e.getMessage());
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error al intentar consultar Comunas");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ComunaDTO>>(comunas, HttpStatus.OK);
	}
}
