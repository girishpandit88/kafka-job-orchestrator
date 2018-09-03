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

![](img/kjo.mp4.gif)

```bash
# Start the Kafka docker image
$ docker-compose pull
$ docker-compose up -d 


# Starting producer
./gradlew producer:bootRun


# Starting consumer
./gradlew consumer:bootRun
```
