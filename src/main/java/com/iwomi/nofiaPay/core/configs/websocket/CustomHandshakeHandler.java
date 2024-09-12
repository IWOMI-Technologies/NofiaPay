//package com.iwomi.nofiaPay.core.configs.websocket;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
//
//import java.security.Principal;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * Set anonymous user (Principal) in WebSocket messages by using UUID
// * This is necessary to avoid broadcasting messages but sending them to specific user sessions
// */
//@RequiredArgsConstructor
//public class CustomHandshakeHandler extends DefaultHandshakeHandler {
//
////    private final WebSocketSessionManager sessionManager;
//
//    @Override
//    protected Principal determineUser(ServerHttpRequest request,
//                                      WebSocketHandler wsHandler,
//                                      Map<String, Object> attributes) {
//        var sessionId = request.getHeaders();
//
//        attributes.put("sessionId", "something");
//
//        System.out.println("HEADERS DATA --- +++ "+sessionId);
//        System.out.println("custom sessionid +++ "+request.getHeaders().getFirst("simpSessionId"));
////        String authUuid = sessionManager.getAuthUuidForSession("sessionId");
//
////        System.out.println("custom uuid****** "+authUuid);
//        return new StompPrincipal("12");
////        return null;
//    }
//}
