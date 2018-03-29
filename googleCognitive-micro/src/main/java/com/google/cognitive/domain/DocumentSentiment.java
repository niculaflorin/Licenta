package com.google.cognitive.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DocumentSentiment.
 */
@Entity
@Table(name = "document_sentiment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocumentSentiment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "magnitude")
    private Double magnitude;

    @Column(name = "score")
    private Double score;

    @Column(name = "language")
    private String language;

    @OneToMany(mappedBy = "documentSentiment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SentencesSentiment> sentences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public DocumentSentiment magnitude(Double magnitude) {
        this.magnitude = magnitude;
        return this;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Double getScore() {
        return score;
    }

    public DocumentSentiment score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getLanguage() {
        return language;
    }

    public DocumentSentiment language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<SentencesSentiment> getSentences() {
        return sentences;
    }

    public DocumentSentiment sentences(Set<SentencesSentiment> sentencesSentiments) {
        this.sentences = sentencesSentiments;
        return this;
    }

    public DocumentSentiment addSentence(SentencesSentiment sentencesSentiment) {
        this.sentences.add(sentencesSentiment);
        sentencesSentiment.setDocumentSentiment(this);
        return this;
    }

    public DocumentSentiment removeSentence(SentencesSentiment sentencesSentiment) {
        this.sentences.remove(sentencesSentiment);
        sentencesSentiment.setDocumentSentiment(null);
        return this;
    }

    public void setSentences(Set<SentencesSentiment> sentencesSentiments) {
        this.sentences = sentencesSentiments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DocumentSentiment documentSentiment = (DocumentSentiment) o;
        if (documentSentiment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentSentiment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentSentiment{" +
            "id=" + getId() +
            ", magnitude=" + getMagnitude() +
            ", score=" + getScore() +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
