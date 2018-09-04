package com.gp.job.consumer.manager;

import com.gp.job.common.dynamodb.access.JobAccess;
import com.gp.job.common.dynamodb.dao.JobDto;
import com.gp.job.common.kafka.dao.WorkUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class WorkUnitConsumer {
	private static final Logger log = LoggerFactory.getLogger(WorkUnitConsumer.class);
	public static final String MAIN_METHOD = "main";
	private final ExecutorService executorService;
	private final JobAccess jobAccess;

	@Autowired
	public WorkUnitConsumer(ExecutorService executorService, JobAccess jobAccess) {
		this.executorService = executorService;
		this.jobAccess = jobAccess;
	}

	@KafkaListener(topics = "${kafka.consumer.topic}")
	public void onReceiving(WorkUnit workUnit, @Header(KafkaHeaders.OFFSET) Integer offset,
							@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
							@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		log.info("Processing topic = {}, partition = {}, offset = {}, workUnit = {}",
				topic, partition, offset, workUnit);
		executorService.execute(() -> executeJob(workUnit));

	}

	private void executeJob(WorkUnit workUnit) {
		String mainClassName = workUnit.getDefinition();
		try {
			final Class<?> aClass = Thread
					.currentThread()
					.getContextClassLoader()
					.loadClass(workUnit.getDefinition());

			final Method main = aClass.getMethod(MAIN_METHOD, String[].class);
			String[] params = workUnit.getArgs();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream err = new ByteArrayOutputStream();
			int exitCode = -1;
			try {
				PrintStream outPr = new PrintStream(out);
				PrintStream errPr = new PrintStream(err);
				System.setOut(outPr);
				System.setErr(errPr);
				main.invoke(null, (Object) params);
				outPr.flush();
				errPr.flush();
				exitCode = 0;
			} catch (IllegalAccessException | InvocationTargetException e) {
				log.error("Error invoking the job {}", e.getCause());
				exitCode = 1;
			} finally {
				saveJobData(workUnit.getId(), mainClassName, out, err, exitCode);
			}
		} catch (Exception e) {
			log.error("Error while calling job: {} : error {}", mainClassName, e.getCause());
		}
	}

	private void saveJobData(String id, String mainClassName, ByteArrayOutputStream out, ByteArrayOutputStream err,
							 int exitCode) {
		final JobDto jobData = new JobDto.Builder()
				.id(UUID.fromString(id))
				.name(mainClassName)
				.output(out.toString())
				.error(err.toString())
				.exitCode(exitCode)
				.build();
		jobAccess.setJobData(jobData);
	}
}
