package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.utils.DateConverterUtils;
import com.iwomi.nofiaPay.processes.scheduler.ScheduleDto;
import com.iwomi.nofiaPay.processes.scheduler.SchedulerEnum;
import com.iwomi.nofiaPay.processes.scheduler.TimerModel;
import com.iwomi.nofiaPay.processes.scheduler.TimerUtils;
import com.iwomi.nofiaPay.processes.scheduler.jobs.TransactionsFileJob;
import com.iwomi.nofiaPay.processes.scheduler.services.ISchedulerService;
import lombok.RequiredArgsConstructor;
import org.quartz.Trigger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class SchedulerController {
    private final ISchedulerService schedulerService;

    @PostMapping()
    public void schedulerJob(@RequestBody ScheduleDto dto) {
        Map<String, String> _data = new HashMap<>();
        _data.put("id", "data");

        Trigger trigger = null;
        TimerModel model = null;

        if (dto.type() == SchedulerEnum.DAILY) {
            String converterTime = DateConverterUtils.convertEpochToTimeFormat(dto.epoch());
            int[] separatedTime = DateConverterUtils.separateTime(converterTime);

            model = TimerModel.builder()
                    .hour(separatedTime[0])
                    .minute(separatedTime[1])
                    .second(separatedTime[2])
                    .dailyInterval(dto.dailyInterval())
                    .data(_data)
                    .build();
            trigger = TimerUtils.dailyBuildTrigger(TransactionsFileJob.class, model);
        }
        else if (dto.type() == SchedulerEnum.HOURLY) {
            model = TimerModel.builder()
                    .hourlyInterval(dto.hourlyInterval())
                    .data(_data)
                    .build();
            trigger = TimerUtils.hourlyBuildTrigger(TransactionsFileJob.class, model);
        }

        schedulerService.schedule(TransactionsFileJob.class, model, trigger);
    }
}
