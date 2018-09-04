package com.gp.job.common.dynamodb.access;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonDynamoDbAsyncClientResources {
	@Autowired
	DynamoDBProperties dynamoDBProperties;

	@Bean
	AmazonDynamoDBAsync amazonDynamoDBAsync() {
		return AmazonDynamoDBAsyncClientBuilder
				.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
						dynamoDBProperties.getEndpoint(), null))
				.build();
	}

	@Bean
	DynamoDBMapper dynamoDBMapper() {
		AmazonDynamoDBAsync amazonDynamoDBAsync = amazonDynamoDBAsync();
		return new DynamoDBMapper(amazonDynamoDBAsync,
				DynamoDBMapperConfig
						.builder()
						.withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(
								dynamoDBProperties.getTableNamePrefix()))
						.build());
	}
}
