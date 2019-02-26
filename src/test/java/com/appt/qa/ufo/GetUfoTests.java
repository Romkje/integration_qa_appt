package com.appt.qa.ufo;

import com.appt.qa.BaseTest;
import com.appt.qa.common.core.Groups;
import com.appt.qa.ufo.clients.UfoClient;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class GetUfoTests extends BaseTest {
    private UfoClient ufoClient;

    @BeforeClass
    public void init(){
        ufoClient = new UfoClient();
    }

    @Test(groups = {Groups.UFO_SERVICE})
    @Description("Should return 415 status code when use html/text content type")
    public void shouldReturn415StatusCodeWhenUseHtmlTextContentType() {
        Map<String, String> params = new HashMap<>();
        params.put("shape", "triangle");
        ufoClient
                .usingContentType("html/text")
                .getUfo(params)
                .expectingStatusCode(415);
    }

    @Test(groups = {Groups.UFO_SERVICE})
    @Description("Should return 400 status code when search by not existing field")
    public void shouldReturn400StatusCodeWhenSearchByNotExistingField() {
        Map<String, String> params = new HashMap<>();
        params.put("shape1", "triangle");
        ufoClient
                .getUfo(params)
                .expectingStatusCode(400);
    }
}
