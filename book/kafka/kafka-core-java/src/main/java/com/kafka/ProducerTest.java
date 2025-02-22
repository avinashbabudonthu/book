package com.kafka;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SuppressWarnings("all")
@Slf4j
public class ProducerTest {

    private static final Faker FAKER = Faker.instance();

    private Properties getProperties() {
        Properties properties = new Properties();
        // bootstrap.servers
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");

        // key.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value.serializer
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        // properties.put(ProducerConfig.ACKS_CONFIG, "0");

        properties.put(ProducerConfig.RETRIES_CONFIG, "3");
        // The amount of time to wait before attempting to retry a failed request to a given topic partition.
        // This avoids repeatedly sending requests in a tight loop under some failure scenarios.
        // This value is the initial backoff value and will increase exponentially for each failed request,
        // up to the retry.backoff.max.ms value.
        // retry.backoff.ms
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 100);
        properties.put(ProducerConfig.RETRY_BACKOFF_MAX_MS_CONFIG, 10000);

        properties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 120000);

        // The producer groups together any records that arrive in between request transmissions into a single batched request.
        // Normally this occurs only under load when records arrive faster than they can be sent out.
        // However in some circumstances the client may want to reduce the number of requests even under moderate load
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
        // ensure we don't push duplicates
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        // properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "false");

        // batch.size
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "400");

        // partitioner.class
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class.getName());
        // properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class.getName()); // uses Sticky partition

        // properties.put(ProducerConfig.LINGER_MS_CONFIG, "20");
        // properties.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));

        // compression.type
        // The compression type for all data generated by the producer. The default is none (i.e. no compression).
        // Valid values are - none, gzip, snappy, lz4 , zstd
        // Compression is of full batches of data, so the efficacy of batching will also impact the compression ratio
        // (more batching means better compression)
        // properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        return properties;
    }

    /**
     * Create topic-1 with 1 partition - then all messages go to same partition
     * Create topic-1 with 3 partitions - then messages goes to each partition in round robin basis
     * Send message without key every 5 seconds
     */
    @Test
    void sendMessageWithoutKey() throws InterruptedException, ExecutionException {
        Properties properties = getProperties();

        // create producer
        Producer<String, String> producer = new KafkaProducer<>(properties);

        // send messages
        for (int i = 0; i <= 100; i++) {
            String message = FAKER.name().fullName();
            log.info("Sending message, i={}, message={}", i, message);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-1", message);
            Future<RecordMetadata> recordMetadataFuture = producer.send(producerRecord);
            RecordMetadata recordMetadata = recordMetadataFuture.get();
            log.info("Message sent, i={}, message={}, topic={}, partition={}, offset={}", i, message, recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());

            // wait 5 seconds
            Thread.sleep(1000 * 5);
        }

        // close producer
        producer.close();

        // same as above code. Producer is created in try-with-resource so no need to manually close
        /*try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i <= 100; i++) {
                String message = FAKER.name().fullName();
                log.info("Sending message, i={}, message={}", i, message);
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-1", message);
                Future<RecordMetadata> recordMetadataFuture = producer.send(producerRecord);
                RecordMetadata recordMetadata = recordMetadataFuture.get();
                log.info("Message sent, i={}, message={}, topic={}, partition={}, offset={}", i, message, recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());

                Thread.sleep(1000 * 5);
            }

        } catch (ExecutionException | InterruptedException e) {
            log.info("Exception in sending message", e);
        }*/
    }

    /**
     * Messages with same key always goes to same partition
     * Send message with key every 5 seconds
     */
    @Test
    void sendMessageWithKey() {
        Properties properties = getProperties();

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i <= 100; i++) {
                String message = FAKER.name().fullName();
                log.info("Sending message, i={}, message={}", i, message);
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-1", "key-1", message);
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
        Properties properties = getProperties();

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

    /**
     * Send message with key every 5 seconds
     * Pass callback to producer send method. So callback will be executed after sending message
     */
    @Test
    void producerCallback() throws ExecutionException, InterruptedException {
        Properties properties = getProperties();

        Producer<String, String> producer = new KafkaProducer<>(properties);

        // producer callback
        Callback callback = (RecordMetadata metadata, Exception exception) -> {
            if (null == exception) {
                log.info("Message sent, topic={}, partition={}, offset={}, timestamp={}", metadata.topic(), metadata.partition(),
                        metadata.offset(), new Date(metadata.timestamp()));
            } else {
                log.info("Exception while sending message", exception);
            }
        };

        // send messages
        for (int i = 0; i <= 100; i++) {
            String topic = "topic-1";
            String key = new SimpleDateFormat("SSS").format(new Date());
            String value = FAKER.name().fullName();
            log.info("Sending value, i={}, key={}, value={}", i, key, value);
            producer.send(new ProducerRecord<>(topic, key, value), callback);
            Thread.sleep(1000 * 5); // wait 5 seconds to send next value
        }

        // close producer
        producer.close();
    }

    /**
     * DefaultPartitioner internally uses Sticky partition
     */
    @Test
    void stickyPartition() throws InterruptedException {
        Properties properties = getProperties();
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class.getName());
        Producer<String, String> producer = new KafkaProducer<>(properties);

        Callback callback = (RecordMetadata recordMetadata, Exception e) -> {
            if (null == e) {
                log.info("message sent, topic={}, partition={}, offset={}",
                        recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
            } else {
                log.error("Exception in producer", e);
            }
        };
        for (int i = 0; i <= 100; i++) {
            String topic = "topic-1";
            String key = new SimpleDateFormat("SSS").format(new Date());
            String value = FAKER.name().fullName();
            producer.send(new ProducerRecord<>(topic, key, value), callback);
            Thread.sleep(500);
        }
        producer.close();
    }

    @Test
    void sendJsonMessage() throws InterruptedException {
        Properties properties = getProperties();
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        Callback callback = (RecordMetadata recordMetadata, Exception e) ->
                log.info("message sent, topic={}, partition={}, offset={}",
                        recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
        Producer<String, Employee> producer = new KafkaProducer<>(properties);
        String topic = "topic-2";
        for (int i = 0; i < 100; i++) {
            String key = new SimpleDateFormat("SSS").format(new Date());
            Employee value = Employee.builder()
                    .id(FAKER.number().randomDigitNotZero())
                    .name(FAKER.name().fullName())
                    .dept(FAKER.company().name())
                    .joiningDate(FAKER.date().between(new Date(80, 01, 01), new Date(125, 01, 01)))
                    .build();
            log.info("message sending, i={}, key={}, value={}", i, key, value);
            producer.send(new ProducerRecord<>(topic, key, value), callback);
            Thread.sleep(1000);
        }
        producer.close();
    }

}
