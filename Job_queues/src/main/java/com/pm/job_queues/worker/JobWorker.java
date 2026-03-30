package com.pm.job_queues.worker;

import com.pm.job_queues.handlers.JobHandler;
import com.pm.job_queues.model.Job;
import com.pm.job_queues.model.JobStatus;
import com.pm.job_queues.queue.JobQueue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JobWorker {

    private final JobQueue jobQueue;
    private final Map<String, JobHandler> handlers;

    public JobWorker(JobQueue jobQueue, Map<String, JobHandler> handlers) {
        this.jobQueue = jobQueue;
        this.handlers = handlers;
    }

    @Scheduled(fixedDelay = 2000)
    public void processJobs() {

        // ✅ dynamically read from all queues (use type as queue name)
        for (String type : handlers.keySet()) {
            Job job = jobQueue.dequeue(type);  // dequeue from type-based queue
            if (job == null) continue;

            try {
                System.out.println("Processing job: " + job.getId() + ", Type: " + job.getType());
                job.setStatus(JobStatus.PROCESSING);

                JobHandler handler = handlers.get(job.getType());
                if (handler != null) {
                    handler.handle(job);  // dynamic handler
                } else {
                    System.out.println(" No handler for type: " + job.getType());
                }

                job.setStatus(JobStatus.SUCCESS);
                System.out.println("Job completed: " + job.getId());

            } catch (Exception e) {
                job.setStatus(JobStatus.FAILED);
                System.out.println("Job failed: " + job.getId());
            }
        }
    }
}