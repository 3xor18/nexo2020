package com.nexo.app.web.rest;

import com.nexo.app.NexoApp;
import com.nexo.app.domain.CostoDelivery;
import com.nexo.app.repository.CostoDeliveryRepository;
import com.nexo.app.service.CostoDeliveryService;
import com.nexo.app.service.dto.CostoDeliveryDTO;
import com.nexo.app.service.mapper.CostoDeliveryMapper;
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
 * Integration tests for the {@link CostoDeliveryResource} REST controller.
 */
@SpringBootTest(classes = NexoApp.class)
public class CostoDeliveryResourceIT {

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTO_INDIVIDUAL = 1D;
    private static final Double UPDATED_MONTO_INDIVIDUAL = 2D;

    private static final Double DEFAULT_MONTO_MAYOR = 1D;
    private static final Double UPDATED_MONTO_MAYOR = 2D;

    private static final Double DEFAULT_CANTIDAD_MAYOR = 1D;
    private static final Double UPDATED_CANTIDAD_MAYOR = 2D;

    private static final ZonedDateTime DEFAULT_FECHA_BD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_BD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CostoDeliveryRepository costoDeliveryRepository;

    @Autowired
    private CostoDeliveryMapper costoDeliveryMapper;

    @Autowired
    private CostoDeliveryService costoDeliveryService;

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

    private MockMvc restCostoDeliveryMockMvc;

