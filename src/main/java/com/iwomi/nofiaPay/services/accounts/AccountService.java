package com.iwomi.nofiaPay.services.accounts;

import com.iwomi.nofiaPay.core.mappers.IAccountHistoryMapper;
import com.iwomi.nofiaPay.core.mappers.IAccountMapper;
import com.iwomi.nofiaPay.core.mappers.ITransactionMapper;
import com.iwomi.nofiaPay.core.utils.CoreUtils;
import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.dtos.responses.AccountTransDto;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory.AccountHistoryRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;

import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.IAccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService  implements  IAccountService{

    private  final AccountRepository accountRepository;

    private  final IAccountMapper mapper;

    private  final ITransactionMapper transactionMapper;

    private  final TransactionRepository transactionRepository;

    private final AccountHistoryRepository accountHistoryRepository;

    private  final IAccountHistoryMapper accountHistoryMapper;
    @Override
    public List<Account> viewAllAccounts() {
        return accountRepository.getAllAccounts()
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

    @Override
    public Account SaveAccount(AccountDto dto) {
        return mapper.mapToModel(accountRepository.createAccount(dto));
    }

    @Override
    public Account viewOne(UUID uuid) {
        return mapper.mapToModel(accountRepository.getOne(uuid));
    }

    @Override
    public Account updateAccount(UUID uuid, AccountDto dto) {
        return mapper.mapToModel(accountRepository.updateAccount(dto, uuid));
    }

    @Override
    public void deleteOne(UUID uuid) {
          accountRepository.deleteAccount(uuid);
    }

    @Override
    public List<Account> getAccountsByClientCode(String clientCode) {
        return  accountRepository.getAccountsByClientCode(clientCode)
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

    @Override
    public List<String> getAccountNumbersByClientCode(String clientCode) {
        return  accountRepository.getAccountNumbersByClientCode(clientCode);
    }

    @Override
    public Map<String, List<Double>> viewAccountBalances(String clientCode) {
        List<String> accountNumbers = accountRepository.getAccountsByClientCode(clientCode)
                .stream()
                .map(AccountEntity::getAccountNumber)
                .collect(Collectors.toList());

        return accountRepository.getAccountBalances(accountNumbers)
                .stream()
                .collect(Collectors.groupingBy(AccountEntity::getAccountNumber,
                        Collectors.mapping(account -> account.getBalance().doubleValue(), Collectors.toList())));
    }

    @Override
    public List<Account> viewAccountByDateRange(Date start, Date end) {

        return accountRepository.getAccountByDateRange(start, end)
                .stream()
                .filter(account -> account.getCreatedAt().after(start) && account.getCreatedAt().before(end))
                .map(mapper::mapToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getAccountsWithLatestTransactions(String clientCode, int limit) {

        List<String> accountNumbers = accountRepository.getAccountNumbersByClientCode(clientCode);

        List<AccountHistory> allAccountHistories = accountHistoryRepository.getLatestAccountHistoryByAccountNumber(accountNumbers)
                .stream()
                .map(accountHistoryMapper::mapToModel)
                .toList();

//             Fetch all transactions for the list of issuer and receiver accounts
        List<Transaction> issuerTransactions = transactionRepository.getLatestTransactionsByIssuerAccount(accountNumbers, limit)
                .stream()
                .map(transactionMapper::mapToModel)
                .toList();

        List<Transaction> receiverTransactions = transactionRepository.getLatestTransactionByReceiverAccount(accountNumbers, limit)
                .stream()
                .map(transactionMapper::mapToModel)
                .toList();

       // Initialize the result list to hold the data for each account
        List<Map<String, Object>> result = new ArrayList<>();

        // Step 3: Fetch transactions and account history for each account
        return accountNumbers.stream()
                .map(accountNumber -> {
                    // Fetch debit and credit transactions
                    List<Transaction> debitTransactions = issuerTransactions.stream()
                            .filter(history -> accountNumber.equals(history.issuerAccount()))
                            .toList();

                    List<Transaction> creditTransactions = receiverTransactions.stream()
                            .filter(history -> accountNumber.equals(history.receiverAccount()))
                            .toList();

//            Fetch account history specific to the current account number
                    List<AccountHistory> accountHistory = allAccountHistories.stream()
                            .filter(history -> accountNumber.equals(history.accountNumber()))
                            .toList();

//           Use Map.of to create the transactions map
                    Map<String, List<Transaction>> transactionsMap = Map.of(
                            "Debit", debitTransactions,
                            "Credit", creditTransactions
                    );

                    // Build the account data map
                    return Map.of(
                            "accountNum", accountNumber,
                            "transactions", transactionsMap,
                            "accountHistory", accountHistory
                    );
                })
                .toList();
    }


}
