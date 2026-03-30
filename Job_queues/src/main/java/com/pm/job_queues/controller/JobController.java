package com.pm.job_queues.controller;

import com.pm.job_queues.model.Job;
import com.pm.job_queues.model.JobPriority;
import com.pm.job_queues.model.JobStatus;
import com.pm.job_queues.queue.JobQueue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobQueue jobQueue;

    public JobController(JobQueue jobQueue) {
        this.jobQueue = jobQueue;
    }

    @PostMapping("/email")
    public String sendEmailJob() {

        Job job = new Job();
        job.setId(UUID.randomUUID().toString());
        job.setType("email");
        job.setPayload("{\"to\":\"user@gmail.com\",\"msg\":\"Hello\"}");
        job.setStatus(JobStatus.PENDING);
        job.setPriority(JobPriority.MEDIUM);
        job.setRetryCount(0);
        job.setMaxRetries(3);
        job.setCreatedAt(System.currentTimeMillis());

        jobQueue.enqueue(job.getType(), job);

        return "Job added!";
    }

}
