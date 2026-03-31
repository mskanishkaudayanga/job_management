package com.pm.job_queues.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.job_queues.model.Job;
import com.pm.job_queues.Enums.JobPriority;
import com.pm.job_queues.Enums.JobStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class JobFactory {
     private final ObjectMapper objectMapper;

     public JobFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
     }
     public Job createJob(String type, Object[] args, JobPriority priority, int maxRetries){
         try{
             String payloard = objectMapper.writeValueAsString(args);

             return Job.builder()
                     .id(UUID.randomUUID().toString())
                     .type(type)
                     .payload(payloard)
                     .status(JobStatus.PENDING)
                     .priority(priority)
                     .retryCount(0)
                     .maxRetries(maxRetries)
                     .createdAt(LocalDateTime.now())
                     .updatedAt(LocalDateTime.now())
                     .build();
         }
         catch (Exception e){
             throw new RuntimeException("Failed to serialize job payload", e);
         }
     }
}
