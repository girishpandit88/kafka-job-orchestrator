package com.gp.job.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorProneJob {
	public static void main(String[] args) throws Exception {
		Logger LOGGER = LoggerFactory.getLogger(ErrorProneJob.class);
		try {
			throw new RuntimeException("I'm here to just cause ruckus!!");
		} catch (RuntimeException e) {
			System.err.println("Error while running job" + stackTraceToString(e));
			throw new Exception(e);
		}
	}

	private static String stackTraceToString(Throwable e) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append(element.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}
