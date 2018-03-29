package com.google.cognitive.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A InitialText.
 */
@Entity
@Table(name = "initial_text")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InitialText implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "user_id")
    private Double userId;

    @OneToOne
    @JoinColumn(unique = true)
    private DocumentSentiment initial;

    @ManyToOne
    private Subject subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public InitialText text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getUserId() {
        return userId;
    }

    public InitialText userId(Double userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Double userId) {
        this.userId = userId;
    }

    public DocumentSentiment getInitial() {
        return initial;
    }

    public InitialText initial(DocumentSentiment documentSentiment) {
        this.initial = documentSentiment;
        return this;
    }

    public void setInitial(DocumentSentiment documentSentiment) {
        this.initial = documentSentiment;
    }

    public Subject getSubject() {
        return subject;
    }

    public InitialText subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
        InitialText initialText = (InitialText) o;
        if (initialText.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), initialText.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InitialText{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
