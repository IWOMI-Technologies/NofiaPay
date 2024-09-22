package com.iwomi.nofiaPay.frameworks.data.repositories.accounts;

import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountRepository extends JpaRepository<AccountEntity, UUID> {
    AccountEntity findByAccountNumber(String account);

    //    Optional<AccountEntity> findByBranchIdAndType(String branchId, AccountTypeEnum type);
//    Optional<AccountEntity> findByClientIdAndType(String clientId, AccountTypeEnum type);
    Optional<AccountEntity> findByAgencyCodeAndAccountTypeCode(String clientId, String type);

    List<AccountEntity> findByClientCode(String clientCode);

    List<AccountEntity> findAccountsByClientCode(String clientCode);

    List<AccountEntity> findByAccountNumberIn(List<String> accountNumbers);

    @Query("SELECT a FROM accounts a WHERE  a.createdAt BETWEEN :startDate AND :endDate ORDER BY a.createdAt DESC")
    List<AccountEntity> findAccountByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


}
