package com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountHistoryRepository {

    private final  IAccountHistoryRepository repository;

    public List<AccountHistoryEntity> getHistory () {
        return repository.findAll();
    }


    public AccountHistoryEntity getOne(UUID uuid) {
        return  repository.findById(uuid)
                .orElseThrow(() -> new GeneralException("Account Not Found"));
    }
}
