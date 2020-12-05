package com.pineframework.core.tutorial.eshop.test.helloworld;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/helloworld/hello_world.feature")
public class HelloWorldCucumberTest {


}