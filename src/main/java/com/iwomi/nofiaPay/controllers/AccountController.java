package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.errors.exceptions.UnAuthorizedException;
import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.dtos.responses.CombineHistory;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.externals.clients.AuthClient;
import com.iwomi.nofiaPay.services.accounthistory.AccountHistoryService;
import com.iwomi.nofiaPay.services.accounts.AccountService;
import com.iwomi.nofiaPay.services.combinedResult.CombineResults;
import com.iwomi.nofiaPay.services.transactions.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping("${apiV1Prefix}/accounts")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountHistoryService historyService;
    private final TransactionService transactionService;

    private final AuthClient authClient;

    private final CombineResults combineResults;

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

//    @GetMapping("history")
//    @Operation(
//            description = "List of accounts and their history",
//            responses = {
//                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
//                    @ApiResponse(responseCode = "201", ref = "successResponse",
//                            content = {@Content(schema = @Schema(implementation = Transaction.class))}),
//            }
//    )
//    public ResponseEntity<?> accountsHistory() {
//        List<Transaction> result = transactionService.viewAllTransactions();
//        return GlobalResponse.responseBuilder("List of  transactions", HttpStatus.OK, HttpStatus.OK.value(), result);
//    }

    @GetMapping("/check-balance")
    public ResponseEntity<?> checkBalance(@RequestParam String clientCode, @RequestParam String pin) {
        if (!authClient.checkPin(clientCode, pin)) throw new UnAuthorizedException("Invalid Pin");

        Map<String, List<Double>> balances = accountService.viewAccountBalances(clientCode);

        return GlobalResponse.responseBuilder("Account deleted", HttpStatus.OK, HttpStatus.OK.value(), balances);
    }

    @GetMapping("/client/{client_code}")
    public ResponseEntity<?> showAccountsByClientCode(@PathVariable String clientCode) {
        List<Account> result = accountService.getAccountsByClientCode(clientCode);
        return GlobalResponse.responseBuilder("Account deleted", HttpStatus.OK, HttpStatus.OK.value(), result);
    }
//
//    @GetMapping("/dashboard/{client_code}")
//    public ResponseEntity<?> dashboard(@PathVariable String clientCode) {
//        List<AccountHistory> accountHistories = historyService.getLatestTop5AccountHistoryByClientCode(clientCode);
//        List<Transaction> transactions = transactionService.getLatestTop5TransactionByClientCode(clientCode);
//
//        List<Map<String, Object>> historiesMap = accountHistories
//                .stream()
//                .map(history -> Map.<String, Object>of(
//                        "uuid", history.uuid(),
//                        "createdAt", history.createdAt()
//                )).toList();
//        List<Map<String, Object>> transactionsMap = transactions
//                .stream()
//                .map(history -> Map.<String, Object>of(
//                        "uuid", history.uuid(),
//                        "createdAt", history.createdAt()
//                )).toList();
//
//
//        Set<String> uuids = Stream.of(historiesMap, transactionsMap)
//                .flatMap(List::stream)
//                .sorted(Comparator.comparing(o -> (Date) o.get("createdAt"), Comparator.reverseOrder()))
//                .limit(5)
//                .map(d -> (String) d.get("uuid"))
//                .collect(Collectors.toSet());
//
//        List<AccountHistory> historyResult = accountHistories
//                .stream()
//                .filter(history -> uuids.contains(history.uuid()))
//                .toList();
//
//        List<Transaction> transactionResult = transactions
//                .stream()
//                .filter(transaction -> uuids.contains(transaction.uuid()))
//                .toList();
//
//        Map<String, Object> result = Map.of(
//                "clientAccounts", accountService.getAccountsByClientCode(clientCode),
//                "accountHistories", historyResult,
//                "transactions", transactionResult
//        );
//
//        return GlobalResponse.responseBuilder("Account deleted", HttpStatus.OK, HttpStatus.OK.value(), result);
//    }

    @GetMapping("/dashboard/{client_code}")
    public ResponseEntity<?> dashboard(@PathVariable String clientCode) {
        List<String> accountNumbers = accountService.getAccountsByClientCode(clientCode)
                .stream()
                .map(Account::accountNumber)
                .toList();

        List<AccountHistory> accountHistories = historyService.getLatestTop5AccountHistoryByClientCode(clientCode);
        List<Transaction> transactions = transactionService.getLatestTop5TransactionByClientCode(clientCode);

        List<Map<String, Object>> historiesMap = accountHistories
                .stream()
                .map(history -> Map.<String, Object>of(
                        "uuid", history.uuid(),
                        "createdAt", history.createdAt()
                )).toList();
        List<Map<String, Object>> transactionsMap = transactions
                .stream()
                .map(history -> Map.<String, Object>of(
                        "uuid", history.uuid(),
                        "createdAt", history.createdAt()
                )).toList();


        Set<String> uuids = Stream.of(historiesMap, transactionsMap)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(o -> (Date) o.get("createdAt"), Comparator.reverseOrder()))
                .limit(5)
                .map(d -> (String) d.get("uuid"))
                .collect(Collectors.toSet());

        List<AccountHistory> historyResult = accountHistories
                .stream()
                .filter(history -> uuids.contains(history.uuid()))
                .toList();

        List<Transaction> transactionResult = transactions
                .stream()
                .filter(transaction -> uuids.contains(transaction.uuid()))
                .toList();

        List<CombineHistory> result = Stream.concat(transactionResult.stream().map(combineResults::mapToTransactionHistory),
                historyResult.stream().map(combineResults::mapToAccountHistory))
                        .collect(Collectors.toList());


        return GlobalResponse.responseBuilder("Account deleted", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @GetMapping("/date-between")
    public ResponseEntity<?> getAccountsByDateRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date start = formatter.parse(startDate);
            Date end = formatter.parse(endDate);
            List<Account> result = accountService.viewAccountByDateRange(start, end);

            return GlobalResponse.responseBuilder("Account deleted", HttpStatus.OK, HttpStatus.OK.value(), result);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getAccountsByLatestTransactions(@RequestParam("clientCode") String clientCode) {
        int limit = 5;
        List<Map<String, Object>> result = accountService.getAccountsWithLatestTransactions(clientCode, limit);
        return GlobalResponse.responseBuilder("Account deleted", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

}
