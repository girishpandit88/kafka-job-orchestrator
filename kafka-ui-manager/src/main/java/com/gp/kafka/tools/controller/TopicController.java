package com.gp.kafka.tools.controller;

import com.gp.kafka.tools.service.KafkaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/cluster/")
public class TopicController {
	@Autowired
	KafkaManager kafkaManager;

	@GetMapping("/topics")
	public ResponseEntity getTopics() {
		return ResponseEntity.ok(kafkaManager.getTopics());
	}

	@GetMapping("/topics/{topicId}")
	public ResponseEntity getTopicDetails(@PathVariable(name = "topicId") String topic) {
		return ResponseEntity.ok(kafkaManager.getTopicDetails(topic));
	}

	@GetMapping("/topics/details")
	public ResponseEntity getAllTopicsDetails(){
		return ResponseEntity.ok(kafkaManager.getTopicDetails(kafkaManager.getTopics()));
	}
}
