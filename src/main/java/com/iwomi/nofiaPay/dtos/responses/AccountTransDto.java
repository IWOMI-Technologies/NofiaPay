package com.iwomi.nofiaPay.dtos.responses;

import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;

public record AccountTransDto(
        AccountEntity account,
        TransactionEntity transaction
)  {
}
