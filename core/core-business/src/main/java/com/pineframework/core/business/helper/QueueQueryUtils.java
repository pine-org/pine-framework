package com.pineframework.core.business.helper;

import java.io.Serializable;

import static java.lang.String.format;

/**
 * @author Saman Alishirishahrbabak
 */
public class QueueQueryUtils {

    public static <I extends Serializable> String correlationIdClause(I id) {
        return format("JMSCorrelationID = '%s'", id);
    }
}