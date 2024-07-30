//package com.iwomi.authms.frameworks.externals.clients;
//
//import com.iwomi.authms.dtos.AuthDto;
//import com.iwomi.authms.frameworks.externals.models.UserModel;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@FeignClient(name = "auth-service", url = "${externals.config.user-management-url}")
//public interface UserManagementClient {
//    @GetMapping("email/{mail}")
//    UserModel getUserByEmail(@PathVariable String mail);
//
//    @GetMapping("email/{mail}")
//    Boolean userExistByEmail(@PathVariable String mail);
//
//    @PostMapping()
//    void saveUser(@RequestBody AuthDto dto, @RequestParam UUID client_id);
//}
