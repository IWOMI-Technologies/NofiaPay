package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;

public record AgentCashCollectionDto(
        String clientAccount,
        String agentCollectionAccount,
        String amount,
        String reason,
        OperationTypeEnum operation
) {
}
