package com.iwomi.nofiaPay.services.accounts;


import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.responses.Account;

import java.util.List;
import java.util.UUID;

public interface IAccountService {
    List<Account> viewAllAccounts();

    Account SaveAccount(AccountDto dto);

    Account viewOne(UUID uuid);

    Account updateAccount(UUID uuid, AccountDto dto);

    void  deleteOne(UUID uuid);
}
