package com.google.cognitive.com.google.cognitive.cloud.api;

public class GoogleSentences {

    private GoogleSentiment sentiment;

    private GoogleText text;

    public GoogleSentiment getSentiment () {
        return sentiment;
    }

    public void setSentiment (GoogleSentiment sentiment) {
        this.sentiment = sentiment;
    }

    public GoogleText getText () {
        return text;
    }

    public void setText (GoogleText text) {
        this.text = text;
    }
}
