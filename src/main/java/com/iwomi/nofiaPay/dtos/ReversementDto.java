package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;

import java.math.BigDecimal;
import java.util.List;

public record ReversementDto(
        String tellersAccount,
        String raison,
        String agentClientId,
        List<BatchDto> batchesPayment,
        OperationTypeEnum operation // agent to teller
) {
}
