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

    private BigDecimal amount;
    private String reason;
    private String batch;

    @Column(name = "account_number") private String accountNumber;
    @Column(name = "destination_account") private String destinationAccount;

    @Enumerated(EnumType.STRING) private OperationTypeEnum type;
    @Enumerated(EnumType.STRING) private SenseTypeEnum sense;
    @Enumerated(EnumType.STRING) private StatusTypeEnum status;

}
