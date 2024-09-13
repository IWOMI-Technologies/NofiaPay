package com.iwomi.nofiaPay.processes.fileGeneration;

import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateTransaction {
    private final TransactionRepository repository;
    private final IGeneration generation;

//    public void generate() {
//        Date today = CoreUtils.localDateToDate(LocalDate.now());
//        List<TransactionEntity> result = repository.getByCreatedAt(today);
//        List<TransactionFile> data = result.stream().flatMap(entity -> {
//            if (entity.getType() == OperationTypeEnum.AGENT_CASH_COLLECTION)
//                return generation.agentCashCollection(entity);
//        });
//
//        return List.of();
//    }
}
