package com.pineframework.core.datastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public abstract class AbstractBusiness {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void infoLog(AbstractModel model) {
        logger.info(format("LOG: %s", model));
    }
}
