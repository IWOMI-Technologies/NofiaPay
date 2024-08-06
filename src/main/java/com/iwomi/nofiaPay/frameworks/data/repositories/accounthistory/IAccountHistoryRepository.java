package com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory;

import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IAccountHistoryRepository extends JpaRepository<AccountHistoryEntity, UUID> {

    Optional<AccountHistoryEntity> getOneByAccountNumber (String accountNumber);
}
