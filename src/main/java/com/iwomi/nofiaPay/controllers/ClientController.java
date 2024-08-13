package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.responses.Branch;
import com.iwomi.nofiaPay.dtos.responses.Client;
import com.iwomi.nofiaPay.services.clients.ClientService;
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

@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class ClientController {
    private final ClientService clientService;

    @GetMapping()
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

//    @GetMapping("/{id}")
//    public ResponseEntity<?> show(@PathVariable UUID id) {
//        Client result = clientService.viewOne(id);
//        return GlobalResponse.responseBuilder("Found client", HttpStatus.OK, HttpStatus.OK.value(), result);
//    }

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

}
