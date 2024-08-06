package com.iwomi.nofiaPay.services.accounthistory;

import com.iwomi.nofiaPay.core.mappers.IAccountHistoryMapper;
import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory.AccountHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AccountHistoryService implements IAccountHistoryService {

    private  final AccountHistoryRepository accountHistoryRepository;

    private  final IAccountHistoryMapper mapper;



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

}
