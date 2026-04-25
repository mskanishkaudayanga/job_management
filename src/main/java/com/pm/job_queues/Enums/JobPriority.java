package com.pm.job_queues.Enums;

public enum JobPriority {
    LOW(3),
    MEDIUM(2),
    HIGH(1);

    private final int value;
    JobPriority(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
