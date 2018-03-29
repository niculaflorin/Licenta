package com.google.cognitive.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.cognitive.domain.SentencesSentiment;

import com.google.cognitive.repository.SentencesSentimentRepository;
import com.google.cognitive.web.rest.errors.BadRequestAlertException;
import com.google.cognitive.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SentencesSentiment.
 */
@RestController
@RequestMapping("/api")
public class SentencesSentimentResource {

    private final Logger log = LoggerFactory.getLogger(SentencesSentimentResource.class);

    private static final String ENTITY_NAME = "sentencesSentiment";

    private final SentencesSentimentRepository sentencesSentimentRepository;

    public SentencesSentimentResource(SentencesSentimentRepository sentencesSentimentRepository) {
        this.sentencesSentimentRepository = sentencesSentimentRepository;
    }

    /**
     * POST  /sentences-sentiments : Create a new sentencesSentiment.
     *
     * @param sentencesSentiment the sentencesSentiment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sentencesSentiment, or with status 400 (Bad Request) if the sentencesSentiment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sentences-sentiments")
    @Timed
    public ResponseEntity<SentencesSentiment> createSentencesSentiment(@RequestBody SentencesSentiment sentencesSentiment) throws URISyntaxException {
        log.debug("REST request to save SentencesSentiment : {}", sentencesSentiment);
        if (sentencesSentiment.getId() != null) {
            throw new BadRequestAlertException("A new sentencesSentiment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SentencesSentiment result = sentencesSentimentRepository.save(sentencesSentiment);
        return ResponseEntity.created(new URI("/api/sentences-sentiments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sentences-sentiments : Updates an existing sentencesSentiment.
     *
     * @param sentencesSentiment the sentencesSentiment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sentencesSentiment,
     * or with status 400 (Bad Request) if the sentencesSentiment is not valid,
     * or with status 500 (Internal Server Error) if the sentencesSentiment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sentences-sentiments")
    @Timed
    public ResponseEntity<SentencesSentiment> updateSentencesSentiment(@RequestBody SentencesSentiment sentencesSentiment) throws URISyntaxException {
        log.debug("REST request to update SentencesSentiment : {}", sentencesSentiment);
        if (sentencesSentiment.getId() == null) {
            return createSentencesSentiment(sentencesSentiment);
        }
        SentencesSentiment result = sentencesSentimentRepository.save(sentencesSentiment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sentencesSentiment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sentences-sentiments : get all the sentencesSentiments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sentencesSentiments in body
     */
    @GetMapping("/sentences-sentiments")
    @Timed
    public List<SentencesSentiment> getAllSentencesSentiments() {
        log.debug("REST request to get all SentencesSentiments");
        return sentencesSentimentRepository.findAll();
        }

    /**
     * GET  /sentences-sentiments/:id : get the "id" sentencesSentiment.
     *
     * @param id the id of the sentencesSentiment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sentencesSentiment, or with status 404 (Not Found)
     */
    @GetMapping("/sentences-sentiments/{id}")
    @Timed
    public ResponseEntity<SentencesSentiment> getSentencesSentiment(@PathVariable Long id) {
        log.debug("REST request to get SentencesSentiment : {}", id);
        SentencesSentiment sentencesSentiment = sentencesSentimentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sentencesSentiment));
    }

    /**
     * DELETE  /sentences-sentiments/:id : delete the "id" sentencesSentiment.
     *
     * @param id the id of the sentencesSentiment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sentences-sentiments/{id}")
    @Timed
    public ResponseEntity<Void> deleteSentencesSentiment(@PathVariable Long id) {
        log.debug("REST request to delete SentencesSentiment : {}", id);
        sentencesSentimentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
