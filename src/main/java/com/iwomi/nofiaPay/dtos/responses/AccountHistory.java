package com.iwomi.nofiaPay.dtos.responses;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

//public record AccountHistory(
//        UUID uuid,
//        String name,
//        String agencyCode,
//        String accountNumber,
//        String currency,
//        String cle,
//        String operationCode,
//        String operationTitle,
//        String transactionReference,
//        BigDecimal amount,
//        SenseTypeEnum sense,
//        String accountingDocument,
//        Date accountingDate,
//        String valueDate,
//        String balance,
//        Date createdAt
//) {
//}

@Setter
@Data
public class AccountHistory {
    private UUID uuid;
    private String name;
    private String agencyCode;
    private String accountNumber;
    private String currency;
    private String cle;
    private String operationCode;
    private String operationTitle;
    private String transactionReference;
    private BigDecimal amount;
    private SenseTypeEnum sense;
    private String accountingDocument;
    private Date accountingDate;
    private String valueDate;
    private String balance;
    private Date createdAt;

}
