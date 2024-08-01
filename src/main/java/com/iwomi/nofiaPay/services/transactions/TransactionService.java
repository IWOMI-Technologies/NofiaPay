package com.iwomi.nofiaPay.services.transactions;

import com.iwomi.nofiaPay.core.mappers.ITransactionMapper;
import com.iwomi.nofiaPay.dtos.EnrollDto;
import com.iwomi.nofiaPay.dtos.TransactionDto;
import com.iwomi.nofiaPay.dtos.response.Enroll;
import com.iwomi.nofiaPay.dtos.response.Transaction;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService implements  ITransactionService {

    private final TransactionRepository transactionRepository;
    private  final ITransactionMapper mapper;
    @Override
    public List<Transaction> viewAllTransactions() {
        return transactionRepository.getAllTransaction()
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

    @Override
    public Transaction SaveTransaction(TransactionDto dto) {
        return mapper.mapToModel(transactionRepository.createTransaction(dto));
    }

    @Override
    public Transaction viewOne(UUID uuid) {
        return mapper.mapToModel(transactionRepository.getOne(uuid));
    }

    @Override
    public Transaction update(UUID uuid, TransactionDto dto) {
        return mapper.mapToModel(transactionRepository.updateTransaction(dto, uuid));
    }
    @Override
    public Transaction updateTransactionStatus(UUID uuid, TransactionDto dto) {
        return mapper.mapToModel(transactionRepository.updateTransaction(dto, uuid));
    }

    @Override
    public void deleteOne(UUID uuid) {
            transactionRepository.deleteTransaction(uuid);
    }
}
