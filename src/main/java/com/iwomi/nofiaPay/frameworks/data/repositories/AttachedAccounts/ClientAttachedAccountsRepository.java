package com.iwomi.nofiaPay.frameworks.data.repositories.AttachedAccounts;

import com.iwomi.nofiaPay.core.enums.StatusEnum;
import com.iwomi.nofiaPay.core.mappers.IClientAttachedAccountMapper;
import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.ClientAttachedAccountDto;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientAttachedAccountEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientAttachedAccountsRepository {


    private final IClientAttachedAccountRepository repository;

    private  final IClientAttachedAccountMapper mapper;

    public ClientAttachedAccountEntity createAccount(ClientAttachedAccountDto dto) {
        ClientAttachedAccountEntity account = mapper.mapToEntity(dto);
        account.setStatus(StatusEnum.DEACTIVATED);
        return repository.save(account);
    }
    public List<ClientAttachedAccountEntity> getAccountByClientCode(String clientCode) {
        return  repository.findAccountsByClientCode(clientCode);
    }
}
