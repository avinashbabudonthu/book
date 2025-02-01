package com.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Properties;

@SuppressWarnings("all")
@Slf4j
public class ConsumerTest {

    public static final String TOPIC_1 = "topic-1";

    @Test
    void consumeWithSubscription() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("group.id", "group-1");

        try(Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(List.of(TOPIC_1));

            while(true) {
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
}
