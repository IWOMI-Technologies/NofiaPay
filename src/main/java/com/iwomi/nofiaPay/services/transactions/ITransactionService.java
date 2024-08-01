package com.iwomi.nofiaPay.services.transactions;

import com.iwomi.nofiaPay.dtos.TransactionDto;
import com.iwomi.nofiaPay.dtos.responses.Transaction;

import java.util.List;
import java.util.UUID;

public interface ITransactionService {
    List<Transaction> viewAllTransactions();

    Transaction SaveTransaction(TransactionDto dto);

    Transaction viewOne(UUID uuid);

    Transaction update(UUID uuid, TransactionDto dto);

    Transaction updateTransactionStatus(UUID uuid, TransactionDto dto);

    void  deleteOne(UUID uuid);
}
