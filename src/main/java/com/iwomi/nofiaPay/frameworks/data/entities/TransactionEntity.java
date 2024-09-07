package com.iwomi.nofiaPay.frameworks.data.entities;


import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "transactions")
@SQLDelete(sql = "UPDATE transactions SET deleted = true WHERE uuid=?")
@Where(clause = "deleted=false")
public class TransactionEntity extends BaseEntity {

    private BigDecimal amount;
    private String reason;
    private String batch;

    @Column(name = "issuer_account") private String issuerAccount;
    @Column(name = "receiver_account") private String receiverAccount;


    @Enumerated(EnumType.STRING) private OperationTypeEnum type;
    @Enumerated(EnumType.STRING) private StatusTypeEnum status;

    private boolean processed = false;

    private boolean deleted = false;
}
