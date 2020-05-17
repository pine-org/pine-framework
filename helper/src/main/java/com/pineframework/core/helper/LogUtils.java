package com.pineframework.core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogUtils {
    private LogUtils() {
    }

    /**
     * Return a slf4j logger instance
     *
     * @param clazz <description>Class</description>
     * @return <description>Logger</description>
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Return a slf4j logger instance
     *
     * @param name <description>logger name</description>
     * @return <description>Logger</description>
     */
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }
}
