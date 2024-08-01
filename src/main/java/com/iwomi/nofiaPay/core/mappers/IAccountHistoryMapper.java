package com.iwomi.nofiaPay.core.mappers;

import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAccountHistoryMapper {

    AccountHistory mapToModel(AccountHistoryEntity entity);
}
