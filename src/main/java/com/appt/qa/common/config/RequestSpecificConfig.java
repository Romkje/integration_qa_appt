package com.appt.qa.common.config;

import org.apache.commons.lang3.StringUtils;

public class RequestSpecificConfig {
    private String servicePath;
    private String contentType;
    private String authHeader;

    public RequestSpecificConfig(RequestSpecificConfig other) {
        this(other.servicePath, other.contentType, other.authHeader);
    }

    public RequestSpecificConfig(String servicePath, String contentType, String authHeader) {
        if (StringUtils.isEmpty(servicePath)) {
            throw new IllegalArgumentException("Not valid service path = " + servicePath + ". Probably an configuration issue");
        }
        this.servicePath = servicePath;
        this.contentType = contentType;
        this.authHeader = authHeader;
    }

    public String getServicePath() {
        return servicePath;
    }

    public String getContentType() {
        return contentType;
    }

    public String getAuthHeader() {
        return authHeader;
    }

    public RequestSpecificConfig withServicePath(String servicePath) {
        RequestSpecificConfig configuration = new RequestSpecificConfig(this);
        configuration.servicePath = servicePath;
        return configuration;
    }

    public RequestSpecificConfig withContentType(String contentType) {
        RequestSpecificConfig configuration = new RequestSpecificConfig(this);
        configuration.contentType = contentType;
        return configuration;
    }

    public RequestSpecificConfig withAuthHeader(String authHeader) {
        RequestSpecificConfig configuration = new RequestSpecificConfig(this);
        configuration.authHeader = authHeader;
        return configuration;
    }
}
