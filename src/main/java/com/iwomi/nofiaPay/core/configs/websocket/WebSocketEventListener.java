//package com.iwomi.nofiaPay.core.configs.websocket;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//@Component
//@RequiredArgsConstructor
//public class WebSocketEventListener {
//
////    private final WebSocketSessionManager sessionManager;
//    private static final String SIMP_SESSION_ID = "simpSessionId";
//
//
//    /**
//     * Handles WebSocket connection events
//     */
//    @EventListener
//    public void handleWebsocketConnectListener(SessionConnectedEvent event) {
//        new StompPrincipal(getAuthUuidFromHeader(event));
////        System.out.println("Event data: "+event.getMessage().getHeaders().get("authUuid"));
//        System.out.println("Event Auth uuid: "+getAuthUuidFromHeader(event));
////        System.out.println("WebSocket session connected: " + event.getMessage().getHeaders());
////        System.out.println("pricipal from event ++++ "+event.getUser().getName());
//
//        String sessId = getSessionIdFromMessageHeaders(event);
////        System.out.println("sess "+sessId);
////        sessionManager.registerSession(sessId, authUuid);
//    }
//
//    /**
//     * Handles WebSocket disconnection events
//     */
//    @EventListener
//    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
//        System.out.printf("WebSocket connection closed for sessionID "+event);
//    }
//
//    private String getSessionIdFromMessageHeaders(SessionConnectedEvent event) {
//        Map<String, Object> headers = event.getMessage().getHeaders();
//        return Objects.requireNonNull(headers.get(SIMP_SESSION_ID)).toString();
//    }
//
//    private String getAuthUuidFromHeader(SessionConnectedEvent event) {
//        // Use SimpMessageHeaderAccessor to access headers
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
//
//        // Print all headers for debugging
//        System.out.println("Message headers: " + headerAccessor.getMessageHeaders());
//
//        // Access the connect message which contains native headers
//        Message<?> connectMessage = (Message<?>) headerAccessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
//        if (connectMessage != null) {
//            SimpMessageHeaderAccessor connectHeaderAccessor = SimpMessageHeaderAccessor.wrap(connectMessage);
//            System.out.println("Connect message headers: " + connectHeaderAccessor.getMessageHeaders());
//
//            // Extract the native headers from the connect message
//            @SuppressWarnings("unchecked")
//            Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) connectHeaderAccessor.getHeader(SimpMessageHeaderAccessor.NATIVE_HEADERS);
//
//            System.out.println("Native headers: " + nativeHeaders);
//
//            if (nativeHeaders != null) {
//                // Extract the authUuid from native headers
//                List<String> authUuidList = nativeHeaders.get("authUuid");
//                if (authUuidList != null && !authUuidList.isEmpty()) {
//                    String authUuid = authUuidList.get(0);
//                    System.out.println("Auth UUID: " + authUuid);
//                    return authUuid;
//                }
//            }
//        }
//        return "";
//    }
//
//
//}
