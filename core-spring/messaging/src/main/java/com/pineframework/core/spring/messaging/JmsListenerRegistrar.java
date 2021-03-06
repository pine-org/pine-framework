package com.pineframework.core.spring.messaging;

import com.pineframework.core.business.jms.JmsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

import java.util.stream.Stream;

@Configuration
@Profile(value = "messaging")
public class JmsListenerRegistrar implements JmsListenerConfigurer {

    @Autowired
    public JmsListener[] listeners;

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
        Stream.of(listeners).map(this::createEndpoint).forEach(registrar::registerEndpoint);
    }

    public SimpleJmsListenerEndpoint createEndpoint(JmsListener listener) {
        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
        endpoint.setId(listener.getId());
        endpoint.setDestination(listener.getDestination());
        endpoint.setMessageListener(listener.getProcess());
        return endpoint;
    }
}