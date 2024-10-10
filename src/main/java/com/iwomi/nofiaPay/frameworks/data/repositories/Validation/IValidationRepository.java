package com.iwomi.nofiaPay.frameworks.data.repositories.Validation;

import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.enums.ValidationTypeEnum;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IValidationRepository extends JpaRepository<ValidationEntity, UUID> {
    Optional<ValidationEntity> findByStatus(ValidationStatusEnum status);

    Optional<ValidationEntity> findBySubscriberClientCode(String clientCode);
    Optional<ValidationEntity> findByTellerClientCode(String tellerCode);

    Optional<ValidationEntity> findByValidatedBy(String validator);

    List<ValidationEntity> findBySubscriberClientCodeIn(List<String> codes);

    List<ValidationEntity> findByTellerClientCodeAndType(String tellerCode, ValidationTypeEnum type);


}
