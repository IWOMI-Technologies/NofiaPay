package com.iwomi.nofiaPay.dtos.responses;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;

import java.math.BigDecimal;

public record Transaction(
        BigDecimal amount,
        String reason,
        String batch,
        String issuerAccount,
        String receiverAccount,
        OperationTypeEnum type,
        StatusTypeEnum status
) {
}
