package com.gp.job.consumer.manager;

import com.gp.job.common.dynamodb.access.AmazonDynamoDbAsyncClientResources;
import com.gp.job.common.kafka.consumer.KafkaConsumerProperties;
import com.gp.job.common.kafka.consumer.KafkaConsumerResources;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@EnableConfigurationProperties({KafkaConsumerProperties.class})
@Import({KafkaConsumerResources.class, AmazonDynamoDbAsyncClientResources.class})
@ComponentScan(basePackages = {
		"com.gp.job.common.kafka.consumer",
		"com.gp.job.consumer.manager",
		"com.gp.job.jobs",
		"com.gp.job.common.dynamodb"
})
public class ConsumerResources {
}
