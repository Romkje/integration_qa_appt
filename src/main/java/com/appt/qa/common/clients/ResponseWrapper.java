package com.appt.qa.common.clients;


import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class ResponseWrapper<T> {
    protected final Response response;
    protected final GenericType<T> genericType;
    protected final String path;

    ResponseWrapper(Response response, GenericType<T> genericType, String path) {
        this.response = response;
        this.genericType = genericType;
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public Response getResponse() {
        return response;
    }

    public T readEntity() {
        return response.readEntity(genericType);
    }

    public ResponseWrapper<T> expectingStatusCode(int statusCode) {
        assertThat("Response code differs", response.getStatus(), is(statusCode));
        return this;
    }

    public ResponseWrapper<T> expectingContentType(String contentType) {
        MediaType mediaType = response.getMediaType();
        assertThat("Content type differs", mediaType.getType() + "/" + mediaType.getSubtype(), is(contentType));
        return this;
    }

}
