package com.nexo.app.web.rest;

import com.nexo.app.NexoApp;
import com.nexo.app.domain.Carrito;
import com.nexo.app.repository.CarritoRepository;
import com.nexo.app.service.CarritoService;
import com.nexo.app.service.dto.CarritoDTO;
import com.nexo.app.service.mapper.CarritoMapper;
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
 * Integration tests for the {@link CarritoResource} REST controller.
 */
@SpringBootTest(classes = NexoApp.class)
public class CarritoResourceIT {

    private static final ZonedDateTime DEFAULT_FECHA_PEDIDO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_PEDIDO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_PAGO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_PAGO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_CONFIRMADO_PAGO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_CONFIRMADO_PAGO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_ENTREGA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_ENTREGA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CONTACTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WHATSAPP = false;
    private static final Boolean UPDATED_WHATSAPP = true;

    private static final Double DEFAULT_PUNTAJE_COMPRADOR = 1D;
    private static final Double UPDATED_PUNTAJE_COMPRADOR = 2D;

    private static final Double DEFAULT_PUNTAJE_VENDEDOR = 1D;
    private static final Double UPDATED_PUNTAJE_VENDEDOR = 2D;

    private static final ZonedDateTime DEFAULT_FECHA_TERMINO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_TERMINO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_RAZON_TERMINO = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_TERMINO = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTO_TOTAL_COMPRA = 1D;
    private static final Double UPDATED_MONTO_TOTAL_COMPRA = 2D;

    private static final Double DEFAULT_MONTO_DELIVERY = 1D;
    private static final Double UPDATED_MONTO_DELIVERY = 2D;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoMapper carritoMapper;

    @Autowired
    private CarritoService carritoService;

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

    private MockMvc restCarritoMockMvc;

