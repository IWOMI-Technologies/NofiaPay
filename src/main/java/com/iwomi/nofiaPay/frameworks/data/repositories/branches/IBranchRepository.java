package com.iwomi.nofiaPay.frameworks.data.repositories.branches;

import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IBranchRepository extends JpaRepository<BranchEntity, UUID> {
    Optional<BranchEntity> findByCode(String code);
}
