package com.step.defs;

import com.service.Calculator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculatorStepDef {

    private Calculator calculator;
    private int result;

    @Before("@calculator")
    public void calculatorSetup() {
        log.info("Calculator setup");
    }

    @After("@calculator")
    public void calculatorTeardown() {
        log.info("Calculator teardown");
    }

    @Given("two number")
    public void given() {
        calculator = new Calculator();
    }

    @When("Add two numbers")
    public void when() {
        result = calculator.sum(10, 20);
    }

    @Then("Get another number")
    public void then() {
        log.info("result={}", result);
    }
}