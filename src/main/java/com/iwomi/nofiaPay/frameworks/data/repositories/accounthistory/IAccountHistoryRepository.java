package com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory;

import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountHistoryRepository extends JpaRepository<AccountHistoryEntity, UUID> {

    Optional<AccountHistoryEntity> getOneByAccountNumber(String accountNumber);

    List<AccountHistoryEntity> findByAccountNumberIn(List<String> accountNumbers);

    //    List<AccountHistoryEntity> findTop5ByOrderByCreatedAtDesc(String );
    @Query("SELECT a FROM accounts_history a WHERE a.accountNumber IN :accountNumbers ORDER BY a.createdAt DESC")
    List<AccountHistoryEntity> findLatestTop5ByAccountNumbers(@Param("accountNumbers") List<String> accountNumbers);

}
