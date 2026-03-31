package com.pm.job_queues.aspect;

import com.pm.job_queues.annotation.AsyncJob;
import com.pm.job_queues.factory.JobFactory;
import com.pm.job_queues.model.Job;
import com.pm.job_queues.queue.JobQueue;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AsyncJobAspect {
    private final JobFactory jobFactory;
    private final JobQueue jobQueue;
    public AsyncJobAspect(JobFactory jobFactory, JobQueue jobQueue) {
        this.jobFactory = jobFactory;
        this.jobQueue = jobQueue;
    }
    @Around("@annotation(asyncJob)")
    public Object handleAsyncJob(ProceedingJoinPoint joinPoint, AsyncJob asyncJob) {

        // 1. Extract method arguments
        Object[] args = joinPoint.getArgs();

        // 2. Create job
        Job job = jobFactory.createJob(
                asyncJob.type(),
                args,
                asyncJob.priority(),
                asyncJob.maxRetries()
        );

        // 3. Push to queue
        jobQueue.enqueue(asyncJob.type(), job);

        // 🔥 IMPORTANT DESIGN CHOICE:
        // Do NOT execute original method
        return null;
    }
}
