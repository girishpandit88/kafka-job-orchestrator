package com.gp.job.producer;

import com.gp.job.common.dynamodb.access.AmazonDynamoDbAsyncClientResources;
import com.gp.job.common.kafka.producer.KafkaProducerProperties;
import com.gp.job.common.kafka.producer.KafkaProducerResources;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties({KafkaProducerProperties.class})
@Import({KafkaProducerResources.class, AmazonDynamoDbAsyncClientResources.class})
@ComponentScan(basePackages = {"com.gp.job.common.kafka.producer", "com.gp.job.producer", "com.gp.job.common.dynamodb"})
public class ProducerResources {
}
