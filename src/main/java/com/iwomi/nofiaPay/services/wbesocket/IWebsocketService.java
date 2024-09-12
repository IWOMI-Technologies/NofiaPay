package com.iwomi.nofiaPay.services.wbesocket;

import java.util.Map;

public interface IWebsocketService {
    void sendToAll(final Object model);
    Map<String, Object> sendToUser(final String user, final String data);
}
