package com.appt.qa.ufo;

import com.appt.qa.BaseTest;
import com.appt.qa.common.core.Groups;
import com.appt.qa.ufo.clients.UfoClient;
import com.appt.qa.ufo.dto.factory.UfoFactory;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UpdateUfoTests extends BaseTest {
    private UfoClient ufoClient;

    @BeforeClass
    public void init(){
        ufoClient = new UfoClient();
    }

    @Test(groups = {Groups.UFO_SERVICE})
    @Description("Should return 500 status code when update Ufo with null id")
    public void shouldReturn500StatusCodeWhenUpdateUfoWithNullId() {
        ufoClient
                .updateUfo(null, UfoFactory.produceDefaultUfo("triangle"))
                .expectingStatusCode(500);
    }
}
