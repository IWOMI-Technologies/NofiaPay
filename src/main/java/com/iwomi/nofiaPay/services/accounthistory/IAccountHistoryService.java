package com.iwomi.nofiaPay.services.accounthistory;

import com.iwomi.nofiaPay.dtos.responses.AccountHistory;

import java.util.List;
import java.util.UUID;

public interface IAccountHistoryService {

    List<AccountHistory> viewHistory();

    AccountHistory viewOne(UUID uuid);
}
