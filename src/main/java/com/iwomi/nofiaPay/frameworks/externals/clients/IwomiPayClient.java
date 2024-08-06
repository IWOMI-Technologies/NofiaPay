package com.iwomi.nofiaPay.frameworks.externals.clients;

import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.IwomiAuthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "auth-service", url = "${externals.base-url.iwomiPay}")
public interface IwomiPayClient {
    @GetMapping("/authenticate")
    ResponseEntity<?> authenticate(@RequestBody IwomiAuthDto dto);

    @PostMapping("/iwomipay")
    Map<String, Object> callPay(@RequestHeader("AccountKey") String apikey, @RequestBody Map<String, Object> payload);
}
