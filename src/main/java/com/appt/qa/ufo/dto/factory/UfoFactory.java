package com.appt.qa.ufo.dto.factory;

import com.appt.qa.ufo.dto.Ufo;
import com.github.javafaker.Faker;

public class UfoFactory {
    public static Ufo produceDefaultUfo(String shape){
        Faker faker = new Faker();
        return Ufo.builder()
                .city(faker.address().city())
                .state(faker.address().state())
                .country(faker.address().country())
                .latitude(Double.valueOf(faker.address().latitude()))
                .longitude(Double.valueOf(faker.address().longitude()))
                .durationSeconds(faker.number().randomDouble(1, 1, 120))
                .description(faker.chuckNorris().fact())
                .durationText(faker.harryPotter().character())
                .reportedOn(faker.harryPotter().location())
                .occurredAt(faker.gameOfThrones().city())
                .shape(shape)
                .build();
    }
}
