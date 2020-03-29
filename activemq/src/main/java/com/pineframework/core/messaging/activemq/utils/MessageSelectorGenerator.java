package com.pineframework.core.messaging.activemq.utils;

import static java.lang.String.format;

public class MessageSelectorGenerator {

    public static String correlationIdSelectors(String correlationId) {
        return format("JMSCorrelationID = '%s'", correlationId);
    }
}
