package com.iwomi.nofiaPay.services.accounthistory;

import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.response.Account;
import com.iwomi.nofiaPay.dtos.response.AccountHistory;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;

import java.util.List;
import java.util.UUID;

public interface IAccountHistoryService {

    List<AccountHistory> viewHistory();

    AccountHistory viewOne(UUID uuid);
}
