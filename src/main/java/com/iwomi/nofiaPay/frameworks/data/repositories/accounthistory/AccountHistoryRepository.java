package com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountHistoryEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountHistoryRepository {

    private final  IAccountHistoryRepository repository;

    private  final  IAccountRepository accountRepository;


    public List<AccountHistoryEntity> getHistory () {
        return repository.findAll();
    }

    public List<AccountHistoryEntity> saveAllHistories (List<AccountHistoryEntity> accountHistories) {
        return repository.saveAll(accountHistories);
    }


    public AccountHistoryEntity getOne(UUID uuid) {
        return  repository.findById(uuid)
                .orElseThrow(() -> new GeneralException("Account Not Found"));
    }
    public  List<AccountHistoryEntity> getAccountHistory(List<String> accountNumbers){
        return repository.findByAccountNumberIn(accountNumbers);

    }

}
