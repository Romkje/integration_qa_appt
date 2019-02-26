package com.appt.qa.ufo.matchers;

import com.appt.qa.common.matchers.AbstractMatcher;
import com.appt.qa.ufo.dto.Ufo;

public class UfoMatcher extends AbstractMatcher<Ufo> {

    private UfoMatcher(Ufo actual, Ufo expected) {
        super(actual, expected);
    }

    public static UfoMatcher getMatcher(Ufo actual, Ufo expected){
        return new UfoMatcher(actual, expected);
    }

    @Override
    protected void addDefaultConditions() {
        addFieldToValidate(Ufo::getOccurredAt, "occurred_at");
        addFieldToValidate(Ufo::getCity, "city");
        addFieldToValidate(Ufo::getCountry, "country");
        addFieldToValidate(Ufo::getState, "state");
        addFieldToValidate(Ufo::getShape, "shape");
        addFieldToValidate(Ufo::getDescription, "description");
        addFieldToValidate(Ufo::getLatitude, "latitude");
        addFieldToValidate(Ufo::getLongitude, "longitude");
        addFieldToValidate(Ufo::getReportedOn, "reported_on");
        addFieldToValidate(Ufo::getDurationText, "duration_text");
        addFieldToValidate(Ufo::getDurationSeconds, "duration_seconds");
    }

    public UfoMatcher withNotNullId(){
        softAssert.assertTrue(actual.getId() != null);
        return this;
    }
}
