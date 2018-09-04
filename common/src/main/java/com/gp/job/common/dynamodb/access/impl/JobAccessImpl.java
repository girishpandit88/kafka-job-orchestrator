package com.gp.job.common.dynamodb.access.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.google.common.collect.ImmutableList;
import com.gp.job.common.dynamodb.access.JobAccess;
import com.gp.job.common.dynamodb.dao.JobDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JobAccessImpl implements JobAccess {
	private final DynamoDBMapper dynamoDBMapper;
	private final static Logger LOGGER = LoggerFactory.getLogger(JobAccessImpl.class);

	@Autowired
	public JobAccessImpl(DynamoDBMapper dynamoDBMapper) {
		this.dynamoDBMapper = dynamoDBMapper;
	}

	@Override
	public Optional<JobDto> getJobData(UUID id) {
		final JobDto jobData = dynamoDBMapper.load(JobDto.class, id);
		LOGGER.info("Loaded jobData data {}", jobData);
		return jobData != null ? Optional.of(jobData) : Optional.empty();
	}

	@Override
	public void setJobData(JobDto jobData) {
		LOGGER.info("Saving job data {}", jobData);
		for (final DynamoDBMapper.FailedBatch failedBatch : dynamoDBMapper.batchSave(ImmutableList.of(jobData))) {
			LOGGER.error("Error saving job data {}", failedBatch.getException());
		}
	}
}
