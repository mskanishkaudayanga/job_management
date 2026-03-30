package com.pm.job_queues.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.job_queues.model.Job;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisJobQueue implements   JobQueue {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisJobQueue(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public void enqueue(String queueName, Job job) {
        try {
            String json = objectMapper.writeValueAsString(job);
            redisTemplate.opsForList().leftPush(queueName, json);
        } catch (Exception e) {
            throw new RuntimeException("Error adding job to queue", e);
        }
    }

    @Override
    public Job dequeue(String queueName) {
        try {
            String json = redisTemplate.opsForList().rightPop(queueName);
            if (json == null) return null;

            return objectMapper.readValue(json, Job.class);
        } catch (Exception e) {
            throw new RuntimeException("Error reading job from queue", e);
        }
    }


}
