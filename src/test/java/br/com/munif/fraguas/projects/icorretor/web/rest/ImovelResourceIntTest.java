package br.com.munif.fraguas.projects.icorretor.web.rest;

import br.com.munif.framework.vicente.core.VicThreadScope;


import br.com.munif.fraguas.projects.icorretor.IcorretorApp;

import java.util.ArrayList;
import br.com.munif.fraguas.projects.icorretor.domain.Imovel;
import br.com.munif.fraguas.projects.icorretor.repository.ImovelRepository;
import br.com.munif.fraguas.projects.icorretor.service.ImovelService;
import br.com.munif.fraguas.projects.icorretor.service.dto.ImovelDTO;
import br.com.munif.fraguas.projects.icorretor.service.mapper.ImovelMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImovelResource REST controller.
 *
 * @see ImovelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcorretorApp.class)
public class ImovelResourceIntTest {

    private static final String DEFAULT_CODIGO_SUB_100 = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_SUB_100 = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRECO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AREA_CONSTRUIDA = new BigDecimal(1);
    private static final BigDecimal UPDATED_AREA_CONSTRUIDA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AREA_TERRENO = new BigDecimal(1);
    private static final BigDecimal UPDATED_AREA_TERRENO = new BigDecimal(2);

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private ImovelMapper imovelMapper;

    @Autowired
    private ImovelService imovelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImovelMockMvc;

    private Imovel imovel;

