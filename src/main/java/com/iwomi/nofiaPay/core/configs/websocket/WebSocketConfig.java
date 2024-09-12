package com.iwomi.nofiaPay.core.configs.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final WebSocketSessionManager sessionManager;
//    private final CustomChannelInterceptor customChannelInterceptor;

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(customChannelInterceptor);
//    }
//
//    @Override
//    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
//        registration.interceptors(customChannelInterceptor);
//
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:3000/");
//                .setAllowedOrigins("*")
//                .setHandshakeHandler(new CustomHandshakeHandler());
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
