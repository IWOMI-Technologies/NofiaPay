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
        String clientCode = accountRepository.getOneByAccount(history.accountNumber()).getClientCode();
        ClientEntity client = clientRepository.getOneByClientCode(clientCode);

        return CombineHistory.builder()
                .name(client.getFullName())
                .service(history.type())
                .amount(history.amount())
                .phone(client.getPhoneNumber())
                .acc(history.accountNumber())
                .senderAcc(history.accountNumber())
                .branchName(client.getAgencyLabel())
                .transactionId(history.uuid())
                .transactionDate(history.createdAt())
                .status("VALIDATED")
                .sense(history.sense())
                .build();
    }

    public CombineHistory mapToTransactionHistory(Transaction transaction) {
        System.out.println("@@@@@ "+transaction.issuerAccount());
        String clientCode = accountRepository.getOneByAccount(transaction.issuerAccount()).getClientCode();
        System.out.println("@@@@@ CODE"+clientCode);

        ClientEntity client = clientRepository.getOneByClientCode(clientCode);

        List<String> clientAccounts = accountRepository.getAccountNumbersByClientCode(clientCode);

        SenseTypeEnum sense = clientAccounts.contains(transaction.issuerAccount()) ?
                SenseTypeEnum.DEBIT :
                SenseTypeEnum.CREDIT;

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
                .sense(sense)
                .build();
    }
}
