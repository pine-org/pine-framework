package com.pineframework.core.contract.log;

import org.slf4j.Logger;

import static java.lang.String.format;

/**
 * @author Saman Alishirishahrbabak
 */
public interface Loggable {

    default void logInfo(Object model) {
        getLogger().info(format("LOG [INFO]: %s", model));
    }

    default void logDebug(Object model) {
        getLogger().debug(format("LOG [DEBUG]: %s", model));
    }

    default void logError(Object model) {
        getLogger().error(format("LOG [ERROR]: %s", model));
    }

    Logger getLogger();
}
