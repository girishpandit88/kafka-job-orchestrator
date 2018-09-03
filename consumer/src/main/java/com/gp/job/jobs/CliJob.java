package com.gp.job.jobs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

public class CliJob implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		SpringApplication.run(CliJob.class);
	}

	public static void main(String[] args) {
		System.out.println("I'm a springboot CLIRunner");
	}
}
