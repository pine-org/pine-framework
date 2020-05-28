package com.pineframework.core.tutorial.eshop.business.helper;

import com.pineframework.core.datamodel.model.message.MqStatus;
import com.pineframework.core.tutorial.eshop.model.MessageModel;

public class StatusHelper {

    public static MqStatus toEnum(MessageModel status) {
        return MqStatus.valueOf(String.valueOf(status.getContent()));
    }
}
