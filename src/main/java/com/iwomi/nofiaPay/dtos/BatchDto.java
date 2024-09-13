package com.iwomi.nofiaPay.dtos;

import java.math.BigDecimal;

public record BatchDto(
        String batchCode,
        BigDecimal amount
) {
}
