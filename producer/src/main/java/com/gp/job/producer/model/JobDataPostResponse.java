package com.gp.job.producer.model;

import java.util.UUID;

public class JobDataPostResponse {
	private UUID id;

	public JobDataPostResponse(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
