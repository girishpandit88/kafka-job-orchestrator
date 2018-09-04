package com.gp.job.common.dynamodb.access;

import com.gp.job.common.dynamodb.dao.JobDto;

import java.util.Optional;
import java.util.UUID;

public interface JobAccess {
	public Optional<JobDto> getJobData(UUID id);

	public void setJobData(JobDto jobData);
}
