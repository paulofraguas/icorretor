package br.com.munif.fraguas.projects.icorretor.web.rest;

import br.com.munif.framework.vicente.core.VicThreadScope;


import br.com.munif.fraguas.projects.icorretor.IcorretorApp;

import java.util.ArrayList;
import br.com.munif.fraguas.projects.icorretor.domain.Foto;
import br.com.munif.fraguas.projects.icorretor.repository.FotoRepository;
import br.com.munif.fraguas.projects.icorretor.service.FotoService;
import br.com.munif.fraguas.projects.icorretor.service.dto.FotoDTO;
import br.com.munif.fraguas.projects.icorretor.service.mapper.FotoMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FotoResource REST controller.
 *
 * @see FotoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcorretorApp.class)
public class FotoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private FotoMapper fotoMapper;

    @Autowired
    private FotoService fotoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFotoMockMvc;

    private Foto foto;

    @Before
    public void setup() {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.ip.set("127.0.0.1");
        MockitoAnnotations.initMocks(this);
        final FotoResource fotoResource = new FotoResource(fotoService);
        this.restFotoMockMvc = MockMvcBuilders.standaloneSetup(fotoResource)
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
    public static Foto createEntity(EntityManager em) {
        Foto foto = new Foto()
            .descricao(DEFAULT_DESCRICAO)
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE);
        return foto;
    }

    private List<Foto> findAll() {
        Iterable<Foto> findAll = fotoRepository.findAll();

        List<Foto> result = new ArrayList<>();
        for (Foto r : findAll) {
            result.add(r);
        }

        return result;
    }

    private int count(){
        return (int) fotoRepository.count();
    }




    @Before
    public void initTest() {
        foto = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoto() throws Exception {
        int databaseSizeBeforeCreate = count();

        // Create the Foto
        FotoDTO fotoDTO = fotoMapper.toDto(foto);
        restFotoMockMvc.perform(post("/api/fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotoDTO)))
            .andExpect(status().isCreated());

        // Validate the Foto in the database
        List<Foto> fotoList = findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeCreate + 1);
        Foto testFoto = fotoList.get(fotoList.size() - 1);
        assertThat(testFoto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testFoto.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testFoto.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFotoWithExistingId() throws Exception {
        fotoRepository.saveAndFlush(foto);
        int databaseSizeBeforeCreate = findAll().size();


        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoMockMvc.perform(post("/api/fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foto)))
            .andExpect(status().isBadRequest());

        // Validate the Foto in the database
        List<Foto> fotoList = findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFotos() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);

        // Get all the fotoList
        restFotoMockMvc.perform(get("/api/fotos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.values.[*].id").value(hasItem(foto.getId())))
            .andExpect(jsonPath("$.values.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.values.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.values.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))));
    }

    @Test
    @Transactional
    public void getFoto() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);

        // Get the foto
        restFotoMockMvc.perform(get("/api/fotos/{id}", foto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foto.getId()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)));
    }

    @Test
    @Transactional
    public void getNonExistingFoto() throws Exception {
        // Get the foto
        restFotoMockMvc.perform(get("/api/fotos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoto() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);
        int databaseSizeBeforeUpdate = findAll().size();

        // Update the foto
        Foto updatedFoto = fotoRepository.findOne(foto.getId());
        updatedFoto
            .descricao(UPDATED_DESCRICAO)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE);
        FotoDTO fotoDTO = fotoMapper.toDto(updatedFoto);

        restFotoMockMvc.perform(put("/api/fotos/"+foto.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoto)))
            .andExpect(status().isOk());

        // Validate the Foto in the database
        List<Foto> fotoList = findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeUpdate);
        Foto testFoto = fotoList.get(fotoList.size() - 1);
        assertThat(testFoto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testFoto.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testFoto.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFoto() throws Exception {
        int databaseSizeBeforeUpdate = findAll().size();

        // Create the Foto
        FotoDTO fotoDTO = fotoMapper.toDto(foto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFotoMockMvc.perform(put("/api/fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotoDTO)))
            .andExpect(status().isCreated());

        // Validate the Foto in the database
        List<Foto> fotoList = findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFoto() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);
        int databaseSizeBeforeDelete = findAll().size();

        // Get the foto
        restFotoMockMvc.perform(delete("/api/fotos/{id}", foto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Foto> fotoList = findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Foto.class);
        Foto foto1 = new Foto();
        foto1.setId("1L");
        Foto foto2 = new Foto();
        foto2.setId(foto1.getId());
        assertThat(foto1).isEqualTo(foto2);
        foto2.setId("2L");
        assertThat(foto1).isNotEqualTo(foto2);
        foto1.setId(null);
        assertThat(foto1).isNotEqualTo(foto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoDTO.class);
        FotoDTO fotoDTO1 = new FotoDTO();
        fotoDTO1.setId("1L");
        FotoDTO fotoDTO2 = new FotoDTO();
        assertThat(fotoDTO1).isNotEqualTo(fotoDTO2);
        fotoDTO2.setId(fotoDTO1.getId());
        assertThat(fotoDTO1).isEqualTo(fotoDTO2);
        fotoDTO2.setId("2L");
        assertThat(fotoDTO1).isNotEqualTo(fotoDTO2);
        fotoDTO1.setId(null);
        assertThat(fotoDTO1).isNotEqualTo(fotoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fotoMapper.fromId("42L").getId()).isEqualTo("42L");
        assertThat(fotoMapper.fromId(null)).isNull();
    }
}
