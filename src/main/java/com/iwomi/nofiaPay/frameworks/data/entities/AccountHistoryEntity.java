package com.iwomi.nofiaPay.frameworks.data.entities;

import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "accounts_history")
public class AccountHistoryEntity extends BaseEntity {

    private String agencyCode;
    private String currency;
    private String cle;
    private String operationCode;
    private String operationTitle;
    private String transactionReference;
    private BigDecimal amount;

    private String accountingDocument;
    private String accountingDate;
    private String valueDate;
    private String balance;

    @Column(name = "account-number") private String accountNumber;

    @Enumerated(EnumType.STRING) private SenseTypeEnum sense;

}
