package com.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("all")
@Slf4j
public class ConsumerTest {

    @Test
    void consumeWithSubscription() {
        Properties properties = new Properties();
        // bootstrap. servers
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
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
        Properties properties = new Properties();
        // bootstrap. servers
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
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
     *
     * Polling and Processing <br />
     * The `poll` method is used to fetch records from the Kafka topic. <br />
     * Each record is processed inside the for-loop. <br /><br />
     *
     * Manual Offset Commit <br />
     * After processing each record, the offset is manually committed using `commitSync()`. <br /><br />
     *
     * This way, you can ensure that offsets are only committed after the messages are processed,
     * which provides better control over the consumer's behavior.
     */
    @Test
    void manualCommitConsumer() {
        Properties properties = new Properties();
        // bootstrap. servers
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
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
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        try(Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(List.of("topic-1"));
            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(20));
                for(ConsumerRecord<String, String> record : records) {
                    // record processing logic
                    log.info("thread={}, Topic={}, partition={}, offset={}, key={}, value={}", Thread.currentThread().getName(), record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());

                    // commit offset
                    Map<TopicPartition, OffsetAndMetadata> commitMap = Map.of(
                      new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset()+1)
                    );
                    consumer.commitSync(commitMap);
                }
            }
        }
    }

}