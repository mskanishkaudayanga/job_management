package com.pm.job_queues.worker;

import com.pm.job_queues.handlers.JobHandler;
import com.pm.job_queues.model.Job;
import com.pm.job_queues.Enums.JobStatus;
import com.pm.job_queues.queue.JobQueue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map;

@Component
public class JobWorker {

    private final JobQueue jobQueue;
    private final Map<String, JobHandler> handlers;
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public JobWorker(JobQueue jobQueue, Map<String, JobHandler> handlers) {
        this.jobQueue = jobQueue;
        this.handlers = handlers;
    }

    @Scheduled(fixedDelay = 2000)
    public void processJobs() {

        // ✅ dynamically read from all queues (use type as queue name)
        for (String type : handlers.keySet()) {

            // process multiple jobs per queue
            for (int i = 0; i < 5; i++) {

                Job job = jobQueue.dequeue(type);
                if (job == null) break;

                // send to thread pool
                executor.submit(() -> processSingleJob(job, type));
            }

        }
    }
    private void processSingleJob(Job job, String queueName) {

        try {
            System.out.println("Processing job: " + job.getId() + ", Type: " + job.getType());

            job.setStatus(JobStatus.PROCESSING);

            JobHandler handler = handlers.get(job.getType());

            if (handler == null) {
                throw new RuntimeException("No handler found for type: " + job.getType());
            }

            handler.handle(job);

            job.setStatus(JobStatus.COMPLETED);
            System.out.println("✅ Job completed: " + job.getId());

        } catch (Exception e) {
            handleFailure(job, queueName, e);
        }
    }
    private void handleFailure(Job job ,String queueName, Exception e) {
        job.setRetryCount(job.getRetryCount() + 1);
        System.out.println("❌ Job failed: " + job.getId() +
                " | Retry: " + job.getRetryCount());

        // 🔁 Retry logic
        if (job.getRetryCount() <= job.getMaxRetries()) {

            job.setStatus(JobStatus.RETRY);

            // Requeue to same queue
            jobQueue.enqueue(queueName, job);

            System.out.println("🔁 Requeued job: " + job.getId());

        } else {

            job.setStatus(JobStatus.FAILED);

            // 💀 Dead Letter Queue
            String dlqName = "DLQ_" + queueName;
            jobQueue.enqueue(dlqName, job);

            System.out.println("💀 Moved to DLQ: " + job.getId());
            System.out.println("Payload: " + job.getPayload());
        }
    }
}