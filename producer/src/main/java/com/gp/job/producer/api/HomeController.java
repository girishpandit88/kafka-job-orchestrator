package com.gp.job.producer.api;

import com.gp.job.common.dao.WorkUnit;
import com.gp.job.producer.dispatcher.KafkaDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/")
public class HomeController {
	@Autowired
	KafkaDispatcher kafkaDispatcher;

	@GetMapping(value = "/job")
	public ResponseEntity producer(@RequestParam("name") String cliName,
								   @RequestParam("args") String args) {
		final String id = UUID.randomUUID().toString();
		kafkaDispatcher.dispatch(new WorkUnit(id, cliName, args.split(",")));
		return ResponseEntity.ok("Your job has been successfully dispatched, id: " + id);
	}
}
