//package com.iwomi.nofiaPay.core.configs.websocket;
//
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class WebSocketSessionManager {
//
//    private final Map<String, String> sessionIdToAuthUuidMap = new ConcurrentHashMap<>();
//
//    public void registerSession(String sessionId, String authUuid) {
//        sessionIdToAuthUuidMap.put(sessionId, authUuid);
//    }
//
//    public String getAuthUuidForSession(String sessionId) {
//        return sessionIdToAuthUuidMap.get(sessionId);
//    }
//
//    public void unregisterSession(String sessionId) {
//        sessionIdToAuthUuidMap.remove(sessionId);
//    }
//}