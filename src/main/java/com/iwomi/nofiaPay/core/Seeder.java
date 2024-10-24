package com.iwomi.nofiaPay.core;

import com.iwomi.nofiaPay.core.constants.AppConst;
import com.iwomi.nofiaPay.core.enums.FileTypeEnum;
import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import com.iwomi.nofiaPay.dtos.UploadDto;
import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TellerBoxEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidatorEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.branches.IBranchRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.tellerBox.ITellerBoxRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.ITransactionRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.validators.IValidatorRepository;
import com.iwomi.nofiaPay.services.filesService.IFilesService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Configuration
@Slf4j
public class Seeder {
    @Bean
    CommandLineRunner initDatabase(
            ITransactionRepository transactionRepository,
            IBranchRepository branchRepository,
            ITellerBoxRepository tellerBoxRepository,
            IFilesService filesService,
            IValidatorRepository validatorRepository
    ) {
        // DO NOT REMOVE
        ValidatorEntity validatorOne = ValidatorEntity.builder()
                .process(AppConst.SUBCRIPTION)
                .profiles(Set.of("agent", "client", "merchant"))
                .build();
        if (!validatorRepository.existsValidatorEntityByProcess(AppConst.SUBCRIPTION))
            validatorRepository.save(validatorOne);

        // FILE UPLOAD for client, account and account history
        List<UploadDto> dtos = List.of(
                new UploadDto(FileTypeEnum.CLIENT_FILE, convertFileToMultipartFile("clients")),
        new UploadDto(FileTypeEnum.ACCOUNT_FILE, convertFileToMultipartFile("accounts")),
        new UploadDto(FileTypeEnum.ACCOUNT_HISTORY_FILE, convertFileToMultipartFile("accounts-history"))
        );
        dtos.forEach(filesService::importFile);


        // Transaction Setup
        List<TransactionEntity> transactions = List.of(
                // Transaction Setup for client
                TransactionEntity.builder()
                        .amount(new BigDecimal("206700"))
                        .reason("transfer")
                        .batch("BATCH002")
                        .issuerAccount("04005818-37227T")
                        .receiverAccount("04005857-37227Z")
                        .type(OperationTypeEnum.SELF_SERVICE_MOMO)
                        .status(StatusTypeEnum.SUCCESSFUL)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("55000"))
                        .reason("Service Payment")
                        .batch("BATCH005")
                        .issuerAccount("04005897-37227L")
                        .receiverAccount("04005818-37227T")
                        .type(OperationTypeEnum.SELF_SERVICE_OM)
                        .status(StatusTypeEnum.SUCCESSFUL)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("14400"))
                        .reason("Purchase Payment")
                        .batch("BATCH007")
                        .issuerAccount("04005833-37227U")
                        .receiverAccount("04005824-37227X")
                        .type(OperationTypeEnum.SELF_SERVICE_MOMO)
                        .status(StatusTypeEnum.SUCCESSFUL)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("8888.00"))
                        .reason("Bill Payment")
                        .batch("BATCH008")
                        .issuerAccount("04005892-37227T")
                        .receiverAccount("04005847-37227W")
                        .type(OperationTypeEnum.MERCHANT_DIGITAL_MOMO)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("120000.00"))
                        .reason("deposit")
                        .batch("BATCH010")
                        .issuerAccount("04005833-37227U")
                        .receiverAccount("04005851-37227U")
                        .type(OperationTypeEnum.AGENT_DIGITAL_COLLECTION_OM)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build()
        );

        List<TransactionEntity> transacs = transactionRepository.findAll();

//        if (transacs.isEmpty()) transactionRepository.saveAll(transactions);

        //Branch setup
        List<BranchEntity> branches = List.of(
                BranchEntity.builder()
                        .code("004")
                        .name("AGENCE AGIP")
                        .deleted(false)
                        .build(),
                BranchEntity.builder()
                        .code("001")
                        .name("AGENCE BONANJO")
                        .deleted(false)
                        .build()
        );
        List<BranchEntity> branch = branchRepository.findAll();

        if (branch.isEmpty()) branchRepository.saveAll(branches);

        List<TellerBoxEntity> tellerBox = List.of(
                TellerBoxEntity.builder()
                        .number("1")
                        .branchCode("004")
                        .clientCode("01019175")
                        .deleted(false)
                        .build(),
                TellerBoxEntity.builder()
                        .number("2")
                        .branchCode("004")
                        .clientCode("01019174")
                        .deleted(false)
                        .build(),
                TellerBoxEntity.builder()
                        .number("3")
                        .branchCode("001")
                        .clientCode("01018632")
                        .deleted(false)
                        .build(),
                TellerBoxEntity.builder()
                        .number("4")
                        .branchCode("001")
                        .clientCode("01018637")
                        .deleted(false)
                        .build()
        );
        List<TellerBoxEntity> tellerb = tellerBoxRepository.findAll();

        if (tellerb.isEmpty()) tellerBoxRepository.saveAll(tellerBox);

        return args -> {
            log.info("Preloading account " + transactions);
            log.info("Preloading branch" + branches);
            log.info("Preloading tellerBox" + tellerBox);
        };
    }

    @SneakyThrows
    public MultipartFile convertFileToMultipartFile(String fileName) {
        File file = new File("src/main/resources/data/"+fileName+".xlsx");
        FileInputStream input = new FileInputStream(file);

        // Create a MockMultipartFile with the content of the file
        return new MockMultipartFile(
                "file", // Name of the file parameter (used in forms, can be anything)
                file.getName(), // Original filename
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // Content type (adjust for your use case)
                input // FileInputStream containing file data
        );
    }

}
