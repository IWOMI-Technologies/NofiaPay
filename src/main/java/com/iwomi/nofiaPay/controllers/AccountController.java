package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.frameworks.externals.clients.AuthClient;
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

@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class AccountController {

    private final AccountService accountService;

    private  final AuthClient authClient;

    @GetMapping()
    @Operation(
            description = "List of accounts",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = Account.class))}),
            }
    )
    public ResponseEntity<?> index() {
        List<Account> result = accountService.viewAllAccounts();
        result.forEach(account -> System.out.println(account.accountNumber()));

        return GlobalResponse.responseBuilder("List of all accounts", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping()
    @Operation(
            description = "Account creation",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> store(@RequestBody AccountDto dto) {
        Account result = accountService.SaveAccount(dto);
        return GlobalResponse.responseBuilder("Account successfully created", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<?> show(@PathVariable UUID uuid) {
        Account result = accountService.viewOne(uuid);
        return GlobalResponse.responseBuilder("Account found ", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable UUID uuid, @RequestBody AccountDto dto) {
        Account result = accountService.updateAccount(uuid, dto);
        return GlobalResponse.responseBuilder("Account update successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(@PathVariable UUID uuid) {
        accountService.deleteOne(uuid);
        return GlobalResponse.responseBuilder("Account deleted", HttpStatus.OK, HttpStatus.OK.value(), null);
    }

    @GetMapping("/check-balance")
    public ResponseEntity<?> checkBalance(@RequestParam String clientCode,
                                          @RequestParam String pin) {
        if (!authClient.checkPin(clientCode, pin)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Pin");
        }

        Map<String, List<Double>>  balances = accountService.viewAccountBalances(clientCode);

        return GlobalResponse.responseBuilder("Account deleted", HttpStatus.OK, HttpStatus.OK.value(), balances);
    }
}
