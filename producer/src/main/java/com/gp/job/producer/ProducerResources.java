package com.gp.job.producer;

import com.gp.job.common.producer.KafkaProducerProperties;
import com.gp.job.common.producer.KafkaProducerResources;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties({KafkaProducerProperties.class})
@Import(KafkaProducerResources.class)
@ComponentScan(basePackages = {"com.gp.job.common.producer", "com.gp.job.producer"})
public class ProducerResources {
}
