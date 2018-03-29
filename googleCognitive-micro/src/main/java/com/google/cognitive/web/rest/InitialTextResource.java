package com.google.cognitive.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.cognitive.domain.InitialText;

import com.google.cognitive.repository.InitialTextRepository;
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
 * REST controller for managing InitialText.
 */
@RestController
@RequestMapping("/api")
public class InitialTextResource {

    private final Logger log = LoggerFactory.getLogger(InitialTextResource.class);

    private static final String ENTITY_NAME = "initialText";

    private final InitialTextRepository initialTextRepository;

    public InitialTextResource(InitialTextRepository initialTextRepository) {
        this.initialTextRepository = initialTextRepository;
    }

    /**
     * POST  /initial-texts : Create a new initialText.
     *
     * @param initialText the initialText to create
     * @return the ResponseEntity with status 201 (Created) and with body the new initialText, or with status 400 (Bad Request) if the initialText has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/initial-texts")
    @Timed
    public ResponseEntity<InitialText> createInitialText(@RequestBody InitialText initialText) throws URISyntaxException {
        log.debug("REST request to save InitialText : {}", initialText);
        if (initialText.getId() != null) {
            throw new BadRequestAlertException("A new initialText cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InitialText result = initialTextRepository.save(initialText);
        return ResponseEntity.created(new URI("/api/initial-texts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /initial-texts : Updates an existing initialText.
     *
     * @param initialText the initialText to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated initialText,
     * or with status 400 (Bad Request) if the initialText is not valid,
     * or with status 500 (Internal Server Error) if the initialText couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/initial-texts")
    @Timed
    public ResponseEntity<InitialText> updateInitialText(@RequestBody InitialText initialText) throws URISyntaxException {
        log.debug("REST request to update InitialText : {}", initialText);
        if (initialText.getId() == null) {
            return createInitialText(initialText);
        }
        InitialText result = initialTextRepository.save(initialText);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, initialText.getId().toString()))
            .body(result);
    }

    /**
     * GET  /initial-texts : get all the initialTexts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of initialTexts in body
     */
    @GetMapping("/initial-texts")
    @Timed
    public List<InitialText> getAllInitialTexts() {
        log.debug("REST request to get all InitialTexts");
        return initialTextRepository.findAll();
        }

    /**
     * GET  /initial-texts/:id : get the "id" initialText.
     *
     * @param id the id of the initialText to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the initialText, or with status 404 (Not Found)
     */
    @GetMapping("/initial-texts/{id}")
    @Timed
    public ResponseEntity<InitialText> getInitialText(@PathVariable Long id) {
        log.debug("REST request to get InitialText : {}", id);
        InitialText initialText = initialTextRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(initialText));
    }

    /**
     * DELETE  /initial-texts/:id : delete the "id" initialText.
     *
     * @param id the id of the initialText to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/initial-texts/{id}")
    @Timed
    public ResponseEntity<Void> deleteInitialText(@PathVariable Long id) {
        log.debug("REST request to delete InitialText : {}", id);
        initialTextRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
