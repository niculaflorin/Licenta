package com.google.cognitive.com.google.cognitive.cloud.api;

public class GoogleAPIRequest {

    private GoogleDocument document;

    private String encodingType;

    public GoogleDocument getDocument () {
        return document;
    }

    public void setDocument (GoogleDocument document) {
        this.document = document;
    }

    public String getEncodingType () {
        return encodingType;
    }

    public void setEncodingType (String encodingType) {
        this.encodingType = encodingType;
    }

    @Override
    public String toString()
    {
        return "GoogleAPIRequest [document = "+document+", encodingType = "+encodingType+"]";
    }
}
