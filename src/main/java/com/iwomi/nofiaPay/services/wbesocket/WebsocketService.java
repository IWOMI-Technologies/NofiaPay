package com.iwomi.nofiaPay.services.wbesocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WebsocketService implements IWebsocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String SIMP_SESSION_ID = "simpSessionId";
    private static final String WS_PRIVATE_DESTINATION = "/queue/messages";
//    private static final String WS_PRIVATE_DESTINATION = "/topic/greetings";
    private List<String> userNames = new ArrayList<>();

    public WebsocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendToAll(Object model) {
        simpMessagingTemplate.convertAndSend("/all/messages", model);
    }

    @Override
    public Map<String, Object> sendToUser(final String user, final String data) {
        System.out.println("Sending to user: " + user + " with data: " + data);
        simpMessagingTemplate.convertAndSendToUser(user,
                WS_PRIVATE_DESTINATION
                , data);
        System.out.println(simpMessagingTemplate.toString());
        System.out.println("Prefix: "+simpMessagingTemplate.getUserDestinationPrefix());
        System.out.println("Destination: "+simpMessagingTemplate.getDefaultDestination());
        return Map.of("data", data);
    }
}
