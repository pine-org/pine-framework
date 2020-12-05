package com.pineframework.core.tutorial.eshop.test.helloworld;

import io.cucumber.java8.En;
import org.junit.jupiter.api.Assertions;

import static java.lang.String.format;

public class HelloWorldStepDefinitions implements En {

    private String name;

    private String answer;

    public HelloWorldStepDefinitions() {
        Given("a {string}", (String string) -> name = string);

        When("generate sentence", () -> answer = format("Hello %s", name));

        Then("it should be {string}", (String string) -> {
            Assertions.assertEquals("Hello World", answer);
        });
    }
}
