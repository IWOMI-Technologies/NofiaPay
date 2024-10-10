package com.iwomi.nofiaPay.services.accounts;

import com.iwomi.nofiaPay.core.constants.NomenclatureConstants;
import com.iwomi.nofiaPay.core.mappers.IAccountHistoryMapper;
import com.iwomi.nofiaPay.core.mappers.IAccountMapper;
import com.iwomi.nofiaPay.core.mappers.ITransactionMapper;
import com.iwomi.nofiaPay.core.utils.CombineResults;
import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.responses.*;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory.AccountHistoryRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import com.iwomi.nofiaPay.services.accounthistory.AccountHistoryService;
import com.iwomi.nofiaPay.services.transactions.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AccountService  implements  IAccountService {

    private  final AccountRepository accountRepository;

    private  final IAccountMapper mapper;

    private  final ITransactionMapper transactionMapper;

    private  final TransactionRepository transactionRepository;

    private final AccountHistoryRepository accountHistoryRepository;

    private  final IAccountHistoryMapper accountHistoryMapper;

    private  final ClientRepository clientRepository;

    private final AccountHistoryService historyService;

    private final TransactionService transactionService;

    private final CombineResults combineResults;

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
                .map(entity -> {
                    BigDecimal balance = entity.getCredit().subtract(entity.getDebit());
                    Account account = mapper.mapToModel(entity);
                    account.setBalance(balance);
                    return account;
                })
                .toList();
    }

    @Override
    public List<ClientAccount> getClientAccountByClientCode(String clientCode){
        List<AccountEntity> account = accountRepository.getAccountByClientCode(clientCode);

        return account.stream().map(acc -> {
            BigDecimal balance = acc.getCredit().subtract(acc.getDebit());

            ClientEntity client =  clientRepository.getOneByClientCode(clientCode);

            System.out.println("+++++++++++++++ "+client.getFullName());

            ClientAccount clientAccount = new ClientAccount();
            clientAccount.setName(client.getFullName());
            clientAccount.setAccountTypeLabel(acc.getAccountTypeLabel());
            clientAccount.setAccountNumber(acc.getAccountNumber());
            clientAccount.setPhone(client.getPhoneNumber());
            clientAccount.setBranchCode(acc.getAgencyCode());
            clientAccount.setBranchName(acc.getAgencyName());
            clientAccount.setOpeningDate(acc.getStartDate());
            clientAccount.setBalance(balance);
            return clientAccount;
        }).toList();
    }

    @Override
    public List<String> getAccountNumbersByClientCode(String clientCode) {
        return  accountRepository.getAccountNumbersByClientCode(clientCode);
    }

//    @Override
//    public Map<String, List<Double>> viewAccountBalances(String clientCode) {
//        List<String> accountNumbers = accountRepository.getAccountsByClientCode(clientCode)
//                .stream()
//                .map(AccountEntity::getAccountNumber)
//                .collect(Collectors.toList());
//
//        return accountRepository.getAccountBalances(accountNumbers)
//                .stream()
//                .collect(Collectors.groupingBy(AccountEntity::getAccountNumber,
//                        Collectors.mapping(account -> account.getBalance().doubleValue(), Collectors.toList())));
//    }
@Override
public Map<String, List<Double>> viewAccountBalances(String clientCode) {
    List<String> accountNumbers = accountRepository.getAccountsByClientCode(clientCode)
            .stream()
            .map(AccountEntity::getAccountNumber)
            .collect(Collectors.toList());


    return accountRepository.getAccountBalances(accountNumbers)
            .stream()
            .collect(Collectors.groupingBy(AccountEntity::getAccountNumber,
                    Collectors.mapping(account -> {
                        BigDecimal balance = account.getCredit().subtract(account.getDebit());
                        return balance.doubleValue();
                    }, Collectors.toList())));
}

//    public List<Account> viewAccountByDateRange(Date start, Date end) {
//        return accountRepository.getAccountByDateRange(start, end)
//                .stream()
//                .filter(account -> !account.getCreatedAt().before(start) && !account.getCreatedAt().after(end))
//                .map(mapper::mapToModel)
//                .collect(Collectors.toList());
//    }

//    @Override
//    public List<CombineHistory> viewAccountByDateRange(Date start, Date end) {
//
//        List<Account> accounts = accountRepository.getAccountByDateRange(start, end)
//                .stream()
//                .filter(account -> account.getCreatedAt().after(start) && account.getCreatedAt().before(end))
//                .map(mapper::mapToModel)
//                .toList();
//        System.out.println("////////" +  accounts);
//
//
//
//        List<AccountHistory> accountHistories = new ArrayList<>();
//        List<Transaction> transactions = new ArrayList<>();
//
//        for (Account account : accounts ) {
//
//            List<AccountHistory> allAccountHistories = historyService.getLatestTop5AccountHistoryByClientCode(account.getClientCode());
//
//            List<Transaction> allTransactions = transactionService.getLatestTop5TransactionByClientCode(account.getClientCode());
//
//            accountHistories.addAll(allAccountHistories);
//            transactions.addAll(allTransactions);
//        }
//
//        // Map histories and transactions to a common format
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
//        // Combine and sort histories and transactions
//        Set<String> uuids = Stream.of(historiesMap, transactionsMap)
//                .flatMap(List::stream)
//                .sorted(Comparator.comparing(o -> (Date) o.get("createdAt"), Comparator.reverseOrder()))
//                .limit(5)
//                .map(d -> (String) d.get("uuid"))
//                .collect(Collectors.toSet());
//
//        List<AccountHistory> historyResult = accountHistories
//                .stream()
//                .filter(history -> uuids.contains(history.uuid().toString()))
//                .toList();
//
//        List<Transaction> transactionResult = transactions
//                .stream()
//                .filter(transaction -> uuids.contains(transaction.uuid().toString()))
//                .toList();
//
//        // Combine results into a single list
//        return Stream.concat(transactionResult.stream().map(combineResults::mapToTransactionHistory),
//                        historyResult.stream().map(combineResults::mapToAccountHistory))
//                .collect(Collectors.toList());
//    }

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
                            .filter(history -> accountNumber.equals(history.getIssuerAccount()))
                            .toList();

                    List<Transaction> creditTransactions = receiverTransactions.stream()
                            .filter(history -> accountNumber.equals(history.getReceiverAccount()))
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

    @Override
    public Account getMainAccount(String clientCode, String role) {
        String accountType = null;
        if (Objects.equals(role, "AGENT")) accountType = NomenclatureConstants.AgentAccountTypeCode;
        if (Objects.equals(role, "CLIENT")) accountType = NomenclatureConstants.ClientAccountTypeCode;
        if (Objects.equals(role, "MERCHANT")) accountType = NomenclatureConstants.MerchantAccountTypeCode;
        if (Objects.equals(role, "TELLER")) accountType = NomenclatureConstants.TellerAccountTypeCode;    // may not be needed

        return mapper.mapToModel(accountRepository.getOneByClientCodeAndType(clientCode, accountType));
    }

    @Override
    public List<Account> viewClientAccountsByCode(String clientCode, String accountCode) {
        return accountRepository
                .getByClientCodeAndType(clientCode, accountCode)
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }


}
