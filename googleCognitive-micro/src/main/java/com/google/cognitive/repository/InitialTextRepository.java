package com.google.cognitive.repository;

import com.google.cognitive.domain.InitialText;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InitialText entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InitialTextRepository extends JpaRepository<InitialText, Long> {

}