    @Before
    public void setup() {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.ip.set("127.0.0.1");
        MockitoAnnotations.initMocks(this);
        final ImovelResource imovelResource = new ImovelResource(imovelService);
        this.restImovelMockMvc = MockMvcBuilders.standaloneSetup(imovelResource)
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
    public static Imovel createEntity(EntityManager em) {
        Imovel imovel = new Imovel()
            .codigoSub100(DEFAULT_CODIGO_SUB_100)
            .endereco(DEFAULT_ENDERECO)
            .bairro(DEFAULT_BAIRRO)
            .descricao(DEFAULT_DESCRICAO)
            .tipo(DEFAULT_TIPO)
            .preco(DEFAULT_PRECO)
            .areaConstruida(DEFAULT_AREA_CONSTRUIDA)
            .areaTerreno(DEFAULT_AREA_TERRENO)
            .observacoes(DEFAULT_OBSERVACOES);
        return imovel;
    }

    private List<Imovel> findAll() {
        Iterable<Imovel> findAll = imovelRepository.findAll();

        List<Imovel> result = new ArrayList<>();
        for (Imovel r : findAll) {
            result.add(r);
        }

        return result;
    }

    private int count(){
        return (int) imovelRepository.count();
    }




    @Before
    public void initTest() {
        imovel = createEntity(em);
    }

    @Test
    @Transactional
    public void createImovel() throws Exception {
        int databaseSizeBeforeCreate = count();

        // Create the Imovel
        ImovelDTO imovelDTO = imovelMapper.toDto(imovel);
        restImovelMockMvc.perform(post("/api/imovels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imovelDTO)))
            .andExpect(status().isCreated());

        // Validate the Imovel in the database
        List<Imovel> imovelList = findAll();
        assertThat(imovelList).hasSize(databaseSizeBeforeCreate + 1);
        Imovel testImovel = imovelList.get(imovelList.size() - 1);
        assertThat(testImovel.getCodigoSub100()).isEqualTo(DEFAULT_CODIGO_SUB_100);
        assertThat(testImovel.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testImovel.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testImovel.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testImovel.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testImovel.getPreco()).isEqualTo(DEFAULT_PRECO);
        assertThat(testImovel.getAreaConstruida()).isEqualTo(DEFAULT_AREA_CONSTRUIDA);
        assertThat(testImovel.getAreaTerreno()).isEqualTo(DEFAULT_AREA_TERRENO);
        assertThat(testImovel.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void createImovelWithExistingId() throws Exception {
        imovelRepository.saveAndFlush(imovel);
        int databaseSizeBeforeCreate = findAll().size();


        // An entity with an existing ID cannot be created, so this API call must fail
        restImovelMockMvc.perform(post("/api/imovels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imovel)))
            .andExpect(status().isBadRequest());

        // Validate the Imovel in the database
        List<Imovel> imovelList = findAll();
        assertThat(imovelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImovels() throws Exception {
        // Initialize the database
        imovelRepository.saveAndFlush(imovel);

        // Get all the imovelList
        restImovelMockMvc.perform(get("/api/imovels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.values.[*].id").value(hasItem(imovel.getId())))
            .andExpect(jsonPath("$.values.[*].codigoSub100").value(hasItem(DEFAULT_CODIGO_SUB_100.toString())))
            .andExpect(jsonPath("$.values.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.values.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
            .andExpect(jsonPath("$.values.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.values.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.values.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())))
            .andExpect(jsonPath("$.values.[*].areaConstruida").value(hasItem(DEFAULT_AREA_CONSTRUIDA.intValue())))
            .andExpect(jsonPath("$.values.[*].areaTerreno").value(hasItem(DEFAULT_AREA_TERRENO.intValue())))
            .andExpect(jsonPath("$.values.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())));
    }

    @Test
    @Transactional
    public void getImovel() throws Exception {
        // Initialize the database
        imovelRepository.saveAndFlush(imovel);

        // Get the imovel
        restImovelMockMvc.perform(get("/api/imovels/{id}", imovel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imovel.getId()))
            .andExpect(jsonPath("$.codigoSub100").value(DEFAULT_CODIGO_SUB_100.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.intValue()))
            .andExpect(jsonPath("$.areaConstruida").value(DEFAULT_AREA_CONSTRUIDA.intValue()))
            .andExpect(jsonPath("$.areaTerreno").value(DEFAULT_AREA_TERRENO.intValue()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImovel() throws Exception {
        // Get the imovel
        restImovelMockMvc.perform(get("/api/imovels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImovel() throws Exception {
        // Initialize the database
        imovelRepository.saveAndFlush(imovel);
        int databaseSizeBeforeUpdate = findAll().size();

        // Update the imovel
        Imovel updatedImovel = imovelRepository.findOne(imovel.getId());
        updatedImovel
            .codigoSub100(UPDATED_CODIGO_SUB_100)
            .endereco(UPDATED_ENDERECO)
            .bairro(UPDATED_BAIRRO)
            .descricao(UPDATED_DESCRICAO)
            .tipo(UPDATED_TIPO)
            .preco(UPDATED_PRECO)
            .areaConstruida(UPDATED_AREA_CONSTRUIDA)
            .areaTerreno(UPDATED_AREA_TERRENO)
            .observacoes(UPDATED_OBSERVACOES);
        ImovelDTO imovelDTO = imovelMapper.toDto(updatedImovel);

        restImovelMockMvc.perform(put("/api/imovels/"+imovel.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImovel)))
            .andExpect(status().isOk());

        // Validate the Imovel in the database
        List<Imovel> imovelList = findAll();
        assertThat(imovelList).hasSize(databaseSizeBeforeUpdate);
        Imovel testImovel = imovelList.get(imovelList.size() - 1);
        assertThat(testImovel.getCodigoSub100()).isEqualTo(UPDATED_CODIGO_SUB_100);
        assertThat(testImovel.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testImovel.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testImovel.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testImovel.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testImovel.getPreco()).isEqualTo(UPDATED_PRECO);
        assertThat(testImovel.getAreaConstruida()).isEqualTo(UPDATED_AREA_CONSTRUIDA);
        assertThat(testImovel.getAreaTerreno()).isEqualTo(UPDATED_AREA_TERRENO);
        assertThat(testImovel.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    public void updateNonExistingImovel() throws Exception {
        int databaseSizeBeforeUpdate = findAll().size();

        // Create the Imovel
        ImovelDTO imovelDTO = imovelMapper.toDto(imovel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImovelMockMvc.perform(put("/api/imovels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imovelDTO)))
            .andExpect(status().isCreated());

        // Validate the Imovel in the database
        List<Imovel> imovelList = findAll();
        assertThat(imovelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImovel() throws Exception {
        // Initialize the database
        imovelRepository.saveAndFlush(imovel);
        int databaseSizeBeforeDelete = findAll().size();

        // Get the imovel
        restImovelMockMvc.perform(delete("/api/imovels/{id}", imovel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Imovel> imovelList = findAll();
        assertThat(imovelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imovel.class);
        Imovel imovel1 = new Imovel();
        imovel1.setId("1L");
        Imovel imovel2 = new Imovel();
        imovel2.setId(imovel1.getId());
        assertThat(imovel1).isEqualTo(imovel2);
        imovel2.setId("2L");
        assertThat(imovel1).isNotEqualTo(imovel2);
        imovel1.setId(null);
        assertThat(imovel1).isNotEqualTo(imovel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImovelDTO.class);
        ImovelDTO imovelDTO1 = new ImovelDTO();
        imovelDTO1.setId("1L");
        ImovelDTO imovelDTO2 = new ImovelDTO();
        assertThat(imovelDTO1).isNotEqualTo(imovelDTO2);
        imovelDTO2.setId(imovelDTO1.getId());
        assertThat(imovelDTO1).isEqualTo(imovelDTO2);
        imovelDTO2.setId("2L");
        assertThat(imovelDTO1).isNotEqualTo(imovelDTO2);
        imovelDTO1.setId(null);
        assertThat(imovelDTO1).isNotEqualTo(imovelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(imovelMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(imovelMapper.fromId(null)).isNull();
    }
}
