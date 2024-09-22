package com.iwomi.nofiaPay.services.accounts;


import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.dtos.responses.ClientAccount;
import com.iwomi.nofiaPay.dtos.responses.CombineHistory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IAccountService {
    List<Account> viewAllAccounts();

    Account SaveAccount(AccountDto dto);

    Account viewOne(UUID uuid);

    Account updateAccount(UUID uuid, AccountDto dto);

    void deleteOne(UUID uuid);

    List<String> getAccountNumbersByClientCode(String clientCode);

    List<Account> getAccountsByClientCode(String clientCode);

    List<ClientAccount> getClientAccountByClientCode(String clientCode);

    Map<String, List<Double>> viewAccountBalances(String clientCode);

//    List<Account> viewAccountByDateRange(Date start, Date end)
    List<CombineHistory> viewAccountByDateRange(Date start, Date end);

    List<Map<String, Object>> getAccountsWithLatestTransactions(String clientCode, int limit);
}
