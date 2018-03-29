package com.google.cognitive.web.rest;

import com.google.cognitive.GoogleCognitiveApp;

import com.google.cognitive.domain.DocumentSentiment;
import com.google.cognitive.repository.DocumentSentimentRepository;
import com.google.cognitive.web.rest.errors.ExceptionTranslator;

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

import static com.google.cognitive.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DocumentSentimentResource REST controller.
 *
 * @see DocumentSentimentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoogleCognitiveApp.class)
public class DocumentSentimentResourceIntTest {

    private static final Double DEFAULT_MAGNITUDE = 1D;
    private static final Double UPDATED_MAGNITUDE = 2D;

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    @Autowired
    private DocumentSentimentRepository documentSentimentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocumentSentimentMockMvc;

    private DocumentSentiment documentSentiment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentSentimentResource documentSentimentResource = new DocumentSentimentResource(documentSentimentRepository);
        this.restDocumentSentimentMockMvc = MockMvcBuilders.standaloneSetup(documentSentimentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentSentiment createEntity(EntityManager em) {
        DocumentSentiment documentSentiment = new DocumentSentiment()
            .magnitude(DEFAULT_MAGNITUDE)
            .score(DEFAULT_SCORE)
            .language(DEFAULT_LANGUAGE);
        return documentSentiment;
    }

    @Before
    public void initTest() {
        documentSentiment = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentSentiment() throws Exception {
        int databaseSizeBeforeCreate = documentSentimentRepository.findAll().size();

        // Create the DocumentSentiment
        restDocumentSentimentMockMvc.perform(post("/api/document-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentSentiment)))
            .andExpect(status().isCreated());

        // Validate the DocumentSentiment in the database
        List<DocumentSentiment> documentSentimentList = documentSentimentRepository.findAll();
        assertThat(documentSentimentList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentSentiment testDocumentSentiment = documentSentimentList.get(documentSentimentList.size() - 1);
        assertThat(testDocumentSentiment.getMagnitude()).isEqualTo(DEFAULT_MAGNITUDE);
        assertThat(testDocumentSentiment.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testDocumentSentiment.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createDocumentSentimentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentSentimentRepository.findAll().size();

        // Create the DocumentSentiment with an existing ID
        documentSentiment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentSentimentMockMvc.perform(post("/api/document-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentSentiment)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentSentiment in the database
        List<DocumentSentiment> documentSentimentList = documentSentimentRepository.findAll();
        assertThat(documentSentimentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDocumentSentiments() throws Exception {
        // Initialize the database
        documentSentimentRepository.saveAndFlush(documentSentiment);

        // Get all the documentSentimentList
        restDocumentSentimentMockMvc.perform(get("/api/document-sentiments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentSentiment.getId().intValue())))
            .andExpect(jsonPath("$.[*].magnitude").value(hasItem(DEFAULT_MAGNITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void getDocumentSentiment() throws Exception {
        // Initialize the database
        documentSentimentRepository.saveAndFlush(documentSentiment);

        // Get the documentSentiment
        restDocumentSentimentMockMvc.perform(get("/api/document-sentiments/{id}", documentSentiment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentSentiment.getId().intValue()))
            .andExpect(jsonPath("$.magnitude").value(DEFAULT_MAGNITUDE.doubleValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentSentiment() throws Exception {
        // Get the documentSentiment
        restDocumentSentimentMockMvc.perform(get("/api/document-sentiments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentSentiment() throws Exception {
        // Initialize the database
        documentSentimentRepository.saveAndFlush(documentSentiment);
        int databaseSizeBeforeUpdate = documentSentimentRepository.findAll().size();

        // Update the documentSentiment
        DocumentSentiment updatedDocumentSentiment = documentSentimentRepository.findOne(documentSentiment.getId());
        updatedDocumentSentiment
            .magnitude(UPDATED_MAGNITUDE)
            .score(UPDATED_SCORE)
            .language(UPDATED_LANGUAGE);

        restDocumentSentimentMockMvc.perform(put("/api/document-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentSentiment)))
            .andExpect(status().isOk());

        // Validate the DocumentSentiment in the database
        List<DocumentSentiment> documentSentimentList = documentSentimentRepository.findAll();
        assertThat(documentSentimentList).hasSize(databaseSizeBeforeUpdate);
        DocumentSentiment testDocumentSentiment = documentSentimentList.get(documentSentimentList.size() - 1);
        assertThat(testDocumentSentiment.getMagnitude()).isEqualTo(UPDATED_MAGNITUDE);
        assertThat(testDocumentSentiment.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testDocumentSentiment.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentSentiment() throws Exception {
        int databaseSizeBeforeUpdate = documentSentimentRepository.findAll().size();

        // Create the DocumentSentiment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDocumentSentimentMockMvc.perform(put("/api/document-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentSentiment)))
            .andExpect(status().isCreated());

        // Validate the DocumentSentiment in the database
        List<DocumentSentiment> documentSentimentList = documentSentimentRepository.findAll();
        assertThat(documentSentimentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDocumentSentiment() throws Exception {
        // Initialize the database
        documentSentimentRepository.saveAndFlush(documentSentiment);
        int databaseSizeBeforeDelete = documentSentimentRepository.findAll().size();

        // Get the documentSentiment
        restDocumentSentimentMockMvc.perform(delete("/api/document-sentiments/{id}", documentSentiment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentSentiment> documentSentimentList = documentSentimentRepository.findAll();
        assertThat(documentSentimentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentSentiment.class);
        DocumentSentiment documentSentiment1 = new DocumentSentiment();
        documentSentiment1.setId(1L);
        DocumentSentiment documentSentiment2 = new DocumentSentiment();
        documentSentiment2.setId(documentSentiment1.getId());
        assertThat(documentSentiment1).isEqualTo(documentSentiment2);
        documentSentiment2.setId(2L);
        assertThat(documentSentiment1).isNotEqualTo(documentSentiment2);
        documentSentiment1.setId(null);
        assertThat(documentSentiment1).isNotEqualTo(documentSentiment2);
    }
}
