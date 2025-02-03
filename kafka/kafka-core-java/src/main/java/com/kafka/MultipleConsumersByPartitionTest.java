package com.kafka;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Execution steps:
 * Create topic named topic-1 with partitions 3, in sync replicas 3, replication factor 1
 * Create 3 consumers with same group.id = group-1. Run test cases {@link this#consumer1()}, {@link this#consumer2()}, {@link this#consumer3()}
 * Run test case {@link this#sendMessageWithDifferentKeys()}
 *
 * Producer sends messages with key. Same keys messages goes to same partition
 * We have 3 partitions and 3 consumers so each consumer is consuming from 1 partition
 * Check logs that each consumer is consuming from different partitions
 *
 * Producer Output:
 * [MultipleConsumersByPartitionTest.sendMessageWithDifferentKeys] - Message sent, i=1, message=Amos Diggory, topic=topic-1, key=key1, partition=2, offset=40
 * [MultipleConsumersByPartitionTest.sendMessageWithDifferentKeys] - Message sent, i=2, message=Molly Weasley, topic=topic-1, key=key2, partition=2, offset=41
 * [MultipleConsumersByPartitionTest.sendMessageWithDifferentKeys] - Message sent, i=3, message=Madam Rosmerta, topic=topic-1, key=key3, partition=1, offset=40
 * [MultipleConsumersByPartitionTest.sendMessageWithDifferentKeys] - Message sent, i=4, message=Penelope Clearwater, topic=topic-1, key=key4, partition=0, offset=20
 * [MultipleConsumersByPartitionTest.sendMessageWithDifferentKeys] - Message sent, i=5, message=Dudley Dursley, topic=topic-1, key=key5, partition=1, offset=41
 * [MultipleConsumersByPartitionTest.sendMessageWithDifferentKeys] - Message sent, i=6, message=Anthony Goldstein, topic=topic-1, key=key1, partition=2, offset=42
 * [MultipleConsumersByPartitionTest.sendMessageWithDifferentKeys] - Message sent, i=7, message=Remus Lupin, topic=topic-1, key=key2, partition=2, offset=43
 * [MultipleConsumersByPartitionTest.sendMessageWithDifferentKeys] - Message sent, i=8, message=Charlie Weasley, topic=topic-1, key=key3, partition=1, offset=42
 * [MultipleConsumersByPartitionTest.sendMessageWithDifferentKeys] - Message sent, i=9, message=Rolanda Hooch, topic=topic-1, key=key4, partition=0, offset=21
 * [MultipleConsumersByPartitionTest.sendMessageWithDifferentKeys] - Message sent, i=10, message=Errol, topic=topic-1, key=key5, partition=1, offset=43
 *
 * Consumer 1 Output:
 * [MultipleConsumersByPartitionTest.consumer1] - Topic=topic-1, partition=2, offset=40, key=key1, value=Amos Diggory
 * [MultipleConsumersByPartitionTest.consumer1] - Topic=topic-1, partition=2, offset=41, key=key2, value=Molly Weasley
 * [MultipleConsumersByPartitionTest.consumer1] - Topic=topic-1, partition=2, offset=42, key=key1, value=Anthony Goldstein
 * [MultipleConsumersByPartitionTest.consumer1] - Topic=topic-1, partition=2, offset=43, key=key2, value=Remus Lupin
 *
 * Consumer 2 Output:
 * [MultipleConsumersByPartitionTest.consumer2] - Topic=topic-1, partition=0, offset=20, key=key4, value=Penelope Clearwater
 * [MultipleConsumersByPartitionTest.consumer2] - Topic=topic-1, partition=0, offset=21, key=key4, value=Rolanda Hooch
 *
 * Consumer 3 Output:
 * [MultipleConsumersByPartitionTest.consumer3] - Topic=topic-1, partition=1, offset=40, key=key3, value=Madam Rosmerta
 * [MultipleConsumersByPartitionTest.consumer3] - Topic=topic-1, partition=1, offset=41, key=key5, value=Dudley Dursley
 * [MultipleConsumersByPartitionTest.consumer3] - Topic=topic-1, partition=1, offset=42, key=key3, value=Charlie Weasley
 * [MultipleConsumersByPartitionTest.consumer3] - Topic=topic-1, partition=1, offset=43, key=key5, value=Errol
 *
 */
@Slf4j
public class MultipleConsumersByPartitionTest {

    private static final Faker FAKER = Faker.instance();

    @Test
    void consumer1() {
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

    @Test
    void consumer2() {
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

    @Test
    void consumer3() {
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
                Thread.sleep(1000 * 5);
            }
        } catch (ExecutionException | InterruptedException e) {
            log.info("Exception in sending message", e);
        }
    }
}
