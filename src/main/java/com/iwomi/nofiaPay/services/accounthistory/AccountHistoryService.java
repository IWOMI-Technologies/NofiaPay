package com.iwomi.nofiaPay.services.accounthistory;

import com.iwomi.nofiaPay.core.mappers.IAccountHistoryMapper;
import com.iwomi.nofiaPay.core.mappers.IAccountMapper;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory.AccountHistoryRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.services.accounts.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountHistoryService implements IAccountHistoryService {

    private  final AccountHistoryRepository accountHistoryRepository;

    private  final IAccountHistoryMapper mapper;

    private  final AccountRepository accountRepository;



    public List<AccountHistory> viewHistory() {

        return accountHistoryRepository.getHistory()
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

    @Override
    public AccountHistory viewOne(UUID uuid) {
        return mapper.mapToModel(accountHistoryRepository.getOne(uuid));
    }


    public Map<String, List<AccountHistory>> getAccountHistoriesByClientCode(String clientCode) {
        List<String> accountNumbers = accountRepository.getAccountsByClientCode(clientCode)
                .stream()
                .map(AccountEntity::getAccountNumber)
                .collect(Collectors.toList());

        return accountHistoryRepository.getAccountHistory(accountNumbers)
                .stream()
                .map(mapper::mapToModel)
                .collect(Collectors.groupingBy(AccountHistory::accountNumber));
    }

    @Override
    public List<AccountHistory> getHistoriesByClientCode(String clientCode) {
        List<String> accountNumbers = accountRepository.getAccountsByClientCode(clientCode)
                .stream()
                .map(AccountEntity::getAccountNumber)
                .collect(Collectors.toList());

        System.out.println("accountNumbers: ===================" + accountNumbers);

        return accountHistoryRepository.getAccountHistory(accountNumbers)
                .stream()
                .map(mapper::mapToModel).toList();
    }

    @Override
    public List<AccountHistory> getLatestTop5AccountHistoryByClientCode(String clientCode) {

        List<String> accounts = accountRepository.getAccountNumbersByClientCode(clientCode);

        return accountHistoryRepository.getTop5ByAccount(accounts)
                .stream()
                .limit(5)
                .map(mapper::mapToModel)
                .toList();
    }


    public List<AccountHistory> getLatestAccountHistoryByClientCode(String clientCode){
        List<String> accounts = accountRepository.getAccountNumbersByClientCode(clientCode);
        return accountHistoryRepository.getTop5ByAccount(accounts)
                .stream()
                .map(mapper::mapToModel)
                .toList();

    }

//    public Object getLatestTop5ByClientCode(String clientCode) {
//        List<String> accountNumbers = accountService.getAccountNumbersByClientCode(clientCode);
//
////        return accountHistoryRepository.getTop5ByAccount(accountNumbers)
////                .stream()
////                .limit(5)
////                .map(mapper::mapToModel)
////                .collect(Collectors.groupingBy(AccountHistory::accountNumber));
////    }
//
//        List<AccountHistoryEntity> account = accountHistoryRepository.getTop5ByAccount(accountNumbers)
//



}
