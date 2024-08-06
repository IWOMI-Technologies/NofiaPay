package com.iwomi.nofiaPay.dtos.responses;

import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record AccountHistory(
          String name,
          String reason,
          BigDecimal amount,
          String accountNumber,
          SenseTypeEnum sense
) {
}
