package com.pineframework.core.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class AbstractTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void infoLog(Object o) {
        logger.info(format("LOG: %s", o));
    }
}
