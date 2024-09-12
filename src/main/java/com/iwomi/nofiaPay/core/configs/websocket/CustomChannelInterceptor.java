//package com.iwomi.nofiaPay.core.configs.websocket;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomChannelInterceptor implements ChannelInterceptor {
//
//    private static final Logger logger = LoggerFactory.getLogger(CustomChannelInterceptor.class);
//
//    @Override
//    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
//        System.out.println("************ "+channel);
//        if (message.getHeaders().containsKey("simpDestination")) {
//            String destination = (String) message.getHeaders().get("simpDestination");
//
//            System.out.println("Message sent to destination: " + destination);
//
//            logger.info("Message sent to destination: " + destination);
//        }
//    }
//
//    // You can override other methods as needed
//}
