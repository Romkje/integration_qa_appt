package com.appt.qa.ufo.clients;

import com.appt.qa.common.clients.ResponseWrapper;
import com.appt.qa.common.clients.RestClient;
import com.appt.qa.common.config.RequestSpecificConfig;
import com.appt.qa.ufo.dto.Ufo;
import io.qameta.allure.Step;

import javax.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UfoClient extends RestClient<UfoClient> {
    private static final String UFOS = "/ufos";
    private static final String UFO = "/ufo";
    private static final String URL = "/";

    @Override
    protected RequestSpecificConfig getDefaultConfiguration() {
        return new RequestSpecificConfig(clientConfiguration.ufoServiceUrl(), "application/json", "");
    }

    @Override
    protected UfoClient getThis() {
        return this;
    }

    @Step("Create ufo")
    public ResponseWrapper<Long> createUfo(Ufo ufo) {
        return post(UFO, new GenericType<>(Long.class), ufo);
    }

    @Step("Create ufos")
    public ResponseWrapper<List<Long>> createUfos(List<Ufo> ufos) {
        return post(UFOS, new GenericType<List<Long>>(){}, ufos);
    }

    @Step("Get all ufos")
    public ResponseWrapper<List<Ufo>> getAllUfos() {
        return get(UFOS, new GenericType<List<Ufo>>(){});
    }

    @Step("Get ufo")
    public ResponseWrapper<List<Ufo>> getUfo(Map<String, String> params) {
        return get(URL, new GenericType<List<Ufo>>(){}, params);
    }

    @Step("Get ufo by id")
    public ResponseWrapper<Ufo> getUfoById(Long id) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("id", String.valueOf(id));
        return get(UFO, new GenericType<>(Ufo.class), queryParams);
    }

    @Step("Delete ufo")
    public ResponseWrapper<String> deleteUfo(Long id) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("id", String.valueOf(id));
        return delete(UFO, new GenericType<>(String.class), queryParams);
    }

    @Step("Update ufo")
    public ResponseWrapper<Ufo> updateUfo(Long id, Ufo ufo) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("id", String.valueOf(id));
        return put(UFO, new GenericType<>(Ufo.class), queryParams, ufo);
    }
}