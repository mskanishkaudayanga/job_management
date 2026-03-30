package com.pm.job_queues.handlers;

import com.pm.job_queues.model.Job;
import org.springframework.stereotype.Component;

@Component("notification")
public class NotificationJobHandler implements JobHandler{
    @Override
    public void handle(Job job) {
        System.out.println("🔔 Sending NOTIFICATION: " + job.getPayload());
    }
}
