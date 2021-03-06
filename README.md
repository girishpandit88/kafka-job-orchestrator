Kafka Job Orchestrator
--------------------------------------

### Kafka Producer
A RESTful service that accepts fully qualified job names and put it on a Kafka topic.

### Kafka Consumer
A backend service that consumes from a Kafka topic, a job name, and execute that class on a separate background executor.

### Kafka UI Manager
A RESTful service that gives details on Kafka topics. 


## Things to do (updated 09-02-2018 17:50:00)
addressed by [#1](https://github.com/girishpandit88/kafka-job-orchestrator/pull/1)

<del> - Status of submitted job </del>

<del> - Status of finished job </del>


### Starting the producer and consumer:

<img src="img/kjo.mov.gif" alt="drawing" width="1280" height="500" />

```bash
# Start the Kafka docker image
$ docker-compose pull
$ docker-compose up -d 


# Starting producer
./gradlew producer:bootRun

# Starting consumer
./gradlew consumer:bootRun

# Submitting jobs:
$ curl -X POST "http://localhost:8080/job" -d '{"jobName":"com.gp.job.jobs.CliJob", "args" : ""}' -H "Content-Type:application/json"  # execute the CliJob class

$ curl -X POST "http://localhost:8080/job" -d '{"jobName":"com.gp.job.jobs.LongRunningJob", "args" : ""}' -H "Content-Type:application/json" # execute the LongRunningJob class

# Getting job status:
$ curl http://localhost:8080/job/{jobId}
```
```json
{
  "jobName": "dd19bc93-26d5-4ddb-a40e-18ab4fc2ab91",
  "className": "com.gp.job.jobs.LongRunningJob",
  "output": "I'm a long running job waiting for future to get waiting for future to get waiting for future to get waiting for future to get Hello ! 2018-09-03 17:37:18.975  INFO 33027 --- [pool-1-thread-2] c.g.j.consumer.manager.WorkUnitConsumer  : Result of job operation : null ",
  "error": null
}
```
