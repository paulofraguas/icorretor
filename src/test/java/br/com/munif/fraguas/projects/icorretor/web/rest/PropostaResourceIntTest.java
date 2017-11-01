package br.com.munif.fraguas.projects.icorretor.web.rest;

import br.com.munif.framework.vicente.core.VicThreadScope;


import br.com.munif.fraguas.projects.icorretor.IcorretorApp;

import java.util.ArrayList;
import br.com.munif.fraguas.projects.icorretor.domain.Proposta;
import br.com.munif.fraguas.projects.icorretor.repository.PropostaRepository;
import br.com.munif.fraguas.projects.icorretor.service.PropostaService;
import br.com.munif.fraguas.projects.icorretor.service.dto.PropostaDTO;
import br.com.munif.fraguas.projects.icorretor.service.mapper.PropostaMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PropostaResource REST controller.
 *
 * @see PropostaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcorretorApp.class)
public class PropostaResourceIntTest {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final LocalDate DEFAULT_DIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DIA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private PropostaMapper propostaMapper;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPropostaMockMvc;

    private Proposta proposta;

    @Before
    public void setup() {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.ip.set("127.0.0.1");
        MockitoAnnotations.initMocks(this);
        final PropostaResource propostaResource = new PropostaResource(propostaService);
        this.restPropostaMockMvc = MockMvcBuilders.standaloneSetup(propostaResource)
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
    public static Proposta createEntity(EntityManager em) {
        Proposta proposta = new Proposta()
            .valor(DEFAULT_VALOR)
            .dia(DEFAULT_DIA)
            .observacao(DEFAULT_OBSERVACAO);
        return proposta;
    }

    private List<Proposta> findAll() {
        Iterable<Proposta> findAll = propostaRepository.findAll();

        List<Proposta> result = new ArrayList<>();
        for (Proposta r : findAll) {
            result.add(r);
        }

        return result;
    }

    private int count(){
        return (int) propostaRepository.count();
    }




    @Before
    public void initTest() {
        proposta = createEntity(em);
    }

    @Test
    @Transactional
    public void createProposta() throws Exception {
        int databaseSizeBeforeCreate = count();

        // Create the Proposta
        PropostaDTO propostaDTO = propostaMapper.toDto(proposta);
        restPropostaMockMvc.perform(post("/api/propostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propostaDTO)))
            .andExpect(status().isCreated());

        // Validate the Proposta in the database
        List<Proposta> propostaList = findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeCreate + 1);
        Proposta testProposta = propostaList.get(propostaList.size() - 1);
        assertThat(testProposta.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testProposta.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testProposta.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    public void createPropostaWithExistingId() throws Exception {
        propostaRepository.saveAndFlush(proposta);
        int databaseSizeBeforeCreate = findAll().size();


        // An entity with an existing ID cannot be created, so this API call must fail
        restPropostaMockMvc.perform(post("/api/propostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposta)))
            .andExpect(status().isBadRequest());

        // Validate the Proposta in the database
        List<Proposta> propostaList = findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPropostas() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);

        // Get all the propostaList
        restPropostaMockMvc.perform(get("/api/propostas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.values.[*].id").value(hasItem(proposta.getId())))
            .andExpect(jsonPath("$.values.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.values.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
            .andExpect(jsonPath("$.values.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));
    }

    @Test
    @Transactional
    public void getProposta() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);

        // Get the proposta
        restPropostaMockMvc.perform(get("/api/propostas/{id}", proposta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proposta.getId()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProposta() throws Exception {
        // Get the proposta
        restPropostaMockMvc.perform(get("/api/propostas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProposta() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);
        int databaseSizeBeforeUpdate = findAll().size();

        // Update the proposta
        Proposta updatedProposta = propostaRepository.findOne(proposta.getId());
        updatedProposta
            .valor(UPDATED_VALOR)
            .dia(UPDATED_DIA)
            .observacao(UPDATED_OBSERVACAO);
        PropostaDTO propostaDTO = propostaMapper.toDto(updatedProposta);

        restPropostaMockMvc.perform(put("/api/propostas/"+proposta.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProposta)))
            .andExpect(status().isOk());

        // Validate the Proposta in the database
        List<Proposta> propostaList = findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
        Proposta testProposta = propostaList.get(propostaList.size() - 1);
        assertThat(testProposta.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testProposta.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testProposta.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingProposta() throws Exception {
        int databaseSizeBeforeUpdate = findAll().size();

        // Create the Proposta
        PropostaDTO propostaDTO = propostaMapper.toDto(proposta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPropostaMockMvc.perform(put("/api/propostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propostaDTO)))
            .andExpect(status().isCreated());

        // Validate the Proposta in the database
        List<Proposta> propostaList = findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProposta() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);
        int databaseSizeBeforeDelete = findAll().size();

        // Get the proposta
        restPropostaMockMvc.perform(delete("/api/propostas/{id}", proposta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Proposta> propostaList = findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proposta.class);
        Proposta proposta1 = new Proposta();
        proposta1.setId("1L");
        Proposta proposta2 = new Proposta();
        proposta2.setId(proposta1.getId());
        assertThat(proposta1).isEqualTo(proposta2);
        proposta2.setId("2L");
        assertThat(proposta1).isNotEqualTo(proposta2);
        proposta1.setId(null);
        assertThat(proposta1).isNotEqualTo(proposta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropostaDTO.class);
        PropostaDTO propostaDTO1 = new PropostaDTO();
        propostaDTO1.setId("1L");
        PropostaDTO propostaDTO2 = new PropostaDTO();
        assertThat(propostaDTO1).isNotEqualTo(propostaDTO2);
        propostaDTO2.setId(propostaDTO1.getId());
        assertThat(propostaDTO1).isEqualTo(propostaDTO2);
        propostaDTO2.setId("2L");
        assertThat(propostaDTO1).isNotEqualTo(propostaDTO2);
        propostaDTO1.setId(null);
        assertThat(propostaDTO1).isNotEqualTo(propostaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propostaMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(propostaMapper.fromId(null)).isNull();
    }
}
