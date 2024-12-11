package org.ticket.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // Marks this class as a configuration class for Spring
@EnableWebSocketMessageBroker // Enables WebSocket message handling with a simple broker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Configures the message broker for WebSocket communication
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Enable a simple broker for destinations starting with "/topic"
        config.setApplicationDestinationPrefixes("/app"); // Prefix for messaging to the server (e.g., "/app/sendMessage")
    }

    // Registers the STOMP endpoints for WebSocket communication
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS(); // Define WebSocket endpoint at "/ws" with SockJS fallback support
    }
}