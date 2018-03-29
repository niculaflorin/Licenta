package com.google.cognitive.repository;

import com.google.cognitive.domain.SentencesSentiment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SentencesSentiment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SentencesSentimentRepository extends JpaRepository<SentencesSentiment, Long> {

}
