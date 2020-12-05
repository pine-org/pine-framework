package com.pineframework.core.contract.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In order to add log ability to a class.
 *
 * @author Saman Alishirishahrbabak
 */
public interface Loggable {

    Logger getLogger();

    default Logger defaultLogger() {
        return LoggerFactory.getLogger(getClass());
    }

    default void info(Object model) {
        getLogger().info("LOG [INFO]: {}", model);
    }

    default void debug(Object model) {
        getLogger().debug("LOG [DEBUG]: {}", model);
    }

    default void error(Object model) {
        getLogger().error("LOG [ERROR]: {}", model);
    }
}