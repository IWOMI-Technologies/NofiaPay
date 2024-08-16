package com.iwomi.nofiaPay.frameworks.data.entities;

import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "subscription-validation")
public class SubscriptionValidationEntity extends BaseEntity {
    private String subscriberClientCode;
    private String validatedBy;

    @Enumerated(EnumType.STRING) private ValidationStatusEnum status;
}
