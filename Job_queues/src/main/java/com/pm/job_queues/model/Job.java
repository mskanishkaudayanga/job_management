package com.pm.job_queues.model;

import com.pm.job_queues.Enums.JobPriority;
import com.pm.job_queues.Enums.JobStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Job {
    private String id;            // Unique job ID
    private String type;          // email, sms, file, etc.
    private String payload;       // JSON data (job input)
    private JobStatus status;     // PENDING, SUCCESS, FAILED
    private JobPriority priority; // LOW, MEDIUM, HIGH
    private int retryCount;       // How many retries done
    private int maxRetries;       // Max retry limit
    private LocalDateTime createdAt;       // timestamp
    private LocalDateTime updatedAt;       // timestamp

}
