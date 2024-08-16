package com.iwomi.nofiaPay.frameworks.data.repositories.validators;

import com.iwomi.nofiaPay.frameworks.data.entities.TellerBoxEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IValidatorRepository extends JpaRepository<ValidatorEntity, UUID> {
    Optional<ValidatorEntity> findByProcess(String process);
//    Optional<ValidatorEntity> findByProfiles(String profile);
//    Optional<ValidatorEntity> findByUsers(String user);
}
