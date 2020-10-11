package com.nexo.app.web.rest;

import com.nexo.app.NexoApp;
import com.nexo.app.domain.CarritoProducto;
import com.nexo.app.repository.CarritoProductoRepository;
import com.nexo.app.service.CarritoProductoService;
import com.nexo.app.service.dto.CarritoProductoDTO;
import com.nexo.app.service.mapper.CarritoProductoMapper;
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
 * Integration tests for the {@link CarritoProductoResource} REST controller.
 */
@SpringBootTest(classes = NexoApp.class)
public class CarritoProductoResourceIT {

    private static final Double DEFAULT_CANTIDAD = 1D;
    private static final Double UPDATED_CANTIDAD = 2D;

    private static final Double DEFAULT_PRECIO_COMPRA = 1D;
    private static final Double UPDATED_PRECIO_COMPRA = 2D;

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Double DEFAULT_COSTO_DELIVERY = 1D;
    private static final Double UPDATED_COSTO_DELIVERY = 2D;

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    @Autowired
    private CarritoProductoMapper carritoProductoMapper;

    @Autowired
    private CarritoProductoService carritoProductoService;

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

    private MockMvc restCarritoProductoMockMvc;

    private CarritoProducto carritoProducto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarritoProductoResource carritoProductoResource = new CarritoProductoResource(carritoProductoService);
        this.restCarritoProductoMockMvc = MockMvcBuilders.standaloneSetup(carritoProductoResource)
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
    public static CarritoProducto createEntity(EntityManager em) {
        CarritoProducto carritoProducto = new CarritoProducto()
            .cantidad(DEFAULT_CANTIDAD)
            .precioCompra(DEFAULT_PRECIO_COMPRA)
            .estado(DEFAULT_ESTADO)
            .costoDelivery(DEFAULT_COSTO_DELIVERY);
        return carritoProducto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarritoProducto createUpdatedEntity(EntityManager em) {
        CarritoProducto carritoProducto = new CarritoProducto()
            .cantidad(UPDATED_CANTIDAD)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .estado(UPDATED_ESTADO)
            .costoDelivery(UPDATED_COSTO_DELIVERY);
        return carritoProducto;
    }

    @BeforeEach
    public void initTest() {
        carritoProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarritoProducto() throws Exception {
        int databaseSizeBeforeCreate = carritoProductoRepository.findAll().size();

        // Create the CarritoProducto
        CarritoProductoDTO carritoProductoDTO = carritoProductoMapper.toDto(carritoProducto);
        restCarritoProductoMockMvc.perform(post("/api/carrito-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoProductoDTO)))
            .andExpect(status().isCreated());

        // Validate the CarritoProducto in the database
        List<CarritoProducto> carritoProductoList = carritoProductoRepository.findAll();
        assertThat(carritoProductoList).hasSize(databaseSizeBeforeCreate + 1);
        CarritoProducto testCarritoProducto = carritoProductoList.get(carritoProductoList.size() - 1);
        assertThat(testCarritoProducto.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testCarritoProducto.getPrecioCompra()).isEqualTo(DEFAULT_PRECIO_COMPRA);
        assertThat(testCarritoProducto.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCarritoProducto.getCostoDelivery()).isEqualTo(DEFAULT_COSTO_DELIVERY);
    }

    @Test
    @Transactional
    public void createCarritoProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carritoProductoRepository.findAll().size();

        // Create the CarritoProducto with an existing ID
        carritoProducto.setId(1L);
        CarritoProductoDTO carritoProductoDTO = carritoProductoMapper.toDto(carritoProducto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarritoProductoMockMvc.perform(post("/api/carrito-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarritoProducto in the database
        List<CarritoProducto> carritoProductoList = carritoProductoRepository.findAll();
        assertThat(carritoProductoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCarritoProductos() throws Exception {
        // Initialize the database
        carritoProductoRepository.saveAndFlush(carritoProducto);

        // Get all the carritoProductoList
        restCarritoProductoMockMvc.perform(get("/api/carrito-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carritoProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.doubleValue())))
            .andExpect(jsonPath("$.[*].precioCompra").value(hasItem(DEFAULT_PRECIO_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].costoDelivery").value(hasItem(DEFAULT_COSTO_DELIVERY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCarritoProducto() throws Exception {
        // Initialize the database
        carritoProductoRepository.saveAndFlush(carritoProducto);

        // Get the carritoProducto
        restCarritoProductoMockMvc.perform(get("/api/carrito-productos/{id}", carritoProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carritoProducto.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.doubleValue()))
            .andExpect(jsonPath("$.precioCompra").value(DEFAULT_PRECIO_COMPRA.doubleValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.costoDelivery").value(DEFAULT_COSTO_DELIVERY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarritoProducto() throws Exception {
        // Get the carritoProducto
        restCarritoProductoMockMvc.perform(get("/api/carrito-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarritoProducto() throws Exception {
        // Initialize the database
        carritoProductoRepository.saveAndFlush(carritoProducto);

        int databaseSizeBeforeUpdate = carritoProductoRepository.findAll().size();

        // Update the carritoProducto
        CarritoProducto updatedCarritoProducto = carritoProductoRepository.findById(carritoProducto.getId()).get();
        // Disconnect from session so that the updates on updatedCarritoProducto are not directly saved in db
        em.detach(updatedCarritoProducto);
        updatedCarritoProducto
            .cantidad(UPDATED_CANTIDAD)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .estado(UPDATED_ESTADO)
            .costoDelivery(UPDATED_COSTO_DELIVERY);
        CarritoProductoDTO carritoProductoDTO = carritoProductoMapper.toDto(updatedCarritoProducto);

        restCarritoProductoMockMvc.perform(put("/api/carrito-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoProductoDTO)))
            .andExpect(status().isOk());

        // Validate the CarritoProducto in the database
        List<CarritoProducto> carritoProductoList = carritoProductoRepository.findAll();
        assertThat(carritoProductoList).hasSize(databaseSizeBeforeUpdate);
        CarritoProducto testCarritoProducto = carritoProductoList.get(carritoProductoList.size() - 1);
        assertThat(testCarritoProducto.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testCarritoProducto.getPrecioCompra()).isEqualTo(UPDATED_PRECIO_COMPRA);
        assertThat(testCarritoProducto.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCarritoProducto.getCostoDelivery()).isEqualTo(UPDATED_COSTO_DELIVERY);
    }

    @Test
    @Transactional
    public void updateNonExistingCarritoProducto() throws Exception {
        int databaseSizeBeforeUpdate = carritoProductoRepository.findAll().size();

        // Create the CarritoProducto
        CarritoProductoDTO carritoProductoDTO = carritoProductoMapper.toDto(carritoProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarritoProductoMockMvc.perform(put("/api/carrito-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarritoProducto in the database
        List<CarritoProducto> carritoProductoList = carritoProductoRepository.findAll();
        assertThat(carritoProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarritoProducto() throws Exception {
        // Initialize the database
        carritoProductoRepository.saveAndFlush(carritoProducto);

        int databaseSizeBeforeDelete = carritoProductoRepository.findAll().size();

        // Delete the carritoProducto
        restCarritoProductoMockMvc.perform(delete("/api/carrito-productos/{id}", carritoProducto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarritoProducto> carritoProductoList = carritoProductoRepository.findAll();
        assertThat(carritoProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarritoProducto.class);
        CarritoProducto carritoProducto1 = new CarritoProducto();
        carritoProducto1.setId(1L);
        CarritoProducto carritoProducto2 = new CarritoProducto();
        carritoProducto2.setId(carritoProducto1.getId());
        assertThat(carritoProducto1).isEqualTo(carritoProducto2);
        carritoProducto2.setId(2L);
        assertThat(carritoProducto1).isNotEqualTo(carritoProducto2);
        carritoProducto1.setId(null);
        assertThat(carritoProducto1).isNotEqualTo(carritoProducto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarritoProductoDTO.class);
        CarritoProductoDTO carritoProductoDTO1 = new CarritoProductoDTO();
        carritoProductoDTO1.setId(1L);
        CarritoProductoDTO carritoProductoDTO2 = new CarritoProductoDTO();
        assertThat(carritoProductoDTO1).isNotEqualTo(carritoProductoDTO2);
        carritoProductoDTO2.setId(carritoProductoDTO1.getId());
        assertThat(carritoProductoDTO1).isEqualTo(carritoProductoDTO2);
        carritoProductoDTO2.setId(2L);
        assertThat(carritoProductoDTO1).isNotEqualTo(carritoProductoDTO2);
        carritoProductoDTO1.setId(null);
        assertThat(carritoProductoDTO1).isNotEqualTo(carritoProductoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carritoProductoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carritoProductoMapper.fromId(null)).isNull();
    }
}
