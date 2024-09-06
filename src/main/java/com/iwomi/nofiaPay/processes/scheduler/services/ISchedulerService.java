package com.iwomi.nofiaPay.processes.scheduler.services;

import com.iwomi.nofiaPay.processes.scheduler.TimerModel;
import org.quartz.Job;
import org.quartz.Trigger;

import java.util.List;

public interface ISchedulerService {
    <T extends Job> void schedule(final Class<T> jobClass, final TimerModel model, final Trigger trigger);
    List<TimerModel> getAllRunningTimers();
    TimerModel getRunningTimer(final String timerId);
    void updateTimer(final String timerId, final TimerModel info);
    Boolean deleteTimer(final String timerId);
}
