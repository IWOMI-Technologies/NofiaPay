package com.iwomi.nofiaPay.frameworks.data.entities;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "accounts_history")
public class AccountHistoryEntity extends BaseEntity {

    private String agencyCode;
    @Column(name = "account-number") private String accountNumber;
    private String currency;
    private String cle;
    private String operationCode;
    private String operationTitle;
    private String transactionReference;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING) private SenseTypeEnum sense;
    private String accountingDocument;
    private Date accountingDate;
    private String valueDate;
    private String balance;


//    @Enumerated(EnumType.STRING) private OperationTypeEnum type;

}
