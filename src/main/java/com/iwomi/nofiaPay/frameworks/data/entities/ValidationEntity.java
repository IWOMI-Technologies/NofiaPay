package com.iwomi.nofiaPay.frameworks.data.entities;

import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.enums.ValidationTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private String impactedTransactionIds;
    @Enumerated(EnumType.STRING) private ValidationTypeEnum type;
    @Enumerated(EnumType.STRING) private ValidationStatusEnum status;

    // Getter for List<String>
    public List<String> getImpactedTransactionIds() {
        return impactedTransactionIds != null
                ? Arrays.asList(impactedTransactionIds.split(","))
                : new ArrayList<>();
    }

    // Setter for List<String>
    public void setImpactedTransactionIds(List<String> impactedTransactionIds) {
        this.impactedTransactionIds = String.join(",", impactedTransactionIds);
    }
}
