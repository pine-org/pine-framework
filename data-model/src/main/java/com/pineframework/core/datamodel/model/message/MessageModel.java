package com.pineframework.core.datamodel.model.message;

import com.pineframework.core.datamodel.model.FlatTransient;

public abstract class MessageModel extends FlatTransient<String> {

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
