package com.iwomi.nofiaPay.services.accounthistory;

import com.iwomi.nofiaPay.dtos.responses.AccountHistory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IAccountHistoryService {

    List<AccountHistory> viewHistory();

    AccountHistory viewOne(UUID uuid);

    Map<String, List<AccountHistory>> getAccountHistoriesByClientCode(String clientCode);
    List<AccountHistory> getClientHistory(String clientCode);

    //    List<AccountHistory> getLatestTop5ByClientCode(String clientCode);
    List<AccountHistory> getHistoriesByClientCode(String clientCode);

    List<AccountHistory> getLatestTop5AccountHistoryByClientCode(String clientCode);

    public List<AccountHistory> getLatestAccountHistoryByClientCode(String clientCode);
}
