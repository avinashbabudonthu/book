package com.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
public class AdminClientTest {

    private Properties getProperties() {
        Properties properties = new Properties();
//        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
         properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");

        return properties;
    }

    @Test
    void createTopic() throws ExecutionException, InterruptedException {
        Properties properties = getProperties();
        // If 1 broker
        /*List<NewTopic> topicList = List.of(
                new NewTopic("topic-1", 3, (short) 1),
                new NewTopic("topic-2", 3, (short) 1));*/

        // If 3 brokers
         List<NewTopic> topicList = List.of(
                new NewTopic("topic-1", 3, (short) 3),
                new NewTopic("topic-2", 3, (short) 3));

        AdminClient adminClient = AdminClient.create(properties);

        CreateTopicsResult topics = adminClient.createTopics(topicList);
        KafkaFuture<Void> all = topics.all();
        all.get();
        adminClient.close();

        log.info("Created topics={}", topicList);
    }

    @Test
    void listTopics() throws ExecutionException, InterruptedException {
        Properties properties = getProperties();
        AdminClient adminClient = AdminClient.create(properties);

        ListTopicsOptions listTopicsOptions = new ListTopicsOptions().listInternal(true);
        ListTopicsResult listTopicsResult = adminClient.listTopics(listTopicsOptions);
        // ListTopicsResult listTopicsResult = adminClient.listTopics();

        KafkaFuture<Collection<TopicListing>> listings = listTopicsResult.listings();
        Collection<TopicListing> topicListings = listings.get();
        for(TopicListing topicListing : topicListings) {
            log.info("topicId={}, name={}, isInternal={}", topicListing.topicId(), topicListing.name(), topicListing.isInternal());
        }

        adminClient.close();
    }

    @Test
    void deleteTopic() throws ExecutionException, InterruptedException {
        Properties properties = getProperties();
        AdminClient adminClient = AdminClient.create(properties);
        List<String> topicsList = List.of("topic-1", "topic-2");
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(topicsList);
        KafkaFuture<Void> all = deleteTopicsResult.all();
        all.get();
        adminClient.close();
        log.info("Deleted topics={}", topicsList);
    }

    @Test
    void resetOffsets() throws ExecutionException, InterruptedException {
        Properties properties = getProperties();

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

    @Test
    @DisplayName("Delete messages from topic where offset of each partition is hard coded")
    void deleteMessagesWithOffsetsHardCoded() throws ExecutionException, InterruptedException {
        Properties properties = getProperties();

        AdminClient adminClient = AdminClient.create(properties);
        String topicName = "topic-1";

        Map<TopicPartition, RecordsToDelete> recordsToDelete = Map.of(
                new TopicPartition(topicName, 0), RecordsToDelete.beforeOffset(335L),
                new TopicPartition(topicName, 1), RecordsToDelete.beforeOffset(306L),
                new TopicPartition(topicName, 2), RecordsToDelete.beforeOffset(295L)
        );
        DeleteRecordsResult deleteRecordsResult = adminClient.deleteRecords(recordsToDelete);
        deleteRecordsResult.all().get();
        adminClient.close();
    }

    @Test
    @DisplayName("Delete messages from topic where partition and respective offsets fetched dynamically")
    void deleteMessages_ByGettingOffsets_Dynamically() throws ExecutionException, InterruptedException {
        String topicName = "topic-2";

        // consumer properties
        Properties consumerProperties = getProperties();
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        consumerProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);
        consumerProperties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "consumerGracefulShutdown-" + UUID.randomUUID());
        consumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        Consumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);

        // admin properties
        Properties adminProperties = new Properties();
        // adminProperties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        adminProperties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");
        AdminClient adminClient = AdminClient.create(adminProperties);

        // get each partition and it's offset
        List<PartitionInfo> partitionInfoList = consumer.partitionsFor(topicName);
        List<TopicPartition> partitions = partitionInfoList.stream()
                .map(partitionInfo -> new TopicPartition(partitionInfo.topic(), partitionInfo.partition())).toList();
        Map<TopicPartition, Long> offsets = consumer.endOffsets(partitions);

        // delete messages from above found offsets
        Map<TopicPartition, RecordsToDelete> recordsToDelete = new HashMap<>();
        offsets.forEach(((topicPartition, offset) -> recordsToDelete.put(topicPartition, RecordsToDelete.beforeOffset(offset))));
        DeleteRecordsResult deleteRecordsResult = adminClient.deleteRecords(recordsToDelete);
        deleteRecordsResult.all().get();

        // close
        adminClient.close();
        consumer.close();
    }

}
