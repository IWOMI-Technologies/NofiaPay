package com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory;

import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountHistoryRepository extends JpaRepository<AccountHistoryEntity, UUID> {

    Optional<AccountHistoryEntity> getOneByAccountNumber (String accountNumber);

    List<AccountHistoryEntity> findByAccountNumber(List<String> accountNumbers);
}
