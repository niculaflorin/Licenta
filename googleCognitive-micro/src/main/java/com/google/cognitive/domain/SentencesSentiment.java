package com.google.cognitive.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A SentencesSentiment.
 */
@Entity
@Table(name = "sentences_sentiment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SentencesSentiment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "magnitude")
    private Double magnitude;

    @Column(name = "score")
    private Double score;

    @ManyToOne
    private DocumentSentiment documentSentiment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public SentencesSentiment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public SentencesSentiment magnitude(Double magnitude) {
        this.magnitude = magnitude;
        return this;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Double getScore() {
        return score;
    }

    public SentencesSentiment score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public DocumentSentiment getDocumentSentiment() {
        return documentSentiment;
    }

    public SentencesSentiment documentSentiment(DocumentSentiment documentSentiment) {
        this.documentSentiment = documentSentiment;
        return this;
    }

    public void setDocumentSentiment(DocumentSentiment documentSentiment) {
        this.documentSentiment = documentSentiment;
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
        SentencesSentiment sentencesSentiment = (SentencesSentiment) o;
        if (sentencesSentiment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sentencesSentiment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SentencesSentiment{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", magnitude=" + getMagnitude() +
            ", score=" + getScore() +
            "}";
    }
}
