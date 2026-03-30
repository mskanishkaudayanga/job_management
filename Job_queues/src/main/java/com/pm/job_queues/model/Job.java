package com.pm.job_queues.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Job {
    @Getter
    @Setter
    private String id;            // Unique job ID

    @Getter
    @Setter
    private String type;          // email, sms, file, etc.

    @Getter
    @Setter
    private String payload;       // JSON data (job input)

    @Setter
    @Getter
    private JobStatus status;     // PENDING, SUCCESS, FAILED

    @Getter
    @Setter
    private JobPriority priority; // LOW, MEDIUM, HIGH

    @Getter
    @Setter
    private int retryCount;       // How many retries done

    private int maxRetries;       // Max retry limit

    private long createdAt;       // timestamp

    private long updatedAt;       // timestamp

}
