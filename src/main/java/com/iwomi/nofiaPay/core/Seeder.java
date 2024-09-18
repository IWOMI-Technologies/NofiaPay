//package com.iwomi.nofiaPay.core;
//
//import com.iwomi.nofiaPay.core.constants.AppConst;
//import com.iwomi.nofiaPay.dtos.BranchDto;
//import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
//import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
//import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
//import com.iwomi.nofiaPay.frameworks.data.entities.ValidatorEntity;
//import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.IAccountRepository;
//import com.iwomi.nofiaPay.frameworks.data.repositories.branches.BranchRepository;
//import com.iwomi.nofiaPay.frameworks.data.repositories.clients.IClientRepository;
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
//            IClientRepository clientRepository,
//            IAccountRepository accountRepository,
//            IValidatorRepository validatorRepository
//) {
//
//        ValidatorEntity validatorOne = ValidatorEntity.builder()
//                .process(AppConst.SUBCRIPTION)
//                .profiles(Set.of("agent"))
//                .build();
////        ValidatorEntity validatorTwo = ValidatorEntity.builder().process("reversement").profiles(Set.of("agent")).build();
//
////        ClientEntity client = ClientEntity.builder()
////                .clientCode("cli1")
////                .phoneNumber("44444")
////                .agencyCode("agency1")
////                .build();
//        AccountEntity accountOne = AccountEntity.builder()
//                .agencyCode("agency2")
//                .agencyName("Agency Two")
//                .currency("USD")
//                .cle("CLE1234")
//                .accountTitle("Savings Account")
//                .chapter("01")
//                .chapterTitle("Savings")
//                .accountTypeCode("SA")
//                .accountTypeLabel("Savings Account")
//                .accountNumber("12345678-1234")
//                .balance(new BigDecimal("10000"))
//                .clientCode("COO1")
//                .accountCreation(new Date()) // Set the date to the current date or a specific date
//                .build();
//
//        AccountEntity accountTwo = AccountEntity.builder()
//                .agencyCode("agency2")
//                .agencyName("Agency Two")
//                .currency("USD")
//                .cle("CLE5678")
//                .accountTitle("Checking Account")
//                .chapter("02")
//                .chapterTitle("Checking")
//                .accountTypeCode("CA")
//                .accountTypeLabel("Checking Account")
//                .accountNumber("87654321-4321")
//                .balance(new BigDecimal("20000"))
//                .clientCode("COO1")
//                .accountCreation(new Date()) // Set the date to the current date or a specific date
//                .build();
////        if (!validatorRepository.existsValidatorEntityByProcess(AppConst.SUBCRIPTION))
////            validatorRepository.save(validatorOne);
//
////        ClientEntity clientEntity = clientRepository.save(client);
//        List<AccountEntity> accounts = accountRepository.saveAll(List.of(accountOne, accountTwo));
//        return args -> {
////            log.info("Preloading validators "+ validatorEntity);
////            log.info("Preloading clients "+ clientEntity);
//            log.info("Preloading account "+ accounts);
//        };
//    }
//}
