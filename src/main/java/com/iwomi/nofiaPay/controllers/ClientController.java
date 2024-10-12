package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.ClientAttachedAccountDto;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.dtos.responses.Client;
import com.iwomi.nofiaPay.dtos.responses.ClientAttachedAccounts;
import com.iwomi.nofiaPay.frameworks.externals.clients.AuthClient;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;
import com.iwomi.nofiaPay.services.AttachedAccounts.IClientAttachedAccountService;
import com.iwomi.nofiaPay.services.clients.ClientService;
import com.iwomi.nofiaPay.services.clients.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("${apiV1Prefix}/clients")
@RequiredArgsConstructor
//@CrossOrigin("*")
@RestController
public class ClientController {
    private final IClientService clientService;

    private  final IClientAttachedAccountService iClientAttachedAccountService;

//    private final AuthClient authClient;

    @GetMapping("/all")
    @Operation(
            description = "List of clients",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))}),
            }
    )
    public ResponseEntity<?> index() {
        List<Client> result = clientService.findAllClient();
        return GlobalResponse.responseBuilder("List of clients", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<?> show(@PathVariable UserTypeEnum role) {
        System.out.println("trying ****************** to get client by role");
        List<Client> result = clientService.findAllByClientCode(role);
        return GlobalResponse.responseBuilder("Found client", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @GetMapping("/deleted")
    public ResponseEntity<?> showByDeleted(@RequestParam("role") String role) {
        List<Client> result = clientService.findAllDeletedByClientCode(role);
        return GlobalResponse.responseBuilder("Found clients", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable UUID id) {
        clientService.deleteOne(id);
        return GlobalResponse.responseBuilder("Client deleted", HttpStatus.OK, HttpStatus.OK.value(), null);
    }

    @GetMapping("/phone")
    @Operation(
            description = "Find client by phone number",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))}),
            }
    )
    public ResponseEntity<?> specificByPhone(@RequestParam("clientPhone") String phone) {
        Client result = clientService.viewOneByPhone(phone);
        return GlobalResponse.responseBuilder("Found client", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @GetMapping("/client-code")
    @Operation(
            description = "Find client by client code",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))}),
            }
    )
    public ResponseEntity<?> specificByClientCode(@RequestParam String clientCode) {
        Client result = clientService.viewOneByClientCode(clientCode);
        return GlobalResponse.responseBuilder("Found client", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping("/attached-account")
    @Operation(
            description = "Account creation",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> store(@RequestBody ClientAttachedAccountDto dto) {
        ClientAttachedAccounts result = iClientAttachedAccountService.saveAccount(dto);
        return GlobalResponse.responseBuilder("Account successfully created", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @GetMapping("/accounts/{clientCode}")
    public ResponseEntity<?> showAccountsByClientCode(@PathVariable String clientCode) {
        List<ClientAttachedAccounts> result = iClientAttachedAccountService.getAccountsByClientCode(clientCode);
        return GlobalResponse.responseBuilder("Account found ", HttpStatus.OK, HttpStatus.OK.value(), result);
    }
}
