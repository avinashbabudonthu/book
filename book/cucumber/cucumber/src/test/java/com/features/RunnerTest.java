package com.features;

import io.cucumber.junit.platform.engine.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Slf4j
@Suite

// tells JUnit to use the Cucumber engine
@IncludeEngines("cucumber")

// - points to your .feature files
@SelectClasspathResource("features")
//@SelectPackages("features")

// package name where your step definition classes live
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.step.defs")

// "pretty" gives you a readable console output. You can also use "json", "html", "junit" etc
// useful for better visibility or reporting
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report.html")
public class RunnerTest {
}