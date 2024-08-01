package com.iwomi.nofiaPay.core.mappers;

import com.iwomi.nofiaPay.dtos.response.AccountHistory;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;

public interface IAccountHistoryMapper {

    AccountHistory mapToModel(AccountHistoryEntity entity);
}
