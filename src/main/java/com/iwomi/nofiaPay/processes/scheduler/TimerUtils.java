package com.iwomi.nofiaPay.processes.scheduler;

import org.quartz.*;

import java.util.Date;

import static org.quartz.DateBuilder.tomorrowAt;

// Helper class to build JobDetails and Triggers necessary for job
// scheduling
public class TimerUtils {
    public TimerUtils() {
    }

    public static JobDetail buildJobDetail(final Class jobClass, final TimerModel model) {
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), model);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    public static Trigger hourlyBuildTrigger(final Class jobClass, final TimerModel model) {
        CalendarIntervalScheduleBuilder builder = CalendarIntervalScheduleBuilder
                .calendarIntervalSchedule()
                .withIntervalInHours(model.getHourlyInterval());

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
//                .startNow()
                .withSchedule(builder)
                .build();
    }

    public static Trigger dailyBuildTrigger(final Class jobClass, final TimerModel model) {
        CalendarIntervalScheduleBuilder builder = CalendarIntervalScheduleBuilder
                .calendarIntervalSchedule()
                .withIntervalInDays(model.getDailyInterval());

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .startAt(tomorrowAt(model.getHour(), model.getMinute(), model.getSecond()))
                .withSchedule(builder)
                .build();
    }
}
