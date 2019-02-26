package com.appt.qa.common.matchers;

import org.testng.asserts.SoftAssert;

import java.util.function.Function;

public abstract class AbstractMatcher<T> {
    protected final SoftAssert softAssert = new SoftAssert();
    protected final T actual;
    protected final T expected;

    protected AbstractMatcher(T actual, T expected) {
        this.actual = actual;
        this.expected = expected;
        addDefaultConditions();
    }

    protected AbstractMatcher(T actual) {
        this.actual = actual;
        this.expected = null;
    }

    public void validateConditions() {
        softAssert.assertAll();
    }

    abstract protected void addDefaultConditions();

    protected <F> void addFieldToValidate(Function<T, F> function) {
        addFieldToValidate(function, "");
    }

    protected <F> void addFieldToValidate(Function<T, F> function, String fieldName) {
        softAssert.assertEquals(function.apply(actual), function.apply(expected),
                "Unexpected value of field \"" + fieldName + "\"" );
    }

}
