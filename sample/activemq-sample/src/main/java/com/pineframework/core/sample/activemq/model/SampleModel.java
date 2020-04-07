package com.pineframework.core.sample.activemq.model;

import com.pineframework.core.datastructure.model.messaging.MessageModel;

public class SampleModel extends MessageModel {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
