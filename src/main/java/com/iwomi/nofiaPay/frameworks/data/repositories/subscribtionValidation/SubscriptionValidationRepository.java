package com.iwomi.nofiaPay.frameworks.data.repositories.subscribtionValidation;

import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.dtos.EnrollDto;
import com.iwomi.nofiaPay.frameworks.data.entities.EnrollEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.SubscriptionValidationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubscriptionValidationRepository {
    private final ISubscriptionValidationRepository validationRepository;

    public SubscriptionValidationEntity createValidation(String code) {;
        SubscriptionValidationEntity validation = SubscriptionValidationEntity.builder()
                .subscriberClientCode(code)
                .status(ValidationStatusEnum.PENDING)
                .build();
        return validationRepository.save(validation);
    }

    public List<SubscriptionValidationEntity> getAllByClientCodes(List<String> codes) {
        return validationRepository.findBySubscriberClientCodeIn(codes);
    }

    public SubscriptionValidationEntity getByClientCode(String code) {
        return validationRepository.findBySubscriberClientCode(code)
                .orElseThrow(() -> new GeneralException("subscription validation not found."));
    }

    public SubscriptionValidationEntity getByStatus(ValidationStatusEnum status) {
        return validationRepository.findByStatus(status)
                .orElseThrow(() -> new GeneralException("subscription validation not found."));
    }

    public SubscriptionValidationEntity updateSubscription(SubscriptionValidationEntity entity) {
        return validationRepository.save(entity);
    }
}
