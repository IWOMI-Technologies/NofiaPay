package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public record ReversementDto(
        String agentClientCode,
        String agentAccountNumber,
        String amount,
        String branchCode,
        String boxNumber,
        String pin,
        OperationTypeEnum operation // agent to teller
) {
}
