package com.iwomi.nofiaPay.services.transactions;

import com.iwomi.nofiaPay.dtos.*;
import com.iwomi.nofiaPay.dtos.responses.Transaction;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ITransactionService {
    List<Transaction> viewAllTransactions();

     List<Transaction> viewByIssuerAccount(String issuer);

    List<Transaction> viewByReceiverAccount(String receiver);
    Boolean isIssuerAccount(String account);
    Transaction SaveTransaction(TransactionDto dto);

    Transaction viewOne(UUID uuid);

    Transaction update(UUID uuid, TransactionDto dto);

    Transaction updateTransactionStatus(UUID uuid, TransactionDto dto);

    void deleteOne(UUID uuid);

    Map<String, Object> allHistory(String clientCode);

    Transaction agentCashCollection(AgentCashCollectionDto dto);

//    List<Transaction> agentDigitalCollection(AgentDigitalCollectionDto dto);

    Transaction selfService(SelfServiceDto dto);

    Transaction AgentDigitalCollection(AgentDigitalCollectionDto dto);

    //TODO very similar to agentCashCollection above (arrange better)
    Transaction merchantCash(MerchantCashDto dto);

//    //TODO similar to agentDigitalCollection above
    Transaction merchantDigital(MerchantDigitalDto dto);

//    Map<String, Object> initiateReversement(String branchCode, String boxNumber, String clientCode);
//    List<Transaction> reversement(ReversementDto dto);

}
