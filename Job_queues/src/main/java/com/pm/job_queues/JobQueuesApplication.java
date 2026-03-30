package com.pm.job_queues;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobQueuesApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobQueuesApplication.class, args);
    }

}
