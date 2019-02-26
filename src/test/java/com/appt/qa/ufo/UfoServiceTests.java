package com.appt.qa.ufo;

import com.appt.qa.BaseTest;
import com.appt.qa.common.core.Groups;
import com.appt.qa.ufo.clients.UfoClient;
import com.appt.qa.ufo.dto.Ufo;
import com.appt.qa.ufo.matchers.UfoMatcher;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.appt.qa.ufo.dto.factory.UfoFactory.produceDefaultUfo;
import static org.testng.Assert.*;

public class UfoServiceTests extends BaseTest {
    private UfoClient ufoClient;

    @BeforeClass
    public void init(){
        ufoClient = new UfoClient();
    }

    @Test(groups = {Groups.UFO_SERVICE})
    @Description("Should return Ufo filtered by shape")
    public void shouldReturnUfoByShape() {
        String ufoShape = "triangle";
        Ufo given = produceDefaultUfo(ufoShape);
        ufoClient
                .createUfo(given)
                .expectingStatusCode(200)
                .expectingContentType("html/json")
                .readEntity();

        Map<String, String> param = new HashMap<>();
        param.put("shape", "triangle");
        List<Ufo> ufos = ufoClient
                .getUfo(param)
                .expectingStatusCode(200)
                .readEntity();

        assertTrue(ufos.size() > 0, "Ufos not found, shape=" + ufoShape);

        ufos.forEach(
                u -> assertEquals(u.getShape(), ufoShape, "Ufo shape is not " + ufoShape)
        );
    }

    @Test(groups = {Groups.UFO_SERVICE})
    @Description("Should return all existing Ufos")
    public void shouldReturnAllUfos() {
        Ufo givenCircle = produceDefaultUfo("circle");
        Ufo givenRhombus = produceDefaultUfo("rhombus");

        ufoClient
                .createUfos(Arrays.asList(givenCircle, givenRhombus))
                .expectingStatusCode(200)
                .expectingContentType("html/json")
                .readEntity();

        List<Ufo> ufos = ufoClient
                .getAllUfos()
                .expectingStatusCode(200)
                .readEntity();

        assertTrue(ufos.size() >= 2, "Ufos not exists");
    }

    @Test(groups = {Groups.UFO_SERVICE})
    @Description("Should create ufo")
    public void shouldCreateUfo() {
        Ufo given = produceDefaultUfo("triangle");

        Long actual = ufoClient
                .createUfo(given)
                .expectingStatusCode(200)
                .readEntity();

        assertNotNull(actual, "Ufo not created");
    }

    @Test(groups = {Groups.UFO_SERVICE})
    @Description("Should return existing ufo by id")
    public void shouldReturnExistingUfo() {
        Ufo given = produceDefaultUfo("square");
        Long id = ufoClient
                .createUfo(given)
                .expectingStatusCode(200)
                .readEntity();

        Ufo actual = ufoClient
                .getUfoById(id)
                .expectingStatusCode(200)
                .readEntity();

        UfoMatcher.getMatcher(actual, given).validateConditions();
    }

    @Test(groups = {Groups.UFO_SERVICE})
    @Description("Should create list of Ufos")
    public void shouldCreateListOfUfos() {
        Ufo givenCircle = produceDefaultUfo("circle");
        Ufo givenRhombus = produceDefaultUfo("rhombus");

        List<Long> actual = ufoClient
                .createUfos(Arrays.asList(givenCircle, givenRhombus))
                .expectingStatusCode(200)
                .readEntity();

        assertEquals(actual.size(), 2, "Ufos not created");
    }

    @Test(groups = {Groups.UFO_SERVICE})
    @Description("Should delete ufo by id")
    public void shouldDeleteUfo() {
        Ufo given = produceDefaultUfo("triangle");

        Long id = ufoClient
                .createUfo(given)
                .expectingStatusCode(200)
                .readEntity();

        String response = ufoClient
                .deleteUfo(id)
                .expectingStatusCode(200)
                .readEntity();

        assertEquals(response, "annnnnnnnd its gone");

        ufoClient
                .getUfoById(id)
                .expectingStatusCode(404);
    }

    @Test(groups = {Groups.UFO_SERVICE})
    @Description("Should update Ufo")
    public void shouldUpdateUfo() {
        Ufo given = produceDefaultUfo("triangle");

        Long id = ufoClient
                .createUfo(given)
                .expectingStatusCode(200)
                .readEntity();

        Ufo givenUpdated = produceDefaultUfo("square");
        givenUpdated.setId(id);

        Ufo actualUpdated = ufoClient
                .updateUfo(id, givenUpdated)
                .expectingStatusCode(200)
                .readEntity();

        UfoMatcher.getMatcher(givenUpdated, actualUpdated).validateConditions();
    }
}
