package com.pineframework.core.jms;

import com.pineframework.core.business.jms.JmsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

import java.util.stream.Stream;

@Configuration
public class JmsListenerRegistrar implements JmsListenerConfigurer {

    @Autowired
    public JmsListener[] listeners;

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
        Stream.of(listeners).map(this::CreateEndpoint).forEach(registrar::registerEndpoint);
    }

    public SimpleJmsListenerEndpoint CreateEndpoint(JmsListener listener) {
        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
        endpoint.setId(listener.getId());
        endpoint.setDestination(listener.getDestination());
        endpoint.setMessageListener(listener.getProcess());
        return endpoint;
    }
}