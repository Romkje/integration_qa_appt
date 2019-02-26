package com.appt.qa;

import com.appt.qa.common.core.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Slf4j
public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext testContext) {
        log.info("Suite <" + testContext.getSuite().getName() + "> started");
        ConfigFactory.setProperty(CommonConstants.CONFIG_DEFAULT_ENV, System.getProperty("env", "dev"));
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite(ITestContext testContext) {
        log.info("Suite <" + testContext.getSuite().getName() + "> ended");
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext testContext) {
        log.info("Test <<" + testContext.getCurrentXmlTest().getName() + ">> started");
    }

    @AfterTest(alwaysRun = true)
    public void afterTest(ITestContext testContext) {
        log.info("Test <<" + testContext.getCurrentXmlTest().getName() + ">> ended");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        log.info("Test method <<<" + method.getName() + ">>> started");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResult, Method method) {
        log.info("Test method <<<" + method.getName() + ">>> ended");
    }

}
