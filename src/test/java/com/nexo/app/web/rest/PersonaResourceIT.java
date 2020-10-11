package com.nexo.app.web.rest;

import com.nexo.app.NexoApp;
import com.nexo.app.domain.Persona;
import com.nexo.app.repository.PersonaRepository;
import com.nexo.app.service.PersonaService;
import com.nexo.app.service.dto.PersonaDTO;
import com.nexo.app.service.mapper.PersonaMapper;
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
import java.time.ZoneId;
import java.util.List;

import static com.nexo.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PersonaResource} REST controller.
 */
@SpringBootTest(classes = NexoApp.class)
public class PersonaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_IDENTIDAD = "AAAAAAAAAA";
    private static final String UPDATED_DOC_IDENTIDAD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NAC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NAC = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_NACIONALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NACIONALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NATURAL = false;
    private static final Boolean UPDATED_NATURAL = true;

    private static final String DEFAULT_NOMBRE_COMERCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COMERCIAL = "BBBBBBBBBB";

    private static final Double DEFAULT_SCORE_COMPRADOR = 1D;
    private static final Double UPDATED_SCORE_COMPRADOR = 2D;

    private static final Double DEFAULT_SCORE_VENDEDOR = 1D;
    private static final Double UPDATED_SCORE_VENDEDOR = 2D;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaMapper personaMapper;

    @Autowired
    private PersonaService personaService;

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

    private MockMvc restPersonaMockMvc;

    private Persona persona;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonaResource personaResource = new PersonaResource(personaService);
        this.restPersonaMockMvc = MockMvcBuilders.standaloneSetup(personaResource)
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
    public static Persona createEntity(EntityManager em) {
        Persona persona = new Persona()
            .nombre(DEFAULT_NOMBRE)
            .apellidoPaterno(DEFAULT_APELLIDO_PATERNO)
            .apellidoMaterno(DEFAULT_APELLIDO_MATERNO)
            .docIdentidad(DEFAULT_DOC_IDENTIDAD)
            .fechaNac(DEFAULT_FECHA_NAC)
            .sexo(DEFAULT_SEXO)
            .nacionalidad(DEFAULT_NACIONALIDAD)
            .email(DEFAULT_EMAIL)
            .telefono(DEFAULT_TELEFONO)
            .estado(DEFAULT_ESTADO)
            .natural(DEFAULT_NATURAL)
            .nombreComercial(DEFAULT_NOMBRE_COMERCIAL)
            .scoreComprador(DEFAULT_SCORE_COMPRADOR)
            .scoreVendedor(DEFAULT_SCORE_VENDEDOR);
        return persona;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persona createUpdatedEntity(EntityManager em) {
        Persona persona = new Persona()
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .docIdentidad(UPDATED_DOC_IDENTIDAD)
            .fechaNac(UPDATED_FECHA_NAC)
            .sexo(UPDATED_SEXO)
            .nacionalidad(UPDATED_NACIONALIDAD)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .estado(UPDATED_ESTADO)
            .natural(UPDATED_NATURAL)
            .nombreComercial(UPDATED_NOMBRE_COMERCIAL)
            .scoreComprador(UPDATED_SCORE_COMPRADOR)
            .scoreVendedor(UPDATED_SCORE_VENDEDOR);
        return persona;
    }

    @BeforeEach
    public void initTest() {
        persona = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersona() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona
        PersonaDTO personaDTO = personaMapper.toDto(persona);
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personaDTO)))
            .andExpect(status().isCreated());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate + 1);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersona.getApellidoPaterno()).isEqualTo(DEFAULT_APELLIDO_PATERNO);
        assertThat(testPersona.getApellidoMaterno()).isEqualTo(DEFAULT_APELLIDO_MATERNO);
        assertThat(testPersona.getDocIdentidad()).isEqualTo(DEFAULT_DOC_IDENTIDAD);
        assertThat(testPersona.getFechaNac()).isEqualTo(DEFAULT_FECHA_NAC);
        assertThat(testPersona.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPersona.getNacionalidad()).isEqualTo(DEFAULT_NACIONALIDAD);
        assertThat(testPersona.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersona.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testPersona.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testPersona.isNatural()).isEqualTo(DEFAULT_NATURAL);
        assertThat(testPersona.getNombreComercial()).isEqualTo(DEFAULT_NOMBRE_COMERCIAL);
        assertThat(testPersona.getScoreComprador()).isEqualTo(DEFAULT_SCORE_COMPRADOR);
        assertThat(testPersona.getScoreVendedor()).isEqualTo(DEFAULT_SCORE_VENDEDOR);
    }

    @Test
    @Transactional
    public void createPersonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona with an existing ID
        persona.setId(1L);
        PersonaDTO personaDTO = personaMapper.toDto(persona);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPersonas() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList
        restPersonaMockMvc.perform(get("/api/personas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidoPaterno").value(hasItem(DEFAULT_APELLIDO_PATERNO)))
            .andExpect(jsonPath("$.[*].apellidoMaterno").value(hasItem(DEFAULT_APELLIDO_MATERNO)))
            .andExpect(jsonPath("$.[*].docIdentidad").value(hasItem(DEFAULT_DOC_IDENTIDAD)))
            .andExpect(jsonPath("$.[*].fechaNac").value(hasItem(DEFAULT_FECHA_NAC.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].nacionalidad").value(hasItem(DEFAULT_NACIONALIDAD)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].natural").value(hasItem(DEFAULT_NATURAL.booleanValue())))
            .andExpect(jsonPath("$.[*].nombreComercial").value(hasItem(DEFAULT_NOMBRE_COMERCIAL)))
            .andExpect(jsonPath("$.[*].scoreComprador").value(hasItem(DEFAULT_SCORE_COMPRADOR.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreVendedor").value(hasItem(DEFAULT_SCORE_VENDEDOR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", persona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(persona.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellidoPaterno").value(DEFAULT_APELLIDO_PATERNO))
            .andExpect(jsonPath("$.apellidoMaterno").value(DEFAULT_APELLIDO_MATERNO))
            .andExpect(jsonPath("$.docIdentidad").value(DEFAULT_DOC_IDENTIDAD))
            .andExpect(jsonPath("$.fechaNac").value(DEFAULT_FECHA_NAC.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.nacionalidad").value(DEFAULT_NACIONALIDAD))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.natural").value(DEFAULT_NATURAL.booleanValue()))
            .andExpect(jsonPath("$.nombreComercial").value(DEFAULT_NOMBRE_COMERCIAL))
            .andExpect(jsonPath("$.scoreComprador").value(DEFAULT_SCORE_COMPRADOR.doubleValue()))
            .andExpect(jsonPath("$.scoreVendedor").value(DEFAULT_SCORE_VENDEDOR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersona() throws Exception {
        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Update the persona
        Persona updatedPersona = personaRepository.findById(persona.getId()).get();
        // Disconnect from session so that the updates on updatedPersona are not directly saved in db
        em.detach(updatedPersona);
        updatedPersona
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .docIdentidad(UPDATED_DOC_IDENTIDAD)
            .fechaNac(UPDATED_FECHA_NAC)
            .sexo(UPDATED_SEXO)
            .nacionalidad(UPDATED_NACIONALIDAD)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .estado(UPDATED_ESTADO)
            .natural(UPDATED_NATURAL)
            .nombreComercial(UPDATED_NOMBRE_COMERCIAL)
            .scoreComprador(UPDATED_SCORE_COMPRADOR)
            .scoreVendedor(UPDATED_SCORE_VENDEDOR);
        PersonaDTO personaDTO = personaMapper.toDto(updatedPersona);

        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personaDTO)))
            .andExpect(status().isOk());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersona.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testPersona.getApellidoMaterno()).isEqualTo(UPDATED_APELLIDO_MATERNO);
        assertThat(testPersona.getDocIdentidad()).isEqualTo(UPDATED_DOC_IDENTIDAD);
        assertThat(testPersona.getFechaNac()).isEqualTo(UPDATED_FECHA_NAC);
        assertThat(testPersona.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPersona.getNacionalidad()).isEqualTo(UPDATED_NACIONALIDAD);
        assertThat(testPersona.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersona.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testPersona.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testPersona.isNatural()).isEqualTo(UPDATED_NATURAL);
        assertThat(testPersona.getNombreComercial()).isEqualTo(UPDATED_NOMBRE_COMERCIAL);
        assertThat(testPersona.getScoreComprador()).isEqualTo(UPDATED_SCORE_COMPRADOR);
        assertThat(testPersona.getScoreVendedor()).isEqualTo(UPDATED_SCORE_VENDEDOR);
    }

    @Test
    @Transactional
    public void updateNonExistingPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Create the Persona
        PersonaDTO personaDTO = personaMapper.toDto(persona);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        int databaseSizeBeforeDelete = personaRepository.findAll().size();

        // Delete the persona
        restPersonaMockMvc.perform(delete("/api/personas/{id}", persona.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Persona.class);
        Persona persona1 = new Persona();
        persona1.setId(1L);
        Persona persona2 = new Persona();
        persona2.setId(persona1.getId());
        assertThat(persona1).isEqualTo(persona2);
        persona2.setId(2L);
        assertThat(persona1).isNotEqualTo(persona2);
        persona1.setId(null);
        assertThat(persona1).isNotEqualTo(persona2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonaDTO.class);
        PersonaDTO personaDTO1 = new PersonaDTO();
        personaDTO1.setId(1L);
        PersonaDTO personaDTO2 = new PersonaDTO();
        assertThat(personaDTO1).isNotEqualTo(personaDTO2);
        personaDTO2.setId(personaDTO1.getId());
        assertThat(personaDTO1).isEqualTo(personaDTO2);
        personaDTO2.setId(2L);
        assertThat(personaDTO1).isNotEqualTo(personaDTO2);
        personaDTO1.setId(null);
        assertThat(personaDTO1).isNotEqualTo(personaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personaMapper.fromId(null)).isNull();
    }
}
