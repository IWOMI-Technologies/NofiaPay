package com.iwomi.nofiaPay.dtos.responses;

import java.math.BigDecimal;
import java.util.Date;

public record Account(
        String agencyCode,
        String agencyName,
        String currency,
        String cle,
        String accountTitle,
        String chapter,
        String chapterTitle,
        String accountTypeCode,
        String accountTypeLabel,

        String accountNumber,
        BigDecimal balance,
        String clientCode,

        Date accountCreation

) {
}
