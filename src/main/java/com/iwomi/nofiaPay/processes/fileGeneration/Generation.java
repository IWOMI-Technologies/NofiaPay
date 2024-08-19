package com.iwomi.nofiaPay.processes.fileGeneration;

import com.iwomi.nofiaPay.core.constants.NomenclatureConstants;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.generate.TransactionFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Generation implements IGeneration {
    private final AccountRepository accountRepository;

    @Override
    public List<TransactionFile> agentCashCollection(TransactionEntity entity) {
        String agentAccount = entity.getIssuerAccount();
        String clientAccount = entity.getIssuerAccount();
        String agentBranch = accountRepository.getOneByAccount(agentAccount).getAgencyCode();
        String clientBranch = accountRepository.getOneByAccount(clientAccount).getAgencyCode();
        if (agentBranch.equals(clientBranch)) {
            return List.of(
                    produceTransactionFile(agentAccount, entity.getAmount().toString(),
                            "0", entity),
                    produceTransactionFile(clientAccount, "0",
                            entity.getAmount().toString(), entity)
            );
        }
        return List.of(
                produceTransactionFile(agentAccount, entity.getAmount().toString(),
                        "0", entity),
                produceTransactionFile(agentBranch + NomenclatureConstants.CL, "0",
                        entity.getAmount().toString(), entity),
                produceTransactionFile(clientBranch + NomenclatureConstants.CL,
                        entity.getAmount().toString(), "0", entity),
                produceTransactionFile(clientAccount, "0", entity.getAmount().toString(),
                        entity)
        );
    }

    @Override
    public List<TransactionFile> agentDigitalCollection(TransactionEntity entity) {
        String agentAccount = entity.getIssuerAccount();
        String clientAccount = entity.getIssuerAccount();
        String agentBranch = accountRepository.getOneByAccount(agentAccount).getAgencyCode();
        String clientBranch = accountRepository.getOneByAccount(clientAccount).getAgencyCode();

        String siegeBranchCode = NomenclatureConstants.SIEGEBRANCHCODE;
        return List.of(
                produceTransactionFile(NomenclatureConstants.CBR, entity.getAmount().toString(),
                        "0", entity),
                produceTransactionFile(siegeBranchCode + NomenclatureConstants.CL, "0",
                        entity.getAmount().toString(), entity),
                produceTransactionFile(clientBranch + NomenclatureConstants.CL,
                        entity.getAmount().toString(), "0", entity),
                produceTransactionFile(clientAccount, "0", entity.getAmount().toString(),
                        entity)
        );
    }

    @Override
    public List<TransactionFile> selfService(TransactionEntity entity) {
        return List.of();
    }

    @Override
    public List<TransactionFile> merchantDigitalCollection(TransactionEntity entity) {
        return List.of();
    }

    private TransactionFile produceTransactionFile(String account, String debitAmt,
                                                   String creditAmt, TransactionEntity entity) {
        return new TransactionFile(account, "label here", entity.getReason(),
                debitAmt, creditAmt, "ref letterage");
    }
}
