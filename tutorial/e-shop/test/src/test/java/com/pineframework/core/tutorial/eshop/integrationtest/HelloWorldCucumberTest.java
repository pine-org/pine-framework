package com.pineframework.core.tutorial.eshop.integrationtest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/hello_world.feature")
public class HelloWorldCucumberTest {


}