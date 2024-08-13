package com.iwomi.nofiaPay.services.transactions;

import com.iwomi.nofiaPay.dtos.*;
import com.iwomi.nofiaPay.dtos.responses.Transaction;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ITransactionService {
    List<Transaction> viewAllTransactions();

    Transaction SaveTransaction(TransactionDto dto);

    Transaction viewOne(UUID uuid);

    Transaction update(UUID uuid, TransactionDto dto);

    Transaction updateTransactionStatus(UUID uuid, TransactionDto dto);

    void deleteOne(UUID uuid);

    Transaction agentCashCollection(AgentCashCollectionDto dto);

//    List<Transaction> agentDigitalCollection(AgentDigitalCollectionDto dto);

    List<Transaction> selfService(SelfServiceDto dto);

    //TODO very similar to agentCashCollection above (arrange better)
    Transaction merchantCash(MerchantCashDto dto);

//    //TODO similar to agentDigitalCollection above
//    List<Transaction> merchantDigital(MerchantDigitalDto dto);

//    Map<String, Object> initiateReversement(String branchCode, String boxNumber, String clientCode);
//    List<Transaction> reversement(ReversementDto dto);

}
