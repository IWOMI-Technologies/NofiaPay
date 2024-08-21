package com.iwomi.nofiaPay.core;

import com.iwomi.nofiaPay.core.constants.AppConst;
import com.iwomi.nofiaPay.dtos.BranchDto;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidatorEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.IAccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.branches.BranchRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.IClientRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.validators.IValidatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Configuration
@Slf4j
public class Seeder {
    @Bean
    CommandLineRunner initDatabase(
            IClientRepository clientRepository,
            IAccountRepository accountRepository,
            IValidatorRepository validatorRepository
) {

        ValidatorEntity validatorOne = ValidatorEntity.builder()
                .process(AppConst.SUBCRIPTION)
                .profiles(Set.of("agent"))
                .build();
//        ValidatorEntity validatorTwo = ValidatorEntity.builder().process("reversement").profiles(Set.of("agent")).build();

        ClientEntity client = ClientEntity.builder()
                .clientCode("cli1")
                .phoneNumber("44444")
                .agencyCode("agency1")
                .build();
        AccountEntity account = AccountEntity.builder()
                .agencyCode("agency1")
                .clientCode("cli1")
                .accountNumber("03445512-4569")
                .balance(new BigDecimal("50000"))
                .build();
        AccountEntity accountDigital = AccountEntity.builder()
                .agencyCode("agency1")
                .accountNumber("033333-3333")
                .balance(new BigDecimal("550000"))
                .accountTypeCode("0003")
                .build();
        if (!validatorRepository.existsValidatorEntityByProcess(AppConst.SUBCRIPTION))
            validatorRepository.save(validatorOne);

//        ClientEntity clientEntity = clientRepository.save(client);
//        List<AccountEntity> accounts = accountRepository.saveAll(List.of(account, accountDigital));
        return args -> {
//            log.info("Preloading validators "+ validatorEntity);
//            log.info("Preloading clients "+ clientEntity);
//            log.info("Preloading account "+ accounts);
        };
    }
}
