package com.gp.job.producer.dispatcher;

import com.gp.job.common.kafka.dao.WorkUnit;
import com.gp.job.common.kafka.producer.KafkaProducerProperties;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;


@Service
public class KafkaDispatcher {

	@Autowired
	private KafkaTemplate<String, WorkUnit> workUnitsKafkaTemplate;

	@Autowired
	private KafkaProducerProperties kafkaProducerProperties;

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDispatcher.class);

	public boolean dispatch(WorkUnit workUnit) {
		try {
			SendResult<String, WorkUnit> sendResult = workUnitsKafkaTemplate.sendDefault(workUnit.getId(), workUnit)
																			.get();
			RecordMetadata recordMetadata = sendResult.getRecordMetadata();
			LOGGER.info("topic = {}, partition = {}, offset = {}, workUnit = {}",
					recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), workUnit);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
