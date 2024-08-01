package com.iwomi.nofiaPay.frameworks.data.repositories.accounts;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.mappers.IAccountMapper;
import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountRepository {

    private final  IAccountRepository repository;

    private  final IAccountMapper mapper;

    public List<AccountEntity> getAllAccounts () {
        return repository.findAll();
    }

    public  AccountEntity createAccount (AccountDto dto) {
        AccountEntity account = mapper.mapToEntity(dto);
        return  repository.save(account);
    }

    public  AccountEntity getOne(UUID uuid) {
        return  repository.findById(uuid)
                .orElseThrow(() -> new GeneralException("Account Not Found"));
    }

    public  AccountEntity updateAccount (AccountDto dto, UUID uuid){
        AccountEntity account = getOne(uuid);
        mapper.updateAccountFromDto(dto, account);
        return  repository.save(account);
    }
    public  void  deleteAccount(UUID uuid){
        repository.deleteById(uuid);
    }
}