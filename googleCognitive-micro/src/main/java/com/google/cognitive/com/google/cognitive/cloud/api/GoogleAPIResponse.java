package com.google.cognitive.com.google.cognitive.cloud.api;

public class GoogleAPIResponse {

    private GoogleSentences[] sentences;

    private String language;

    private GoogleDocumentSentiment documentSentiment;

    public GoogleSentences[] getSentences () {
        return sentences;
    }

    public void setSentences (GoogleSentences[] sentences) {
        this.sentences = sentences;
    }

    public String getLanguage () {
        return language;
    }

    public void setLanguage (String language) {
        this.language = language;
    }

    public GoogleDocumentSentiment getDocumentSentiment () {
        return documentSentiment;
    }

    public void setDocumentSentiment (GoogleDocumentSentiment documentSentiment) {
        this.documentSentiment = documentSentiment;
    }
}
