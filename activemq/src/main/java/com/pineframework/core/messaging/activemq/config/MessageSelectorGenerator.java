package com.pineframework.core.messaging.activemq.config;

import static java.lang.String.format;

public class MessageSelectorGenerator {

    public static String correlationIDSelectors(String correlationID) {
        return format("JMSCorrelationID = '%s'", correlationID);
    }
}
