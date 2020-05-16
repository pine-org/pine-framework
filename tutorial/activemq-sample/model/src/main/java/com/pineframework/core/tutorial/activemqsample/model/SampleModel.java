package com.pineframework.core.tutorial.activemqsample.model;

import com.pineframework.core.datastructure.model.message.MessageModel;

public class SampleModel extends MessageModel {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
