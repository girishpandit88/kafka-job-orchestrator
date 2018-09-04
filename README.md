Kafka Job Orchestrator
--------------------------------------

### Kafka Producer
A RESTful service that accepts fully qualified job names and put it on a Kafka topic.

### Kafka Consumer
A backend service that consumes from a Kafka topic, a job name, and execute that class on a separate background executor.

### Kafka UI Manager
A RESTful service that gives details on Kafka topics. 


## Things to do
 - Status of submitted job
 - Status of finished job


### Starting the producer and consumer:

![](img/kjo.mov.gif)

```bash
# Start the Kafka docker image
$ docker-compose pull
$ docker-compose up -d 


# Starting producer
./gradlew producer:bootRun

# Starting consumer
./gradlew consumer:bootRun

# Submitting jobs:
$ curl http://localhost:8080/job?name=com.gp.job.jobs.CliJob&args= # execute the CliJob class

$ http://localhost:8080/job?name=com.gp.job.jobs.LongRunningJob&args= # execute the LongRunningJob class

# Getting job status:
$ curl http://localhost:8080/job/status/{jobId}
```
```json
{
  "id": "dd19bc93-26d5-4ddb-a40e-18ab4fc2ab91",
  "className": "com.gp.job.jobs.LongRunningJob",
  "output": "I'm a long running job waiting for future to get waiting for future to get waiting for future to get waiting for future to get Hello ! 2018-09-03 17:37:18.975  INFO 33027 --- [pool-1-thread-2] c.g.j.consumer.manager.WorkUnitConsumer  : Result of job operation : null ",
  "error": null,
}
```
