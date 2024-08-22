//package com.iwomi.nofiaPay.processes.fileGeneration;
//
//import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
//import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
//import com.iwomi.nofiaPay.core.utils.CoreUtils;
//import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
//import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
//import com.iwomi.nofiaPay.frameworks.generate.TransactionFile;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class GenerateTransaction {
//    private final TransactionRepository repository;
//    private final IGeneration generation;
//
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
//}
