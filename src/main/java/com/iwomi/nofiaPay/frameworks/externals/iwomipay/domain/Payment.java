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
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Payment implements IPayment {
    private final IwomiPayClient client;

    @Value("${externals.iwomi.username}")
    private String username;

    @Value("${externals.iwomi.password}")
    private String password;

    @Value("${externals.iwomi.apiKeys.momo}")
    private String momoKey;

    @Value("${externals.iwomi.apiSecrets.momo}")
    private String momoSecret;

    @Value("${externals.iwomi.apiKeys.om}")
    private String omKey;

    @Value("${externals.iwomi.apiSecrets.om}")
    private String omSecret;

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

        String key = Objects.equals(dto.type(), "om") ? omKey : momoKey;
        String secret = Objects.equals(dto.type(), "om") ? omSecret : momoSecret;
        String joined = key + ":" + secret;

        String encoded = Base64.getEncoder().encodeToString(joined.getBytes());

        var object = CoreUtils.objectToMap(dto);
        Map<String, Object> response = client.callPay(encoded, object);
        System.out.printf("-------- "+ response);
//        System.out.printf("-------- "+ response);
        return Map.of();
    }
}
