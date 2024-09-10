package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.enums.ValidationTypeEnum;
import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.*;
import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidationEntity;
import com.iwomi.nofiaPay.services.accounthistory.AccountHistoryService;
import com.iwomi.nofiaPay.services.transactions.TransactionService;
import com.iwomi.nofiaPay.services.validations.ValidationService;
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

@RequestMapping("${apiV1Prefix}/transactions")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountHistoryService historyService;
    private final ValidationService validationService;

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

    @GetMapping("/account-history")
    @Operation(
            description = "Returns List of transactions for an account",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", ref = "successResponse",
                            content = {@Content(schema = @Schema(
                                    implementation = Transaction.class,
                                    type = "array"
                            ))}),
            }
    )
    public ResponseEntity<?> getHistoryByAccount(@RequestParam String account) {

        // Determine the account type (issuer or receiver)
        boolean isIssuerAccount = transactionService.isIssuerAccount(account);

        List<Transaction> transactions;

        if (isIssuerAccount) {
            transactions = transactionService.viewByIssuerAccount(account);
        } else {
            transactions = transactionService.viewByReceiverAccount(account);
        }

        Map<String, List<Transaction>> result = Map.of(
                "Transactions", transactions
        );

        return GlobalResponse.responseBuilder("List of transactions", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @GetMapping("/all-history")
    @Operation(
            description = "",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = Transaction.class))}),
            }
    )
    public ResponseEntity<?> allHistory(@RequestParam String clientCode) {
        Map<String, List<AccountHistory>> accountsHistory = historyService.getAccountHistoriesByClientCode(clientCode);
        Map<String, Object> transactions = transactionService.allHistory(clientCode);
        Map<String, Object> result = Map.of(
                "accountHistory", accountsHistory,
                "transactions", transactions
        );
        return GlobalResponse.responseBuilder("List of  transactions", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> show(@PathVariable UUID uuid) {
        Transaction result = transactionService.viewOne(uuid);
        return GlobalResponse.responseBuilder("Transaction found", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

//    @PutMapping("/{uuid}")
//    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody TransactionDto dto) {
//        Transaction result = transactionService.update(id, dto);
//        return GlobalResponse.responseBuilder("Transaction  update successful", HttpStatus.OK, HttpStatus.OK.value(), result);
//    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(@PathVariable UUID uuid) {
        transactionService.deleteOne(uuid);
        return GlobalResponse.responseBuilder("Transaction deleted", HttpStatus.OK, HttpStatus.OK.value(), null);
    }

    @PostMapping("/agent-cash")
    @Operation(
            description = "Agent cash collection transaction ::: use -> AGENT_CASH_COLLECTION",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> storeAgentCash(@RequestBody AgentCashCollectionDto dto) {
        Transaction result = transactionService.agentCashCollection(dto);
        return GlobalResponse.responseBuilder("Transaction created successfully", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @PostMapping("/self-service")
    @Operation(
            description = "Client self service transaction ::: use -> SELF_SERVICE_OM or SELF_SERVICE_MOMO for operation",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> storeSelfService(@RequestHeader String authUuid, @RequestBody SelfServiceDto dto) {
        System.out.println("Authenticated user uuid "+authUuid);
        Transaction result = transactionService.selfService(dto);
        return GlobalResponse.responseBuilder("Transaction created successfully", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @PostMapping("/agent-digital")
    @Operation(
            description = """
                    Agent digital collection transaction ::: use -> AGENT_DIGITAL_COLLECTION_MOMO, AGENT_DIGITAL_COLLECTION_OM
                    used for any with prefix AGENT_DIGITAL_COLLECTION_**
                    """,
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> storeAgentDigital(@RequestBody AgentDigitalCollectionDto dto) {
        Transaction result = transactionService.AgentDigitalCollection(dto);
        return GlobalResponse.responseBuilder("Transaction created successfully", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping("/merchant-cash")
    @Operation(
            description = "Agent cash collection transaction ::: use -> MERCHANT_CASH_COLLECTION",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> storeMerchantCash(@RequestBody MerchantCashDto dto) {
        Transaction result = transactionService.merchantCash(dto);
        return GlobalResponse.responseBuilder("Transaction created successfully", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @PostMapping("/merchant-digital")
    @Operation(
            description = """
                    Agent digital collection transaction ::: use -> MERCHANT_DIGITAL_COLLECTION_MOMO, AGENT_DIGITAL_COLLECTION_OM
                    used for any with prefix MERCHANT_DIGITAL_COLLECTION_**
                    """,
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> storeMerchantDigital(@RequestBody MerchantDigitalDto dto) {
        Transaction result = transactionService.merchantDigital(dto);
        return GlobalResponse.responseBuilder("Transaction created successfully", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping("/reversement")
    @Operation(
            description = """
                    Agent to teller transaction reversement
                    """,
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> reversal(@RequestBody ReversementDto dto) {
        Map<String, Object> result = transactionService.reversement(dto);
        Transaction transaction = (Transaction) result.get("transaction");
        String tellerCode = (String) result.get("tellerCode");
        ValidationEntity validation = validationService.sendToTellerValidation(
                tellerCode,
                transaction.uuid(),
                dto.agentAccountNumber(),
                ValidationTypeEnum.REVERSEMENT
        );
        return GlobalResponse.responseBuilder("Transactions to teller successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }
}
