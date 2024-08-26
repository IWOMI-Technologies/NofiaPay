package com.iwomi.nofiaPay.services.transactions;

import com.iwomi.nofiaPay.core.constants.NomenclatureConstants;
import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;
import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import com.iwomi.nofiaPay.core.mappers.ITransactionMapper;
import com.iwomi.nofiaPay.dtos.*;
import com.iwomi.nofiaPay.dtos.responses.Account;
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
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.ITransactionRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import com.iwomi.nofiaPay.frameworks.externals.enums.IwomiPayTypesEnum;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.domain.IPayment;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.DigitalPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final ITransactionRepository iTransactionRepository;
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
    public List<Transaction> viewByIssuerAccount(String issuer) {

        return transactionRepository.getByIssuerAccount(issuer)
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }


    @Override
    public List<Transaction> viewByReceiverAccount(String receiver) {
        return transactionRepository.getByReceiverAccount(receiver)
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
        String batchCode = agentBranchCode(dto.agentAccount());

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .issuerAccount(dto.agentAccount())
                .receiverAccount(dto.clientAccount())
                .type(dto.operation())
                .status(StatusTypeEnum.COLLECTED)
                .build();

        return mapper.mapToModel(transactionRepository.createTransaction(entity));
    }

    @Override
    public Transaction selfService(SelfServiceDto dto) {

//        String payType = IwomiPayTypesEnum.om.toString().toLowerCase();
//        if (dto.operation().toString().contains("MOMO")) payType = IwomiPayTypesEnum.momo.toString().toLowerCase();
//
//        DigitalPaymentDto paymentDto = new DigitalPaymentDto("debit", payType, dto.amount(),
//                "generateMe", dto.reason(), dto.sourcePhoneNumber(), "CM", "xaf");
//        // make iwomi Pay request
//        Map<String, Object> response = payment.pay(paymentDto);
        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .issuerAccount(NomenclatureConstants.CBR)
                .receiverAccount(dto.clientAccount())
                .type(dto.operation())
                .status(StatusTypeEnum.PENDING)
                .build();

        return mapper.mapToModel(transactionRepository.createTransaction(entity));

    }

    @Override
    public Transaction AgentDigitalCollection(AgentDigitalCollectionDto dto) {

//        String payType = IwomiPayTypesEnum.om.toString().toLowerCase();
//        if (dto.operation().toString().contains("MOMO")) payType = IwomiPayTypesEnum.momo.toString().toLowerCase();
//
//        DigitalPaymentDto paymentDto = new DigitalPaymentDto("debit", payType, dto.amount(),
//                "generateMe", dto.reason(), dto.sourcePhoneNumber(), "CM", "xaf");
//
//        Map<String, Object> response = payment.pay(paymentDto);
        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .issuerAccount(dto.agentAccount())
                .receiverAccount(dto.clientAccount())
                .type(dto.operation())
                .status(StatusTypeEnum.COLLECTED)
                .build();

        return mapper.mapToModel(transactionRepository.createTransaction(entity));

    }

    @Override
    public Transaction merchantCash(MerchantCashDto dto) {
        String batchCode = agentBranchCode(dto.merchantAccount());

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .issuerAccount(null)
                .receiverAccount(dto.merchantAccount())
                .type(dto.operation())
                .status(StatusTypeEnum.PENDING)
                .build();

        if (entity.getType() != OperationTypeEnum.MERCHANT_CASH && entity.getIssuerAccount() != null)
            throw new IllegalArgumentException("Issuer account must be null for MERCHANT_CASH type.");

        return mapper.mapToModel(transactionRepository.createTransaction(entity));
    }

    @Override
    public Transaction merchantDigital(MerchantDigitalDto dto) {

//        String payType = IwomiPayTypesEnum.om.toString().toLowerCase();
//        if (dto.operation().toString().contains("MOMO")) payType = IwomiPayTypesEnum.momo.toString().toLowerCase();
//
//        DigitalPaymentDto paymentDto = new DigitalPaymentDto("debit", payType, dto.amount(),
//                "generateMe", dto.reason(), dto.sourcePhoneNumber(), "CM", "xaf");
//
//        Map<String, Object> response = payment.pay(paymentDto);
        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .issuerAccount(NomenclatureConstants.CBR)
                .receiverAccount(dto.merchantAccount())
                .type(dto.operation())
                .status(StatusTypeEnum.COLLECTED)
                .build();
        if (entity.getType().toString().startsWith("MERCHANT_DIGITAL") && !Objects.equals(entity.getIssuerAccount(), NomenclatureConstants.CBR))
            throw new IllegalArgumentException("Issuer account must be " + NomenclatureConstants.CBR + " for MERCHANT_DIGITAL** type.");

        return mapper.mapToModel(transactionRepository.createTransaction(entity));

    }


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

    public Boolean isIssuerAccount(String account) {
        return iTransactionRepository.existsByIssuerAccount(account);
    }

    @Override
    public List<Transaction> getLatestTop5TransactionByClientCode(String clientCode) {

        List<String> accounts = accountRepository.getAccountNumbersByClientCode(clientCode)
                .stream()
                .map(AccountEntity::getAccountNumber)
                .toList();

        List<Transaction> issuerAccounts = transactionRepository.getTop5ByIssuerAccount(accounts)
                .stream()
                .limit(5)
                .map(mapper::mapToModel)
                .toList();

        List<Transaction> receiverAccounts = transactionRepository.getTop5ByReceiverAccount(accounts)
                .stream()
                .limit(5)
                .map(mapper::mapToModel)
                .toList();

        List<Transaction> merged = Stream.of(issuerAccounts, receiverAccounts)
                .flatMap(List::stream)
                .toList();

        return merged
                .stream()
                .sorted(Comparator.comparing(Transaction::createdAt).reversed())
                .limit(5)
                .toList();

    }

}
