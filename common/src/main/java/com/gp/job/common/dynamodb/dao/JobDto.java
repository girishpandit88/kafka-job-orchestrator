package com.gp.job.common.dynamodb.dao;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;

@DynamoDBTable(tableName = "Job")
public class JobDto {
	private UUID id;
	private String name;
	private String output;
	private String error;
	private int exitCode;

	public JobDto(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.output = builder.output;
		this.error = builder.error;
		this.exitCode = builder.exitCode;
	}

	public JobDto() {
	}

	public JobDto(UUID id, String name, String output, String error, int exitCode) {
		this.id = id;
		this.name = name;
		this.output = output;
		this.error = error;
		this.exitCode = exitCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@DynamoDBHashKey
	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public int getExitCode() {
		return exitCode;
	}

	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}

	@Override
	public String toString() {
		return "JobDto{" +
				"id=" + id +
				", name='" + name + '\'' +
				", output='" + output + '\'' +
				", error='" + error + '\'' +
				", exitCode=" + exitCode +
				'}';
	}

	public static class Builder {
		private UUID id;
		private String name;
		private String output;
		private String error;
		private int exitCode;

		public Builder() {
		}

		public Builder id(UUID uuid) {
			id = uuid;
			return this;
		}

		public Builder name(String n) {
			name = n;
			return this;
		}

		public Builder output(String o) {
			output = o;
			return this;
		}

		public Builder error(String o) {
			error = o;
			return this;
		}

		public Builder exitCode(int e) {
			exitCode = e;
			return this;
		}

		public JobDto build() {
			return new JobDto(this);
		}
	}


}
