package com.iwomi.nofiaPay.frameworks.externals.iwomipay.domain;

import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.DigitalPaymentDto;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.IwomiAuthDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IPayment {
    String auth(IwomiAuthDto dto);

    Map<String, Object> pay(DigitalPaymentDto dto);

    CompletableFuture<Map<String, Object>> checkPaymentStatusWithBackoff(String internalId);
}
