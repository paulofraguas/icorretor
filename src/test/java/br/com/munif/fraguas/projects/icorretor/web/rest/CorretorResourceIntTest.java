package br.com.munif.fraguas.projects.icorretor.web.rest;

import br.com.munif.framework.vicente.core.VicThreadScope;


import br.com.munif.fraguas.projects.icorretor.IcorretorApp;

import java.util.ArrayList;
import br.com.munif.fraguas.projects.icorretor.domain.Corretor;
import br.com.munif.fraguas.projects.icorretor.repository.CorretorRepository;
import br.com.munif.fraguas.projects.icorretor.service.CorretorService;
import br.com.munif.fraguas.projects.icorretor.service.dto.CorretorDTO;
import br.com.munif.fraguas.projects.icorretor.service.mapper.CorretorMapper;
import br.com.munif.fraguas.projects.icorretor.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CorretorResource REST controller.
 *
 * @see CorretorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcorretorApp.class)
public class CorretorResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    @Autowired
    private CorretorRepository corretorRepository;

    @Autowired
    private CorretorMapper corretorMapper;

    @Autowired
    private CorretorService corretorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorretorMockMvc;

    private Corretor corretor;

    @Before
    public void setup() {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.ip.set("127.0.0.1");
        MockitoAnnotations.initMocks(this);
        final CorretorResource corretorResource = new CorretorResource(corretorService);
        this.restCorretorMockMvc = MockMvcBuilders.standaloneSetup(corretorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corretor createEntity(EntityManager em) {
        Corretor corretor = new Corretor()
            .nome(DEFAULT_NOME)
            .telefone(DEFAULT_TELEFONE);
        return corretor;
    }

    private List<Corretor> findAll() {
        Iterable<Corretor> findAll = corretorRepository.findAll();

        List<Corretor> result = new ArrayList<>();
        for (Corretor r : findAll) {
            result.add(r);
        }

        return result;
    }

    private int count(){
        return (int) corretorRepository.count();
    }




    @Before
    public void initTest() {
        corretor = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorretor() throws Exception {
        int databaseSizeBeforeCreate = count();

        // Create the Corretor
        CorretorDTO corretorDTO = corretorMapper.toDto(corretor);
        restCorretorMockMvc.perform(post("/api/corretors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corretorDTO)))
            .andExpect(status().isCreated());

        // Validate the Corretor in the database
        List<Corretor> corretorList = findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeCreate + 1);
        Corretor testCorretor = corretorList.get(corretorList.size() - 1);
        assertThat(testCorretor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCorretor.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    public void createCorretorWithExistingId() throws Exception {
        corretorRepository.saveAndFlush(corretor);
        int databaseSizeBeforeCreate = findAll().size();


        // An entity with an existing ID cannot be created, so this API call must fail
        restCorretorMockMvc.perform(post("/api/corretors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corretor)))
            .andExpect(status().isBadRequest());

        // Validate the Corretor in the database
        List<Corretor> corretorList = findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorretors() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);

        // Get all the corretorList
        restCorretorMockMvc.perform(get("/api/corretors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.values.[*].id").value(hasItem(corretor.getId())))
            .andExpect(jsonPath("$.values.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.values.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())));
    }

    @Test
    @Transactional
    public void getCorretor() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);

        // Get the corretor
        restCorretorMockMvc.perform(get("/api/corretors/{id}", corretor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corretor.getId()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorretor() throws Exception {
        // Get the corretor
        restCorretorMockMvc.perform(get("/api/corretors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorretor() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);
        int databaseSizeBeforeUpdate = findAll().size();

        // Update the corretor
        Corretor updatedCorretor = corretorRepository.findOne(corretor.getId());
        updatedCorretor
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE);
        CorretorDTO corretorDTO = corretorMapper.toDto(updatedCorretor);

        restCorretorMockMvc.perform(put("/api/corretors/"+corretor.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCorretor)))
            .andExpect(status().isOk());

        // Validate the Corretor in the database
        List<Corretor> corretorList = findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate);
        Corretor testCorretor = corretorList.get(corretorList.size() - 1);
        assertThat(testCorretor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCorretor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void updateNonExistingCorretor() throws Exception {
        int databaseSizeBeforeUpdate = findAll().size();

        // Create the Corretor
        CorretorDTO corretorDTO = corretorMapper.toDto(corretor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorretorMockMvc.perform(put("/api/corretors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corretorDTO)))
            .andExpect(status().isCreated());

        // Validate the Corretor in the database
        List<Corretor> corretorList = findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorretor() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);
        int databaseSizeBeforeDelete = findAll().size();

        // Get the corretor
        restCorretorMockMvc.perform(delete("/api/corretors/{id}", corretor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Corretor> corretorList = findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Corretor.class);
        Corretor corretor1 = new Corretor();
        corretor1.setId("1L");
        Corretor corretor2 = new Corretor();
        corretor2.setId(corretor1.getId());
        assertThat(corretor1).isEqualTo(corretor2);
        corretor2.setId("2L");
        assertThat(corretor1).isNotEqualTo(corretor2);
        corretor1.setId(null);
        assertThat(corretor1).isNotEqualTo(corretor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorretorDTO.class);
        CorretorDTO corretorDTO1 = new CorretorDTO();
        corretorDTO1.setId("1L");
        CorretorDTO corretorDTO2 = new CorretorDTO();
        assertThat(corretorDTO1).isNotEqualTo(corretorDTO2);
        corretorDTO2.setId(corretorDTO1.getId());
        assertThat(corretorDTO1).isEqualTo(corretorDTO2);
        corretorDTO2.setId("2L");
        assertThat(corretorDTO1).isNotEqualTo(corretorDTO2);
        corretorDTO1.setId(null);
        assertThat(corretorDTO1).isNotEqualTo(corretorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(corretorMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(corretorMapper.fromId(null)).isNull();
    }
}
