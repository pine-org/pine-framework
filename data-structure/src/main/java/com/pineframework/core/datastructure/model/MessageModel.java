package com.pineframework.core.datastructure.model;

public class MessageModel extends AbstractTransient<Long, Integer> {

    private String correlationId;

    private String status;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
