//package com.iwomi.nofiaPay.core;
//
//import com.iwomi.nofiaPay.core.constants.AppConst;
//import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
//import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
//import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
//import com.iwomi.nofiaPay.core.utils.DateConverterUtils;
//import com.iwomi.nofiaPay.dtos.BranchDto;
//import com.iwomi.nofiaPay.frameworks.data.entities.*;
//import com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory.IAccountHistoryRepository;
//import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.IAccountRepository;
//import com.iwomi.nofiaPay.frameworks.data.repositories.branches.BranchRepository;
//import com.iwomi.nofiaPay.frameworks.data.repositories.clients.IClientRepository;
//import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.ITransactionRepository;
//import com.iwomi.nofiaPay.frameworks.data.repositories.validators.IValidatorRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
//@Configuration
//@Slf4j
//public class Seeder {
//    @Bean
//    CommandLineRunner initDatabase(
//            ITransactionRepository transactionRepository
//    ) {
//        // Transaction Setup
//        List<TransactionEntity> transactions = List.of(
//                // Transaction Setup for client
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("200000.00"))
//                        .reason("transfer")
//                        .batch("BATCH002")
//                        .issuerAccount("04005820-37227Y")
//                        .receiverAccount("04005857-37227Z")
//                        .type(OperationTypeEnum.SELF_SERVICE_MOMO)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("50000.00"))
//                        .reason("Service Payment")
//                        .batch("BATCH005")
//                        .issuerAccount("04005897-37227L")
//                        .receiverAccount("04005852-37227U")
//                        .type(OperationTypeEnum.SELF_SERVICE_OM)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("14400.00"))
//                        .reason("Purchase Payment")
//                        .batch("BATCH007")
//                        .issuerAccount("04005833-37227U")
//                        .receiverAccount("04005824-37227X")
//                        .type(OperationTypeEnum.SELF_SERVICE_MOMO)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("2000.00"))
//                        .reason("Bill Payment")
//                        .batch("BATCH008")
//                        .issuerAccount("04005827-37227Q")
//                        .receiverAccount("04005847-37227W")
//                        .type(OperationTypeEnum.SELF_SERVICE_OM)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("120000.00"))
//                        .reason("deposit")
//                        .batch("BATCH010")
//                        .issuerAccount("04005833-37227U")
//                        .receiverAccount("04005818-37227T")
//                        .type(OperationTypeEnum.SELF_SERVICE_MOMO)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("110000.00"))
//                        .reason("Personal Loan Payment")
//                        .batch("BATCH002")
//                        .issuerAccount("04005837-37227T")
//                        .receiverAccount("04005858-37227F")
//                        .type(OperationTypeEnum.SELF_SERVICE_OM)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("15000.00"))
//                        .reason("Service Payment")
//                        .batch("BATCH002")
//                        .issuerAccount("04005950-37227W")
//                        .receiverAccount("04005824-37227X")
//                        .type(OperationTypeEnum.SELF_SERVICE_MOMO)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("13000.00"))
//                        .reason("Service Payment")
//                        .batch("BATCH0016")
//                        .issuerAccount("04005846-37227Q")
//                        .receiverAccount("04005875-37227T")
//                        .type(OperationTypeEnum.SELF_SERVICE_OM)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//
//                // Transaction Setup for Merchant
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("20200.00"))
//                        .reason("Car Purchase")
//                        .batch("BATCH028")
//                        .issuerAccount("04005857-37227Z")
//                        .receiverAccount("04006024-37227S")
//                        .type(OperationTypeEnum.MERCHANT_DIGITAL_MOMO)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("25000.00"))
//                        .reason("Salary Payment")
//                        .batch("BATCH020")
//                        .issuerAccount("04005820-37227Y")
//                        .receiverAccount("04005847-37227W")
//                        .type(OperationTypeEnum.MERCHANT_CASH)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("2000.00"))
//                        .reason("Insurance Payment")
//                        .batch("BATCH022")
//                        .issuerAccount("04005854-37227G")
//                        .receiverAccount("04005872-37227A")
//                        .type(OperationTypeEnum.MERCHANT_DIGITAL_MOMO)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("3000.00"))
//                        .reason("Purchase Payment")
//                        .batch("BATCH024")
//                        .issuerAccount("04005818-37227T")
//                        .receiverAccount("04005857-37227Z")
//                        .type(OperationTypeEnum.MERCHANT_DIGITAL_OM)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//
//                // Transaction Setup for Agent
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("5000.00"))
//                        .reason("Tuition Payment")
//                        .batch("Batch026")
//                        .issuerAccount("04005858-37227F")
//                        .receiverAccount("04005872-37227A")
//                        .type(OperationTypeEnum.AGENT_CASH_COLLECTION)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("1500.00"))
//                        .reason("money transfer")
//                        .batch("Batch034")
//                        .issuerAccount("04005847-37227W")
//                        .receiverAccount("04005860-37227K")
//                        .type(OperationTypeEnum.AGENT_DIGITAL_COLLECTION_MOMO)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("7000.00"))
//                        .reason("money transfer")
//                        .batch("BATCH032")
//                        .issuerAccount("04005861-37227R")
//                        .receiverAccount("04005860-37227K")
//                        .type(OperationTypeEnum.REVERSEMENT)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build(),
//                TransactionEntity.builder()
//                        .amount(new BigDecimal("7000.00"))
//                        .reason("Invoice payment")
//                        .batch("Batch0030")
//                        .issuerAccount("04005858-37227F")
//                        .receiverAccount("04005872-37227A")
//                        .type(OperationTypeEnum.AGENT_TO_TELLER)
//                        .status(StatusTypeEnum.COLLECTED)
//                        .processed(true)
//                        .deleted(false)
//                        .build()
//        );
//
//        List<TransactionEntity> transacs = transactionRepository.findAll();
//
//        if (transacs.isEmpty()) transactionRepository.saveAll(transactions);
//
//        return args -> {
//            log.info("Preloading account " + transactions);
//        };
//    }
//}
