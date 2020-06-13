package com.pineframework.core.business.jms;

import javax.jms.MessageListener;

public interface JmsListener {

    String getId();

    String getDestination();

    MessageListener getProcess();
}