    private Carrito carrito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarritoResource carritoResource = new CarritoResource(carritoService);
        this.restCarritoMockMvc = MockMvcBuilders.standaloneSetup(carritoResource)
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
    public static Carrito createEntity(EntityManager em) {
        Carrito carrito = new Carrito()
            .fechaPedido(DEFAULT_FECHA_PEDIDO)
            .fechaPago(DEFAULT_FECHA_PAGO)
            .fechaConfirmadoPago(DEFAULT_FECHA_CONFIRMADO_PAGO)
            .fechaEntrega(DEFAULT_FECHA_ENTREGA)
            .estado(DEFAULT_ESTADO)
            .telefonoContacto(DEFAULT_TELEFONO_CONTACTO)
            .whatsapp(DEFAULT_WHATSAPP)
            .puntajeComprador(DEFAULT_PUNTAJE_COMPRADOR)
            .puntajeVendedor(DEFAULT_PUNTAJE_VENDEDOR)
            .fechaTermino(DEFAULT_FECHA_TERMINO)
            .razonTermino(DEFAULT_RAZON_TERMINO)
            .montoTotalCompra(DEFAULT_MONTO_TOTAL_COMPRA)
            .montoDelivery(DEFAULT_MONTO_DELIVERY);
        return carrito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carrito createUpdatedEntity(EntityManager em) {
        Carrito carrito = new Carrito()
            .fechaPedido(UPDATED_FECHA_PEDIDO)
            .fechaPago(UPDATED_FECHA_PAGO)
            .fechaConfirmadoPago(UPDATED_FECHA_CONFIRMADO_PAGO)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .estado(UPDATED_ESTADO)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .whatsapp(UPDATED_WHATSAPP)
            .puntajeComprador(UPDATED_PUNTAJE_COMPRADOR)
            .puntajeVendedor(UPDATED_PUNTAJE_VENDEDOR)
            .fechaTermino(UPDATED_FECHA_TERMINO)
            .razonTermino(UPDATED_RAZON_TERMINO)
            .montoTotalCompra(UPDATED_MONTO_TOTAL_COMPRA)
            .montoDelivery(UPDATED_MONTO_DELIVERY);
        return carrito;
    }

    @BeforeEach
    public void initTest() {
        carrito = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarrito() throws Exception {
        int databaseSizeBeforeCreate = carritoRepository.findAll().size();

        // Create the Carrito
        CarritoDTO carritoDTO = carritoMapper.toDto(carrito);
        restCarritoMockMvc.perform(post("/api/carritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoDTO)))
            .andExpect(status().isCreated());

        // Validate the Carrito in the database
        List<Carrito> carritoList = carritoRepository.findAll();
        assertThat(carritoList).hasSize(databaseSizeBeforeCreate + 1);
        Carrito testCarrito = carritoList.get(carritoList.size() - 1);
        assertThat(testCarrito.getFechaPedido()).isEqualTo(DEFAULT_FECHA_PEDIDO);
        assertThat(testCarrito.getFechaPago()).isEqualTo(DEFAULT_FECHA_PAGO);
        assertThat(testCarrito.getFechaConfirmadoPago()).isEqualTo(DEFAULT_FECHA_CONFIRMADO_PAGO);
        assertThat(testCarrito.getFechaEntrega()).isEqualTo(DEFAULT_FECHA_ENTREGA);
        assertThat(testCarrito.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCarrito.getTelefonoContacto()).isEqualTo(DEFAULT_TELEFONO_CONTACTO);
        assertThat(testCarrito.isWhatsapp()).isEqualTo(DEFAULT_WHATSAPP);
        assertThat(testCarrito.getPuntajeComprador()).isEqualTo(DEFAULT_PUNTAJE_COMPRADOR);
        assertThat(testCarrito.getPuntajeVendedor()).isEqualTo(DEFAULT_PUNTAJE_VENDEDOR);
        assertThat(testCarrito.getFechaTermino()).isEqualTo(DEFAULT_FECHA_TERMINO);
        assertThat(testCarrito.getRazonTermino()).isEqualTo(DEFAULT_RAZON_TERMINO);
        assertThat(testCarrito.getMontoTotalCompra()).isEqualTo(DEFAULT_MONTO_TOTAL_COMPRA);
        assertThat(testCarrito.getMontoDelivery()).isEqualTo(DEFAULT_MONTO_DELIVERY);
    }

    @Test
    @Transactional
    public void createCarritoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carritoRepository.findAll().size();

        // Create the Carrito with an existing ID
        carrito.setId(1L);
        CarritoDTO carritoDTO = carritoMapper.toDto(carrito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarritoMockMvc.perform(post("/api/carritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carrito in the database
        List<Carrito> carritoList = carritoRepository.findAll();
        assertThat(carritoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCarritos() throws Exception {
        // Initialize the database
        carritoRepository.saveAndFlush(carrito);

        // Get all the carritoList
        restCarritoMockMvc.perform(get("/api/carritos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrito.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaPedido").value(hasItem(sameInstant(DEFAULT_FECHA_PEDIDO))))
            .andExpect(jsonPath("$.[*].fechaPago").value(hasItem(sameInstant(DEFAULT_FECHA_PAGO))))
            .andExpect(jsonPath("$.[*].fechaConfirmadoPago").value(hasItem(sameInstant(DEFAULT_FECHA_CONFIRMADO_PAGO))))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(sameInstant(DEFAULT_FECHA_ENTREGA))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].telefonoContacto").value(hasItem(DEFAULT_TELEFONO_CONTACTO)))
            .andExpect(jsonPath("$.[*].whatsapp").value(hasItem(DEFAULT_WHATSAPP.booleanValue())))
            .andExpect(jsonPath("$.[*].puntajeComprador").value(hasItem(DEFAULT_PUNTAJE_COMPRADOR.doubleValue())))
            .andExpect(jsonPath("$.[*].puntajeVendedor").value(hasItem(DEFAULT_PUNTAJE_VENDEDOR.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaTermino").value(hasItem(sameInstant(DEFAULT_FECHA_TERMINO))))
            .andExpect(jsonPath("$.[*].razonTermino").value(hasItem(DEFAULT_RAZON_TERMINO)))
            .andExpect(jsonPath("$.[*].montoTotalCompra").value(hasItem(DEFAULT_MONTO_TOTAL_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].montoDelivery").value(hasItem(DEFAULT_MONTO_DELIVERY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCarrito() throws Exception {
        // Initialize the database
        carritoRepository.saveAndFlush(carrito);

        // Get the carrito
        restCarritoMockMvc.perform(get("/api/carritos/{id}", carrito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carrito.getId().intValue()))
            .andExpect(jsonPath("$.fechaPedido").value(sameInstant(DEFAULT_FECHA_PEDIDO)))
            .andExpect(jsonPath("$.fechaPago").value(sameInstant(DEFAULT_FECHA_PAGO)))
            .andExpect(jsonPath("$.fechaConfirmadoPago").value(sameInstant(DEFAULT_FECHA_CONFIRMADO_PAGO)))
            .andExpect(jsonPath("$.fechaEntrega").value(sameInstant(DEFAULT_FECHA_ENTREGA)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.telefonoContacto").value(DEFAULT_TELEFONO_CONTACTO))
            .andExpect(jsonPath("$.whatsapp").value(DEFAULT_WHATSAPP.booleanValue()))
            .andExpect(jsonPath("$.puntajeComprador").value(DEFAULT_PUNTAJE_COMPRADOR.doubleValue()))
            .andExpect(jsonPath("$.puntajeVendedor").value(DEFAULT_PUNTAJE_VENDEDOR.doubleValue()))
            .andExpect(jsonPath("$.fechaTermino").value(sameInstant(DEFAULT_FECHA_TERMINO)))
            .andExpect(jsonPath("$.razonTermino").value(DEFAULT_RAZON_TERMINO))
            .andExpect(jsonPath("$.montoTotalCompra").value(DEFAULT_MONTO_TOTAL_COMPRA.doubleValue()))
            .andExpect(jsonPath("$.montoDelivery").value(DEFAULT_MONTO_DELIVERY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarrito() throws Exception {
        // Get the carrito
        restCarritoMockMvc.perform(get("/api/carritos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarrito() throws Exception {
        // Initialize the database
        carritoRepository.saveAndFlush(carrito);

        int databaseSizeBeforeUpdate = carritoRepository.findAll().size();

        // Update the carrito
        Carrito updatedCarrito = carritoRepository.findById(carrito.getId()).get();
        // Disconnect from session so that the updates on updatedCarrito are not directly saved in db
        em.detach(updatedCarrito);
        updatedCarrito
            .fechaPedido(UPDATED_FECHA_PEDIDO)
            .fechaPago(UPDATED_FECHA_PAGO)
            .fechaConfirmadoPago(UPDATED_FECHA_CONFIRMADO_PAGO)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .estado(UPDATED_ESTADO)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .whatsapp(UPDATED_WHATSAPP)
            .puntajeComprador(UPDATED_PUNTAJE_COMPRADOR)
            .puntajeVendedor(UPDATED_PUNTAJE_VENDEDOR)
            .fechaTermino(UPDATED_FECHA_TERMINO)
            .razonTermino(UPDATED_RAZON_TERMINO)
            .montoTotalCompra(UPDATED_MONTO_TOTAL_COMPRA)
            .montoDelivery(UPDATED_MONTO_DELIVERY);
        CarritoDTO carritoDTO = carritoMapper.toDto(updatedCarrito);

        restCarritoMockMvc.perform(put("/api/carritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoDTO)))
            .andExpect(status().isOk());

        // Validate the Carrito in the database
        List<Carrito> carritoList = carritoRepository.findAll();
        assertThat(carritoList).hasSize(databaseSizeBeforeUpdate);
        Carrito testCarrito = carritoList.get(carritoList.size() - 1);
        assertThat(testCarrito.getFechaPedido()).isEqualTo(UPDATED_FECHA_PEDIDO);
        assertThat(testCarrito.getFechaPago()).isEqualTo(UPDATED_FECHA_PAGO);
        assertThat(testCarrito.getFechaConfirmadoPago()).isEqualTo(UPDATED_FECHA_CONFIRMADO_PAGO);
        assertThat(testCarrito.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testCarrito.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCarrito.getTelefonoContacto()).isEqualTo(UPDATED_TELEFONO_CONTACTO);
        assertThat(testCarrito.isWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
        assertThat(testCarrito.getPuntajeComprador()).isEqualTo(UPDATED_PUNTAJE_COMPRADOR);
        assertThat(testCarrito.getPuntajeVendedor()).isEqualTo(UPDATED_PUNTAJE_VENDEDOR);
        assertThat(testCarrito.getFechaTermino()).isEqualTo(UPDATED_FECHA_TERMINO);
        assertThat(testCarrito.getRazonTermino()).isEqualTo(UPDATED_RAZON_TERMINO);
        assertThat(testCarrito.getMontoTotalCompra()).isEqualTo(UPDATED_MONTO_TOTAL_COMPRA);
        assertThat(testCarrito.getMontoDelivery()).isEqualTo(UPDATED_MONTO_DELIVERY);
    }

    @Test
    @Transactional
    public void updateNonExistingCarrito() throws Exception {
        int databaseSizeBeforeUpdate = carritoRepository.findAll().size();

        // Create the Carrito
        CarritoDTO carritoDTO = carritoMapper.toDto(carrito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarritoMockMvc.perform(put("/api/carritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carrito in the database
        List<Carrito> carritoList = carritoRepository.findAll();
        assertThat(carritoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarrito() throws Exception {
        // Initialize the database
        carritoRepository.saveAndFlush(carrito);

        int databaseSizeBeforeDelete = carritoRepository.findAll().size();

        // Delete the carrito
        restCarritoMockMvc.perform(delete("/api/carritos/{id}", carrito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Carrito> carritoList = carritoRepository.findAll();
        assertThat(carritoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carrito.class);
        Carrito carrito1 = new Carrito();
        carrito1.setId(1L);
        Carrito carrito2 = new Carrito();
        carrito2.setId(carrito1.getId());
        assertThat(carrito1).isEqualTo(carrito2);
        carrito2.setId(2L);
        assertThat(carrito1).isNotEqualTo(carrito2);
        carrito1.setId(null);
        assertThat(carrito1).isNotEqualTo(carrito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarritoDTO.class);
        CarritoDTO carritoDTO1 = new CarritoDTO();
        carritoDTO1.setId(1L);
        CarritoDTO carritoDTO2 = new CarritoDTO();
        assertThat(carritoDTO1).isNotEqualTo(carritoDTO2);
        carritoDTO2.setId(carritoDTO1.getId());
        assertThat(carritoDTO1).isEqualTo(carritoDTO2);
        carritoDTO2.setId(2L);
        assertThat(carritoDTO1).isNotEqualTo(carritoDTO2);
        carritoDTO1.setId(null);
        assertThat(carritoDTO1).isNotEqualTo(carritoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carritoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carritoMapper.fromId(null)).isNull();
    }
}
