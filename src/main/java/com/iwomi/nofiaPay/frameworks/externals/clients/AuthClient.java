package com.iwomi.nofiaPay.frameworks.externals.clients;

import com.iwomi.nofiaPay.frameworks.externals.enums.UserStatusEnum;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "${externals.base-url.auth}")
public interface AuthClient {

   @PostMapping("/users/{id}/check-pin")
   boolean checkPin(@PathVariable String clientCode, @RequestParam("pin") String pin);

   @GetMapping("/users/{role}/deleted")
   ResponseEntity<?>  getUsersByRoleAndDeleted(@RequestParam String role);

   @GetMapping("/users")
   ResponseEntity<?> getUsersByRole(@RequestParam UserTypeEnum role);

   @PostMapping("/users/change-status/{clientCode}")
   ResponseEntity<?> changeStatus(@PathVariable String clientCode, @RequestParam("status") UserStatusEnum status);
}
