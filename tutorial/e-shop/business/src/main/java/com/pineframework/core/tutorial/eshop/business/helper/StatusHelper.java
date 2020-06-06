package com.pineframework.core.tutorial.eshop.business.helper;

import com.pineframework.core.datamodel.model.message.MessageModel;
import com.pineframework.core.datamodel.model.message.MqStatus;

public class StatusHelper {

    public static MqStatus toEnum(MessageModel status) {
        return MqStatus.valueOf(String.valueOf(status.getContent()));
    }
}
