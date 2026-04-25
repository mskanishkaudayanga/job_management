package com.pm.job_queues.controller;

import com.pm.job_queues.queue.JobQueue;
import com.pm.job_queues.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobQueue jobQueue;
 private  final EmailService emailService;

    public JobController(JobQueue jobQueue, EmailService emailService) {
        this.jobQueue = jobQueue;
        this.emailService = emailService;
    }

    @PostMapping("/email")
    public String sendEmailJob() {
        emailService.sendEmail("user@gmail.com", "Hello");
        emailService.sendNotification("tomentor ","New notification");
        return "Job added via annotation!";
    }


}
