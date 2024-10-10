package com.iwomi.nofiaPay.services.AttachedAccounts;

import com.iwomi.nofiaPay.core.mappers.IClientAttachedAccountMapper;
import com.iwomi.nofiaPay.dtos.ClientAttachedAccountDto;
import com.iwomi.nofiaPay.dtos.responses.ClientAttachedAccounts;
import com.iwomi.nofiaPay.frameworks.data.repositories.AttachedAccounts.ClientAttachedAccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientAttachedAccountsService implements  IClientAttachedAccountService{

    private  final ClientAttachedAccountsRepository repository;

    private  final IClientAttachedAccountMapper mapper;

    @Override
    public ClientAttachedAccounts saveAccount(ClientAttachedAccountDto dto) {
        return mapper.mapToModel(repository.createAccount(dto));
    }

    @Override
    public List<ClientAttachedAccounts> getAccountsByClientCode(String clientCode) {
        return repository.getAccountByClientCode(clientCode)
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }
}
