package com.iwomi.nofiaPay.services.transactions;

import com.iwomi.nofiaPay.core.enums.AccountTypeEnum;
import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.SenseTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import com.iwomi.nofiaPay.core.mappers.ITransactionMapper;
import com.iwomi.nofiaPay.dtos.*;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.batches.BatchRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.branches.BranchRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.tellerBox.TellerBoxRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.COLLECTED)
                .build();

        return mapper.mapToModel(transactionRepository.createTransaction(entity));
    }

    @Override
    public List<Transaction> agentDigitalCollection(AgentDigitalCollectionDto dto) {
        ClientEntity client = clientRepository.getOneByPhone(dto.clientPhoneNumber());
        // get client branchid then account from that branchid and AccountTypeEnum -> BRANCH_DIGITAL_ACCOUNT
        String branchAccountNumber = accountRepository.getOneByBranchAndType(client.getBranchId(),
                AccountTypeEnum.BRANCH_DIGITAL_ACCOUNT).getAccountNumber();

        String batchCode = agentBranchCode(dto.agentCollectionAccount());

        TransactionEntity outOfDigitalBranchAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .accountNumber(branchAccountNumber)
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.PENDING)
                .build();
        TransactionEntity inAgentAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .accountNumber(dto.agentCollectionAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.PENDING)
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
                .createMany(List.of(outOfDigitalBranchAccount, inAgentAccount,
                        outOfAgentAccount, inClientAccount))
                .stream().map(mapper::mapToModel)
                .toList();
    }

    @Override
    public List<Transaction> selfService(SelfServiceDto dto) {
        ClientEntity client = clientRepository.getOneByPhone(dto.clientPhoneNumber());
        // get client branchid then account with that branchid and AccountTypeEnum -> BRANCH_DIGITAL_ACCOUNT
        String branchAccountNumber = accountRepository.getOneByBranchAndType(client.getBranchId(),
                AccountTypeEnum.BRANCH_DIGITAL_ACCOUNT).getAccountNumber();

        // since agent is not involved there is no need to fetch "batchCode"

        TransactionEntity outOfDigitalBranchAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
//                .batch(batchCode)
                .accountNumber(branchAccountNumber)
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.PENDING)
                .build();

        TransactionEntity inClientAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
//                .batch(batchCode)
                .accountNumber(dto.clientAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.PENDING)
                .build();

        return transactionRepository
                .createMany(List.of(outOfDigitalBranchAccount, inClientAccount))
                .stream().map(mapper::mapToModel)
                .toList();
    }

    @Override
    public Transaction merchantCash(MerchantCashDto dto) {
        String batchCode = agentBranchCode(dto.merchantAccount());

        TransactionEntity entity = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
                .batch(batchCode)
                .accountNumber(dto.merchantAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.PENDING)
                .build();

        return mapper.mapToModel(transactionRepository.createTransaction(entity));
    }

    @Override
    public List<Transaction> merchantDigital(MerchantDigitalDto dto) {
        ClientEntity client = clientRepository.getOneByPhone(dto.clientPhoneNumber());
        // get client branchid then account with that branchid and AccountTypeEnum -> BRANCH_DIGITAL_ACCOUNT
        String branchAccountNumber = accountRepository.getOneByBranchAndType(client.getBranchId(),
                AccountTypeEnum.BRANCH_DIGITAL_ACCOUNT).getAccountNumber();

        // since agent is not involved there is no need to fetch "batchCode"

        TransactionEntity outOfDigitalBranchAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
//                .batch(batchCode)
                .accountNumber(branchAccountNumber)
                .type(dto.operation())
                .sense(SenseTypeEnum.DEBIT)
                .status(StatusTypeEnum.PENDING)
                .build();

        TransactionEntity inClientAccount = TransactionEntity.builder()
                .amount(new BigDecimal(dto.amount()))
                .reason(dto.reason())
//                .batch(batchCode)
                .accountNumber(dto.merchantAccount())
                .type(dto.operation())
                .sense(SenseTypeEnum.CREDIT)
                .status(StatusTypeEnum.PENDING)
                .build();

        return transactionRepository
                .createMany(List.of(outOfDigitalBranchAccount, inClientAccount))
                .stream().map(mapper::mapToModel)
                .toList();
    }

//    @Override
//    public Object initiateReversement(String branchCode, String boxNumber, String agentClientId) {
//        String branchUuid = branchRepository.getOneByCode(branchCode).getUuid().toString();
//        String clientUuid = tellerBoxRepository.getOneByNumber(boxNumber, branchUuid).getUuid().toString();
//        String tellerAccount = accountRepository.getOneByClientIdAndType(clientUuid,
//                AccountTypeEnum.COLLECTION).getAccountNumber();
//
//
//    }

    private String agentBranchCode(String agentCollectionAccount) {
        AccountEntity account = accountRepository.getOneByAccount(agentCollectionAccount);
        return batchRepository.getTodaysBatchCode(account.getClientId().toString());
    }
}
