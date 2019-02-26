package com.appt.qa.common.clients;


import com.appt.qa.common.clients.factory.ClientFactory;
import com.appt.qa.common.config.ClientConfiguration;
import com.appt.qa.common.config.RequestSpecificConfig;
import com.appt.qa.common.core.CommonConstants;
import org.aeonbits.owner.ConfigFactory;
import org.glassfish.jersey.message.internal.HttpHeaderReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RestClient<Impl extends RestClient<Impl>> {
    private final Client client = createCoreClient();
    private RequestSpecificConfig requestSpecificConfig;
    private Map<String, Object> headers = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();
    protected final ClientConfiguration clientConfiguration;

    public RestClient() {
        this.clientConfiguration = ConfigFactory.create(ClientConfiguration.class);
        ConfigFactory.clearProperty(CommonConstants.CONFIG_SERVICE_SPECIFIC);
        requestSpecificConfig = getDefaultConfiguration();
    }

    protected abstract RequestSpecificConfig getDefaultConfiguration();

    protected abstract Impl getThis();

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    protected Client createCoreClient() {
        return ClientFactory.createClient();
    }

    protected <F> ResponseWrapper<F> get(String path, GenericType<F> genericType) {
        return get(path, genericType, new HashMap<>());
    }

    protected <F> ResponseWrapper<F> get(String path, GenericType<F> genericType, Map<String, String> queryParams) {
        return executeRequest(path, genericType, queryParams, "GET", null);
    }

    protected <F> ResponseWrapper<F> delete(String path, GenericType<F> genericType) {
        return delete(path, genericType, new HashMap<>());
    }

    protected <F> ResponseWrapper<F> delete(String path, GenericType<F> genericType, Map<String, String> queryParams) {
        return executeRequest(path, genericType, queryParams, "DELETE", null);
    }

    protected <T, F> ResponseWrapper<F> post(String path, GenericType<F> genericType, T dto) {
        return post(path, genericType, new HashMap<>(), dto);
    }

    protected <T, F> ResponseWrapper<F> post(String path, GenericType<F> genericType, Map<String, String> queryParams, T dto) {
        return executeRequest(path, genericType, queryParams, "POST", dto);
    }

    protected <T, F> ResponseWrapper<F> put(String path, GenericType<F> genericType, T dto) {
        return put(path, genericType, new HashMap<>(), dto);
    }

    protected <T, F> ResponseWrapper<F> put(String path, GenericType<F> genericType, Map<String, String> queryParams, T dto) {
        return executeRequest(path, genericType, queryParams, "PUT", dto);
    }

    protected <T, F> ResponseWrapper<F> patch(String path, GenericType<F> genericType, T dto) {
        return patch(path, genericType, new HashMap<>(), dto);
    }

    protected <T, F> ResponseWrapper<F> patch(String path, GenericType<F> genericType, Map<String, String> queryParams, T dto) {
        return executeRequest(path, genericType, queryParams, "PATCH", dto);
    }

    public void addHeader(String key, Object value) {
        headers.put(key, value);
    }

    protected Object getHeader(String key) {
        return headers.get(key);
    }

    public Impl usingContentType(String contentType) {
        requestSpecificConfig = requestSpecificConfig.withContentType(contentType);
        return getThis();
    }

    public Impl usingContentType(MediaType contentType) {
        return usingContentType(contentType.toString());
    }

    public Impl usingAuthHeader(String authHeader) {
        requestSpecificConfig = requestSpecificConfig.withAuthHeader(authHeader);
        return getThis();
    }

    /**
     * The main method to perform http call
     *
     * @param path        resource path
     * @param genericType class of expected response
     * @param queryParams query params
     * @param method      http method
     * @param dto         payload
     * @param <T>         type of request
     * @param <F>         type of class
     * @return ResponseWrapper
     */
    protected <T, F> ResponseWrapper<F> executeRequest(
            String path,
            GenericType<F> genericType,
            Map<String, String> queryParams,
            String method,
            T dto) {
        RequestSpecificConfig configuration = new RequestSpecificConfig(requestSpecificConfig);
        requestSpecificConfig = getDefaultConfiguration();

        WebTarget target = client.target(configuration.getServicePath()).path(path);
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            target = target.queryParam(entry.getKey(), entry.getValue());
        }

        Invocation.Builder builder = target.request();
        builder.header(HttpHeaders.AUTHORIZATION, configuration.getAuthHeader());
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }
        headers.clear();

        if (!cookies.isEmpty()) {
            StringBuilder cookieHeader = new StringBuilder();
            for (String cookie : cookies.values()) {
                cookieHeader.append(cookie).append("; ");
            }
            builder.header(HttpHeaders.COOKIE, cookieHeader);
        }

        Invocation invocation = builder.build(method, Entity.entity(dto, configuration.getContentType()));
        Response response = invocation.invoke();

        saveResponseCookies(response);

        return new ResponseWrapper<>(response, genericType, path);
    }

    private void saveResponseCookies(Response response) {
        List<Object> newCookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (newCookies != null && !newCookies.isEmpty()) {
            newCookies.sort(Comparator.comparingInt(c -> c.toString().length()));
            for (Object cookie : newCookies) {
                if (cookie != null) {
                    NewCookie newCookie = HttpHeaderReader.readNewCookie(cookie.toString());
                    this.cookies.put(newCookie.getName(), cookie.toString());
                }
            }
        }
    }

}
