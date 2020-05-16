package com.pineframework.core.datamodel.model.message;

public enum MqStatus {
    ACCEPTED("Accepted"),
    ;

    private final String status;

    MqStatus(String status) {
        this.status = status;
    }

    public static MqStatus instanceOf(String value) {
        return valueOf(value.toUpperCase());
    }

    public String getValue() {
        return status;
    }
}
