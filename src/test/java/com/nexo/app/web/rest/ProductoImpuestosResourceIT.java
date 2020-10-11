package com.nexo.app.web.rest;

import com.nexo.app.NexoApp;
import com.nexo.app.domain.ProductoImpuestos;
import com.nexo.app.repository.ProductoImpuestosRepository;
import com.nexo.app.service.ProductoImpuestosService;
import com.nexo.app.service.dto.ProductoImpuestosDTO;
import com.nexo.app.service.mapper.ProductoImpuestosMapper;
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
 * Integration tests for the {@link ProductoImpuestosResource} REST controller.
 */
@SpringBootTest(classes = NexoApp.class)
public class ProductoImpuestosResourceIT {

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_BD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_BD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IMPUESTO_MONTO_FIJO = false;
    private static final Boolean UPDATED_IMPUESTO_MONTO_FIJO = true;

    private static final Double DEFAULT_MONTO_O_TASA = 1D;
    private static final Double UPDATED_MONTO_O_TASA = 2D;

    private static final Boolean DEFAULT_IMPUESTO_NACIONAL = false;
    private static final Boolean UPDATED_IMPUESTO_NACIONAL = true;

    private static final ZonedDateTime DEFAULT_FECHA_APLICABLE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_APLICABLE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ProductoImpuestosRepository productoImpuestosRepository;

    @Autowired
    private ProductoImpuestosMapper productoImpuestosMapper;

    @Autowired
    private ProductoImpuestosService productoImpuestosService;

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

    private MockMvc restProductoImpuestosMockMvc;

