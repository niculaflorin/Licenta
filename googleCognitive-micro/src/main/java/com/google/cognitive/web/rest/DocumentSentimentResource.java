package com.google.cognitive.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.cognitive.domain.DocumentSentiment;

import com.google.cognitive.repository.DocumentSentimentRepository;
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
 * REST controller for managing DocumentSentiment.
 */
@RestController
@RequestMapping("/api")
public class DocumentSentimentResource {

    private final Logger log = LoggerFactory.getLogger(DocumentSentimentResource.class);

    private static final String ENTITY_NAME = "documentSentiment";

    private final DocumentSentimentRepository documentSentimentRepository;

    public DocumentSentimentResource(DocumentSentimentRepository documentSentimentRepository) {
        this.documentSentimentRepository = documentSentimentRepository;
    }

    /**
     * POST  /document-sentiments : Create a new documentSentiment.
     *
     * @param documentSentiment the documentSentiment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentSentiment, or with status 400 (Bad Request) if the documentSentiment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-sentiments")
    @Timed
    public ResponseEntity<DocumentSentiment> createDocumentSentiment(@RequestBody DocumentSentiment documentSentiment) throws URISyntaxException {
        log.debug("REST request to save DocumentSentiment : {}", documentSentiment);
        if (documentSentiment.getId() != null) {
            throw new BadRequestAlertException("A new documentSentiment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentSentiment result = documentSentimentRepository.save(documentSentiment);
        return ResponseEntity.created(new URI("/api/document-sentiments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-sentiments : Updates an existing documentSentiment.
     *
     * @param documentSentiment the documentSentiment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentSentiment,
     * or with status 400 (Bad Request) if the documentSentiment is not valid,
     * or with status 500 (Internal Server Error) if the documentSentiment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-sentiments")
    @Timed
    public ResponseEntity<DocumentSentiment> updateDocumentSentiment(@RequestBody DocumentSentiment documentSentiment) throws URISyntaxException {
        log.debug("REST request to update DocumentSentiment : {}", documentSentiment);
        if (documentSentiment.getId() == null) {
            return createDocumentSentiment(documentSentiment);
        }
        DocumentSentiment result = documentSentimentRepository.save(documentSentiment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentSentiment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-sentiments : get all the documentSentiments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of documentSentiments in body
     */
    @GetMapping("/document-sentiments")
    @Timed
    public List<DocumentSentiment> getAllDocumentSentiments() {
        log.debug("REST request to get all DocumentSentiments");
        return documentSentimentRepository.findAll();
        }

    /**
     * GET  /document-sentiments/:id : get the "id" documentSentiment.
     *
     * @param id the id of the documentSentiment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentSentiment, or with status 404 (Not Found)
     */
    @GetMapping("/document-sentiments/{id}")
    @Timed
    public ResponseEntity<DocumentSentiment> getDocumentSentiment(@PathVariable Long id) {
        log.debug("REST request to get DocumentSentiment : {}", id);
        DocumentSentiment documentSentiment = documentSentimentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentSentiment));
    }

    /**
     * DELETE  /document-sentiments/:id : delete the "id" documentSentiment.
     *
     * @param id the id of the documentSentiment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-sentiments/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumentSentiment(@PathVariable Long id) {
        log.debug("REST request to delete DocumentSentiment : {}", id);
        documentSentimentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
