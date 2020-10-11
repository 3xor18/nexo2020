package com.nexo.app.web.rest;

import com.nexo.app.NexoApp;
import com.nexo.app.domain.ProductoImagenes;
import com.nexo.app.repository.ProductoImagenesRepository;
import com.nexo.app.service.ProductoImagenesService;
import com.nexo.app.service.dto.ProductoImagenesDTO;
import com.nexo.app.service.mapper.ProductoImagenesMapper;
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
 * Integration tests for the {@link ProductoImagenesResource} REST controller.
 */
@SpringBootTest(classes = NexoApp.class)
public class ProductoImagenesResourceIT {

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_BD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_BD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private ProductoImagenesRepository productoImagenesRepository;

    @Autowired
    private ProductoImagenesMapper productoImagenesMapper;

    @Autowired
    private ProductoImagenesService productoImagenesService;

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

    private MockMvc restProductoImagenesMockMvc;

    private ProductoImagenes productoImagenes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoImagenesResource productoImagenesResource = new ProductoImagenesResource(productoImagenesService);
        this.restProductoImagenesMockMvc = MockMvcBuilders.standaloneSetup(productoImagenesResource)
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
    public static ProductoImagenes createEntity(EntityManager em) {
        ProductoImagenes productoImagenes = new ProductoImagenes()
            .estado(DEFAULT_ESTADO)
            .fechaBd(DEFAULT_FECHA_BD)
            .path(DEFAULT_PATH)
            .nombre(DEFAULT_NOMBRE);
        return productoImagenes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoImagenes createUpdatedEntity(EntityManager em) {
        ProductoImagenes productoImagenes = new ProductoImagenes()
            .estado(UPDATED_ESTADO)
            .fechaBd(UPDATED_FECHA_BD)
            .path(UPDATED_PATH)
            .nombre(UPDATED_NOMBRE);
        return productoImagenes;
    }

    @BeforeEach
    public void initTest() {
        productoImagenes = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductoImagenes() throws Exception {
        int databaseSizeBeforeCreate = productoImagenesRepository.findAll().size();

        // Create the ProductoImagenes
        ProductoImagenesDTO productoImagenesDTO = productoImagenesMapper.toDto(productoImagenes);
        restProductoImagenesMockMvc.perform(post("/api/producto-imagenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImagenesDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductoImagenes in the database
        List<ProductoImagenes> productoImagenesList = productoImagenesRepository.findAll();
        assertThat(productoImagenesList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoImagenes testProductoImagenes = productoImagenesList.get(productoImagenesList.size() - 1);
        assertThat(testProductoImagenes.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testProductoImagenes.getFechaBd()).isEqualTo(DEFAULT_FECHA_BD);
        assertThat(testProductoImagenes.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testProductoImagenes.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createProductoImagenesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoImagenesRepository.findAll().size();

        // Create the ProductoImagenes with an existing ID
        productoImagenes.setId(1L);
        ProductoImagenesDTO productoImagenesDTO = productoImagenesMapper.toDto(productoImagenes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoImagenesMockMvc.perform(post("/api/producto-imagenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImagenesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoImagenes in the database
        List<ProductoImagenes> productoImagenesList = productoImagenesRepository.findAll();
        assertThat(productoImagenesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductoImagenes() throws Exception {
        // Initialize the database
        productoImagenesRepository.saveAndFlush(productoImagenes);

        // Get all the productoImagenesList
        restProductoImagenesMockMvc.perform(get("/api/producto-imagenes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoImagenes.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].fechaBd").value(hasItem(sameInstant(DEFAULT_FECHA_BD))))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getProductoImagenes() throws Exception {
        // Initialize the database
        productoImagenesRepository.saveAndFlush(productoImagenes);

        // Get the productoImagenes
        restProductoImagenesMockMvc.perform(get("/api/producto-imagenes/{id}", productoImagenes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productoImagenes.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.fechaBd").value(sameInstant(DEFAULT_FECHA_BD)))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingProductoImagenes() throws Exception {
        // Get the productoImagenes
        restProductoImagenesMockMvc.perform(get("/api/producto-imagenes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductoImagenes() throws Exception {
        // Initialize the database
        productoImagenesRepository.saveAndFlush(productoImagenes);

        int databaseSizeBeforeUpdate = productoImagenesRepository.findAll().size();

        // Update the productoImagenes
        ProductoImagenes updatedProductoImagenes = productoImagenesRepository.findById(productoImagenes.getId()).get();
        // Disconnect from session so that the updates on updatedProductoImagenes are not directly saved in db
        em.detach(updatedProductoImagenes);
        updatedProductoImagenes
            .estado(UPDATED_ESTADO)
            .fechaBd(UPDATED_FECHA_BD)
            .path(UPDATED_PATH)
            .nombre(UPDATED_NOMBRE);
        ProductoImagenesDTO productoImagenesDTO = productoImagenesMapper.toDto(updatedProductoImagenes);

        restProductoImagenesMockMvc.perform(put("/api/producto-imagenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImagenesDTO)))
            .andExpect(status().isOk());

        // Validate the ProductoImagenes in the database
        List<ProductoImagenes> productoImagenesList = productoImagenesRepository.findAll();
        assertThat(productoImagenesList).hasSize(databaseSizeBeforeUpdate);
        ProductoImagenes testProductoImagenes = productoImagenesList.get(productoImagenesList.size() - 1);
        assertThat(testProductoImagenes.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testProductoImagenes.getFechaBd()).isEqualTo(UPDATED_FECHA_BD);
        assertThat(testProductoImagenes.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testProductoImagenes.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductoImagenes() throws Exception {
        int databaseSizeBeforeUpdate = productoImagenesRepository.findAll().size();

        // Create the ProductoImagenes
        ProductoImagenesDTO productoImagenesDTO = productoImagenesMapper.toDto(productoImagenes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoImagenesMockMvc.perform(put("/api/producto-imagenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoImagenesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoImagenes in the database
        List<ProductoImagenes> productoImagenesList = productoImagenesRepository.findAll();
        assertThat(productoImagenesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductoImagenes() throws Exception {
        // Initialize the database
        productoImagenesRepository.saveAndFlush(productoImagenes);

        int databaseSizeBeforeDelete = productoImagenesRepository.findAll().size();

        // Delete the productoImagenes
        restProductoImagenesMockMvc.perform(delete("/api/producto-imagenes/{id}", productoImagenes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoImagenes> productoImagenesList = productoImagenesRepository.findAll();
        assertThat(productoImagenesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoImagenes.class);
        ProductoImagenes productoImagenes1 = new ProductoImagenes();
        productoImagenes1.setId(1L);
        ProductoImagenes productoImagenes2 = new ProductoImagenes();
        productoImagenes2.setId(productoImagenes1.getId());
        assertThat(productoImagenes1).isEqualTo(productoImagenes2);
        productoImagenes2.setId(2L);
        assertThat(productoImagenes1).isNotEqualTo(productoImagenes2);
        productoImagenes1.setId(null);
        assertThat(productoImagenes1).isNotEqualTo(productoImagenes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoImagenesDTO.class);
        ProductoImagenesDTO productoImagenesDTO1 = new ProductoImagenesDTO();
        productoImagenesDTO1.setId(1L);
        ProductoImagenesDTO productoImagenesDTO2 = new ProductoImagenesDTO();
        assertThat(productoImagenesDTO1).isNotEqualTo(productoImagenesDTO2);
        productoImagenesDTO2.setId(productoImagenesDTO1.getId());
        assertThat(productoImagenesDTO1).isEqualTo(productoImagenesDTO2);
        productoImagenesDTO2.setId(2L);
        assertThat(productoImagenesDTO1).isNotEqualTo(productoImagenesDTO2);
        productoImagenesDTO1.setId(null);
        assertThat(productoImagenesDTO1).isNotEqualTo(productoImagenesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productoImagenesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productoImagenesMapper.fromId(null)).isNull();
    }
}
