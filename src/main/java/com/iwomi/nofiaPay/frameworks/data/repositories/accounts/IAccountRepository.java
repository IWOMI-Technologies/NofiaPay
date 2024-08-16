package com.iwomi.nofiaPay.frameworks.data.repositories.accounts;

import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByAccountNumber(String account);
//    Optional<AccountEntity> findByBranchIdAndType(String branchId, AccountTypeEnum type);
//    Optional<AccountEntity> findByClientIdAndType(String clientId, AccountTypeEnum type);
    Optional<AccountEntity> findByAgencyCodeAndAccountTypeCode(String clientId, String type);
    List<AccountEntity> findByClientCode(String clientCode);

    List<AccountEntity> findByAccountNumberIn(List<String> accountNumbers);

}
