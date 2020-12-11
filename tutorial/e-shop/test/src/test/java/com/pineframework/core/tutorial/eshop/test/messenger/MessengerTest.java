package com.pineframework.core.tutorial.eshop.test.messenger;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/messenger.feature",
        glue = "com.pineframework.core.tutorial.eshop.test.messenger",
        tags = "@MessengerFeature",
        plugin = {"pretty"})
public class MessengerTest {


}