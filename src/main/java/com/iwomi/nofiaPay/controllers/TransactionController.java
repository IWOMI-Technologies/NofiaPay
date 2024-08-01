package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.TransactionDto;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.services.transactions.TransactionService;
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

@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class TransactionController {

    private  final TransactionService transactionService;

    @GetMapping()
    @Operation(
            description = "List of transactions",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = Transaction.class))}),
            }
    )
    public ResponseEntity<?> index() {
        List<Transaction> result = transactionService.viewAllTransactions();
        return GlobalResponse.responseBuilder("List of  transactions", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping()
    @Operation(
            description = "Transaction creation",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> store(@RequestBody TransactionDto dto) {
        Transaction result = transactionService.SaveTransaction(dto);
        return GlobalResponse.responseBuilder("Transaction created successfully", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<?> show(@PathVariable UUID uuid) {
        Transaction result = transactionService.viewOne(uuid);
        return GlobalResponse.responseBuilder("Transaction found", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody TransactionDto dto) {
        Transaction result = transactionService.update(id, dto);
        return GlobalResponse.responseBuilder("Transaction  update successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(@PathVariable UUID uuid) {
        transactionService.deleteOne(uuid);
        return GlobalResponse.responseBuilder("Transaction deleted", HttpStatus.OK, HttpStatus.OK.value(), null);
    }
}
