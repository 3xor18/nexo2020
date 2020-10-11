package com.nexo.app.web.rest;

import com.nexo.app.NexoApp;
import com.nexo.app.domain.UnidadMedida;
import com.nexo.app.repository.UnidadMedidaRepository;
import com.nexo.app.service.UnidadMedidaService;
import com.nexo.app.service.dto.UnidadMedidaDTO;
import com.nexo.app.service.mapper.UnidadMedidaMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.nexo.app.web.rest.TestUtil.sameInstant;
import static com.nexo.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UnidadMedidaResource} REST controller.
 */
@SpringBootTest(classes = NexoApp.class)
public class UnidadMedidaResourceIT {

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_BD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_BD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private UnidadMedidaMapper unidadMedidaMapper;

    @Autowired
    private UnidadMedidaService unidadMedidaService;

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

    private MockMvc restUnidadMedidaMockMvc;

    private UnidadMedida unidadMedida;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadMedidaResource unidadMedidaResource = new UnidadMedidaResource(unidadMedidaService);
        this.restUnidadMedidaMockMvc = MockMvcBuilders.standaloneSetup(unidadMedidaResource)
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
    public static UnidadMedida createEntity(EntityManager em) {
        UnidadMedida unidadMedida = new UnidadMedida()
            .estado(DEFAULT_ESTADO)
            .nombre(DEFAULT_NOMBRE)
            .fechaBd(DEFAULT_FECHA_BD);
        return unidadMedida;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadMedida createUpdatedEntity(EntityManager em) {
        UnidadMedida unidadMedida = new UnidadMedida()
            .estado(UPDATED_ESTADO)
            .nombre(UPDATED_NOMBRE)
            .fechaBd(UPDATED_FECHA_BD);
        return unidadMedida;
    }

    @BeforeEach
    public void initTest() {
        unidadMedida = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadMedida() throws Exception {
        int databaseSizeBeforeCreate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);
        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO)))
            .andExpect(status().isCreated());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testUnidadMedida.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUnidadMedida.getFechaBd()).isEqualTo(DEFAULT_FECHA_BD);
    }

    @Test
    @Transactional
    public void createUnidadMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida with an existing ID
        unidadMedida.setId(1L);
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUnidadMedidas() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].fechaBd").value(hasItem(sameInstant(DEFAULT_FECHA_BD))));
    }
    
    @Test
    @Transactional
    public void getUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas/{id}", unidadMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadMedida.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.fechaBd").value(sameInstant(DEFAULT_FECHA_BD)));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadMedida() throws Exception {
        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Update the unidadMedida
        UnidadMedida updatedUnidadMedida = unidadMedidaRepository.findById(unidadMedida.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadMedida are not directly saved in db
        em.detach(updatedUnidadMedida);
        updatedUnidadMedida
            .estado(UPDATED_ESTADO)
            .nombre(UPDATED_NOMBRE)
            .fechaBd(UPDATED_FECHA_BD);
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(updatedUnidadMedida);

        restUnidadMedidaMockMvc.perform(put("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO)))
            .andExpect(status().isOk());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testUnidadMedida.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUnidadMedida.getFechaBd()).isEqualTo(UPDATED_FECHA_BD);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadMedidaMockMvc.perform(put("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        int databaseSizeBeforeDelete = unidadMedidaRepository.findAll().size();

        // Delete the unidadMedida
        restUnidadMedidaMockMvc.perform(delete("/api/unidad-medidas/{id}", unidadMedida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadMedida.class);
        UnidadMedida unidadMedida1 = new UnidadMedida();
        unidadMedida1.setId(1L);
        UnidadMedida unidadMedida2 = new UnidadMedida();
        unidadMedida2.setId(unidadMedida1.getId());
        assertThat(unidadMedida1).isEqualTo(unidadMedida2);
        unidadMedida2.setId(2L);
        assertThat(unidadMedida1).isNotEqualTo(unidadMedida2);
        unidadMedida1.setId(null);
        assertThat(unidadMedida1).isNotEqualTo(unidadMedida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadMedidaDTO.class);
        UnidadMedidaDTO unidadMedidaDTO1 = new UnidadMedidaDTO();
        unidadMedidaDTO1.setId(1L);
        UnidadMedidaDTO unidadMedidaDTO2 = new UnidadMedidaDTO();
        assertThat(unidadMedidaDTO1).isNotEqualTo(unidadMedidaDTO2);
        unidadMedidaDTO2.setId(unidadMedidaDTO1.getId());
        assertThat(unidadMedidaDTO1).isEqualTo(unidadMedidaDTO2);
        unidadMedidaDTO2.setId(2L);
        assertThat(unidadMedidaDTO1).isNotEqualTo(unidadMedidaDTO2);
        unidadMedidaDTO1.setId(null);
        assertThat(unidadMedidaDTO1).isNotEqualTo(unidadMedidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unidadMedidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unidadMedidaMapper.fromId(null)).isNull();
    }
}
