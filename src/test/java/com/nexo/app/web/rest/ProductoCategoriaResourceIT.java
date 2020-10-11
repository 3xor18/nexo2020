package com.nexo.app.web.rest;

import com.nexo.app.NexoApp;
import com.nexo.app.domain.ProductoCategoria;
import com.nexo.app.repository.ProductoCategoriaRepository;
import com.nexo.app.service.ProductoCategoriaService;
import com.nexo.app.service.dto.ProductoCategoriaDTO;
import com.nexo.app.service.mapper.ProductoCategoriaMapper;
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
 * Integration tests for the {@link ProductoCategoriaResource} REST controller.
 */
@SpringBootTest(classes = NexoApp.class)
public class ProductoCategoriaResourceIT {

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_BD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_BD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ProductoCategoriaRepository productoCategoriaRepository;

    @Autowired
    private ProductoCategoriaMapper productoCategoriaMapper;

    @Autowired
    private ProductoCategoriaService productoCategoriaService;

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

    private MockMvc restProductoCategoriaMockMvc;

    private ProductoCategoria productoCategoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoCategoriaResource productoCategoriaResource = new ProductoCategoriaResource(productoCategoriaService);
        this.restProductoCategoriaMockMvc = MockMvcBuilders.standaloneSetup(productoCategoriaResource)
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
    public static ProductoCategoria createEntity(EntityManager em) {
        ProductoCategoria productoCategoria = new ProductoCategoria()
            .estado(DEFAULT_ESTADO)
            .fechaBD(DEFAULT_FECHA_BD);
        return productoCategoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoCategoria createUpdatedEntity(EntityManager em) {
        ProductoCategoria productoCategoria = new ProductoCategoria()
            .estado(UPDATED_ESTADO)
            .fechaBD(UPDATED_FECHA_BD);
        return productoCategoria;
    }

    @BeforeEach
    public void initTest() {
        productoCategoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductoCategoria() throws Exception {
        int databaseSizeBeforeCreate = productoCategoriaRepository.findAll().size();

        // Create the ProductoCategoria
        ProductoCategoriaDTO productoCategoriaDTO = productoCategoriaMapper.toDto(productoCategoria);
        restProductoCategoriaMockMvc.perform(post("/api/producto-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoCategoriaDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductoCategoria in the database
        List<ProductoCategoria> productoCategoriaList = productoCategoriaRepository.findAll();
        assertThat(productoCategoriaList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoCategoria testProductoCategoria = productoCategoriaList.get(productoCategoriaList.size() - 1);
        assertThat(testProductoCategoria.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testProductoCategoria.getFechaBD()).isEqualTo(DEFAULT_FECHA_BD);
    }

    @Test
    @Transactional
    public void createProductoCategoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoCategoriaRepository.findAll().size();

        // Create the ProductoCategoria with an existing ID
        productoCategoria.setId(1L);
        ProductoCategoriaDTO productoCategoriaDTO = productoCategoriaMapper.toDto(productoCategoria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoCategoriaMockMvc.perform(post("/api/producto-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoCategoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoCategoria in the database
        List<ProductoCategoria> productoCategoriaList = productoCategoriaRepository.findAll();
        assertThat(productoCategoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductoCategorias() throws Exception {
        // Initialize the database
        productoCategoriaRepository.saveAndFlush(productoCategoria);

        // Get all the productoCategoriaList
        restProductoCategoriaMockMvc.perform(get("/api/producto-categorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoCategoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].fechaBD").value(hasItem(sameInstant(DEFAULT_FECHA_BD))));
    }
    
    @Test
    @Transactional
    public void getProductoCategoria() throws Exception {
        // Initialize the database
        productoCategoriaRepository.saveAndFlush(productoCategoria);

        // Get the productoCategoria
        restProductoCategoriaMockMvc.perform(get("/api/producto-categorias/{id}", productoCategoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productoCategoria.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.fechaBD").value(sameInstant(DEFAULT_FECHA_BD)));
    }

    @Test
    @Transactional
    public void getNonExistingProductoCategoria() throws Exception {
        // Get the productoCategoria
        restProductoCategoriaMockMvc.perform(get("/api/producto-categorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductoCategoria() throws Exception {
        // Initialize the database
        productoCategoriaRepository.saveAndFlush(productoCategoria);

        int databaseSizeBeforeUpdate = productoCategoriaRepository.findAll().size();

        // Update the productoCategoria
        ProductoCategoria updatedProductoCategoria = productoCategoriaRepository.findById(productoCategoria.getId()).get();
        // Disconnect from session so that the updates on updatedProductoCategoria are not directly saved in db
        em.detach(updatedProductoCategoria);
        updatedProductoCategoria
            .estado(UPDATED_ESTADO)
            .fechaBD(UPDATED_FECHA_BD);
        ProductoCategoriaDTO productoCategoriaDTO = productoCategoriaMapper.toDto(updatedProductoCategoria);

        restProductoCategoriaMockMvc.perform(put("/api/producto-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoCategoriaDTO)))
            .andExpect(status().isOk());

        // Validate the ProductoCategoria in the database
        List<ProductoCategoria> productoCategoriaList = productoCategoriaRepository.findAll();
        assertThat(productoCategoriaList).hasSize(databaseSizeBeforeUpdate);
        ProductoCategoria testProductoCategoria = productoCategoriaList.get(productoCategoriaList.size() - 1);
        assertThat(testProductoCategoria.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testProductoCategoria.getFechaBD()).isEqualTo(UPDATED_FECHA_BD);
    }

    @Test
    @Transactional
    public void updateNonExistingProductoCategoria() throws Exception {
        int databaseSizeBeforeUpdate = productoCategoriaRepository.findAll().size();

        // Create the ProductoCategoria
        ProductoCategoriaDTO productoCategoriaDTO = productoCategoriaMapper.toDto(productoCategoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoCategoriaMockMvc.perform(put("/api/producto-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoCategoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoCategoria in the database
        List<ProductoCategoria> productoCategoriaList = productoCategoriaRepository.findAll();
        assertThat(productoCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductoCategoria() throws Exception {
        // Initialize the database
        productoCategoriaRepository.saveAndFlush(productoCategoria);

        int databaseSizeBeforeDelete = productoCategoriaRepository.findAll().size();

        // Delete the productoCategoria
        restProductoCategoriaMockMvc.perform(delete("/api/producto-categorias/{id}", productoCategoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoCategoria> productoCategoriaList = productoCategoriaRepository.findAll();
        assertThat(productoCategoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoCategoria.class);
        ProductoCategoria productoCategoria1 = new ProductoCategoria();
        productoCategoria1.setId(1L);
        ProductoCategoria productoCategoria2 = new ProductoCategoria();
        productoCategoria2.setId(productoCategoria1.getId());
        assertThat(productoCategoria1).isEqualTo(productoCategoria2);
        productoCategoria2.setId(2L);
        assertThat(productoCategoria1).isNotEqualTo(productoCategoria2);
        productoCategoria1.setId(null);
        assertThat(productoCategoria1).isNotEqualTo(productoCategoria2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoCategoriaDTO.class);
        ProductoCategoriaDTO productoCategoriaDTO1 = new ProductoCategoriaDTO();
        productoCategoriaDTO1.setId(1L);
        ProductoCategoriaDTO productoCategoriaDTO2 = new ProductoCategoriaDTO();
        assertThat(productoCategoriaDTO1).isNotEqualTo(productoCategoriaDTO2);
        productoCategoriaDTO2.setId(productoCategoriaDTO1.getId());
        assertThat(productoCategoriaDTO1).isEqualTo(productoCategoriaDTO2);
        productoCategoriaDTO2.setId(2L);
        assertThat(productoCategoriaDTO1).isNotEqualTo(productoCategoriaDTO2);
        productoCategoriaDTO1.setId(null);
        assertThat(productoCategoriaDTO1).isNotEqualTo(productoCategoriaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productoCategoriaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productoCategoriaMapper.fromId(null)).isNull();
    }
}
