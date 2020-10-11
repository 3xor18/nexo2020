package com.nexo.app.web.rest;

import com.nexo.app.NexoApp;
import com.nexo.app.domain.Producto;
import com.nexo.app.repository.ProductoRepository;
import com.nexo.app.service.ProductoService;
import com.nexo.app.service.dto.ProductoDTO;
import com.nexo.app.service.mapper.ProductoMapper;
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
import java.time.LocalDate;
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
 * Integration tests for the {@link ProductoResource} REST controller.
 */
@SpringBootTest(classes = NexoApp.class)
public class ProductoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_BARRA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_BARRA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Double DEFAULT_CANTIDAD_DISPONIBLE = 1D;
    private static final Double UPDATED_CANTIDAD_DISPONIBLE = 2D;

    private static final Double DEFAULT_ALERTA_MINIMO = 1D;
    private static final Double UPDATED_ALERTA_MINIMO = 2D;

    private static final LocalDate DEFAULT_FECHA_VENCIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENCIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_FECHA_BD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_BD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO_COMPRA_BRUTO = 1D;
    private static final Double UPDATED_PRECIO_COMPRA_BRUTO = 2D;

    private static final Double DEFAULT_PRECIO_VENTA_TOTAL_DETAL = 1D;
    private static final Double UPDATED_PRECIO_VENTA_TOTAL_DETAL = 2D;

    private static final Double DEFAULT_PRECIO_VENTA_TOTAL_MAYOR = 1D;
    private static final Double UPDATED_PRECIO_VENTA_TOTAL_MAYOR = 2D;

    private static final Double DEFAULT_UNIDAD_MEDIDA_VENDIDA = 1D;
    private static final Double UPDATED_UNIDAD_MEDIDA_VENDIDA = 2D;

    private static final Double DEFAULT_PRECIO_ALMAYOR_DESPUESDE = 1D;
    private static final Double UPDATED_PRECIO_ALMAYOR_DESPUESDE = 2D;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private ProductoService productoService;

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

    private MockMvc restProductoMockMvc;

    private Producto producto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoResource productoResource = new ProductoResource(productoService);
        this.restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
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
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
            .nombre(DEFAULT_NOMBRE)
            .codigo(DEFAULT_CODIGO)
            .codigoBarra(DEFAULT_CODIGO_BARRA)
            .descripcion(DEFAULT_DESCRIPCION)
            .cantidadDisponible(DEFAULT_CANTIDAD_DISPONIBLE)
            .alertaMinimo(DEFAULT_ALERTA_MINIMO)
            .fechaVencimiento(DEFAULT_FECHA_VENCIMIENTO)
            .fechaBd(DEFAULT_FECHA_BD)
            .estado(DEFAULT_ESTADO)
            .precioCompraBruto(DEFAULT_PRECIO_COMPRA_BRUTO)
            .precioVentaTotalDetal(DEFAULT_PRECIO_VENTA_TOTAL_DETAL)
            .precioVentaTotalMayor(DEFAULT_PRECIO_VENTA_TOTAL_MAYOR)
            .unidadMedidaVendida(DEFAULT_UNIDAD_MEDIDA_VENDIDA)
            .precioAlmayorDespuesde(DEFAULT_PRECIO_ALMAYOR_DESPUESDE);
        return producto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createUpdatedEntity(EntityManager em) {
        Producto producto = new Producto()
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO)
            .codigoBarra(UPDATED_CODIGO_BARRA)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidadDisponible(UPDATED_CANTIDAD_DISPONIBLE)
            .alertaMinimo(UPDATED_ALERTA_MINIMO)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .fechaBd(UPDATED_FECHA_BD)
            .estado(UPDATED_ESTADO)
            .precioCompraBruto(UPDATED_PRECIO_COMPRA_BRUTO)
            .precioVentaTotalDetal(UPDATED_PRECIO_VENTA_TOTAL_DETAL)
            .precioVentaTotalMayor(UPDATED_PRECIO_VENTA_TOTAL_MAYOR)
            .unidadMedidaVendida(UPDATED_UNIDAD_MEDIDA_VENDIDA)
            .precioAlmayorDespuesde(UPDATED_PRECIO_ALMAYOR_DESPUESDE);
        return producto;
    }

    @BeforeEach
    public void initTest() {
        producto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProducto.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testProducto.getCodigoBarra()).isEqualTo(DEFAULT_CODIGO_BARRA);
        assertThat(testProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProducto.getCantidadDisponible()).isEqualTo(DEFAULT_CANTIDAD_DISPONIBLE);
        assertThat(testProducto.getAlertaMinimo()).isEqualTo(DEFAULT_ALERTA_MINIMO);
        assertThat(testProducto.getFechaVencimiento()).isEqualTo(DEFAULT_FECHA_VENCIMIENTO);
        assertThat(testProducto.getFechaBd()).isEqualTo(DEFAULT_FECHA_BD);
        assertThat(testProducto.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testProducto.getPrecioCompraBruto()).isEqualTo(DEFAULT_PRECIO_COMPRA_BRUTO);
        assertThat(testProducto.getPrecioVentaTotalDetal()).isEqualTo(DEFAULT_PRECIO_VENTA_TOTAL_DETAL);
        assertThat(testProducto.getPrecioVentaTotalMayor()).isEqualTo(DEFAULT_PRECIO_VENTA_TOTAL_MAYOR);
        assertThat(testProducto.getUnidadMedidaVendida()).isEqualTo(DEFAULT_UNIDAD_MEDIDA_VENDIDA);
        assertThat(testProducto.getPrecioAlmayorDespuesde()).isEqualTo(DEFAULT_PRECIO_ALMAYOR_DESPUESDE);
    }

    @Test
    @Transactional
    public void createProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto with an existing ID
        producto.setId(1L);
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].codigoBarra").value(hasItem(DEFAULT_CODIGO_BARRA)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].cantidadDisponible").value(hasItem(DEFAULT_CANTIDAD_DISPONIBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].alertaMinimo").value(hasItem(DEFAULT_ALERTA_MINIMO.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaVencimiento").value(hasItem(DEFAULT_FECHA_VENCIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].fechaBd").value(hasItem(sameInstant(DEFAULT_FECHA_BD))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].precioCompraBruto").value(hasItem(DEFAULT_PRECIO_COMPRA_BRUTO.doubleValue())))
            .andExpect(jsonPath("$.[*].precioVentaTotalDetal").value(hasItem(DEFAULT_PRECIO_VENTA_TOTAL_DETAL.doubleValue())))
            .andExpect(jsonPath("$.[*].precioVentaTotalMayor").value(hasItem(DEFAULT_PRECIO_VENTA_TOTAL_MAYOR.doubleValue())))
            .andExpect(jsonPath("$.[*].unidadMedidaVendida").value(hasItem(DEFAULT_UNIDAD_MEDIDA_VENDIDA.doubleValue())))
            .andExpect(jsonPath("$.[*].precioAlmayorDespuesde").value(hasItem(DEFAULT_PRECIO_ALMAYOR_DESPUESDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.codigoBarra").value(DEFAULT_CODIGO_BARRA))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.cantidadDisponible").value(DEFAULT_CANTIDAD_DISPONIBLE.doubleValue()))
            .andExpect(jsonPath("$.alertaMinimo").value(DEFAULT_ALERTA_MINIMO.doubleValue()))
            .andExpect(jsonPath("$.fechaVencimiento").value(DEFAULT_FECHA_VENCIMIENTO.toString()))
            .andExpect(jsonPath("$.fechaBd").value(sameInstant(DEFAULT_FECHA_BD)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.precioCompraBruto").value(DEFAULT_PRECIO_COMPRA_BRUTO.doubleValue()))
            .andExpect(jsonPath("$.precioVentaTotalDetal").value(DEFAULT_PRECIO_VENTA_TOTAL_DETAL.doubleValue()))
            .andExpect(jsonPath("$.precioVentaTotalMayor").value(DEFAULT_PRECIO_VENTA_TOTAL_MAYOR.doubleValue()))
            .andExpect(jsonPath("$.unidadMedidaVendida").value(DEFAULT_UNIDAD_MEDIDA_VENDIDA.doubleValue()))
            .andExpect(jsonPath("$.precioAlmayorDespuesde").value(DEFAULT_PRECIO_ALMAYOR_DESPUESDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findById(producto.getId()).get();
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO)
            .codigoBarra(UPDATED_CODIGO_BARRA)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidadDisponible(UPDATED_CANTIDAD_DISPONIBLE)
            .alertaMinimo(UPDATED_ALERTA_MINIMO)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .fechaBd(UPDATED_FECHA_BD)
            .estado(UPDATED_ESTADO)
            .precioCompraBruto(UPDATED_PRECIO_COMPRA_BRUTO)
            .precioVentaTotalDetal(UPDATED_PRECIO_VENTA_TOTAL_DETAL)
            .precioVentaTotalMayor(UPDATED_PRECIO_VENTA_TOTAL_MAYOR)
            .unidadMedidaVendida(UPDATED_UNIDAD_MEDIDA_VENDIDA)
            .precioAlmayorDespuesde(UPDATED_PRECIO_ALMAYOR_DESPUESDE);
        ProductoDTO productoDTO = productoMapper.toDto(updatedProducto);

        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProducto.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testProducto.getCodigoBarra()).isEqualTo(UPDATED_CODIGO_BARRA);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProducto.getCantidadDisponible()).isEqualTo(UPDATED_CANTIDAD_DISPONIBLE);
        assertThat(testProducto.getAlertaMinimo()).isEqualTo(UPDATED_ALERTA_MINIMO);
        assertThat(testProducto.getFechaVencimiento()).isEqualTo(UPDATED_FECHA_VENCIMIENTO);
        assertThat(testProducto.getFechaBd()).isEqualTo(UPDATED_FECHA_BD);
        assertThat(testProducto.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testProducto.getPrecioCompraBruto()).isEqualTo(UPDATED_PRECIO_COMPRA_BRUTO);
        assertThat(testProducto.getPrecioVentaTotalDetal()).isEqualTo(UPDATED_PRECIO_VENTA_TOTAL_DETAL);
        assertThat(testProducto.getPrecioVentaTotalMayor()).isEqualTo(UPDATED_PRECIO_VENTA_TOTAL_MAYOR);
        assertThat(testProducto.getUnidadMedidaVendida()).isEqualTo(UPDATED_UNIDAD_MEDIDA_VENDIDA);
        assertThat(testProducto.getPrecioAlmayorDespuesde()).isEqualTo(UPDATED_PRECIO_ALMAYOR_DESPUESDE);
    }

    @Test
    @Transactional
    public void updateNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Delete the producto
        restProductoMockMvc.perform(delete("/api/productos/{id}", producto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Producto.class);
        Producto producto1 = new Producto();
        producto1.setId(1L);
        Producto producto2 = new Producto();
        producto2.setId(producto1.getId());
        assertThat(producto1).isEqualTo(producto2);
        producto2.setId(2L);
        assertThat(producto1).isNotEqualTo(producto2);
        producto1.setId(null);
        assertThat(producto1).isNotEqualTo(producto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoDTO.class);
        ProductoDTO productoDTO1 = new ProductoDTO();
        productoDTO1.setId(1L);
        ProductoDTO productoDTO2 = new ProductoDTO();
        assertThat(productoDTO1).isNotEqualTo(productoDTO2);
        productoDTO2.setId(productoDTO1.getId());
        assertThat(productoDTO1).isEqualTo(productoDTO2);
        productoDTO2.setId(2L);
        assertThat(productoDTO1).isNotEqualTo(productoDTO2);
        productoDTO1.setId(null);
        assertThat(productoDTO1).isNotEqualTo(productoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productoMapper.fromId(null)).isNull();
    }
}
