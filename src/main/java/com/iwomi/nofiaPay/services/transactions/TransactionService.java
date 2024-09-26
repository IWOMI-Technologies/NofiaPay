package com.iwomi.nofiaPay.services.transactions;

import com.iwomi.nofiaPay.core.constants.NomenclatureConstants;
import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;
import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.errors.exceptions.UnAuthorizedException;
import com.iwomi.nofiaPay.core.mappers.ITransactionMapper;
import com.iwomi.nofiaPay.core.utils.OperationTypeUtil;
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
import com.iwomi.nofiaPay.frameworks.data.repositories.validators.ValidatorRepository;
import com.iwomi.nofiaPay.frameworks.externals.clients.AuthClient;
import com.iwomi.nofiaPay.frameworks.externals.enums.IwomiPayTypesEnum;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.domain.IPayment;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.DigitalPaymentDto;
import com.iwomi.nofiaPay.services.wbesocket.IWebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
    private final ValidatorRepository validatorRepository;
    private  final AuthClient authClient;
    private final ITransactionMapper mapper;
    private final IPayment payment;
    private final IWebsocketService websocketService;

    @Override
    public List<Transaction> viewAllTransactions() {

        List<TransactionEntity> trans = transactionRepository.getAllTransaction();
        return trans
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
    public Map<String, Object> allHistory(String clientCode) {
        List<String> accountNumbers = accountRepository.getAccountNumbersByClientCode(clientCode);

        List<TransactionEntity> debitTransactions = accountNumbers.stream().flatMap(acc -> transactionRepository
                .getByIssuerAccount(acc).stream()).toList();

        List<TransactionEntity> creditTransactions = accountNumbers.stream().flatMap(acc -> transactionRepository
                .getByReceiverAccount(acc).stream()).toList();

        return Map.of(
                "Debited", debitTransactions,
                "Credited", creditTransactions
        );
    }

    @Override
    public Transaction agentCashCollection(AgentCashCollectionDto dto) {
        String batchCode = agentBranchCode(dto.agentAccount());
        OperationTypeEnum operationType = OperationTypeUtil
                .getOperationTypeFromString(dto.operation());
        System.out.println("CASH :: op type "+operationType);

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .issuerAccount(dto.agentAccount())
                .receiverAccount(dto.clientAccount())
                .type(operationType)
                .status(StatusTypeEnum.COLLECTED)
                .build();

        return mapper.mapToModel(transactionRepository.createTransaction(entity));
    }

    @Override
    public Transaction selfService(String authUuid, SelfServiceDto dto) {
        OperationTypeEnum operationType = OperationTypeUtil
                .getOperationTypeFromString(dto.operation());
        System.out.println("CASH :: op type "+operationType);

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .issuerAccount(NomenclatureConstants.CBR)
                .receiverAccount(dto.clientAccount())
                .type(operationType)
                .status(StatusTypeEnum.PENDING)
                .build();

        Transaction savedTransaction = mapper.mapToModel(transactionRepository.createTransaction(entity));

        System.out.println("saved transaction: ================>>>>>>>>>>>>"+ savedTransaction);
        handlePaymentProcess(authUuid, dto, savedTransaction);

        return mapper.mapToModel(transactionRepository.getOne(savedTransaction.uuid()));
    }

    @Override
    public Transaction AgentDigitalCollection(AgentDigitalCollectionDto dto) {
        OperationTypeEnum operationType = OperationTypeUtil
                .getOperationTypeFromString(dto.operation());
        System.out.println("CASH :: op type "+operationType);

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .issuerAccount(dto.agentAccount())
                .receiverAccount(dto.clientAccount())
                .type(operationType)
                .status(StatusTypeEnum.COLLECTED)
                .build();

        return mapper.mapToModel(transactionRepository.createTransaction(entity));
    }

    @Override
    public Transaction merchantCash(MerchantCashDto dto) {
        String batchCode = agentBranchCode(dto.merchantAccount());
        OperationTypeEnum operationType = OperationTypeUtil
                .getOperationTypeFromString(dto.operation());
        System.out.println("CASH :: op type "+operationType);

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .issuerAccount(null)
                .receiverAccount(dto.merchantAccount())
                .type(operationType)
                .status(StatusTypeEnum.PENDING)
                .build();

        if (entity.getType() != OperationTypeEnum.MERCHANT_CASH && entity.getIssuerAccount() != null)
            throw new IllegalArgumentException("Issuer account must be null for MERCHANT_CASH type.");

        return mapper.mapToModel(transactionRepository.createTransaction(entity));
    }

    @Override
    public Transaction merchantDigital(MerchantDigitalDto dto) {
        OperationTypeEnum operationType = OperationTypeUtil
                .getOperationTypeFromString(dto.operation());
        System.out.println("CASH :: op type "+operationType);

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .issuerAccount(NomenclatureConstants.CBR)
                .receiverAccount(dto.merchantAccount())
                .type(operationType)
                .status(StatusTypeEnum.COLLECTED)
                .build();
        if (entity.getType().toString().startsWith("MERCHANT_DIGITAL") && !Objects.equals(entity.getIssuerAccount(), NomenclatureConstants.CBR))
            throw new IllegalArgumentException("Issuer account must be " + NomenclatureConstants.CBR + " for MERCHANT_DIGITAL** type.");

        return mapper.mapToModel(transactionRepository.createTransaction(entity));

    }

    @Override
    public Map<String, Object> reversement(ReversementDto dto){
        if (!authClient.checkPin(dto.agentClientCode(), dto.pin())) throw new UnAuthorizedException("Invalid Pin");

        String tellerClientCode = tellerBoxRepository.getOneByNumberAndBranchCode(dto.boxNumber(), dto.branchCode())
                .getClientCode();

        String tellerAccountNumber = accountRepository.getAccountsByClientCode(tellerClientCode)
                .stream()
                .filter(acc -> acc.getAccountTypeCode() == NomenclatureConstants.TellerAccountTypeCode)
                .map(AccountEntity::getAccountNumber)
                .findFirst()
                .orElseThrow(() -> new GeneralException("Teller account Not Found"));

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason("Reversement to a teller")
                .issuerAccount(dto.agentAccountNumber())
                .receiverAccount(tellerAccountNumber)
                .type(dto.operation())
                .status(StatusTypeEnum.PENDING)
                .build();

        if (entity.getType() != OperationTypeEnum.REVERSEMENT)
            throw new IllegalArgumentException("Operation type must be REVERSEMENT");

        Transaction transaction = mapper.mapToModel(transactionRepository.createTransaction(entity));

        return Map.of(
                "transaction", transaction,
                "tellerCode", tellerClientCode
        );
    }

    @Override
    public List<Transaction> getLatestTop5TransactionByClientCode(String clientCode) {

        List<String> accounts = accountRepository.getAccountNumbersByClientCode(clientCode);
        System.out.println("-------- _+____ accounts"+ accounts);

        List<Transaction> issuerAccounts = transactionRepository.getTop5ByIssuerAccount(accounts)
                .stream()
//                .filter(Objects::nonNull)
                .limit(5)
                .map(mapper::mapToModel)
                .toList();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!! ");

        List<Transaction> receiverAccounts = transactionRepository.getTop5ByReceiverAccount(accounts)
                .stream()
                .limit(5)
                .map(mapper::mapToModel)
                .toList();

        System.out.println("issuer data ***** "+issuerAccounts);
        System.out.println("receiver data ***** "+issuerAccounts);
        List<Transaction> merged = Stream.of(issuerAccounts, receiverAccounts)
                .flatMap(List::stream)
                .toList();
        System.out.println("############################");

        return merged
                .stream()
                .sorted(Comparator.comparing(Transaction::createdAt).reversed())
                .limit(5)
                .toList();

    }

    @Override
    public List<Transaction> viewTransactionsByClientCode(String clientCode) {
        List<String> accounts = accountRepository.getAccountNumbersByClientCode(clientCode);

        List<Transaction> issuerAccounts = transactionRepository.getTop5ByIssuerAccount(accounts)
                .stream()
//                .filter(Objects::nonNull)
                .limit(5)
                .map(mapper::mapToModel)
                .toList();

        List<Transaction> receiverAccounts = transactionRepository.getTop5ByReceiverAccount(accounts)
                .stream()
                .limit(5)
                .map(mapper::mapToModel)
                .toList();
        return Stream.of(issuerAccounts, receiverAccounts)
                .flatMap(List::stream)
                .toList();
    }


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

    private void handlePaymentProcess(String authUuid, SelfServiceDto dto, Transaction savedTransaction) {
        System.out.println("handle authUUID //////////// "+authUuid);
        String payType = IwomiPayTypesEnum.om.toString().toLowerCase();
        if (dto.operation().toString().contains("MOMO")) payType = IwomiPayTypesEnum.momo.toString().toLowerCase();

        DigitalPaymentDto paymentDto = new DigitalPaymentDto("credit", payType, dto.amount(),
                "generateMe", dto.reason(), dto.sourcePhoneNumber(), "CM", "xaf");
        // make iwomi Pay request
        Map<String, Object> response = payment.pay(paymentDto);

        System.out.println("payment---------- "+response);
        System.out.println("--- stat -- "+response.get("status"));

        if (Objects.equals(response.get("status").toString(), "1000")) { // 1000 means request pending made, awaiting client confirmation
            // Extract internal ID from response
            String internalId = response.get("internal_id").toString();
            // Check payment status with exponential backoff
            CompletableFuture<Map<String, Object>> statusFuture = payment.checkPaymentStatusWithBackoff(internalId);

            statusFuture.thenApply(result -> {
                // Process or transform the result here
                System.out.println("Processing payment status: " + result);
                return result; // Return the transformed result if needed
            }).thenAccept(transformedResult -> {
                // Further processing of the transformed result
                System.out.println("Transformed result: " + transformedResult);
                // update status in DB
                if ("01".equalsIgnoreCase(transformedResult.get("status").toString())) {    // success
                    transactionRepository.updateTransactionStatus(savedTransaction.uuid(), StatusTypeEnum.VALIDATED);
                    websocketService.sendToUser(authUuid, StatusTypeEnum.VALIDATED.toString());
                }
                else if ("100".equalsIgnoreCase(transformedResult.get("status").toString())) {  // fail
                    transactionRepository.updateTransactionStatus(savedTransaction.uuid(), StatusTypeEnum.FAILED);
                    websocketService.sendToUser(authUuid, StatusTypeEnum.FAILED.toString());
                }
                else {  // remain pending
//                    transactionRepository.updateTransactionStatus(savedTransaction.uuid(), StatusTypeEnum.FAILED);
                    websocketService.sendToUser(authUuid, StatusTypeEnum.PENDING.toString());
                    System.out.println("******** !!! Transaction remained pending in checkStatus iwomipay check your code please !!! **********");
                }
            }).exceptionally(ex -> {
                // Handle exceptions here
                System.err.println("Error: " + ex.getMessage());
                return null;
            });
        } else if (Objects.equals(response.get("status").toString(), "100")) {  // 100 means status is fail
            transactionRepository.updateTransactionStatus(savedTransaction.uuid(), StatusTypeEnum.FAILED);
            websocketService.sendToUser(authUuid, StatusTypeEnum.FAILED.toString());
        } else {    // this is for status 01 which is surely never called
            System.out.println("******** !!! Payment status is 01 check your code please !!! **********");
        }
    }

}
