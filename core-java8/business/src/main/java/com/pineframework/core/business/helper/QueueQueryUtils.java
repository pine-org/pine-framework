package com.pineframework.core.business.helper;

import java.io.Serializable;

import static java.lang.String.format;

/**
 * @author Saman Alishirishahrbabak
 */
public final class QueueQueryUtils {

    private QueueQueryUtils() {
    }

    /**
     * Generate where clause for correlation ID to use in message filtering that pushed in a queue.
     *
     * @param id  correlation ID
     * @param <I> correlation ID type
     * @return string
     */
    public static <I extends Serializable> String correlationIdClause(I id) {
        return format("JMSCorrelationID = '%s'", id);
    }
}