package com.iwomi.nofiaPay.frameworks.data.repositories.Validation;

import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.enums.ValidationTypeEnum;
import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidationRepository {
    private final IValidationRepository validationRepository;

    public ValidationEntity createValidation(ValidationEntity entity) {;

        return validationRepository.save(entity);
    }

    public List<ValidationEntity> getAllByClientCodes(List<String> codes) {
        return validationRepository.findBySubscriberClientCodeIn(codes);
    }

    public List<ValidationEntity> getAllTellerAndType(String tellerCode, ValidationTypeEnum type) {
        return validationRepository.findByTellerClientCodeAndType(tellerCode, type);
    }

    public ValidationEntity getByClientCode(String code) {
        System.out.println("IN GET CLIENT________");
        Optional<ValidationEntity> subVal = validationRepository.findBySubscriberClientCode(code);
        if (subVal.isPresent()) return subVal.get();

        Optional<ValidationEntity> tellerVal = validationRepository.findByTellerClientCode(code);
        return tellerVal.orElseThrow(() -> new GeneralException("Validation not found."));
    }

    public ValidationEntity getByStatus(ValidationStatusEnum status) {
        return validationRepository.findByStatus(status)
                .orElseThrow(() -> new GeneralException("subscription validation not found."));
    }

    public ValidationEntity updateSubscription(ValidationEntity entity) {
        return validationRepository.save(entity);
    }
}