    private ProductoImpuestos productoImpuestos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoImpuestosResource productoImpuestosResource = new ProductoImpuestosResource(productoImpuestosService);
        this.restProductoImpuestosMockMvc = MockMvcBuilders.standaloneSetup(productoImpuestosResource)
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
    public static ProductoImpuestos createEntity(EntityManager em) {
        ProductoImpuestos productoImpuestos = new ProductoImpuestos()
            .estado(DEFAULT_ESTADO)
            .nombre(DEFAULT_NOMBRE)
            .fechaBd(DEFAULT_FECHA_BD)
            .impuestoMontoFijo(DEFAULT_IMPUESTO_MONTO_FIJO)
            .montoOTasa(DEFAULT_MONTO_O_TASA)
            .impuestoNacional(DEFAULT_IMPUESTO_NACIONAL)
            .fechaAplicable(DEFAULT_FECHA_APLICABLE);
        return productoImpuestos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoImpuestos createUpdatedEntity(EntityManager em) {
        ProductoImpuestos productoImpuestos = new ProductoImpuestos()
            .estado(UPDATED_ESTADO)
            .nombre(UPDATED_NOMBRE)
            .fechaBd(UPDATED_FECHA_BD)
            .impuestoMontoFijo(UPDATED_IMPUESTO_MONTO_FIJO)
            .montoOTasa(UPDATED_MONTO_O_TASA)
            .impuestoNacional(UPDATED_IMPUESTO_NACIONAL)
            .fechaAplicable(UPDATED_FECHA_APLICABLE);
        return productoImpuestos;
    }

    @BeforeEach
    public void initTest() {
        productoImpuestos = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductoImpuestos() throws Exception {
        int databaseSizeBeforeCreate = productoImpuestosRepository.findAll().size();

        // Create the ProductoImpuestos
        ProductoImpuestosDTO productoImpuestosDTO = productoImpuestosMapper.toDto(productoImpuestos);
        restProductoImpuestosMockMvc.perform(post("/api/producto-impuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImpuestosDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductoImpuestos in the database
        List<ProductoImpuestos> productoImpuestosList = productoImpuestosRepository.findAll();
        assertThat(productoImpuestosList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoImpuestos testProductoImpuestos = productoImpuestosList.get(productoImpuestosList.size() - 1);
        assertThat(testProductoImpuestos.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testProductoImpuestos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProductoImpuestos.getFechaBd()).isEqualTo(DEFAULT_FECHA_BD);
        assertThat(testProductoImpuestos.isImpuestoMontoFijo()).isEqualTo(DEFAULT_IMPUESTO_MONTO_FIJO);
        assertThat(testProductoImpuestos.getMontoOTasa()).isEqualTo(DEFAULT_MONTO_O_TASA);
        assertThat(testProductoImpuestos.isImpuestoNacional()).isEqualTo(DEFAULT_IMPUESTO_NACIONAL);
        assertThat(testProductoImpuestos.getFechaAplicable()).isEqualTo(DEFAULT_FECHA_APLICABLE);
    }

    @Test
    @Transactional
    public void createProductoImpuestosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoImpuestosRepository.findAll().size();

        // Create the ProductoImpuestos with an existing ID
        productoImpuestos.setId(1L);
        ProductoImpuestosDTO productoImpuestosDTO = productoImpuestosMapper.toDto(productoImpuestos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoImpuestosMockMvc.perform(post("/api/producto-impuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImpuestosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoImpuestos in the database
        List<ProductoImpuestos> productoImpuestosList = productoImpuestosRepository.findAll();
        assertThat(productoImpuestosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductoImpuestos() throws Exception {
        // Initialize the database
        productoImpuestosRepository.saveAndFlush(productoImpuestos);

        // Get all the productoImpuestosList
        restProductoImpuestosMockMvc.perform(get("/api/producto-impuestos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoImpuestos.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].fechaBd").value(hasItem(sameInstant(DEFAULT_FECHA_BD))))
            .andExpect(jsonPath("$.[*].impuestoMontoFijo").value(hasItem(DEFAULT_IMPUESTO_MONTO_FIJO.booleanValue())))
            .andExpect(jsonPath("$.[*].montoOTasa").value(hasItem(DEFAULT_MONTO_O_TASA.doubleValue())))
            .andExpect(jsonPath("$.[*].impuestoNacional").value(hasItem(DEFAULT_IMPUESTO_NACIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaAplicable").value(hasItem(sameInstant(DEFAULT_FECHA_APLICABLE))));
    }
    
    @Test
    @Transactional
    public void getProductoImpuestos() throws Exception {
        // Initialize the database
        productoImpuestosRepository.saveAndFlush(productoImpuestos);

        // Get the productoImpuestos
        restProductoImpuestosMockMvc.perform(get("/api/producto-impuestos/{id}", productoImpuestos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productoImpuestos.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.fechaBd").value(sameInstant(DEFAULT_FECHA_BD)))
            .andExpect(jsonPath("$.impuestoMontoFijo").value(DEFAULT_IMPUESTO_MONTO_FIJO.booleanValue()))
            .andExpect(jsonPath("$.montoOTasa").value(DEFAULT_MONTO_O_TASA.doubleValue()))
            .andExpect(jsonPath("$.impuestoNacional").value(DEFAULT_IMPUESTO_NACIONAL.booleanValue()))
            .andExpect(jsonPath("$.fechaAplicable").value(sameInstant(DEFAULT_FECHA_APLICABLE)));
    }

    @Test
    @Transactional
    public void getNonExistingProductoImpuestos() throws Exception {
        // Get the productoImpuestos
        restProductoImpuestosMockMvc.perform(get("/api/producto-impuestos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductoImpuestos() throws Exception {
        // Initialize the database
        productoImpuestosRepository.saveAndFlush(productoImpuestos);

        int databaseSizeBeforeUpdate = productoImpuestosRepository.findAll().size();

        // Update the productoImpuestos
        ProductoImpuestos updatedProductoImpuestos = productoImpuestosRepository.findById(productoImpuestos.getId()).get();
        // Disconnect from session so that the updates on updatedProductoImpuestos are not directly saved in db
        em.detach(updatedProductoImpuestos);
        updatedProductoImpuestos
            .estado(UPDATED_ESTADO)
            .nombre(UPDATED_NOMBRE)
            .fechaBd(UPDATED_FECHA_BD)
            .impuestoMontoFijo(UPDATED_IMPUESTO_MONTO_FIJO)
            .montoOTasa(UPDATED_MONTO_O_TASA)
            .impuestoNacional(UPDATED_IMPUESTO_NACIONAL)
            .fechaAplicable(UPDATED_FECHA_APLICABLE);
        ProductoImpuestosDTO productoImpuestosDTO = productoImpuestosMapper.toDto(updatedProductoImpuestos);

        restProductoImpuestosMockMvc.perform(put("/api/producto-impuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImpuestosDTO)))
            .andExpect(status().isOk());

        // Validate the ProductoImpuestos in the database
        List<ProductoImpuestos> productoImpuestosList = productoImpuestosRepository.findAll();
        assertThat(productoImpuestosList).hasSize(databaseSizeBeforeUpdate);
        ProductoImpuestos testProductoImpuestos = productoImpuestosList.get(productoImpuestosList.size() - 1);
        assertThat(testProductoImpuestos.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testProductoImpuestos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProductoImpuestos.getFechaBd()).isEqualTo(UPDATED_FECHA_BD);
        assertThat(testProductoImpuestos.isImpuestoMontoFijo()).isEqualTo(UPDATED_IMPUESTO_MONTO_FIJO);
        assertThat(testProductoImpuestos.getMontoOTasa()).isEqualTo(UPDATED_MONTO_O_TASA);
        assertThat(testProductoImpuestos.isImpuestoNacional()).isEqualTo(UPDATED_IMPUESTO_NACIONAL);
        assertThat(testProductoImpuestos.getFechaAplicable()).isEqualTo(UPDATED_FECHA_APLICABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductoImpuestos() throws Exception {
        int databaseSizeBeforeUpdate = productoImpuestosRepository.findAll().size();

        // Create the ProductoImpuestos
        ProductoImpuestosDTO productoImpuestosDTO = productoImpuestosMapper.toDto(productoImpuestos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoImpuestosMockMvc.perform(put("/api/producto-impuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImpuestosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoImpuestos in the database
        List<ProductoImpuestos> productoImpuestosList = productoImpuestosRepository.findAll();
        assertThat(productoImpuestosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductoImpuestos() throws Exception {
        // Initialize the database
        productoImpuestosRepository.saveAndFlush(productoImpuestos);

        int databaseSizeBeforeDelete = productoImpuestosRepository.findAll().size();

        // Delete the productoImpuestos
        restProductoImpuestosMockMvc.perform(delete("/api/producto-impuestos/{id}", productoImpuestos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoImpuestos> productoImpuestosList = productoImpuestosRepository.findAll();
        assertThat(productoImpuestosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoImpuestos.class);
        ProductoImpuestos productoImpuestos1 = new ProductoImpuestos();
        productoImpuestos1.setId(1L);
        ProductoImpuestos productoImpuestos2 = new ProductoImpuestos();
        productoImpuestos2.setId(productoImpuestos1.getId());
        assertThat(productoImpuestos1).isEqualTo(productoImpuestos2);
        productoImpuestos2.setId(2L);
        assertThat(productoImpuestos1).isNotEqualTo(productoImpuestos2);
        productoImpuestos1.setId(null);
        assertThat(productoImpuestos1).isNotEqualTo(productoImpuestos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoImpuestosDTO.class);
        ProductoImpuestosDTO productoImpuestosDTO1 = new ProductoImpuestosDTO();
        productoImpuestosDTO1.setId(1L);
        ProductoImpuestosDTO productoImpuestosDTO2 = new ProductoImpuestosDTO();
        assertThat(productoImpuestosDTO1).isNotEqualTo(productoImpuestosDTO2);
        productoImpuestosDTO2.setId(productoImpuestosDTO1.getId());
        assertThat(productoImpuestosDTO1).isEqualTo(productoImpuestosDTO2);
        productoImpuestosDTO2.setId(2L);
        assertThat(productoImpuestosDTO1).isNotEqualTo(productoImpuestosDTO2);
        productoImpuestosDTO1.setId(null);
        assertThat(productoImpuestosDTO1).isNotEqualTo(productoImpuestosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productoImpuestosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productoImpuestosMapper.fromId(null)).isNull();
    }
}
