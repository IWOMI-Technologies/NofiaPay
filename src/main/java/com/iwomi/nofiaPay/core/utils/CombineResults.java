package com.iwomi.nofiaPay.core.utils;

import com.iwomi.nofiaPay.core.mappers.IAccountMapper;
import com.iwomi.nofiaPay.core.mappers.IClientMapper;
import com.iwomi.nofiaPay.dtos.responses.*;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CombineResults {

    private  final AccountRepository accountRepository;

    private  final ClientRepository clientRepository;
    private  final IAccountMapper mapper;
    private  final IClientMapper clientMapper;
    public CombineHistory mapToAccountHistory(AccountHistory history) {
        String accountNumber = history.accountNumber();
        String account = accountRepository.getOneByAccount(accountNumber).getClientCode();

        Client client = clientMapper.mapToModel(clientRepository.getOneByClientCode(account));
        return CombineHistory.builder()
                .name(client.getFullName())
                .service(history.type().toString())
                .amount(history.amount())
                .phone(client.getPhoneNumber())
                .acc(history.accountNumber())
                .senderAcc(history.accountNumber())
                .branchName(client.getAgencyLabel())
                .transactionId(history.uuid())
                .transactionDate(history.createdAt())
                .status("VALIDATED")
                .build();
    }

    public CombineHistory mapToTransactionHistory(Transaction transaction) {

        String accountNumber = transaction.issuerAccount();
        String account = mapper.mapToModel(accountRepository.getOneByAccount(accountNumber)).clientCode();

        Client client = clientMapper.mapToModel(clientRepository.getOneByClientCode(account));

        return CombineHistory.builder()
                .name(client.getFullName())
                .service(transaction.type().toString())
                .amount(transaction.amount())
                .phone(client.getPhoneNumber())
                .acc(transaction.receiverAccount())
                .senderAcc(transaction.issuerAccount())
                .branchName(client.getAgencyLabel())
                .transactionId(transaction.uuid())
                .transactionDate(transaction.createdAt())
                .status(transaction.status().toString())
                .build();
    }
}
