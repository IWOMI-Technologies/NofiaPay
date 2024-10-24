package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.ValidationTypeEnum;
import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.errors.exceptions.PendingValidationException;
import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.*;
import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidationEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import com.iwomi.nofiaPay.services.accounthistory.AccountHistoryService;
import com.iwomi.nofiaPay.services.clients.IClientService;
import com.iwomi.nofiaPay.services.transactions.ITransactionService;
import com.iwomi.nofiaPay.services.transactions.TransactionService;
import com.iwomi.nofiaPay.services.validations.IvalidationService;
import com.iwomi.nofiaPay.services.validations.ValidationService;
import com.iwomi.nofiaPay.services.wbesocket.IWebsocketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping("${apiV1Prefix}/transactions")
@RequiredArgsConstructor
//@CrossOrigin("*")
@RestController
public class TransactionController {

    private final ITransactionService transactionService;
    private final AccountHistoryService historyService;
    private final IClientService clientService;
    private final IvalidationService validationService;
    private final IWebsocketService websocketService;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


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

    //    History of account
    @GetMapping("/history/{clientCode}")
    public ResponseEntity<?> getAccountHistory(@PathVariable String clientCode) {
        System.out.println("Inside getAccountHistory ---------- /history/{clientCode}");
        List<String> clientAccounts = accountRepository.getAccountsByClientCode(clientCode)
                .stream()
                .map(AccountEntity::getAccountNumber)
                .toList();

        List<Map<String, List<Transaction>>> transactions = clientAccounts.stream()
                .map(account -> {
                    var name = clientService.getClientNameByAccountNumber(account);
                    List<Transaction> transactionList = Stream.concat(
                                    transactionService.viewByIssuerAccount(account).stream(),
                                    transactionService.viewByReceiverAccount(account).stream()
                            )
                            .peek(transaction -> {
                                if (transactionService.isIssuerAccount(transaction.getIssuerAccount())) {
                                    transaction.setSense(SenseTypeEnum.DEBIT.toString());
                                    transaction.setName(name);
                                } else {
                                    transaction.setSense(SenseTypeEnum.CREDIT.toString());
                                    transaction.setName(name);
                                }
                            })
                            .toList();
                    return Map.of(account, transactionList);
                })
                .toList();
        System.out.println("==== result" + transactions);

        return GlobalResponse.responseBuilder("List of account transactions", HttpStatus.OK, HttpStatus.OK.value(), transactions);
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

    @PostMapping("/self-service/{userUuid}")
    @Operation(
            description = "Client self service transaction ::: use -> SELF_SERVICE_OM or SELF_SERVICE_MOMO for operation",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> storeSelfService(
            @RequestBody SelfServiceDto dto,
            @PathVariable String userUuid
    ) {
        Transaction result = transactionService.selfService(userUuid, dto);
        return GlobalResponse.responseBuilder("Transaction created successfully", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @PostMapping("/agent-digital/{userUuid}")
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
    public ResponseEntity<?> storeAgentDigital(
            @RequestBody AgentDigitalCollectionDto dto,
            @PathVariable String userUuid
    ) {
        Transaction result = transactionService.AgentDigitalCollection(userUuid, dto);
        return GlobalResponse.responseBuilder("Transaction created successfully", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
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

    @PostMapping("/merchant-digital/{userUuid}")
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
    public ResponseEntity<?> storeMerchantDigital(@RequestBody MerchantDigitalDto dto, @PathVariable String userUuid) {
        Transaction result = transactionService.merchantDigital(userUuid, dto);
        return GlobalResponse.responseBuilder("Transaction created successfully", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @GetMapping("/collected-amount/{clientCode}")
    @Operation(
            description = """
                    """,
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> agentCollectedAmount(@PathVariable String clientCode) {
        String type = "2222.0"; // .0 because of how its in the db
        try {
            BigDecimal result = transactionService.viewAgentUnProcessedCollectionAmountByClientCode(clientCode, type);
            System.out.println("AMOUNT :: " + result);
            return GlobalResponse.responseBuilder("Agent collected amount", HttpStatus.OK, HttpStatus.OK.value(), result);
        } catch (PendingValidationException e) {
            System.out.println("GeneralException occurred.");
            return GlobalResponse.responseBuilder(e.getMessage(),
                    HttpStatus.ACCEPTED,
                    HttpStatus.ACCEPTED.value(),
                    null);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred ");
            return GlobalResponse.responseBuilder("An unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null);
        }
    }

    @PostMapping("/reversement/{userUuid}")
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
    public ResponseEntity<?> reversal(
            @RequestBody ReversementDto dto,
            @PathVariable String userUuid,
            @RequestHeader(value = "authUuid", required = false) String authUuid
    ) {
        System.out.println("HEADER :: " + authUuid);
        Map<String, Object> result = transactionService.reversement(userUuid, dto);

        Transaction transaction = (Transaction) result.get("transaction");
        String tellerCode = (String) result.get("tellerCode");
        ValidationEntity validation = validationService.sendToTellerValidation(
                tellerCode,
                transaction.getUuid(),
                dto.agentAccountNumber(),
                ValidationTypeEnum.REVERSEMENT
        );
        return GlobalResponse.responseBuilder("Transactions to teller successful", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }
}
