package com.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AlterConsumerGroupOffsetsResult;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class AdminClientTest {

    @Test
    void createTopic() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        AdminClient adminClient = AdminClient.create(properties);
        String topicName = "topic-2";
        NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
        CreateTopicsResult topics = adminClient.createTopics(List.of(newTopic));
        KafkaFuture<Void> all = topics.all();
        all.get();
        adminClient.close();
        log.info("Created topic={}", topicName);
    }

    @Test
    void deleteTopic() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        AdminClient adminClient = AdminClient.create(properties);
        String topicName = "input-topic-001";
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(List.of(topicName));
        KafkaFuture<Void> all = deleteTopicsResult.all();
        all.get();
        adminClient.close();
        log.info("Deleted topic={}", topicName);
    }

    @Test
    void resetOffsets() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        AdminClient adminClient = AdminClient.create(properties);
        String topicName = "topic-1";
        Map<TopicPartition, OffsetAndMetadata> offsets = Map.of(
                new TopicPartition(topicName, 0), new OffsetAndMetadata(0),
                new TopicPartition(topicName, 1), new OffsetAndMetadata(0),
                new TopicPartition(topicName, 2), new OffsetAndMetadata(0)
        );
        AlterConsumerGroupOffsetsResult alterConsumerGroupOffsetsResult = adminClient.alterConsumerGroupOffsets("group-1", offsets);
        KafkaFuture<Void> all = alterConsumerGroupOffsetsResult.all();
        all.get();
        adminClient.close();
    }
}
