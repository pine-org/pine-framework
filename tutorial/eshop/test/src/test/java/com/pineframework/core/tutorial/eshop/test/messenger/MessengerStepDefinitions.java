package com.pineframework.core.tutorial.eshop.test.messenger;

import io.cucumber.java8.En;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class MessengerStepDefinitions implements En {

    private final Logger logger = LoggerFactory.getLogger(MessengerStepDefinitions.class.getSimpleName());

    private Person speaker;

    private Person listener;

    private String message;

    public MessengerStepDefinitions() {


        Given("{person} is talking to {person}", (Person speaker, Person listener) -> {
            this.speaker = speaker;
            this.listener = listener;
        });

        When("It generate {}", (String greeting) -> {
            this.message = format("%s%s:%s",
                    speaker.getName(),
                    (listener.represent ? " > " : "") + listener.getName(),
                    greeting).trim();
        });

        Then("It should be {}", (String message) -> {
            logger.info(String.format("%s", speaker, message));
            Assertions.assertEquals(message, this.message);
        });

        ParameterType("person", "[a-zA-Z0-9]*\\s*,true|[a-zA-Z0-9]*\\s*,false", (String str) -> {
            String[] args = str.split(",");
            return new Person(args[0].trim(), Boolean.valueOf(args[1].trim()));
        });
    }

    static final class Person {

        private final String name;
        private final Boolean represent;

        public Person(String name, Boolean represent) {
            this.name = name;
            this.represent = represent;
        }

        public String getName() {
            return represent ? name : "";
        }
    }
}
