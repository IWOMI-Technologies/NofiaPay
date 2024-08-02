package com.iwomi.nofiaPay.frameworks.externals.iwomipay.domain;

import com.iwomi.nofiaPay.core.utils.CoreUtils;
import com.iwomi.nofiaPay.frameworks.externals.clients.IwomiPayClient;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.DigitalPaymentDto;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.IwomiAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Payment implements IPayment{
    private final IwomiPayClient client;

    @Value("${externals.iwomi.username}")
    private String username;

    @Value("${externals.iwomi.password}")
    private String password;

    @Value("${externals.iwomi.apiKey}")
    private String apiKey;

    @Value("${externals.iwomi.apiSecret}")
    private String apiSecret;

    @Override
    public String auth(IwomiAuthDto dto) {
        ResponseEntity<?> response = client.authenticate(dto);
        // get token from below response
        return response.getBody().toString();
    }

    @Override
    public Map<String, Object> pay(DigitalPaymentDto dto) {
        IwomiAuthDto authDto = new IwomiAuthDto(username, password);
        String token = auth(authDto);

        var object = CoreUtils.objectToMap(dto);
        String encodedKey = "base64(apikey:apisecret)";
        Map<String, Object> response = client.callPay(encodedKey, object);

        return Map.of();
    }
}
