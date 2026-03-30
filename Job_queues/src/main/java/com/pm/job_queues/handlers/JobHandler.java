package com.pm.job_queues.handlers;

import com.pm.job_queues.model.Job;

public interface JobHandler {
    void handle(Job job);
}
