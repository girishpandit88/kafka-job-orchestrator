package com.gp.job.common.consumer;

import com.gp.job.common.dao.WorkUnit;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableKafka
public class KafkaConsumerResources {

	@Autowired
	private KafkaConsumerProperties kafkaConsumerProperties;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, WorkUnit> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, WorkUnit> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConcurrency(1);
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, WorkUnit> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerProps(), stringKeyDeserializer(), workUnitJsonValueDeserializer());
	}


	@Bean
	public Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerProperties.getBootstrap());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProperties.getGroup());
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConsumerProperties.isEnableAutoCommit());
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaConsumerProperties.getAutoCommitInterval());
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaConsumerProperties.getSessionTimeout());
		return props;
	}

	@Bean
	public Deserializer stringKeyDeserializer() {
		return new StringDeserializer();
	}

	@Bean
	public Deserializer workUnitJsonValueDeserializer() {
		return new JsonDeserializer(WorkUnit.class);
	}

	@Bean
	public ExecutorService executorService(){
		return Executors.newFixedThreadPool(kafkaConsumerProperties.getConsumerThreads());
	}
}
