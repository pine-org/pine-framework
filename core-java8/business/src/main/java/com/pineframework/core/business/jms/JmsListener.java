package com.pineframework.core.business.jms;

import javax.jms.MessageListener;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface JmsListener {

    /**
     * Get listener name.
     *
     * @return string
     */
    String getId();

    /**
     * Get queue name.
     *
     * @return string
     */
    String getDestination();

    /**
     * @return message processor
     */
    MessageListener getProcess();
}
