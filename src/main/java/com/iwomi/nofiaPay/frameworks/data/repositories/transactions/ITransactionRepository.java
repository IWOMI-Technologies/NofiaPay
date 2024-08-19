package com.iwomi.nofiaPay.frameworks.data.repositories.transactions;

import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findByBatch(String batchCode);
    List<TransactionEntity> findByBatchIn(List<String> batchCodes);

    List<TransactionEntity> findByIssuerAccount(String IssuerAccount);
    List<TransactionEntity> findByReceiverAccount(String receiverAccount);

    boolean  existByAccountNumber(String IssuerAccount);
}
