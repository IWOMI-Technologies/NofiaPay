package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;

public record AgentCashCollectionDto(
        String clientAccount,
        String agentAccount,
        String amount,
        String reason,
        String operation
) {
}
