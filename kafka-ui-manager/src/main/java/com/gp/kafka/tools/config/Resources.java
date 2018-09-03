package com.gp.kafka.tools.config;

import com.gp.job.common.consumer.KafkaConsumerProperties;
import com.gp.job.common.consumer.KafkaConsumerResources;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties({KafkaConsumerProperties.class})
@Import(KafkaConsumerResources.class)
@ComponentScan(basePackages = {"com.gp.kafka.tools"})
public class Resources {
}
