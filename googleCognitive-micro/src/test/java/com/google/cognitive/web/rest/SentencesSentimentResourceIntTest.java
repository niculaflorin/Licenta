package com.google.cognitive.web.rest;

import com.google.cognitive.GoogleCognitiveApp;

import com.google.cognitive.domain.SentencesSentiment;
import com.google.cognitive.repository.SentencesSentimentRepository;
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
 * Test class for the SentencesSentimentResource REST controller.
 *
 * @see SentencesSentimentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoogleCognitiveApp.class)
public class SentencesSentimentResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Double DEFAULT_MAGNITUDE = 1D;
    private static final Double UPDATED_MAGNITUDE = 2D;

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    @Autowired
    private SentencesSentimentRepository sentencesSentimentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSentencesSentimentMockMvc;

    private SentencesSentiment sentencesSentiment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SentencesSentimentResource sentencesSentimentResource = new SentencesSentimentResource(sentencesSentimentRepository);
        this.restSentencesSentimentMockMvc = MockMvcBuilders.standaloneSetup(sentencesSentimentResource)
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
    public static SentencesSentiment createEntity(EntityManager em) {
        SentencesSentiment sentencesSentiment = new SentencesSentiment()
            .content(DEFAULT_CONTENT)
            .magnitude(DEFAULT_MAGNITUDE)
            .score(DEFAULT_SCORE);
        return sentencesSentiment;
    }

    @Before
    public void initTest() {
        sentencesSentiment = createEntity(em);
    }

    @Test
    @Transactional
    public void createSentencesSentiment() throws Exception {
        int databaseSizeBeforeCreate = sentencesSentimentRepository.findAll().size();

        // Create the SentencesSentiment
        restSentencesSentimentMockMvc.perform(post("/api/sentences-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sentencesSentiment)))
            .andExpect(status().isCreated());

        // Validate the SentencesSentiment in the database
        List<SentencesSentiment> sentencesSentimentList = sentencesSentimentRepository.findAll();
        assertThat(sentencesSentimentList).hasSize(databaseSizeBeforeCreate + 1);
        SentencesSentiment testSentencesSentiment = sentencesSentimentList.get(sentencesSentimentList.size() - 1);
        assertThat(testSentencesSentiment.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testSentencesSentiment.getMagnitude()).isEqualTo(DEFAULT_MAGNITUDE);
        assertThat(testSentencesSentiment.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createSentencesSentimentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sentencesSentimentRepository.findAll().size();

        // Create the SentencesSentiment with an existing ID
        sentencesSentiment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSentencesSentimentMockMvc.perform(post("/api/sentences-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sentencesSentiment)))
            .andExpect(status().isBadRequest());

        // Validate the SentencesSentiment in the database
        List<SentencesSentiment> sentencesSentimentList = sentencesSentimentRepository.findAll();
        assertThat(sentencesSentimentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSentencesSentiments() throws Exception {
        // Initialize the database
        sentencesSentimentRepository.saveAndFlush(sentencesSentiment);

        // Get all the sentencesSentimentList
        restSentencesSentimentMockMvc.perform(get("/api/sentences-sentiments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sentencesSentiment.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].magnitude").value(hasItem(DEFAULT_MAGNITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())));
    }

    @Test
    @Transactional
    public void getSentencesSentiment() throws Exception {
        // Initialize the database
        sentencesSentimentRepository.saveAndFlush(sentencesSentiment);

        // Get the sentencesSentiment
        restSentencesSentimentMockMvc.perform(get("/api/sentences-sentiments/{id}", sentencesSentiment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sentencesSentiment.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.magnitude").value(DEFAULT_MAGNITUDE.doubleValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSentencesSentiment() throws Exception {
        // Get the sentencesSentiment
        restSentencesSentimentMockMvc.perform(get("/api/sentences-sentiments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSentencesSentiment() throws Exception {
        // Initialize the database
        sentencesSentimentRepository.saveAndFlush(sentencesSentiment);
        int databaseSizeBeforeUpdate = sentencesSentimentRepository.findAll().size();

        // Update the sentencesSentiment
        SentencesSentiment updatedSentencesSentiment = sentencesSentimentRepository.findOne(sentencesSentiment.getId());
        updatedSentencesSentiment
            .content(UPDATED_CONTENT)
            .magnitude(UPDATED_MAGNITUDE)
            .score(UPDATED_SCORE);

        restSentencesSentimentMockMvc.perform(put("/api/sentences-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSentencesSentiment)))
            .andExpect(status().isOk());

        // Validate the SentencesSentiment in the database
        List<SentencesSentiment> sentencesSentimentList = sentencesSentimentRepository.findAll();
        assertThat(sentencesSentimentList).hasSize(databaseSizeBeforeUpdate);
        SentencesSentiment testSentencesSentiment = sentencesSentimentList.get(sentencesSentimentList.size() - 1);
        assertThat(testSentencesSentiment.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testSentencesSentiment.getMagnitude()).isEqualTo(UPDATED_MAGNITUDE);
        assertThat(testSentencesSentiment.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingSentencesSentiment() throws Exception {
        int databaseSizeBeforeUpdate = sentencesSentimentRepository.findAll().size();

        // Create the SentencesSentiment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSentencesSentimentMockMvc.perform(put("/api/sentences-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sentencesSentiment)))
            .andExpect(status().isCreated());

        // Validate the SentencesSentiment in the database
        List<SentencesSentiment> sentencesSentimentList = sentencesSentimentRepository.findAll();
        assertThat(sentencesSentimentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSentencesSentiment() throws Exception {
        // Initialize the database
        sentencesSentimentRepository.saveAndFlush(sentencesSentiment);
        int databaseSizeBeforeDelete = sentencesSentimentRepository.findAll().size();

        // Get the sentencesSentiment
        restSentencesSentimentMockMvc.perform(delete("/api/sentences-sentiments/{id}", sentencesSentiment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SentencesSentiment> sentencesSentimentList = sentencesSentimentRepository.findAll();
        assertThat(sentencesSentimentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SentencesSentiment.class);
        SentencesSentiment sentencesSentiment1 = new SentencesSentiment();
        sentencesSentiment1.setId(1L);
        SentencesSentiment sentencesSentiment2 = new SentencesSentiment();
        sentencesSentiment2.setId(sentencesSentiment1.getId());
        assertThat(sentencesSentiment1).isEqualTo(sentencesSentiment2);
        sentencesSentiment2.setId(2L);
        assertThat(sentencesSentiment1).isNotEqualTo(sentencesSentiment2);
        sentencesSentiment1.setId(null);
        assertThat(sentencesSentiment1).isNotEqualTo(sentencesSentiment2);
    }
}
