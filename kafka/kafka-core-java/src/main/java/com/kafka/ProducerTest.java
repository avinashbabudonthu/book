package com.kafka;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.IntStream;

@SuppressWarnings("all")
@Slf4j
public class ProducerTest {

    private static final Faker FAKER = Faker.instance();
    public static final String TOPIC_1 = "topic-1";

    public ProducerTest() {

    }

    @Test
    void sendMessageWithoutKey() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:29092");
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < 100; i++) {
                log.info("Sending message");

                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_1, FAKER.name().fullName());
                Future<RecordMetadata> recordMetadataFuture = producer.send(producerRecord);
                RecordMetadata recordMetadata = recordMetadataFuture.get();
                log.info("Message sent to topic={}, partition={}, offset={}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());

                Thread.sleep(1000 * 5);
            }

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void sendMessageWithKey() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        Function<Integer, Void> sender = (input) -> {
            try(Producer<String, String> producer = new KafkaProducer<>(properties)) {
                ProducerRecord<String, String> producerRecords = new ProducerRecord<>(TOPIC_1, UUID.randomUUID().toString(), FAKER.harryPotter().character());
                Future<RecordMetadata> recordMetadataFuture = producer.send(producerRecords);
                RecordMetadata recordMetadata = recordMetadataFuture.get();

                log.info("Message sent to topic={}, partition={}, offset={}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());

                Thread.sleep(1000 * 10);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            return null;
        };

        IntStream intStream = IntStream.rangeClosed(1, 100);
        intStream.forEach((i) -> sender.apply(i));
    }
}
