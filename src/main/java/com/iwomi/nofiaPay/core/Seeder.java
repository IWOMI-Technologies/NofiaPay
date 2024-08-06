package com.iwomi.nofiaPay.core;

import com.iwomi.nofiaPay.dtos.BranchDto;
import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.branches.BranchRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.IClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Seeder {
    @Bean
    CommandLineRunner initDatabase(
            IClientRepository clientRepository,
            BranchRepository branchRepository
) {
//        BranchDto branchDto = new BranchDto("Central", "8888");
//        // save branch
//        BranchEntity branch = branchRepository.createBranch(branchDto);
//
//        ClientEntity client = ClientEntity.builder()
//                .clientCode("CliCode78965")
//                .phoneNumber("44556688")
//                .address("etoudi")
//                .branchId(branch.getUuid().toString())
//                .build();

//        ClientEntity clientEntity = clientRepository.save(client);
        return args -> {
//            log.info("Preloading branch "+ branch);
//            log.info("Preloading clients "+ clientEntity);
        };
    }
}
