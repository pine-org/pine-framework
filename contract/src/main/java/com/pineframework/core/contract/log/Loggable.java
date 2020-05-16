package com.pineframework.core.contract.log;

import com.pineframework.core.datamodel.model.FlatTransient;
import org.slf4j.Logger;

import static java.lang.String.format;

/**
 * @author Saman Alishirishahrbabak
 */
public interface Loggable {

    default void infoLog(FlatTransient model) {
        getLogger().info(format("LOG: %s", model));
    }

    default void infoLog(String str) {
        getLogger().info(format("LOG: %s", str));
    }

    Logger getLogger();
}
