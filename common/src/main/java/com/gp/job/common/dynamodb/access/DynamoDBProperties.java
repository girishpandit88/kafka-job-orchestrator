package com.gp.job.common.dynamodb.access;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dynamodb")
public class DynamoDBProperties {
	private String endpoint;
	private String signingRegion;
	private String tableNamePrefix;

	public String getTableNamePrefix() {
		return tableNamePrefix;
	}

	public void setTableNamePrefix(String tableNamePrefix) {
		this.tableNamePrefix = tableNamePrefix;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getSigningRegion() {
		return signingRegion;
	}

	public void setSigningRegion(String signingRegion) {
		this.signingRegion = signingRegion;
	}
}
