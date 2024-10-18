package com.iwomi.nofiaPay.core.utils;

import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.dtos.responses.AccountHistory;
import com.iwomi.nofiaPay.dtos.responses.CombineHistory;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CombineResults {

    private final AccountRepository accountRepository;

    private final ClientRepository clientRepository;

    public CombineHistory mapToAccountHistory(AccountHistory history) {
        String clientCode = accountRepository.getOneByAccount(history.getAccountNumber()).getClientCode();
        ClientEntity client = clientRepository.getOneByClientCode(clientCode);

        return CombineHistory.builder()
                .name(client.getFullName())
                .service(history.getOperationCode())
                .amount(history.getAmount())
                .phone(client.getPhoneNumber())
                .acc(history.getAccountNumber())
                .senderAcc(history.getAccountNumber())
                .branchName(client.getAgencyLabel())
                .transactionId(history.getUuid())
                .transactionDate(history.getCreatedAt())
                .status("VALIDATED")
                .sense(history.getSense())
                .build();
    }

    public CombineHistory mapToTransactionHistory(Transaction transaction) {
        System.out.println("@@@@@ "+transaction.getIssuerAccount());
        String clientCode = accountRepository.getOneByAccount(transaction.getIssuerAccount()).getClientCode();
        System.out.println("@@@@@ CODE"+clientCode);

        ClientEntity client = clientRepository.getOneByClientCode(clientCode);

        List<String> clientAccounts = accountRepository.getAccountNumbersByClientCode(clientCode);

        SenseTypeEnum sense = clientAccounts.contains(transaction.getIssuerAccount()) ?
                SenseTypeEnum.DEBIT :
                SenseTypeEnum.CREDIT;

        return CombineHistory.builder()
                .name(client.getFullName())
                .service(transaction.getType().toString())
                .amount(transaction.getAmount())
                .phone(client.getPhoneNumber())
                .acc(transaction.getReceiverAccount())
                .senderAcc(transaction.getIssuerAccount())
                .branchName(client.getAgencyLabel())
                .transactionId(transaction.getUuid())
                .transactionDate(transaction.getCreatedAt())
                .status(transaction.getStatus().toString())
                .sense(sense)
                .build();
    }
}
