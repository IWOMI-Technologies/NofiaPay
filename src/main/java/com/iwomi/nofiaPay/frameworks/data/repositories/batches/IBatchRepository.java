package com.iwomi.nofiaPay.frameworks.data.repositories.batches;

import com.iwomi.nofiaPay.frameworks.data.entities.BatchEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBatchRepository extends JpaRepository<BatchEntity, UUID> {
    Optional<BatchEntity> findByBatchCode(String code);
    List<BatchEntity> findByClientIdAndRemainderIsNot(String clientid, BigDecimal remainder);
    Optional<BatchEntity> findByClientIdAndCreatedAt(String clientid, Date date);
}