    private CostoDelivery costoDelivery;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CostoDeliveryResource costoDeliveryResource = new CostoDeliveryResource(costoDeliveryService);
        this.restCostoDeliveryMockMvc = MockMvcBuilders.standaloneSetup(costoDeliveryResource)
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
    public static CostoDelivery createEntity(EntityManager em) {
        CostoDelivery costoDelivery = new CostoDelivery()
            .estado(DEFAULT_ESTADO)
            .montoIndividual(DEFAULT_MONTO_INDIVIDUAL)
            .montoMayor(DEFAULT_MONTO_MAYOR)
            .cantidadMayor(DEFAULT_CANTIDAD_MAYOR)
            .fechaBd(DEFAULT_FECHA_BD);
        return costoDelivery;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CostoDelivery createUpdatedEntity(EntityManager em) {
        CostoDelivery costoDelivery = new CostoDelivery()
            .estado(UPDATED_ESTADO)
            .montoIndividual(UPDATED_MONTO_INDIVIDUAL)
            .montoMayor(UPDATED_MONTO_MAYOR)
            .cantidadMayor(UPDATED_CANTIDAD_MAYOR)
            .fechaBd(UPDATED_FECHA_BD);
        return costoDelivery;
    }

    @BeforeEach
    public void initTest() {
        costoDelivery = createEntity(em);
    }

    @Test
    @Transactional
    public void createCostoDelivery() throws Exception {
        int databaseSizeBeforeCreate = costoDeliveryRepository.findAll().size();

        // Create the CostoDelivery
        CostoDeliveryDTO costoDeliveryDTO = costoDeliveryMapper.toDto(costoDelivery);
        restCostoDeliveryMockMvc.perform(post("/api/costo-deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoDeliveryDTO)))
            .andExpect(status().isCreated());

        // Validate the CostoDelivery in the database
        List<CostoDelivery> costoDeliveryList = costoDeliveryRepository.findAll();
        assertThat(costoDeliveryList).hasSize(databaseSizeBeforeCreate + 1);
        CostoDelivery testCostoDelivery = costoDeliveryList.get(costoDeliveryList.size() - 1);
        assertThat(testCostoDelivery.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCostoDelivery.getMontoIndividual()).isEqualTo(DEFAULT_MONTO_INDIVIDUAL);
        assertThat(testCostoDelivery.getMontoMayor()).isEqualTo(DEFAULT_MONTO_MAYOR);
        assertThat(testCostoDelivery.getCantidadMayor()).isEqualTo(DEFAULT_CANTIDAD_MAYOR);
        assertThat(testCostoDelivery.getFechaBd()).isEqualTo(DEFAULT_FECHA_BD);
    }

    @Test
    @Transactional
    public void createCostoDeliveryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = costoDeliveryRepository.findAll().size();

        // Create the CostoDelivery with an existing ID
        costoDelivery.setId(1L);
        CostoDeliveryDTO costoDeliveryDTO = costoDeliveryMapper.toDto(costoDelivery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostoDeliveryMockMvc.perform(post("/api/costo-deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoDeliveryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CostoDelivery in the database
        List<CostoDelivery> costoDeliveryList = costoDeliveryRepository.findAll();
        assertThat(costoDeliveryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCostoDeliveries() throws Exception {
        // Initialize the database
        costoDeliveryRepository.saveAndFlush(costoDelivery);

        // Get all the costoDeliveryList
        restCostoDeliveryMockMvc.perform(get("/api/costo-deliveries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costoDelivery.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].montoIndividual").value(hasItem(DEFAULT_MONTO_INDIVIDUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].montoMayor").value(hasItem(DEFAULT_MONTO_MAYOR.doubleValue())))
            .andExpect(jsonPath("$.[*].cantidadMayor").value(hasItem(DEFAULT_CANTIDAD_MAYOR.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaBd").value(hasItem(sameInstant(DEFAULT_FECHA_BD))));
    }
    
    @Test
    @Transactional
    public void getCostoDelivery() throws Exception {
        // Initialize the database
        costoDeliveryRepository.saveAndFlush(costoDelivery);

        // Get the costoDelivery
        restCostoDeliveryMockMvc.perform(get("/api/costo-deliveries/{id}", costoDelivery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(costoDelivery.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.montoIndividual").value(DEFAULT_MONTO_INDIVIDUAL.doubleValue()))
            .andExpect(jsonPath("$.montoMayor").value(DEFAULT_MONTO_MAYOR.doubleValue()))
            .andExpect(jsonPath("$.cantidadMayor").value(DEFAULT_CANTIDAD_MAYOR.doubleValue()))
            .andExpect(jsonPath("$.fechaBd").value(sameInstant(DEFAULT_FECHA_BD)));
    }

    @Test
    @Transactional
    public void getNonExistingCostoDelivery() throws Exception {
        // Get the costoDelivery
        restCostoDeliveryMockMvc.perform(get("/api/costo-deliveries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCostoDelivery() throws Exception {
        // Initialize the database
        costoDeliveryRepository.saveAndFlush(costoDelivery);

        int databaseSizeBeforeUpdate = costoDeliveryRepository.findAll().size();

        // Update the costoDelivery
        CostoDelivery updatedCostoDelivery = costoDeliveryRepository.findById(costoDelivery.getId()).get();
        // Disconnect from session so that the updates on updatedCostoDelivery are not directly saved in db
        em.detach(updatedCostoDelivery);
        updatedCostoDelivery
            .estado(UPDATED_ESTADO)
            .montoIndividual(UPDATED_MONTO_INDIVIDUAL)
            .montoMayor(UPDATED_MONTO_MAYOR)
            .cantidadMayor(UPDATED_CANTIDAD_MAYOR)
            .fechaBd(UPDATED_FECHA_BD);
        CostoDeliveryDTO costoDeliveryDTO = costoDeliveryMapper.toDto(updatedCostoDelivery);

        restCostoDeliveryMockMvc.perform(put("/api/costo-deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoDeliveryDTO)))
            .andExpect(status().isOk());

        // Validate the CostoDelivery in the database
        List<CostoDelivery> costoDeliveryList = costoDeliveryRepository.findAll();
        assertThat(costoDeliveryList).hasSize(databaseSizeBeforeUpdate);
        CostoDelivery testCostoDelivery = costoDeliveryList.get(costoDeliveryList.size() - 1);
        assertThat(testCostoDelivery.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCostoDelivery.getMontoIndividual()).isEqualTo(UPDATED_MONTO_INDIVIDUAL);
        assertThat(testCostoDelivery.getMontoMayor()).isEqualTo(UPDATED_MONTO_MAYOR);
        assertThat(testCostoDelivery.getCantidadMayor()).isEqualTo(UPDATED_CANTIDAD_MAYOR);
        assertThat(testCostoDelivery.getFechaBd()).isEqualTo(UPDATED_FECHA_BD);
    }

    @Test
    @Transactional
    public void updateNonExistingCostoDelivery() throws Exception {
        int databaseSizeBeforeUpdate = costoDeliveryRepository.findAll().size();

        // Create the CostoDelivery
        CostoDeliveryDTO costoDeliveryDTO = costoDeliveryMapper.toDto(costoDelivery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCostoDeliveryMockMvc.perform(put("/api/costo-deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoDeliveryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CostoDelivery in the database
        List<CostoDelivery> costoDeliveryList = costoDeliveryRepository.findAll();
        assertThat(costoDeliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCostoDelivery() throws Exception {
        // Initialize the database
        costoDeliveryRepository.saveAndFlush(costoDelivery);

        int databaseSizeBeforeDelete = costoDeliveryRepository.findAll().size();

        // Delete the costoDelivery
        restCostoDeliveryMockMvc.perform(delete("/api/costo-deliveries/{id}", costoDelivery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CostoDelivery> costoDeliveryList = costoDeliveryRepository.findAll();
        assertThat(costoDeliveryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostoDelivery.class);
        CostoDelivery costoDelivery1 = new CostoDelivery();
        costoDelivery1.setId(1L);
        CostoDelivery costoDelivery2 = new CostoDelivery();
        costoDelivery2.setId(costoDelivery1.getId());
        assertThat(costoDelivery1).isEqualTo(costoDelivery2);
        costoDelivery2.setId(2L);
        assertThat(costoDelivery1).isNotEqualTo(costoDelivery2);
        costoDelivery1.setId(null);
        assertThat(costoDelivery1).isNotEqualTo(costoDelivery2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostoDeliveryDTO.class);
        CostoDeliveryDTO costoDeliveryDTO1 = new CostoDeliveryDTO();
        costoDeliveryDTO1.setId(1L);
        CostoDeliveryDTO costoDeliveryDTO2 = new CostoDeliveryDTO();
        assertThat(costoDeliveryDTO1).isNotEqualTo(costoDeliveryDTO2);
        costoDeliveryDTO2.setId(costoDeliveryDTO1.getId());
        assertThat(costoDeliveryDTO1).isEqualTo(costoDeliveryDTO2);
        costoDeliveryDTO2.setId(2L);
        assertThat(costoDeliveryDTO1).isNotEqualTo(costoDeliveryDTO2);
        costoDeliveryDTO1.setId(null);
        assertThat(costoDeliveryDTO1).isNotEqualTo(costoDeliveryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(costoDeliveryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(costoDeliveryMapper.fromId(null)).isNull();
    }
}
