package com.gp.job.consumer.manager;

import com.gp.job.common.dao.WorkUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

@Service
public class WorkUnitConsumer {
	private static final Logger log = LoggerFactory.getLogger(WorkUnitConsumer.class);
	public static final String MAIN_METHOD = "main";
	private final ExecutorService executorService;

	@Autowired
	public WorkUnitConsumer(ExecutorService executorService) {
		this.executorService = executorService;
	}

	@KafkaListener(topics = "${kafka.consumer.topic}")
	public void onReceiving(WorkUnit workUnit, @Header(KafkaHeaders.OFFSET) Integer offset,
							@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
							@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		log.info("Processing topic = {}, partition = {}, offset = {}, workUnit = {}",
				topic, partition, offset, workUnit);
		String mainClassName = workUnit.getDefinition();
		try {
			final Class<?> aClass = Thread
					.currentThread()
					.getContextClassLoader()
					.loadClass(mainClassName);
			final Method main = aClass.getMethod(MAIN_METHOD, String[].class);
			String[] params = workUnit.getArgs();
			executorService.execute(() -> {
				try {
					final Object invoke = main.invoke(null, (Object) params);
					log.info("Result of job operation : " + invoke);
				} catch (IllegalAccessException | InvocationTargetException e) {
					log.error("Error invoking the job {}", e.getCause());
				}
			});
		} catch (Exception e) {
			log.error("Error while calling job: {} : error {}", mainClassName, e.getCause());
		}
	}
}
