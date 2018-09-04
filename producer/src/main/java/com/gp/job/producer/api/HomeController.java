package com.gp.job.producer.api;

import com.gp.job.common.dynamodb.access.JobAccess;
import com.gp.job.common.dynamodb.dao.JobDto;
import com.gp.job.common.kafka.dao.WorkUnit;
import com.gp.job.producer.dispatcher.KafkaDispatcher;
import com.gp.job.producer.model.JobData;
import com.gp.job.producer.model.JobDataPostResponse;
import com.gp.job.producer.model.JobDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/")
public class HomeController {
	@Autowired
	KafkaDispatcher kafkaDispatcher;
	@Autowired
	JobAccess jobAccess;

	@PostMapping(value = "/job")
	public ResponseEntity producer(@RequestBody JobDataRequest jobDataRequest) {
		final UUID id = UUID.randomUUID();
		final WorkUnit workUnit = new WorkUnit(id.toString(),
				jobDataRequest.getJobName(),
				jobDataRequest.getArgs().split(","));
		kafkaDispatcher.dispatch(workUnit);
		return ResponseEntity.ok(new JobDataPostResponse(id));
	}

	@GetMapping(value = "/job/{jobId}")
	public ResponseEntity jobStatus(@PathVariable("jobId") String id) {
		final Optional<JobDto> result = jobAccess.getJobData(UUID.fromString(id));
		if (result.isPresent()) {
			JobDto jobDto = result.get();
			return ResponseEntity.ok(new JobData(
					jobDto.getId(),
					jobDto.getName(),
					jobDto.getOutput(),
					jobDto.getError()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
