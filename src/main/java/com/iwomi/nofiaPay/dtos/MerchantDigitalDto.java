package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;

public record MerchantDigitalDto(
        String merchantAccount,
        String paymentMethod,
        String amount,
        String sourcePhoneNumber,
        String reason,
        OperationTypeEnum operation
) {
}
