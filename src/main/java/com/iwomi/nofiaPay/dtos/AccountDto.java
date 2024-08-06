package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.AccountStatusEnum;
import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountDto {

    private String accountNumber;
    private AccountStatusEnum accountStatus;

    private BigDecimal balance;


    private AccountTypeEnum type;

    private UUID clientId;
}
