package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;

public record SelfServiceDto(
//        String clientPhoneNumber,
        String clientAccount,
        String amount,
        String sourcePhoneNumber,
        String reason,
        OperationTypeEnum operation
) {
}
