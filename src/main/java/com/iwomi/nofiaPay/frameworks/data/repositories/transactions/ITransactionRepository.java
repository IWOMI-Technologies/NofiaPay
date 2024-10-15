package com.iwomi.nofiaPay.frameworks.data.repositories.transactions;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ITransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByBatch(String batchCode);

    List<TransactionEntity> findByBatchIn(List<String> batchCodes);

    List<TransactionEntity> findByCreatedAt(Date date);

    List<TransactionEntity> findByIssuerAccountAndCreatedAtBetween(String account, Date start, Date end);

    List<TransactionEntity> findByIssuerAccount(String IssuerAccount);

    List<TransactionEntity> findByReceiverAccount(String receiverAccount);

    List<TransactionEntity> findByIssuerAccountAndStatusAndProcessed(String IssuerAccount, StatusTypeEnum type, boolean processed);

    boolean existsByIssuerAccount(String IssuerAccount);

    @Query("SELECT a FROM transactions a WHERE a.issuerAccount IN :issuerAccounts ORDER BY a.createdAt DESC")
    List<TransactionEntity> findTop5ByIssuerAccount(@Param("issuerAccounts") List<String> issuerAccounts);

    @Query("SELECT a FROM transactions a WHERE a.receiverAccount IN :receiverAccount ORDER BY a.createdAt DESC")
    List<TransactionEntity> findTop5ByReceiverAccount(@Param("receiverAccount") List<String> receiverAccount);

    @Query("SELECT a FROM transactions a WHERE a.issuerAccount IN :issuerAccount ORDER BY a.createdAt DESC")
    List<TransactionEntity> findTransactionsByIssuerAccountsOrderedByCreatedAtDesc(@Param("issuerAccount") List<String> issuerAccount, Pageable pageable);

    @Query("SELECT a FROM transactions a WHERE a.receiverAccount IN :receiverAccount ORDER BY a.createdAt DESC")
    List<TransactionEntity> findTransactionsByReceiverAccountsOrderedByCreatedAtDesc(@Param("receiverAccount") List<String> receiverAccount, Pageable pageable);


    List<TransactionEntity> findByCreatedAtBetween(Date start, Date end);

    @Modifying
    @Transactional
    @Query("UPDATE transactions t SET t.processed = true WHERE t.uuid IN :uuids")
    int markAsProcessed(List<UUID> uuids);


}
