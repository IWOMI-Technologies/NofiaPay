package com.iwomi.nofiaPay.frameworks.data.repositories.transactions;

import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.mappers.ITransactionMapper;
import com.iwomi.nofiaPay.dtos.TransactionDto;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionRepository {

    private final ITransactionRepository repository;

    private  final ITransactionMapper mapper;

    public List<TransactionEntity> getAllTransaction () {
        return repository.findAll();
    }

    public  TransactionEntity createTransaction (TransactionDto dto) {
        TransactionEntity transaction = mapper.mapToEntity(dto);
        return  repository.save(transaction);
    }

    public  TransactionEntity createTransaction (TransactionEntity entity) {
        return  repository.save(entity);
    }

    @Transactional
    public  List<TransactionEntity> createMany (List<TransactionEntity> entities) {
        return  repository.saveAll(entities);
    }

    public  TransactionEntity getOne(UUID uuid) {
        return  repository.findById(uuid)
                .orElseThrow(() -> new GeneralException("Transaction Not Found"));
    }

    public  List<TransactionEntity> getByBatch(String batchCode) {
        List<TransactionEntity> transactions =  repository.findByBatch(batchCode);
        if (transactions.isEmpty()) throw  new GeneralException("Transaction not found.");

        return transactions;
    }


    public  List<TransactionEntity> getByBatchCodes(List<String> batches) {
        List<TransactionEntity> transactions =  repository.findByBatchIn(batches);
        if (transactions.isEmpty()) throw  new GeneralException("Transaction not found.");

        return transactions;
    }

    public  List<TransactionEntity> getByIssuerAccount(String issuerAccount) {
        List<TransactionEntity> transactions =  repository.findByIssuerAccount(issuerAccount);
         if(transactions.isEmpty()) throw  new GeneralException("Transaction not found.");
         return transactions;

    }

    public  List<TransactionEntity> getByReceiverAccount(String receiverAccount) {
        List<TransactionEntity> transactions =  repository.findByIssuerAccount(receiverAccount);
        if(transactions.isEmpty()) throw  new GeneralException("Transaction not found.");
        return transactions;
    }
    public  TransactionEntity updateTransaction (TransactionDto dto, UUID uuid){
        TransactionEntity transaction = getOne(uuid);
        mapper.updateTransactionFromDto(dto, transaction);
        return  repository.save(transaction);
    }
    public  void  deleteTransaction(UUID uuid){
        repository.deleteById(uuid);
    }

    public TransactionEntity updateTransactionStatus(UUID uuid, StatusTypeEnum status) {
        TransactionEntity entity = getOne(uuid);
        entity.setStatus(status);
        return repository.save(entity);
    }
}
