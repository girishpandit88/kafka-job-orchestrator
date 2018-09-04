package com.gp.kafka.tools.service;

import com.google.common.collect.Lists;
import com.gp.job.common.kafka.consumer.KafkaConsumerProperties;
import com.gp.kafka.tools.model.NodeDetails;
import com.gp.kafka.tools.model.PartitionDetails;
import com.gp.kafka.tools.model.TopicDetails;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaManager {
	private static final Logger logger = LoggerFactory.getLogger(KafkaManager.class);
	private final AdminClient adminClient;
	private final KafkaConsumer consumer;

	@Autowired
	public KafkaManager(KafkaConsumerProperties kafkaConsumerProperties) {
		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaConsumerProperties.getBootstrap());
		props.put("group.id", kafkaConsumerProperties.getGroup());
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer(props);
		this.adminClient = KafkaAdminClient.create(props);
	}

	public List<String> getTopics() {
		final List<String> result = Lists.newArrayList();
		final ListTopicsResult listTopicsResult = adminClient.listTopics();
		try {
			result.addAll(new ArrayList<>(listTopicsResult.names().get()));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Map<String, TopicDetails> getTopicDetails(final Collection<String> topics) {
		final Map<String, TopicDetails> results = new HashMap<>();
		try {
			final Map<String, TopicDescription> topicDescriptionMap = adminClient
					.describeTopics(topics)
					.all()
					.get();
			for (final Map.Entry<String, TopicDescription> entry : topicDescriptionMap.entrySet()) {
				final String topic = entry.getKey();
				final TopicDescription topicDescription = entry.getValue();

				final List<PartitionDetails> partitionDetails = new ArrayList<>();

				for (final TopicPartitionInfo partitionInfo : topicDescription.partitions()) {
					final List<NodeDetails> isrNodes = new ArrayList<>();
					final List<NodeDetails> replicaNodes = new ArrayList<>();

					// Translate Leader
					final Node partitionLeader = partitionInfo.leader();
					final NodeDetails leaderNode = new NodeDetails(
							partitionLeader.id(), partitionLeader.host(), partitionLeader.port(), partitionLeader
							.rack()
					);

					// Translate ISR nodes
					for (final Node node : partitionInfo.isr()) {
						isrNodes.add(new NodeDetails(node.id(), node.host(), node.port(), node.rack()));
					}

					// Translate Replicas nodes
					for (final Node node : partitionInfo.replicas()) {
						replicaNodes.add(new NodeDetails(node.id(), node.host(), node.port(), node.rack()));
					}

					// Create the details
					final PartitionDetails partitionDetail = new PartitionDetails(
							topicDescription.name(),
							partitionInfo.partition(),
							leaderNode,
							replicaNodes,
							isrNodes
					);

					// Add to the list.
					partitionDetails.add(partitionDetail);

					// Create new TopicDetails.
					final TopicDetails topicDetails = new TopicDetails(
							topicDescription.name(),
							topicDescription.isInternal(),
							partitionDetails
					);
					results.put(topic, topicDetails);
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return results;
	}

	public Map<String, TopicDetails> getTopicDetails(String topic) {
		return getTopicDetails(Collections.singleton(topic));
	}
}
