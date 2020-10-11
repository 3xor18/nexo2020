package com.nexo.app.web.rest;

import com.nexo.app.service.PersonaService;
import com.nexo.app.web.rest.errors.BadRequestAlertException;
import com.nexo.app.service.dto.PersonaDTO;

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
 * REST controller for managing {@link com.nexo.app.domain.Persona}.
 */
@RestController
@RequestMapping("/api")
public class PersonaResource {

    private final Logger log = LoggerFactory.getLogger(PersonaResource.class);

    private static final String ENTITY_NAME = "persona";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonaService personaService;

    public PersonaResource(PersonaService personaService) {
        this.personaService = personaService;
    }

    /**
     * {@code POST  /personas} : Create a new persona.
     *
     * @param personaDTO the personaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personaDTO, or with status {@code 400 (Bad Request)} if the persona has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personas")
    public ResponseEntity<PersonaDTO> createPersona(@RequestBody PersonaDTO personaDTO) throws URISyntaxException {
        log.debug("REST request to save Persona : {}", personaDTO);
        if (personaDTO.getId() != null) {
            throw new BadRequestAlertException("A new persona cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonaDTO result = personaService.save(personaDTO);
        return ResponseEntity.created(new URI("/api/personas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personas} : Updates an existing persona.
     *
     * @param personaDTO the personaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personaDTO,
     * or with status {@code 400 (Bad Request)} if the personaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personas")
    public ResponseEntity<PersonaDTO> updatePersona(@RequestBody PersonaDTO personaDTO) throws URISyntaxException {
        log.debug("REST request to update Persona : {}", personaDTO);
        if (personaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonaDTO result = personaService.save(personaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /personas} : get all the personas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personas in body.
     */
    @GetMapping("/personas")
    public List<PersonaDTO> getAllPersonas() {
        log.debug("REST request to get all Personas");
        return personaService.findAll();
    }

    /**
     * {@code GET  /personas/:id} : get the "id" persona.
     *
     * @param id the id of the personaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personas/{id}")
    public ResponseEntity<PersonaDTO> getPersona(@PathVariable Long id) {
        log.debug("REST request to get Persona : {}", id);
        Optional<PersonaDTO> personaDTO = personaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personaDTO);
    }

    /**
     * {@code DELETE  /personas/:id} : delete the "id" persona.
     *
     * @param id the id of the personaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personas/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        log.debug("REST request to delete Persona : {}", id);
        personaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
