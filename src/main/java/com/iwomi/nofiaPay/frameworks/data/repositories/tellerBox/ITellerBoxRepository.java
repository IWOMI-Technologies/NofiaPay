package com.iwomi.nofiaPay.frameworks.data.repositories.tellerBox;

import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TellerBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ITellerBoxRepository extends JpaRepository<TellerBoxEntity, UUID> {
    Optional<TellerBoxEntity> findByNumberAndBranchId(String boxNumber, String branchId);

}
