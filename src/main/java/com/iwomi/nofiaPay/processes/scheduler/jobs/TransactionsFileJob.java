package com.iwomi.nofiaPay.processes.scheduler.jobs;

import com.iwomi.nofiaPay.processes.fileGeneration.GenerateTransaction;
import com.iwomi.nofiaPay.processes.scheduler.TimerModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionsFileJob implements Job {
    private final GenerateTransaction generateTransaction;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TimerModel model = (TimerModel) jobDataMap.get(TransactionsFileJob.class.getSimpleName());

//        generateTransaction.excelTransactionFileGeneration();

        log.info("Remaining fire count is;;;; '{}'", model.getData());
    }
}
