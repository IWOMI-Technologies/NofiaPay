package com.iwomi.nofiaPay.dtos.responses;

import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record AccountHistory(
        UUID uuid,
        String name,
        String reason,
        BigDecimal amount,
        String accountNumber,
        SenseTypeEnum sense,
        Date createdAt
) {
}
