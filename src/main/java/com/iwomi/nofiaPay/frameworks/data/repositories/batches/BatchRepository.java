package com.iwomi.nofiaPay.frameworks.data.repositories.batches;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.utils.CoreUtils;
import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.BatchEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BatchRepository {
    private final IBatchRepository repository;

    /**
     * @param clientId is only for agent
     */
    public BatchEntity createBatch(String clientId) {
        String code = RandomStringUtils.random(10, true, true);
        BatchEntity batch = BatchEntity.builder().batchCode(code).remainder(new BigDecimal("0")).clientId(clientId).build();
        return repository.save(batch);
    }

    public  BatchEntity getOne(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new GeneralException("Batch Not Found"));
    }

    public BatchEntity getOneByCode(String code) {
        return repository.findByBatchCode(code)
                .orElseThrow(() -> new GeneralException("Batch not found."));
    }

    public List<BatchEntity> getOneByClientId(String clientId) {
        List<BatchEntity> batches = repository.findByClientIdAndRemainderIsNot(clientId, new BigDecimal("0"));

        if (batches.isEmpty()) throw  new GeneralException("Batch not found.");

//        return batches.stream().filter(batchEntity -> batchEntity.getRemainder().intValue() > 0).toList();
        return batches;
    }

    public BatchEntity updateBatch (UUID uuid, BigDecimal remainder){
        BatchEntity batch = getOne(uuid);
        batch.setRemainder(remainder);
        return  repository.save(batch);
    }

    public String getTodaysBatchCode(String clientId) {
        Date today = CoreUtils.localDateToDate(LocalDate.now());
        // Get existing or create one for the day
        BatchEntity batch = repository.findByClientIdAndCreatedAt(clientId, today)
                .orElseGet(() -> createBatch(clientId));

        return batch.getBatchCode();
    }
}
