package com.iwomi.nofiaPay.services.transactions;

import com.iwomi.nofiaPay.core.constants.NomenclatureConstants;
import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;
import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import com.iwomi.nofiaPay.core.mappers.ITransactionMapper;
import com.iwomi.nofiaPay.dtos.*;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.BatchEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.batches.BatchRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.branches.BranchRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.tellerBox.TellerBoxRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import com.iwomi.nofiaPay.frameworks.externals.enums.IwomiPayTypesEnum;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.domain.IPayment;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.DigitalPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final BatchRepository batchRepository;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final BranchRepository branchRepository;
    private final TellerBoxRepository tellerBoxRepository;
    private final ITransactionMapper mapper;
    private final IPayment payment;

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

    @Override
    public Transaction agentCashCollection(AgentCashCollectionDto dto) {
        String batchCode = agentBranchCode(dto.agentCollectionAccount());

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .accountNumber(dto.agentCollectionAccount())
                .destinationAccount(dto.clientAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();

        if (entity.getType() == OperationTypeEnum.AGENT_CASH_COLLECTION && (entity.getDestinationAccount() == null ||
                entity.getDestinationAccount().isEmpty()))
            throw new IllegalArgumentException("Destination account must not be null for AGENT_CASH_COLLECTION type.");

        return mapper.mapToModel(transactionRepository.createTransaction(entity));
    }

//    @Override
//    public List<Transaction> agentDigitalCollection(AgentDigitalCollectionDto dto) {
//        ClientEntity client = clientRepository.getOneByPhone(dto.clientPhoneNumber());
//        // get client branchid then account from that branchid and AccountTypeEnum -> BRANCH_DIGITAL_ACCOUNT
//        String branchAccountNumber = accountRepository.getOneByBranchAndType(client.getAgencyCode(),
//                AccountTypeEnum.BRANCH_DIGITAL_ACCOUNT).getAccountNumber();
//
//        String batchCode = agentBranchCode(dto.agentCollectionAccount());
//
//        TransactionEntity outOfDigitalBranchAccount = TransactionEntity.builder()
//                .amount(new BigDecimal(dto.amount()))
//                .reason(dto.reason())
//                .batch(batchCode)
//                .accountNumber(branchAccountNumber)
//                .type(dto.operation())
//                .sense(SenseTypeEnum.DEBIT)
//                .status(StatusTypeEnum.PENDING)
//                .build();
//        TransactionEntity inAgentAccount = TransactionEntity.builder()
//                .amount(new BigDecimal(dto.amount()))
//                .reason(dto.reason())
//                .batch(batchCode)
//                .accountNumber(dto.agentCollectionAccount())
//                .type(dto.operation())
//                .sense(SenseTypeEnum.CREDIT)
//                .status(StatusTypeEnum.PENDING)
//                .build();
//        TransactionEntity outOfAgentAccount = TransactionEntity.builder()
//                .amount(new BigDecimal(dto.amount()))
//                .reason(dto.reason())
//                .batch(batchCode)
//                .accountNumber(dto.agentCollectionAccount())
//                .type(dto.operation())
//                .sense(SenseTypeEnum.DEBIT)
//                .status(StatusTypeEnum.PENDING)
//                .build();
//        TransactionEntity inClientAccount = TransactionEntity.builder()
//                .amount(new BigDecimal(dto.amount()))
//                .reason(dto.reason())
//                .batch(batchCode)
//                .accountNumber(dto.clientDestinationAccount())
//                .type(dto.operation())
//                .sense(SenseTypeEnum.CREDIT)
//                .status(StatusTypeEnum.PENDING)
//                .build();
//        return transactionRepository
//                .createMany(List.of(outOfDigitalBranchAccount, inAgentAccount,
//                        outOfAgentAccount, inClientAccount))
//                .stream().map(mapper::mapToModel)
//                .toList();
//    }

    @Override
    public List<Transaction> selfService(SelfServiceDto dto) {
        System.out.println("in service************");
//        // get client branchid then account with that branchid and AccountTypeEnum -> BRANCH_DIGITAL_ACCOUNT
        String clientBranchCode = accountRepository.getOneByAccount(dto.clientAccount()).getAgencyCode();
        String branchCDA = accountRepository.getOneByBranchCodeAndType(clientBranchCode,
                "0003").getAccountNumber();     // 0001 is type code for digital accounts

//        String payType = IwomiPayTypesEnum.om.toString().toLowerCase();
//        if (dto.operation().toString().contains("MOMO")) payType = IwomiPayTypesEnum.momo.toString().toLowerCase();
//
//        DigitalPaymentDto paymentDto = new DigitalPaymentDto("debit", payType, dto.amount(),
//                "generateMe", dto.reason(), dto.sourcePhoneNumber(), "CM", "xaf");
//        // make iwomi Pay request
//        Map<String, Object> response = payment.pay(paymentDto);

        TransactionEntity outOfCBR = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(NomenclatureConstants.CBR)
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();
        TransactionEntity inCL = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(clientBranchCode + NomenclatureConstants.CL)   // append branch code to compte liaison
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();
        TransactionEntity outOfCL = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(clientBranchCode + NomenclatureConstants.CL)   // append branch code to compte liaison
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();
        // CDA means compte digital agence
        TransactionEntity inCDA = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(branchCDA)
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();
        TransactionEntity outOfCDA = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(branchCDA)
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();
        TransactionEntity inClientAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(dto.clientAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();

        return transactionRepository
                .createMany(List.of(outOfCBR, inCL, outOfCL, inCDA, outOfCDA, inClientAccount))
                .stream().map(mapper::mapToModel)
                .toList();
    }

    @Override
    public List<Transaction> AgentDigitalCollection(AgentDigitalCollectionDto dto) {
        String batchCode = agentBranchCode(dto.agentCollectionAccount());

        String clientBranchCode = accountRepository.getOneByAccount(dto.clientAccount()).getAgencyCode();
        String branchCDA = accountRepository.getOneByBranchCodeAndType(clientBranchCode,
                "0003").getAccountNumber();

//        String payType = IwomiPayTypesEnum.om.toString().toLowerCase();
//        if (dto.operation().toString().contains("MOMO")) payType = IwomiPayTypesEnum.momo.toString().toLowerCase();
//
//        DigitalPaymentDto paymentDto = new DigitalPaymentDto("debit", payType, dto.amount(),
//                "generateMe", dto.reason(), dto.sourcePhoneNumber(), "CM", "xaf");
//
//        Map<String, Object> response = payment.pay(paymentDto);

        TransactionEntity outOfCBR = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .accountNumber(NomenclatureConstants.CBR)
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();
        TransactionEntity inCL = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .accountNumber(clientBranchCode + NomenclatureConstants.CL)
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();
        TransactionEntity outOfCL = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .accountNumber(clientBranchCode + NomenclatureConstants.CL)
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();

        TransactionEntity inAgentAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .accountNumber(dto.agentCollectionAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();

        TransactionEntity outOfAgentAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .accountNumber(dto.agentCollectionAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.PENDING)
                .build();

        TransactionEntity inClientAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .accountNumber(dto.clientDestinationAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.PENDING)
                .build();

        return transactionRepository
                .createMany(List.of(outOfCBR, inCL, outOfCL, inAgentAccount, outOfAgentAccount,inClientAccount))
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

    @Override
    public Transaction merchantCash(MerchantCashDto dto) {
        String batchCode = agentBranchCode(dto.merchantAccount());

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
//                .batch(batchCode)
                .accountNumber(dto.merchantAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.PENDING)
                .build();

        return mapper.mapToModel(transactionRepository.createTransaction(entity));
    }

    @Override
    public List<Transaction> merchantDigital(MerchantDigitalDto dto) {

        String clientBranchCode = accountRepository.getOneByAccount(dto.clientAccount()).getAgencyCode();
        String branchCDA = accountRepository.getOneByBranchCodeAndType(clientBranchCode,
                "0003").getAccountNumber();

//        String payType = IwomiPayTypesEnum.om.toString().toLowerCase();
//        if (dto.operation().toString().contains("MOMO")) payType = IwomiPayTypesEnum.momo.toString().toLowerCase();
//
//        DigitalPaymentDto paymentDto = new DigitalPaymentDto("debit", payType, dto.amount(),
//                "generateMe", dto.reason(), dto.sourcePhoneNumber(), "CM", "xaf");
//
//        Map<String, Object> response = payment.pay(paymentDto);

        TransactionEntity outOfCBR = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(NomenclatureConstants.CBR)
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.PENDING)
                .build();

        TransactionEntity inCL = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(clientBranchCode + NomenclatureConstants.CL)   // append branch code to compte liaison
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();

        TransactionEntity outOfCL = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(clientBranchCode + NomenclatureConstants.CL)   // append branch code to compte liaison
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();

        TransactionEntity inCDA = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(branchCDA)
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();

        TransactionEntity outOfCDA = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(branchCDA)
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();
        TransactionEntity inMerchantAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .accountNumber(dto.merchantAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.PENDING)
                .build();
        return transactionRepository
                .createMany(List.of(outOfCBR,inCL, outOfCL, inCDA, outOfCDA, inMerchantAccount ))
                .stream().map(mapper::mapToModel)
                .toList();
    }

//    @Override
//    public List<Transaction> merchantDigital(MerchantDigitalDto dto) {
//        ClientEntity client = clientRepository.getOneByPhone(dto.clientPhoneNumber());
//        // get client branchid then account with that branchid and AccountTypeEnum -> BRANCH_DIGITAL_ACCOUNT
//        String branchAccountNumber = accountRepository.getOneByBranchAndType(client.getAgencyCode(),
//                AccountTypeEnum.BRANCH_DIGITAL_ACCOUNT).getAccountNumber();
//
//        // since agent is not involved there is no need to fetch "batchCode"
//
//        TransactionEntity outOfDigitalBranchAccount = TransactionEntity.builder()
//                .amount(new BigDecimal(dto.amount()))
//                .reason(dto.reason())
////               .batch(batchCode)
//                .accountNumber(branchAccountNumber)
//                .type(dto.operation())
//                .sense(SenseTypeEnum.DEBIT)
//                .status(StatusTypeEnum.PENDING)
//                .build();
//
//        TransactionEntity inClientAccount = TransactionEntity.builder()
//                .amount(new BigDecimal(dto.amount()))
//                .reason(dto.reason())
////                .batch(batchCode)
//                .accountNumber(dto.merchantAccount())
//                .type(dto.operation())
//                .sense(SenseTypeEnum.CREDIT)
//                .status(StatusTypeEnum.PENDING)
//                .build();
//
//        return transactionRepository
//                .createMany(List.of(outOfDigitalBranchAccount, inClientAccount))
//                .stream().map(mapper::mapToModel)
//                .toList();
//    }

//    @Override
//    public Map<String, Object> initiateReversement(String branchCode, String boxNumber, String agentClientId) {
//        String branchUuid = branchRepository.getOneByCode(branchCode).getUuid().toString();
//        String clientUuid = tellerBoxRepository.getOneByNumber(boxNumber, branchUuid).getUuid().toString();
//        String tellerAccount = accountRepository.getOneByClientIdAndType(clientUuid,
//                AccountTypeEnum.COLLECTION).getAccountNumber();
//
//        // get only the batches code
//        List<String> batchCodes = batchRepository.getOneByClientId(agentClientId).stream().map(BatchEntity::getBatchCode).toList();
//        List<TransactionEntity> transactions = transactionRepository.getByBatchCodes(batchCodes);
//
//        Map<String, BigDecimal> groupedTransactions = transactions.stream().collect(Collectors.groupingBy(
//                TransactionEntity::getBatch, // Key extractor
//                Collectors.mapping(
//                        TransactionEntity::getAmount, // Value extractor
//                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add) // Aggregation
//                )
//        ));
//
//        return Map.of(
//                "tellerAccount", tellerAccount,
//                "batchesTransactions", groupedTransactions
//        );
//
//    }

//    @Override
//    public List<Transaction> reversement(ReversementDto dto) {
//        String agentAccount = accountRepository.getOneByClientIdAndType(dto.agentClientId(),
//                AccountTypeEnum.COLLECTION).getAccountNumber();
//
//        List<Transaction> transactions = List.of();
//
//        for (BatchDto batch : dto.batchesPayment()) {
//            BatchEntity foundBatch = batchRepository.getOneByCode(batch.batchCode());
//            BigDecimal subtractedAmount = foundBatch.getRemainder().subtract(batch.amount());
//            if (subtractedAmount.intValue() > 0) batchRepository.updateBatch(foundBatch.getUuid(), subtractedAmount);
//
//            TransactionEntity outOfAgentAccount = TransactionEntity.builder()
//                    .amount(batch.amount())
//                    .reason(dto.raison())
//                    .batch(batch.batchCode())
//                    .accountNumber(agentAccount)
//                    .type(dto.operation())  // agent to teller type
//                    .sense(SenseTypeEnum.DEBIT)
//                    .status(StatusTypeEnum.PENDING)
//                    .build();
//            TransactionEntity inTellersAccount = TransactionEntity.builder()
//                    .amount(batch.amount())
//                    .reason(dto.raison())
//                    .batch(batch.batchCode())
//                    .accountNumber(dto.tellersAccount())
//                    .type(dto.operation())
//                    .sense(SenseTypeEnum.CREDIT)
//                    .status(StatusTypeEnum.PENDING)
//                    .build();
//
//            transactions = transactionRepository
//                    .createMany(List.of(outOfAgentAccount, inTellersAccount))
//                    .stream().map(mapper::mapToModel)
//                    .toList();
//
//            //TODO to validate change both status to validated
//        }
//
//        return transactions;
//
//    }

    private String agentBranchCode(String agentCollectionAccount) {
//        AccountEntity account = accountRepository.getOneByAccount(agentCollectionAccount);
//        return batchRepository.getTodaysBatchCode(account.getClientId().toString());
        return "test";
    }

    private Boolean areAccountsInSameBranch(String accountOne, String accountTwo) {
        AccountEntity firstAcc = accountRepository.getOneByAccount(accountOne);
        AccountEntity secondAcc = accountRepository.getOneByAccount(accountTwo);

        return Objects.equals(firstAcc.getAgencyCode(), secondAcc.getAgencyCode());
    }
}
