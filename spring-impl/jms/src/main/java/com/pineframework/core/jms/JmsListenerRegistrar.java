package com.pineframework.core.jms;

import com.pineframework.core.business.jms.JmsListenertHolder;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

import java.util.stream.Stream;

public abstract class JmsListenerRegistrar implements JmsListenerConfigurer {

    public abstract JmsListenertHolder[] getEndpoint();

    public SimpleJmsListenerEndpoint convertToEndpoint(JmsListenertHolder holder) {
        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
        endpoint.setId(holder.getId());
        endpoint.setDestination(holder.getDestination());
        endpoint.setMessageListener(holder.getListener());
        return endpoint;
    }

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
        Stream.of(getEndpoint())
                .map(this::convertToEndpoint)
                .forEach(registrar::registerEndpoint);
    }
}