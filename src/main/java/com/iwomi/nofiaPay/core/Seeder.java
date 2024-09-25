package com.iwomi.nofiaPay.core;

import com.iwomi.nofiaPay.core.constants.AppConst;
import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import com.iwomi.nofiaPay.core.utils.DateConverterUtils;
import com.iwomi.nofiaPay.dtos.BranchDto;
import com.iwomi.nofiaPay.frameworks.data.entities.*;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounthistory.IAccountHistoryRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.IAccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.branches.BranchRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.IClientRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.ITransactionRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.validators.IValidatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Configuration
@Slf4j
public class Seeder {
    @Bean
    CommandLineRunner initDatabase(
            IClientRepository clientRepository,
            IAccountRepository accountRepository,
            IValidatorRepository validatorRepository,
            IAccountHistoryRepository historyRepository,
            ITransactionRepository transactionRepository
    ) {
        ClientEntity client = ClientEntity.builder()
                .clientCode("COO1")                          // Unique client code
                .firstName("Will")                           // Client's first name
                .lastName("Smith")                           // Client's last name
                .fullName("Will Smith")                      // Client's full name
                .agencyCode("AGENCY1")                       // Code of the agency
                .agencyLabel("CORT")                         // Label of the agency
                .managerCode("MGR123")                       // Manager's code
                .managerName("John Doe")                     // Manager's name
                .firstAddress("123 Main St")                 // First address
                .secondAddress("456 Secondary St")           // Second address (optional)
                .clientType("PP")                            // Client type: PP (physical person) or PM (moral person)
                .dateOfBirth(new Date(1980 - 1900, 4, 25))   // Date of birth
                .placeOfBirth("Philadelphia")                // Place of birth
                .idNumber("A123456789")                      // Identification number
                .idDeliveryDate(new Date(2015 - 1900, 7, 15)) // Date of ID delivery
                .idDeliveryPlace("Philadelphia Office")      // Place of ID delivery
                .idExpirationDate(new Date(2025 - 1900, 7, 15)) // ID expiration date
                .commercialRegNum("CRN123456")               // Commercial registration number
                .taxPayerNumber("TPN987654")                 // Taxpayer number
                .businessCreationDate(new Date(2005 - 1900, 3, 15)) // Business creation date
                .notificationPhoneNumber("555-555-5555")     // Phone number for notifications (SMS)
                .phoneNumber("699887766")                        // Client's phone number for connection
                .clientCreationDate(new Date(2020 - 1900, 1, 1)) // Client creation date
                .email("will.smith@example.com")             // Client's email
                .agentCode("AGT987")                         // Agent's code
                .agentName("Agent Name")                     // Agent's name
                .build();

        ClientEntity savedClient = clientRepository.save(client);

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
//                .accountNumber("111122223333")
//                .debit(new BigDecimal("990"))
//                .credit(new BigDecimal("10000"))
//                .clientCode("COO1")
//                .startDate(new Date())
//                .endDate(new Date())
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
//                .debit(new BigDecimal("990"))
//                .credit(new BigDecimal("560000"))
//                .clientCode("COO1")
//                .startDate(new Date())
//                .endDate(new Date())
//                .build();
//        List<AccountEntity> savedAccount = accountRepository.saveAll(List.of(accountOne, accountTwo));
//
//        TransactionEntity transactionOne = TransactionEntity.builder()
//                .amount(new BigDecimal("500.00"))
//                .reason("Salary Payment")
//                .batch("Batch001")
//                .issuerAccount("87654321-4321")
//                .receiverAccount("0987654321")
//                .type(OperationTypeEnum.AGENT_CASH_COLLECTION)
//                .status(StatusTypeEnum.COLLECTED)
////                .sense(SenseTypeEnum.CREDIT)
//                .processed(true)
//                .deleted(false)
//                .build();
//
//        TransactionEntity transactionTwo = TransactionEntity.builder()
//                .amount(new BigDecimal("1000.00"))
//                .reason("Invoice Payment")
//                .batch("Batch002")
//                .issuerAccount("111122223333")
//                .receiverAccount("87654321-4321")
//                .type(OperationTypeEnum.AGENT_CASH_COLLECTION)
//                .status(StatusTypeEnum.FAILED)
////                .sense(SenseTypeEnum.DEBIT)
//                .processed(false)
//                .deleted(false)
//                .build();
//
//        List<TransactionEntity> transactions = transactionRepository
//                .saveAll(List.of(transactionOne, transactionTwo));
//
//        AccountHistoryEntity historyOne = AccountHistoryEntity.builder()
//                .agencyCode("AG001")
//                .accountNumber("87654321-4321")
//                .currency("USD")
//                .cle("CLE001")
//                .operationCode("OP001")
//                .operationTitle("Deposit")
//                .transactionReference("TX001")
//                .amount(new BigDecimal("500.00"))
//                .sense(SenseTypeEnum.CREDIT)
//                .accountingDocument("DOC001")
//                .accountingDate(DateConverterUtils.convertToDate("2024-09-01"))
//                .valueDate("2024-09-01")
//                .balance("1500.00")
//                .type(OperationTypeEnum.AGENT_CASH_COLLECTION)
//                .build();
//
//        AccountHistoryEntity historyTwo = AccountHistoryEntity.builder()
//                .agencyCode("AG002")
//                .accountNumber("87654321-4321")
//                .currency("EUR")
//                .cle("CLE002")
//                .operationCode("OP002")
//                .operationTitle("Withdrawal")
//                .transactionReference("TX002")
//                .amount(new BigDecimal("300.00"))
//                .sense(SenseTypeEnum.DEBIT)
//                .accountingDocument("DOC002")
//                .accountingDate(DateConverterUtils.convertToDate("2024-09-01"))
//                .valueDate("2024-09-10")
//                .balance("700.00")
//                .type(OperationTypeEnum.AGENT_CASH_COLLECTION)
//                .build();
//
//        // Save account histories to the repository
//        List<AccountHistoryEntity> accounts = historyRepository
//                .saveAll(List.of(historyOne, historyTwo));

        // Transaction Setup
        List<TransactionEntity> transactions = List.of(
                // Transaction Setup for client
                TransactionEntity.builder()
                        .amount(new BigDecimal("200000.00"))
                        .reason("transfer")
                        .batch("BATCH002")
                        .issuerAccount("04005820-37227Y")
                        .receiverAccount("04005857-37227Z")
                        .type(OperationTypeEnum.SELF_SERVICE_MOMO)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("50000.00"))
                        .reason("Service Payment")
                        .batch("BATCH005")
                        .issuerAccount("04005897-37227L")
                        .receiverAccount("04005852-37227U")
                        .type(OperationTypeEnum.SELF_SERVICE_OM)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("14400.00"))
                        .reason("Purchase Payment")
                        .batch("BATCH007")
                        .issuerAccount("04005833-37227U")
                        .receiverAccount("04005824-37227X")
                        .type(OperationTypeEnum.SELF_SERVICE_MOMO)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("2000.00"))
                        .reason("Bill Payment")
                        .batch("BATCH008")
                        .issuerAccount("04005827-37227Q")
                        .receiverAccount("04005847-37227W")
                        .type(OperationTypeEnum.SELF_SERVICE_OM)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("120000.00"))
                        .reason("deposit")
                        .batch("BATCH010")
                        .issuerAccount("04005833-37227U")
                        .receiverAccount("04005818-37227T")
                        .type(OperationTypeEnum.SELF_SERVICE_MOMO)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("110000.00"))
                        .reason("Personal Loan Payment")
                        .batch("BATCH002")
                        .issuerAccount("04005837-37227T")
                        .receiverAccount("04005858-37227F")
                        .type(OperationTypeEnum.SELF_SERVICE_OM)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("15000.00"))
                        .reason("Service Payment")
                        .batch("BATCH002")
                        .issuerAccount("04005950-37227W")
                        .receiverAccount("04005824-37227X")
                        .type(OperationTypeEnum.SELF_SERVICE_MOMO)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("13000.00"))
                        .reason("Service Payment")
                        .batch("BATCH0016")
                        .issuerAccount("04005846-37227Q")
                        .receiverAccount("04005875-37227T")
                        .type(OperationTypeEnum.SELF_SERVICE_OM)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),

                // Transaction Setup for Merchant
                TransactionEntity.builder()
                        .amount(new BigDecimal("20200.00"))
                        .reason("Car Purchase")
                        .batch("BATCH028")
                        .issuerAccount("04005857-37227Z")
                        .receiverAccount("04006024-37227S")
                        .type(OperationTypeEnum.MERCHANT_DIGITAL_MOMO)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("25000.00"))
                        .reason("Salary Payment")
                        .batch("BATCH020")
                        .issuerAccount("04005820-37227Y")
                        .receiverAccount("04005847-37227W")
                        .type(OperationTypeEnum.MERCHANT_CASH)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("2000.00"))
                        .reason("Insurance Payment")
                        .batch("BATCH022")
                        .issuerAccount("04005854-37227G")
                        .receiverAccount("04005872-37227A")
                        .type(OperationTypeEnum.MERCHANT_DIGITAL_MOMO)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("3000.00"))
                        .reason("Purchase Payment")
                        .batch("BATCH024")
                        .issuerAccount("04005818-37227T")
                        .receiverAccount("04005857-37227Z")
                        .type(OperationTypeEnum.MERCHANT_DIGITAL_OM)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),

                // Transaction Setup for Agent
                TransactionEntity.builder()
                        .amount(new BigDecimal("5000.00"))
                        .reason("Tuition Payment")
                        .batch("Batch026")
                        .issuerAccount("04005858-37227F")
                        .receiverAccount("04005872-37227A")
                        .type(OperationTypeEnum.AGENT_CASH_COLLECTION)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("1500.00"))
                        .reason("money transfer")
                        .batch("Batch034")
                        .issuerAccount("04005847-37227W")
                        .receiverAccount("04005860-37227K")
                        .type(OperationTypeEnum.AGENT_DIGITAL_COLLECTION_MOMO)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("7000.00"))
                        .reason("money transfer")
                        .batch("BATCH032")
                        .issuerAccount("04005861-37227R")
                        .receiverAccount("04005860-37227K")
                        .type(OperationTypeEnum.REVERSEMENT)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build(),
                TransactionEntity.builder()
                        .amount(new BigDecimal("7000.00"))
                        .reason("Invoice payment")
                        .batch("Batch0030")
                        .issuerAccount("04005858-37227F")
                        .receiverAccount("04005872-37227A")
                        .type(OperationTypeEnum.AGENT_TO_TELLER)
                        .status(StatusTypeEnum.COLLECTED)
                        .processed(true)
                        .deleted(false)
                        .build()
        );

        // Save all transactions
        transactionRepository.saveAll(transactions);

        return args -> {
//            log.info("Preloading validators "+ validatorEntity);
            log.info("Preloading clients " + savedClient);
//            log.info("Preloading clients " + savedAccount);
            log.info("Preloading account " + transactions);
//            log.info("Preloading account " + accounts);
        };
    }
}
