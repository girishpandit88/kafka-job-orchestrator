package com.gp.job.producer.model;

public class JobDataRequest {
	private String jobName;
	private String args;

	public JobDataRequest(String jobName, String args) {
		this.jobName = jobName;
		this.args = args;
	}

	public JobDataRequest() {
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
}
