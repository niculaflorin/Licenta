package com.google.cognitive.com.google.cognitive.cloud.api;

import com.google.cognitive.domain.DocumentSentiment;
import com.google.cognitive.domain.InitialText;
import com.google.cognitive.domain.SentencesSentiment;
import com.google.cognitive.repository.DocumentSentimentRepository;
import com.google.cognitive.repository.InitialTextRepository;
import com.google.cognitive.repository.SentencesSentimentRepository;
import com.google.cognitive.web.rest.DocumentSentimentResource;
import com.google.cognitive.web.rest.InitialTextResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class GoogleAPIService {

    private static final Logger log = LoggerFactory.getLogger(GoogleAPIService.class);

    private static final String KEY = "AIzaSyCpjJD0MUi3TRWgpBg6E-L3TPgt70_6ssc";
    private static final String URL = "https://language.googleapis.com/v1/documents:analyzeSentiment?fields=documentSentiment%2Clanguage%2Csentences&key=";
    private static final String UTF_8 = "UTF8";
    private static final String TYPE = "PLAIN_TEXT";
    public static final String LANGUAGE = "en";

    private static DocumentSentimentRepository documentSentimentRepository;
    private static SentencesSentimentRepository sentencesSentimentRepository;

    @Autowired
    public GoogleAPIService(DocumentSentimentRepository documentSentimentRepository, SentencesSentimentRepository sentencesSentimentRepository) {
        this.documentSentimentRepository = documentSentimentRepository;
        this.sentencesSentimentRepository = sentencesSentimentRepository;
    }

    @Transactional
    public InitialText parseInitialText(InitialText initialText) {
        GoogleAPIRequest googleRequest = createRequest(initialText);
//        The Jersey way
        Client client = Client.create();
        WebResource webResource = client
            .resource(URL + KEY);
        try {
            ClientResponse clientResponse = webResource.type("application/json")
                .post(ClientResponse.class, googleRequest);
            GoogleAPIResponse googleResponse = clientResponse.getEntity(GoogleAPIResponse.class);
            DocumentSentiment documentSentiment = getDocumentSentiment(googleResponse);
            log.error("REpository: " + documentSentimentRepository);
            DocumentSentiment DCresult = documentSentimentRepository.save(documentSentiment);
            log.info("Status DocumentSentiment: " + DCresult.toString());
            initialText.setInitial(documentSentiment);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return initialText;
    }

    private static DocumentSentiment getDocumentSentiment(GoogleAPIResponse googleResponse) {
        DocumentSentiment documentSentiment = new DocumentSentiment();
        documentSentiment.setLanguage(googleResponse.getLanguage());
        documentSentiment.setMagnitude(googleResponse.getDocumentSentiment().getMagnitude());
        documentSentiment.setScore(googleResponse.getDocumentSentiment().getScore());
        Set<SentencesSentiment> sentencesSentiments = getSentencesSentiments(googleResponse, documentSentiment);
        documentSentiment.setSentences(sentencesSentiments);
        return documentSentiment;
    }

    private static Set<SentencesSentiment> getSentencesSentiments(GoogleAPIResponse googleResponse, DocumentSentiment documentSentiment) {
        Set<SentencesSentiment> sentencesSentiments = new HashSet<>();
        SentencesSentiment sentencesSentiment;
        for (GoogleSentences sentence : googleResponse.getSentences()) {
            sentencesSentiment = new SentencesSentiment();
            sentencesSentiment.setContent(sentence.getText().getContent());
            sentencesSentiment.setMagnitude(sentence.getSentiment().getMagnitude());
            sentencesSentiment.setScore(sentence.getSentiment().getScore());
            sentencesSentiment.setDocumentSentiment(documentSentiment);
            sentencesSentimentRepository.save(sentencesSentiment);
            sentencesSentiments.add(sentencesSentiment);
        }
        return sentencesSentiments;
    }

    private static GoogleAPIRequest createRequest(InitialText initialText) {
        GoogleAPIRequest googleRequest = new GoogleAPIRequest();
        googleRequest.setEncodingType(UTF_8);
        GoogleDocument document = new GoogleDocument();
        document.setContent(initialText.getText());
        document.setLanguage(LANGUAGE);
        document.setType(TYPE);
        googleRequest.setDocument(document);
        return googleRequest;
    }

}
