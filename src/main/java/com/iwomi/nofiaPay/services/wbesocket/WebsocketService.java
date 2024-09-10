package com.iwomi.nofiaPay.services.wbesocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebsocketService implements IWebsocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebsocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendToAll(String model) {
        simpMessagingTemplate.convertAndSend("/all/messages", model);
    }

    @Override
    public Map<String, Object> sendToUser(String model) {
        return Map.of("data", model);
    }
}
