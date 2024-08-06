package com.iwomi.nofiaPay.dtos.responses;

import com.iwomi.nofiaPay.core.enums.AccountStatusEnum;
import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;

import java.math.BigDecimal;
import java.util.UUID;

public record Account(
         String accountNumber,
         AccountStatusEnum accountStatus,
         BigDecimal balance,
         AccountTypeEnum type,
         UUID clientId

) {
}
