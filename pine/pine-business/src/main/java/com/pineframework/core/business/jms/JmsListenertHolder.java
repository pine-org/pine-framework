package com.pineframework.core.business.jms;

import javax.jms.MessageListener;

public interface JmsListenertHolder {

    String getId();

    String getDestination();

    MessageListener getListener();
}
