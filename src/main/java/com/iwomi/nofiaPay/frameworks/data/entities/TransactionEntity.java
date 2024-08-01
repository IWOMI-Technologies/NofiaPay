package com.iwomi.nofiaPay.frameworks.data.entities;


import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "transactions")
public class TransactionEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private OperationTypeEnum type;

    @Enumerated(EnumType.STRING)
    private SenseTypeEnum sense;

    private BigDecimal amount;
    private String reason;

    @Enumerated(EnumType.STRING)
    private StatusTypeEnum status;
}
