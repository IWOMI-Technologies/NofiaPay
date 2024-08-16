package com.iwomi.nofiaPay.controllers;


import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.frameworks.externals.clients.AuthClient;
import com.iwomi.nofiaPay.services.accounthistory.AccountHistoryService;
import com.iwomi.nofiaPay.services.accounts.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@RequestMapping("/api/v1/accounthis")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class AccountHistoryController {

    private final AccountHistoryService accountHistoryService;

    private  final AuthClient authClient;

    private final AccountService accountService;

    @GetMapping()
    @Operation(
            description = "List of all accounts Histories",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = AccountHistory.class))}),
            }
    )
    public ResponseEntity<?> index() {
        List<AccountHistory> result = accountHistoryService.viewHistory();
        return GlobalResponse.responseBuilder("List of all Account Histories", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> show(@PathVariable UUID uuid) {
        AccountHistory result = accountHistoryService.viewOne(uuid);
        return GlobalResponse.responseBuilder("Account found ", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @GetMapping("/check-history")
    public  ResponseEntity<?> checkBalance(@RequestParam String clientCode,
                                           @RequestParam String pin){
        if(!authClient.checkPin(clientCode, pin)){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Pin");
        }

        Map<String, List<AccountHistory>> histories = accountHistoryService.getAccountHistoriesByClientCode(clientCode);

        return GlobalResponse.responseBuilder("Account deleted", HttpStatus.OK, HttpStatus.OK.value(), histories);
    }
}
