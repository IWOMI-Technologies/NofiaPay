package com.iwomi.nofiaPay.dtos.responses;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Setter
@Data
public class Transaction {
    private UUID uuid;
    private String name;
    private BigDecimal amount;
    private String reason;
    private String batch;
    private String issuerAccount;
    private String receiverAccount;
    private OperationTypeEnum type;
    private StatusTypeEnum status;
    private Date createdAt;

}
