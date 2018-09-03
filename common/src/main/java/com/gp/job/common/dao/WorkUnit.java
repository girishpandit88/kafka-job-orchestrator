package com.gp.job.common.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Arrays;

public class WorkUnit {

	private String id;
	private String definition;
	private String[] args;


	public WorkUnit() {
		//for Spring-Web binding
	}

	@JsonCreator
	public WorkUnit(@JsonProperty("id") String id,
					@JsonProperty("definition") String definition,
					@JsonProperty("args") String[] args) {
		this.id = id;
		this.definition = definition;
		this.args = args;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String getId() {
		return id;
	}


	public String getDefinition() {
		return definition;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
						  .add("id", id)
						  .add("definition", definition)
						  .add("args", Arrays.toString(args))
						  .toString();
	}
}
