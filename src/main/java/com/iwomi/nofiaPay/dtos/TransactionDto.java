package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private OperationTypeEnum type;
    private SenseTypeEnum sense;
    private BigDecimal amount;
    private String reason;
}
