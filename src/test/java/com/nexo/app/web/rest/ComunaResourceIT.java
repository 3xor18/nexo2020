package com.nexo.app.web.rest;

import com.nexo.app.NexoApp;
import com.nexo.app.domain.Comuna;
import com.nexo.app.repository.ComunaRepository;
import com.nexo.app.service.ComunaService;
import com.nexo.app.service.dto.ComunaDTO;
import com.nexo.app.service.mapper.ComunaMapper;
import com.nexo.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.nexo.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ComunaResource} REST controller.
 */
@SpringBootTest(classes = NexoApp.class)
public class ComunaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private ComunaMapper comunaMapper;

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restComunaMockMvc;

    private Comuna comuna;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComunaResource comunaResource = new ComunaResource(comunaService);
        this.restComunaMockMvc = MockMvcBuilders.standaloneSetup(comunaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comuna createEntity(EntityManager em) {
        Comuna comuna = new Comuna()
            .nombre(DEFAULT_NOMBRE);
        return comuna;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comuna createUpdatedEntity(EntityManager em) {
        Comuna comuna = new Comuna()
            .nombre(UPDATED_NOMBRE);
        return comuna;
    }

    @BeforeEach
    public void initTest() {
        comuna = createEntity(em);
    }

    @Test
    @Transactional
    public void createComuna() throws Exception {
        int databaseSizeBeforeCreate = comunaRepository.findAll().size();

        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);
        restComunaMockMvc.perform(post("/api/comunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunaDTO)))
            .andExpect(status().isCreated());

        // Validate the Comuna in the database
        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeCreate + 1);
        Comuna testComuna = comunaList.get(comunaList.size() - 1);
        assertThat(testComuna.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createComunaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comunaRepository.findAll().size();

        // Create the Comuna with an existing ID
        comuna.setId(1L);
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComunaMockMvc.perform(post("/api/comunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comuna in the database
        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComunas() throws Exception {
        // Initialize the database
        comunaRepository.saveAndFlush(comuna);

        // Get all the comunaList
        restComunaMockMvc.perform(get("/api/comunas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comuna.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getComuna() throws Exception {
        // Initialize the database
        comunaRepository.saveAndFlush(comuna);

        // Get the comuna
        restComunaMockMvc.perform(get("/api/comunas/{id}", comuna.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comuna.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingComuna() throws Exception {
        // Get the comuna
        restComunaMockMvc.perform(get("/api/comunas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComuna() throws Exception {
        // Initialize the database
        comunaRepository.saveAndFlush(comuna);

        int databaseSizeBeforeUpdate = comunaRepository.findAll().size();

        // Update the comuna
        Comuna updatedComuna = comunaRepository.findById(comuna.getId()).get();
        // Disconnect from session so that the updates on updatedComuna are not directly saved in db
        em.detach(updatedComuna);
        updatedComuna
            .nombre(UPDATED_NOMBRE);
        ComunaDTO comunaDTO = comunaMapper.toDto(updatedComuna);

        restComunaMockMvc.perform(put("/api/comunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunaDTO)))
            .andExpect(status().isOk());

        // Validate the Comuna in the database
        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeUpdate);
        Comuna testComuna = comunaList.get(comunaList.size() - 1);
        assertThat(testComuna.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingComuna() throws Exception {
        int databaseSizeBeforeUpdate = comunaRepository.findAll().size();

        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComunaMockMvc.perform(put("/api/comunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comuna in the database
        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComuna() throws Exception {
        // Initialize the database
        comunaRepository.saveAndFlush(comuna);

        int databaseSizeBeforeDelete = comunaRepository.findAll().size();

        // Delete the comuna
        restComunaMockMvc.perform(delete("/api/comunas/{id}", comuna.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comuna.class);
        Comuna comuna1 = new Comuna();
        comuna1.setId(1L);
        Comuna comuna2 = new Comuna();
        comuna2.setId(comuna1.getId());
        assertThat(comuna1).isEqualTo(comuna2);
        comuna2.setId(2L);
        assertThat(comuna1).isNotEqualTo(comuna2);
        comuna1.setId(null);
        assertThat(comuna1).isNotEqualTo(comuna2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComunaDTO.class);
        ComunaDTO comunaDTO1 = new ComunaDTO();
        comunaDTO1.setId(1L);
        ComunaDTO comunaDTO2 = new ComunaDTO();
        assertThat(comunaDTO1).isNotEqualTo(comunaDTO2);
        comunaDTO2.setId(comunaDTO1.getId());
        assertThat(comunaDTO1).isEqualTo(comunaDTO2);
        comunaDTO2.setId(2L);
        assertThat(comunaDTO1).isNotEqualTo(comunaDTO2);
        comunaDTO1.setId(null);
        assertThat(comunaDTO1).isNotEqualTo(comunaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(comunaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(comunaMapper.fromId(null)).isNull();
    }
}
