package com.pm.job_queues.handlers;

import com.pm.job_queues.model.Job;
import org.springframework.stereotype.Component;

@Component("email")
public class EmailJobHandler implements  JobHandler{
    @Override
    public void handle(Job job) {
        throw new RuntimeException("Not supported yet.");

    }
}
