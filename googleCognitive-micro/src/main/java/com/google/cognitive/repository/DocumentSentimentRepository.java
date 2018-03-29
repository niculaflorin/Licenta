package com.google.cognitive.repository;

import com.google.cognitive.domain.DocumentSentiment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DocumentSentiment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentSentimentRepository extends JpaRepository<DocumentSentiment, Long> {

}
