package com.iwomi.nofiaPay.frameworks.data.repositories.subscribtionValidation;

import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.frameworks.data.entities.SubscriptionValidationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISubscriptionValidationRepository extends JpaRepository<SubscriptionValidationEntity, UUID> {
    Optional<SubscriptionValidationEntity> findByStatus(ValidationStatusEnum status);
    Optional<SubscriptionValidationEntity> findBySubscriberClientCode(String clientCode);
    Optional<SubscriptionValidationEntity> findByValidatedBy(String validator);
    List<SubscriptionValidationEntity> findBySubscriberClientCodeIn(List<String> codes);


}
