package com.kafka;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.List;
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

    /**
     * Create topic-1 with 1 partition - then all messages go to same partition
     * Create topic-1 with 3 partitions - then messages goes to each partition in round robin basis
     * Send message without key every 5 seconds
     */
    @Test
    void sendMessageWithoutKey() {
        Properties properties = new Properties();
        // bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        // key.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value.serializer
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");
        // The producer groups together any records that arrive in between request transmissions into a single batched request.
        // Normally this occurs only under load when records arrive faster than they can be sent out.
        // However in some circumstances the client may want to reduce the number of requests even under moderate load
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
        // ensure we don't push duplicates
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i <= 100; i++) {
                String message = FAKER.name().fullName();
                log.info("Sending message, i={}, message={}", i, message);
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_1, message);
                Future<RecordMetadata> recordMetadataFuture = producer.send(producerRecord);
                RecordMetadata recordMetadata = recordMetadataFuture.get();
                log.info("Message sent, i={}, message={}, topic={}, partition={}, offset={}", i, message, recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());

                Thread.sleep(1000 * 5);
            }

        } catch (ExecutionException | InterruptedException e) {
            log.info("Exception in sending message", e);
        }
    }

    /**
     * Send message with key every 5 seconds
     */
    @Test
    void sendMessageWithKey() {
        Properties properties = new Properties();
        // bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        // key.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value.serializer
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");
        // The producer groups together any records that arrive in between request transmissions into a single batched request.
        // Normally this occurs only under load when records arrive faster than they can be sent out.
        // However in some circumstances the client may want to reduce the number of requests even under moderate load
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
        // ensure we don't push duplicates
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i <= 100; i++) {
                String message = FAKER.name().fullName();
                log.info("Sending message, i={}, message={}", i, message);
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_1, "key-1", message);
                Future<RecordMetadata> recordMetadataFuture = producer.send(producerRecord);
                RecordMetadata recordMetadata = recordMetadataFuture.get();
                log.info("Message sent, i={}, message={}, topic={}, partition={}, offset={}", i, message, recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());

                Thread.sleep(1000 * 5);
            }
        } catch (ExecutionException | InterruptedException e) {
            log.info("Exception in sending message", e);
        }
    }

    /**
     * Messages with same key always goes to same partition. Below is output:
     * <p>
     * [ProducerTest.sendMessageWithDifferentKeys] - Message sent, i=1, message=Albert Runcorn, topic=topic-1, key=key1, partition=2, offset=47
     * [ProducerTest.sendMessageWithDifferentKeys] - Message sent, i=2, message=Bathsheda Babbling, topic=topic-1, key=key2, partition=2, offset=48
     * [ProducerTest.sendMessageWithDifferentKeys] - Message sent, i=3, message=Penelope Clearwater, topic=topic-1, key=key3, partition=1, offset=15
     * [ProducerTest.sendMessageWithDifferentKeys] - Message sent, i=4, message=Poppy Pomfrey, topic=topic-1, key=key4, partition=0, offset=30
     * [ProducerTest.sendMessageWithDifferentKeys] - Message sent, i=5, message=Errol, topic=topic-1, key=key5, partition=1, offset=16
     * [ProducerTest.sendMessageWithDifferentKeys] - Message sent, i=6, message=Aurora Sinistra, topic=topic-1, key=key1, partition=2, offset=49
     * [ProducerTest.sendMessageWithDifferentKeys] - Message sent, i=7, message=James Potter, topic=topic-1, key=key2, partition=2, offset=50
     * [ProducerTest.sendMessageWithDifferentKeys] - Message sent, i=8, message=Graham Montague, topic=topic-1, key=key3, partition=1, offset=17
     * [ProducerTest.sendMessageWithDifferentKeys] - Message sent, i=9, message=Draco Malfoy, topic=topic-1, key=key4, partition=0, offset=31
     * [ProducerTest.sendMessageWithDifferentKeys] - Message sent, i=10, message=Arabella Figg, topic=topic-1, key=key5, partition=1, offset=18
     */
    @Test
    void sendMessageWithDifferentKeys() {
        Properties properties = new Properties();
        // bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        // key.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value.serializer
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");
        // The producer groups together any records that arrive in between request transmissions into a single batched request.
        // Normally this occurs only under load when records arrive faster than they can be sent out.
        // However in some circumstances the client may want to reduce the number of requests even under moderate load
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
        // ensure we don't push duplicates
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        List<String> keys = List.of("key1", "key2", "key3", "key4", "key5");
        int j = 0;

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 1; i <= 100; i++) {
                String key = keys.get(j);
                String message = FAKER.harryPotter().character();
                //log.info("Sending message, i={}, key={}, message={}", i, key, message);

                ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-1", key, message);
                Future<RecordMetadata> future = producer.send(producerRecord);
                RecordMetadata recordMetadata = future.get(); // wait for result. Not recommended in PROD
                log.info("Message sent, i={}, message={}, topic={}, key={}, partition={}, offset={}", i, message, recordMetadata.topic(), key, recordMetadata.partition(), recordMetadata.offset());
                j++;
                j = (j == 5) ? 0 : j; // reset j to 0 if j is 5
//                Thread.sleep(1000 * 5);
            }
        } catch (ExecutionException | InterruptedException e) {
            log.info("Exception in sending message", e);
        }
    }
}
