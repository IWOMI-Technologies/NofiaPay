package com.iwomi.nofiaPay.processes.scheduler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimerModel implements Serializable {
    private int hour;
    private int minute;
    private int second;
    private int dailyInterval;
    private int hourlyInterval;
    private String callbackData;
    private Map<String, String> data;
}
