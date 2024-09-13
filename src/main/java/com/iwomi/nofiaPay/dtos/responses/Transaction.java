package com.iwomi.nofiaPay.dtos.responses;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record Transaction(
        UUID uuid,
        BigDecimal amount,
        String reason,
        String batch,
        String issuerAccount,
        String receiverAccount,
        OperationTypeEnum type,
        StatusTypeEnum status,
        Date createdAt
) {
}
