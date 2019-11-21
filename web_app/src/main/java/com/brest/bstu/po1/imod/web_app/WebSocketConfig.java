package com.brest.bstu.po1.imod.web_app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        LOGGER.debug("configureMessageBroker({})", config);
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
        LOGGER.debug("configureMessageBroker() returned.");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        LOGGER.debug("registerStompEndpoints({})", registry);
        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("*").withSockJS();
        LOGGER.debug("registerStompEndpoints() returned.");
    }

}
