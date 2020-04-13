package com.pineframework.core.datastructure.model.messaging;

import com.pineframework.core.datastructure.model.FlatTransient;

public class MessageModel extends FlatTransient<Long> {

    private String correlationId;

    private MqStatus status;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public MqStatus getStatus() {
        return status;
    }

    public void setStatus(MqStatus status) {
        this.status = status;
    }
}
