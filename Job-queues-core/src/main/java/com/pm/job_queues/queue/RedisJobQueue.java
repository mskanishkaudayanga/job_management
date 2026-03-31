package com.pm.job_queues.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.job_queues.model.Job;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisJobQueue implements   JobQueue {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisJobQueue(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public void enqueue(String queueName, Job job) {
        try {
            String json = objectMapper.writeValueAsString(job);
            //score = priority
            double score = job.getPriority().getValue();
            redisTemplate.opsForZSet().add(queueName,json,score);
        } catch (Exception e) {
            throw new RuntimeException("Error adding job to queue", e);
        }
    }

    @Override
    public Job dequeue(String queueName) {
        try {
            Set<String> jobs = redisTemplate.opsForZSet()
                    .range(queueName, 0, 0);
            if(jobs== null || jobs.isEmpty()) return null;
            String jobJson = jobs.iterator().next();

            //Remove after fetching
            redisTemplate.opsForZSet().remove(queueName,jobJson);
            return objectMapper.readValue(jobJson, Job.class);
        } catch (Exception e) {
            throw new RuntimeException("Error reading job from queue", e);
        }
    }


}
