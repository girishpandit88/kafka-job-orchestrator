package com.gp.job.producer.model;

import java.util.UUID;

public class JobData {
	UUID id;
	String className;
	String output;
	String error;

	public JobData(UUID id, String className, String output, String error) {
		this.id = id;
		this.className = className;
		this.output = output;
		this.error = error;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
