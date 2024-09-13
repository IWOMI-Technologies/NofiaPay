package com.iwomi.nofiaPay.processes.fileGeneration;

import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.generate.TransactionFile;

import java.util.List;

public interface IGeneration {
    List<TransactionFile> agentCashCollection(TransactionEntity entity);
    List<TransactionFile> agentDigitalCollection(TransactionEntity entity);
    List<TransactionFile> selfService(TransactionEntity entity);
    List<TransactionFile> merchantDigitalCollection(TransactionEntity entity);
}
