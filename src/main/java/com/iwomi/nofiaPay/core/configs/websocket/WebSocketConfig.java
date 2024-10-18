package com.iwomi.nofiaPay.core.configs.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*");
//                .setAllowedOriginPatterns("http://localhost:3000/");

//        registry.addEndpoint("/ws")
//                .setAllowedOrigins("*")
////                .setHandshakeHandler(new CustomHandshakeHandler())
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/all", "/specific", "/queue");
        config.enableSimpleBroker("/user");
        config.setApplicationDestinationPrefixes("/app");

        config.setUserDestinationPrefix("/user");
    }
}
