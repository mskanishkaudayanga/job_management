package com.pm.job_queues.service;

import com.pm.job_queues.annotation.AsyncJob;
import com.pm.job_queues.Enums.JobPriority;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @AsyncJob(type = "email", priority = JobPriority.HIGH)
    public void sendEmail(String to, String message) {
        System.out.println("This should NOT execute immediately");
    }
    @AsyncJob(type = "notification", priority = JobPriority.MEDIUM)
    public void sendNotification(String to, String message) {
        System.out.println("This should NOT execute immediately");
    }
}


