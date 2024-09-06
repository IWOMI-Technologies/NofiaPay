package com.iwomi.nofiaPay.processes.scheduler;

//public record ScheduleDto(String url, int fireNumber, int repeatInterval) {
//}

public record ScheduleDto(
        SchedulerEnum type,
        long epoch, // for daily
        int dailyInterval,
        int hourlyInterval
) {
}