package com.kafka;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.Consumer;

@SuppressWarnings("all")
@Slf4j
public class ConsumerTest {

    private Properties getProperties() {
        Properties properties = new Properties();
        // bootstrap. servers
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
         properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");

        // key.deserializer
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // value.deserializer
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // group.id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");

        // auto.offset.reset
        // earliest: automatically reset the offset to the earliest offset
        // latest: automatically reset the offset to the latest offset
        // none: throw exception to the consumer if no previous offset is found for the consumer's group</li>
        // anything else: throw exception to the consumer
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // enable.auto.commit
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        // auto.commit.interval.ms
        // with auto commit set to true then offset auto commit will happen every 5 sec based on below property
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);

        // partition.assignment.strategy
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class.getName());

        // group.instance.id
        properties.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "consumerGracefulShutdown-" + UUID.randomUUID());

        // session.timeout.ms
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        return properties;
    }

    @Test
    void consumeWithSubscription() {
        Properties properties = getProperties();

        try (Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(List.of("topic-1"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.of(20, ChronoUnit.SECONDS));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Topic={}, partition={}, offset={}, key={}, value={}", record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                }
            }
        }
    }

    /**
     * Set concsumer to consume from specific topic & specific partition
     */
    @Test
    void consumeWithAssign() {
        Properties properties = getProperties();

        try (Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.assign(List.of(new TopicPartition("topic-1", 0)));

            while (true) {
                Thread.sleep(1000 * 10);

                ConsumerRecords<String, String> records = consumer.poll(Duration.of(20, ChronoUnit.SECONDS));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("thread={}, Topic={}, partition={}, offset={}, key={}, value={}", Thread.currentThread().getName(), record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Properties Configuration <br />
     * `enable.auto.commit` is set to `false` to disable automatic offset commits. <br /><br />
     * <p>
     * Polling and Processing <br />
     * The `poll` method is used to fetch records from the Kafka topic. <br />
     * Each record is processed inside the for-loop. <br /><br />
     * <p>
     * Manual Offset Commit <br />
     * After processing each record, the offset is manually committed using `commitSync()`. <br /><br />
     * <p>
     * This way, you can ensure that offsets are only committed after the messages are processed,
     * which provides better control over the consumer's behavior.
     */
    @Test
    void manualCommitConsumer() {
        Properties properties = getProperties();

        // enable.auto.commit
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        try (Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(List.of("topic-1"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(20));
                for (ConsumerRecord<String, String> record : records) {
                    // record processing logic
                    log.info("thread={}, Topic={}, partition={}, offset={}, key={}, value={}", Thread.currentThread().getName(), record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());

                    // commit offset
                    Map<TopicPartition, OffsetAndMetadata> commitMap = Map.of(
                            new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1)
                    );
                    consumer.commitSync(commitMap);
                    // consumer.commitAsync();
                }
            }
        }
    }

    @Test
    void consumerGracefulShutdown() {
        Properties properties = getProperties();
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("Consumer shut down initiated");
                consumer.wakeup();
                mainThread.join();
            }
        });

        try {
            consumer.subscribe(List.of("topic-1"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(20));
                for (ConsumerRecord<String, String> record : records) {
                    // record processing logic
                    log.info("thread={}, Topic={}, partition={}, offset={}, key={}, value={}",
                            Thread.currentThread().getName(), record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                }
            }
        } catch (WakeupException e) {
            log.info("Shutting down consumer");
        } catch (Exception e) {
            log.error("Exception in consumer", e);
        } finally {
            consumer.close();
            log.info("Consumer shut down successfully");
        }
    }

    @Test
    void getLastOffsetOfEachPartition() {
        Properties properties = getProperties();
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        String topicName1 =  "__consumer_offsets";
        String topicName2 = "topic-1";
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topicName1);
        List<TopicPartition> partitions = partitionInfos.stream()
                .map(partitionInfo -> new TopicPartition(partitionInfo.topic(), partitionInfo.partition())).toList();
        Map<TopicPartition, Long> offsets = consumer.endOffsets(partitions);
        offsets.forEach(((topicPartition, offset) -> {
            log.info("topic-partition={}, offset={}", topicPartition, offset);
        }));
        consumer.close();
    }

    @Test
    void consumeEmployeeObject() throws InterruptedException {
        Properties properties = getProperties();
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        Consumer<String, Employee> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of("topic-2"));
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            ConsumerRecords<String, Employee> records = consumer.poll(Duration.ofSeconds(20));
            for (ConsumerRecord<String, Employee> record : records) {
                // record processing logic
                log.info("thread={}, Topic={}, partition={}, offset={}, key={}, value={}",
                        Thread.currentThread().getName(), record.topic(), record.partition(),
                        record.offset(), record.key(), record.value());
            }
        }
    }

}