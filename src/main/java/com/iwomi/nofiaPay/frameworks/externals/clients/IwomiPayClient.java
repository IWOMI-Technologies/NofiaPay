package com.iwomi.nofiaPay.frameworks.externals.clients;

import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.IwomiAuthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "iwomiPay-service", url = "${externals.base-url.iwomiPay}")
public interface IwomiPayClient {

    @PostMapping("/authenticate")
    ResponseEntity<?> authenticate(@RequestBody IwomiAuthDto dto);

    @PostMapping("/iwomipay")
    Map<String, Object> iwomiPay(
            @RequestHeader("Authorization") String token,
            @RequestHeader("AccountKey") String apikey,
            @RequestBody Map<String, Object> payload
    );

    @GetMapping("/iwomipayStatus/{internal_id}")
    Map<String, Object> paymentStatus(@RequestHeader("Authorization") String token, @PathVariable String internal_id);
}
