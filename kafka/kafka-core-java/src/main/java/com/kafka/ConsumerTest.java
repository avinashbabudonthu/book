package com.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
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

        try (Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(List.of("topic-1"));

            while (true) {
                Thread.sleep(1000 * 10);

                ConsumerRecords<String, String> records = consumer.poll(Duration.of(20, ChronoUnit.SECONDS));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Topic={}, partition={}, offset={}, key={}, value={}", record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set concsumer with specific topic & specific partition
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
}
