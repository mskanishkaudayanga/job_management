# Distributed Job Queue System

This project is a **Distributed Job Queue System** built with **Spring Boot** and **Redis**. It allows applications to run tasks asynchronously in the background, handle multiple job types, manage retries, and scale efficiently. The system follows **Clean Architecture** and **SOLID principles**, making it maintainable and extensible.

## Overview

The system is designed to provide a production-ready solution for background job processing in Java applications. It supports multiple queues, dynamic job types, automatic retries for failed jobs, and dead-letter queues for persistent failures. Workers process jobs efficiently, allowing applications to remain responsive while performing heavy or delayed tasks.

## Key Features

1. **Asynchronous Processing**  
   Jobs are executed in the background, ensuring that main application processes are not blocked.

2. **Multiple Job Types**  
   Supports email, notifications, and custom job types through dynamic job handlers.

3. **Dynamic Queues**  
   Allows creation of multiple queues based on job type for better organization and scaling.

4. **Retry and Failure Handling**  
   Failed jobs can be retried automatically. Jobs that exceed retry limits are moved to a dead-letter queue.

5. **Scalable Worker System**  
   Workers can process jobs from multiple queues efficiently, supporting high throughput applications.

6. **Clean Architecture and Extensibility**  
   Layered design separates responsibilities and supports adding new job types or custom features easily.

## Architecture

The system is structured in layers:

- **Application Layer**: Controllers and services that submit jobs.
- **Queue Layer**: Handles job storage using Redis and JSON serialization.
- **Worker Layer**: Scheduled workers that process jobs asynchronously.
- **Handler Layer**: Job-specific handlers that implement the logic for different job types.

## Technology Stack

- Java 17  
- Spring Boot  
- Redis (via Docker)  
- Spring AOP (for future annotation-based job submission)  
- Clean Architecture, SOLID principles  
- Design Patterns: Strategy, Factory

## Getting Started

### Prerequisites

- Java 17 installed
- Maven installed
- Docker installed for running Redis

### Running Redis

Run Redis using Docker:

```bash
docker run -d -p 6379:6379 redis
