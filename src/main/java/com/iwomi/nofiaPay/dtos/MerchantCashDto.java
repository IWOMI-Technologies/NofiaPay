package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;

public record MerchantCashDto(
        String merchantAccount,
        String amount,
        String reason,
        String operation
) {
}
