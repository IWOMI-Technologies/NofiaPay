package com.iwomi.nofiaPay.frameworks.externals.clients;

import com.iwomi.nofiaPay.dtos.responses.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "auth-service", url = "${externals.base-url.auth}")
public interface AuthServiceClient {

   @PostMapping("/{id}/check-pin")
   boolean checkPin(@PathVariable String clientCode, @RequestParam("pin") String pin);

   @GetMapping("/{role}/deleted")
   ResponseEntity<?>  getUsersByRoleAndDeleted(@RequestParam String role);

   @GetMapping("/{role}")
   ResponseEntity<?> getUsersByRole(@RequestParam String role);
}
