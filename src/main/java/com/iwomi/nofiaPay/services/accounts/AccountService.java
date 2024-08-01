package com.iwomi.nofiaPay.services.accounts;

import com.iwomi.nofiaPay.core.mappers.IAccountMapper;
import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService  implements  IAccountService{

    private  final AccountRepository accountRepository;

    private  final IAccountMapper mapper;
    @Override
    public List<Account> viewAllAccounts() {
        return accountRepository.getAllAccounts()
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

    @Override
    public Account SaveAccount(AccountDto dto) {
        return mapper.mapToModel(accountRepository.createAccount(dto));
    }

    @Override
    public Account viewOne(UUID uuid) {
        return mapper.mapToModel(accountRepository.getOne(uuid));
    }

    @Override
    public Account updateAccount(UUID uuid, AccountDto dto) {
        return mapper.mapToModel(accountRepository.updateAccount(dto, uuid));
    }

    @Override
    public void deleteOne(UUID uuid) {
          accountRepository.deleteAccount(uuid);
    }
}
