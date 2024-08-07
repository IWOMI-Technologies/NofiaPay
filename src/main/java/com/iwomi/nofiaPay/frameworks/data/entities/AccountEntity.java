package com.iwomi.nofiaPay.frameworks.data.entities;


import com.iwomi.nofiaPay.core.enums.AccountStatusEnum;
import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "accounts")
@SQLDelete(sql = "UPDATE clients SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class AccountEntity extends BaseEntity {

    @Column(name = "account_number") private String accountNumber;
    @Column(name = "balance") private BigDecimal balance;
    @Column(name = "client_id") private String clientId;
    @Column(name = "branch_id") private String branchId;

    @Enumerated(EnumType.STRING) private AccountStatusEnum accountStatus;
    @Enumerated(EnumType.STRING) private AccountTypeEnum type;

    private boolean deleted = Boolean.FALSE;


}
