package com.pineframework.core.messaging.activemq.service;

import com.pineframework.core.datastructure.model.AbstractTransient;
import org.slf4j.Logger;

import static java.lang.String.format;

public interface Loggable {

    default void infoLog(AbstractTransient model) {
        getLogger().info(format("LOG: %s", model));
    }

    Logger getLogger();
}
