package com.iwomi.nofiaPay.frameworks.data.entities;


import com.iwomi.nofiaPay.core.enums.AccountStatusEnum;
import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "accounts")
public class AccountEntity extends BaseEntity {

    @Column(name = "account-number")
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountStatusEnum accountStatus;
    @Column(name = "balance")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountTypeEnum type;

    @Column(name = "client_id")
    private UUID clientId;

}
