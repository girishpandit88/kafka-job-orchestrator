package com.gp.job.producer.api;

import com.gp.job.common.dynamodb.access.JobAccess;
import com.gp.job.common.dynamodb.dao.JobDto;
import com.gp.job.common.kafka.dao.WorkUnit;
import com.gp.job.producer.dispatcher.KafkaDispatcher;
import com.gp.job.producer.model.JobData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping(value = "/job")
	public ResponseEntity producer(@RequestParam("name") String cliName,
								   @RequestParam("args") String args) {
		final String id = UUID.randomUUID().toString();
		kafkaDispatcher.dispatch(new WorkUnit(id, cliName, args.split(",")));
		return ResponseEntity.ok("Your job has been successfully dispatched, id: " + id);
	}

	@GetMapping(value = "/job/status/{jobId}")
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
