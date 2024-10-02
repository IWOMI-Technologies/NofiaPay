package com.iwomi.nofiaPay.controllers;

//import com.iwomi.nofiaPay.core.configs.websocket.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

//@RequestMapping("${apiV1Prefix}/transactions")
@RequiredArgsConstructor
//@CrossOrigin("*")
@RestController
public class WebSocketController {

//    private final WebSocketSessionManager sessionManager;

    @MessageMapping("/register")
    public void registerSession(
            @Header("simpSessionId") String sessionId,
            @Payload Map<String, Object> authUuid,
            SimpMessageHeaderAccessor headerAccessor,
            Principal principal
    ) {
        System.out.println("Principal from controler **** "+principal.getName());
        System.out.println("Received greeting from "+authUuid.get("authUuid")+" with sessionId "+sessionId);
        String sessId = headerAccessor.getSessionId();
        System.out.println("sess "+sessId);
//        Map<String, Object> headers = authUuid;
//        sessionManager.registerSession("sessionId", authUuid.get("authUuid").toString());
    }
}
