package com.iwomi.nofiaPay.services.wbesocket;

import java.util.Map;

public interface IWebsocketService {
    void sendToAll(final String model);
    Map<String, Object> sendToUser(final String model);
}
