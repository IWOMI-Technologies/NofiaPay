package com.iwomi.nofiaPay.services.accounts;

import com.iwomi.nofiaPay.core.mappers.IAccountMapper;
import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

//    @Override
//    public List<String> getAccountNumbersByClientCode(String clientCode) {
//        return  accountRepository.getAccountNumbersByClientCode(clientCode)
//                .stream()
//                .map(AccountEntity::getAccountNumber)
//                .collect(Collectors.toList());
//    }

    @Override
    public Map<String, List<Double>> viewAccountBalances(String clientCode) {
        List<String> accountNumbers = accountRepository.getAccountsByClientCode(clientCode)
                .stream()
                .map(AccountEntity::getAccountNumber)
                .collect(Collectors.toList());

        return accountRepository.getAccountBalances(accountNumbers) // Assuming a method to fetch balances
                .stream()
                .collect(Collectors.groupingBy(AccountEntity::getAccountNumber,
                        Collectors.mapping(account -> account.getBalance().doubleValue(), Collectors.toList())));
    }

}
