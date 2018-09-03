package com.gp.job.jobs;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LongRunningJob {
	public static void main(String[] args) {
		System.out.println("I'm a long running job");
		Callable<String> callableTask = () -> {
			TimeUnit.MILLISECONDS.sleep(3000);
			return "Task's execution";
		};
		final ScheduledExecutorService executorService = Executors
				.newSingleThreadScheduledExecutor();
		Future<String> resultFuture =
				executorService.schedule(callableTask, 1, TimeUnit.SECONDS);
		try {
			while (!resultFuture.isDone()) {
				System.out.println("waiting for future to get");
				Thread.sleep(1000);
			}
			resultFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("Hello " + String.join(",", args) + "!");
	}
}
