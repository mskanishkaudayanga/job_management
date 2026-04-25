package com.pm.job_queues.queue;

import com.pm.job_queues.model.Job;

public interface JobQueue {
    void enqueue(String queueName, Job job);
    Job dequeue(String queueName);
}
