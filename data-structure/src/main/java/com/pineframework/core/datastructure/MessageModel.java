package com.pineframework.core.datastructure;

public class MessageModel extends AbstractModel<Long> {

    private String correlationID;
    private String status;

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
