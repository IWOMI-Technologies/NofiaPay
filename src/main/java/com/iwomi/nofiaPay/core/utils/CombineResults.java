package com.iwomi.nofiaPay.core.utils;

import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.dtos.responses.CombineHistory;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CombineResults {

    private final AccountRepository accountRepository;

    private final ClientRepository clientRepository;

    public CombineHistory mapToAccountHistory(AccountHistory history) {
        String account = accountRepository.getOneByAccount(history.accountNumber()).getClientCode();
        ClientEntity client = clientRepository.getOneByClientCode(account);

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
        String clientCode = accountRepository.getOneByAccount(transaction.issuerAccount()).getClientCode();
        ClientEntity client = clientRepository.getOneByClientCode(clientCode);

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
