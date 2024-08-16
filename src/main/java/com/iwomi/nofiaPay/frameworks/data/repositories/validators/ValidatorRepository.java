package com.iwomi.nofiaPay.frameworks.data.repositories.validators;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.frameworks.data.entities.TellerBoxEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidatorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidatorRepository {
    private final IValidatorRepository repository;

    public ValidatorEntity getOneByProcess(String process) {
        return repository.findByProcess(process)
                .orElseThrow(() -> new GeneralException("Branch not found."));
    }

//    public ValidatorEntity getOneByProcess(String process) {
//        return repository.findByProcess(process)
//                .orElseThrow(() -> new GeneralException("Branch not found."));
//    }
//
//    public ValidatorEntity getOneByProcess(String process) {
//        return repository.findByProcess(process)
//                .orElseThrow(() -> new GeneralException("Branch not found."));
//    }
}
