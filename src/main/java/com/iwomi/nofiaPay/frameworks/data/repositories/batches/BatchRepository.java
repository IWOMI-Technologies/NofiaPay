package com.iwomi.nofiaPay.frameworks.data.repositories.batches;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.utils.CoreUtils;
import com.iwomi.nofiaPay.frameworks.data.entities.BatchEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class BatchRepository {
    private final IBatchRepository batchRepository;

    /**
     * @param clientId is only for agent
     */
    public BatchEntity createBatch(String clientId) {
        String code = RandomStringUtils.random(10, true, true);
        BatchEntity batch = BatchEntity.builder().batchCode(code).clientId(clientId).build();
        return batchRepository.save(batch);
    }

    public BatchEntity getOne(String code) {
        return batchRepository.findByBatchCode(code)
                .orElseThrow(() -> new GeneralException("Batch not found."));
    }

//    public BatchEntity getOneByClientId(String clientId) {
//        return batchRepository.findByClientId(clientId)
//                .orElseThrow(() -> new GeneralException("Batch not found."));
//    }

    public String getTodaysBatchCode(String clientId) {
        Date today = CoreUtils.localDateToDate(LocalDate.now());
        // Get existing or create one for the day
        BatchEntity batch = batchRepository.findByClientIdAndCreatedAt(clientId, today)
                .orElseGet(() -> createBatch(clientId));

        return batch.getBatchCode();
    }
}
