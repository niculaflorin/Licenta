package com.google.cognitive.com.google.cognitive.cloud.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface GoogleAPIRestClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GoogleAPIResponse getSentiment(GoogleAPIRequest googleRequest);
}
