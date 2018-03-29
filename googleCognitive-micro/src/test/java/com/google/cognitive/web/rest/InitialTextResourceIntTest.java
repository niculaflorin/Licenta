package com.google.cognitive.web.rest;

import com.google.cognitive.GoogleCognitiveApp;

import com.google.cognitive.domain.InitialText;
import com.google.cognitive.repository.InitialTextRepository;
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
 * Test class for the InitialTextResource REST controller.
 *
 * @see InitialTextResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoogleCognitiveApp.class)
public class InitialTextResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Double DEFAULT_USER_ID = 1D;
    private static final Double UPDATED_USER_ID = 2D;

    @Autowired
    private InitialTextRepository initialTextRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInitialTextMockMvc;

    private InitialText initialText;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InitialTextResource initialTextResource = new InitialTextResource(initialTextRepository);
        this.restInitialTextMockMvc = MockMvcBuilders.standaloneSetup(initialTextResource)
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
    public static InitialText createEntity(EntityManager em) {
        InitialText initialText = new InitialText()
            .text(DEFAULT_TEXT)
            .userId(DEFAULT_USER_ID);
        return initialText;
    }

    @Before
    public void initTest() {
        initialText = createEntity(em);
    }

    @Test
    @Transactional
    public void createInitialText() throws Exception {
        int databaseSizeBeforeCreate = initialTextRepository.findAll().size();

        // Create the InitialText
        restInitialTextMockMvc.perform(post("/api/initial-texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialText)))
            .andExpect(status().isCreated());

        // Validate the InitialText in the database
        List<InitialText> initialTextList = initialTextRepository.findAll();
        assertThat(initialTextList).hasSize(databaseSizeBeforeCreate + 1);
        InitialText testInitialText = initialTextList.get(initialTextList.size() - 1);
        assertThat(testInitialText.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testInitialText.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createInitialTextWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = initialTextRepository.findAll().size();

        // Create the InitialText with an existing ID
        initialText.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInitialTextMockMvc.perform(post("/api/initial-texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialText)))
            .andExpect(status().isBadRequest());

        // Validate the InitialText in the database
        List<InitialText> initialTextList = initialTextRepository.findAll();
        assertThat(initialTextList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInitialTexts() throws Exception {
        // Initialize the database
        initialTextRepository.saveAndFlush(initialText);

        // Get all the initialTextList
        restInitialTextMockMvc.perform(get("/api/initial-texts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(initialText.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.doubleValue())));
    }

    @Test
    @Transactional
    public void getInitialText() throws Exception {
        // Initialize the database
        initialTextRepository.saveAndFlush(initialText);

        // Get the initialText
        restInitialTextMockMvc.perform(get("/api/initial-texts/{id}", initialText.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(initialText.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInitialText() throws Exception {
        // Get the initialText
        restInitialTextMockMvc.perform(get("/api/initial-texts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInitialText() throws Exception {
        // Initialize the database
        initialTextRepository.saveAndFlush(initialText);
        int databaseSizeBeforeUpdate = initialTextRepository.findAll().size();

        // Update the initialText
        InitialText updatedInitialText = initialTextRepository.findOne(initialText.getId());
        updatedInitialText
            .text(UPDATED_TEXT)
            .userId(UPDATED_USER_ID);

        restInitialTextMockMvc.perform(put("/api/initial-texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInitialText)))
            .andExpect(status().isOk());

        // Validate the InitialText in the database
        List<InitialText> initialTextList = initialTextRepository.findAll();
        assertThat(initialTextList).hasSize(databaseSizeBeforeUpdate);
        InitialText testInitialText = initialTextList.get(initialTextList.size() - 1);
        assertThat(testInitialText.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testInitialText.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingInitialText() throws Exception {
        int databaseSizeBeforeUpdate = initialTextRepository.findAll().size();

        // Create the InitialText

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInitialTextMockMvc.perform(put("/api/initial-texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(initialText)))
            .andExpect(status().isCreated());

        // Validate the InitialText in the database
        List<InitialText> initialTextList = initialTextRepository.findAll();
        assertThat(initialTextList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInitialText() throws Exception {
        // Initialize the database
        initialTextRepository.saveAndFlush(initialText);
        int databaseSizeBeforeDelete = initialTextRepository.findAll().size();

        // Get the initialText
        restInitialTextMockMvc.perform(delete("/api/initial-texts/{id}", initialText.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InitialText> initialTextList = initialTextRepository.findAll();
        assertThat(initialTextList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InitialText.class);
        InitialText initialText1 = new InitialText();
        initialText1.setId(1L);
        InitialText initialText2 = new InitialText();
        initialText2.setId(initialText1.getId());
        assertThat(initialText1).isEqualTo(initialText2);
        initialText2.setId(2L);
        assertThat(initialText1).isNotEqualTo(initialText2);
        initialText1.setId(null);
        assertThat(initialText1).isNotEqualTo(initialText2);
    }
}
