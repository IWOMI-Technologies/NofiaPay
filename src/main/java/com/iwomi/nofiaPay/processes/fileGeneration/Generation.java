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
        System.out.println("in agent cash collection generation: "+entity.toString());
        String agentAccount = entity.getIssuerAccount();
        String clientAccount = entity.getReceiverAccount();
        System.out.println("preparing to do account query: "+agentAccount);
        String agentBranch = accountRepository.getOneByAccount(agentAccount).getAgencyCode();
        String clientBranch = accountRepository.getOneByAccount(clientAccount).getAgencyCode();

        System.out.println("get branches: "+  agentBranch + " \nclient: "+ clientBranch);
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

        System.out.println("in agent digital collection generation: "+entity.toString());

        String siegeBranchCode = NomenclatureConstants.SIEGEBRANCHCODE;
        return List.of(
                produceTransactionFile(NomenclatureConstants.CBR, entity.getAmount().toString(),
                        "0", entity),
                produceTransactionFile(siegeBranchCode + NomenclatureConstants.CL, "0",
                        entity.getAmount().toString(), entity),
                produceTransactionFile(clientBranch + NomenclatureConstants.CL,
                        entity.getAmount().toString(), "0", entity),
                produceTransactionFile(clientAccount, "0", entity.getAmount().toString(),
                        entity),
                // additions
                produceTransactionFile(NomenclatureConstants.CDA,
                        entity.getAmount().toString(), "0",entity),
                produceTransactionFile(NomenclatureConstants.CDA, "0",
                        entity.getAmount().toString(), entity),
                produceTransactionFile(agentAccount,  entity.getAmount().toString(), "0",
                        entity),
                produceTransactionFile(clientAccount, "0", entity.getAmount().toString(),
                        entity)
        );
    }

    @Override
    public List<TransactionFile> selfService(TransactionEntity entity) {
        String clientAccount = entity.getReceiverAccount();
        String clientBranch = accountRepository.getOneByAccount(clientAccount).getAgencyCode();

        System.out.println("in agent self collection generation: "+entity.toString());

        String siegeBranchCode = NomenclatureConstants.SIEGEBRANCHCODE;
        return List.of(
                produceTransactionFile(NomenclatureConstants.CBR, entity.getAmount().toString(),
                        "0", entity),
                produceTransactionFile(siegeBranchCode + NomenclatureConstants.CL, "0",
                        entity.getAmount().toString(), entity),
                produceTransactionFile(clientBranch + NomenclatureConstants.CL,
                        entity.getAmount().toString(), "0", entity),
                produceTransactionFile(NomenclatureConstants.CDA, "0",
                        entity.getAmount().toString(), entity),
                produceTransactionFile(NomenclatureConstants.CDA,
                        entity.getAmount().toString(), "0", entity),
                produceTransactionFile(clientAccount, "0", entity.getAmount().toString(),
                        entity)
        );
    }

    @Override
    public List<TransactionFile> merchantDigitalCollection(TransactionEntity entity) {
//        String agentAccount = entity.getIssuerAccount();
        String merchantAccount = entity.getReceiverAccount();
//        String agentBranch = accountRepository.getOneByAccount(agentAccount).getAgencyCode();
        String merchantBranch = accountRepository.getOneByAccount(merchantAccount).getAgencyCode();

        System.out.println("in merchant digital collection generation: "+entity.toString());

        String siegeBranchCode = NomenclatureConstants.SIEGEBRANCHCODE;
        return List.of(
                produceTransactionFile(NomenclatureConstants.CBR, entity.getAmount().toString(),
                        "0", entity),
                produceTransactionFile(siegeBranchCode + NomenclatureConstants.CL, "0",
                        entity.getAmount().toString(), entity),
                produceTransactionFile(merchantBranch + NomenclatureConstants.CL,
                        entity.getAmount().toString(), "0", entity),
                produceTransactionFile(NomenclatureConstants.CDCA, "0",
                        entity.getAmount().toString(), entity),
                produceTransactionFile(NomenclatureConstants.CDCA,
                        entity.getAmount().toString(), "0", entity),
                produceTransactionFile(merchantAccount, "0", entity.getAmount().toString(),
                        entity)
        );
    }

    private TransactionFile produceTransactionFile(String account, String debitAmt,
                                                   String creditAmt, TransactionEntity entity) {
        System.out.println("in produce transaction file");
        return new TransactionFile(account, "label here", entity.getReason(),
                debitAmt, creditAmt, "ref letterage");
    }
}
