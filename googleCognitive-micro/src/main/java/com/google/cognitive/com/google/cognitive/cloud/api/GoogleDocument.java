package com.google.cognitive.com.google.cognitive.cloud.api;

public class GoogleDocument {

    private String content;

    private String language;

    private String type;

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public String getLanguage () {
        return language;
    }

    public void setLanguage (String language) {
        this.language = language;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "GoogleDocument [content = "+content+", language = "+language+", type = "+type+"]";
    }
}
