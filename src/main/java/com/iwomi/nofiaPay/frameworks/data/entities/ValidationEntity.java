package com.iwomi.nofiaPay.frameworks.data.entities;

import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.enums.ValidationTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "subscription-validation")
public class ValidationEntity extends BaseEntity {
    private String subscriberClientCode;
    private String validatedBy;

    private String tellerClientCode;    // only for reversement validation
    private BigDecimal expectedAmount;    // only for reversement validation
    private UUID transactionId;   // only for reversement validation
    @Enumerated(EnumType.STRING) private ValidationTypeEnum type;
    @Enumerated(EnumType.STRING) private ValidationStatusEnum status;
}
