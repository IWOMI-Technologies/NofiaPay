package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;

public record AgentDigitalCollectionDto(
     //   String clientPhoneNumber,

        String clientAccount,
//        String clientDestinationAccount,

        String agentAccount,
        String amount,
        String sourcePhoneNumber,
        String reason,
        OperationTypeEnum operation
) {
}
