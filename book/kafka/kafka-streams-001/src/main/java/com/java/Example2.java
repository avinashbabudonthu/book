package com.java;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("all")
@Slf4j
public class Example2 {

    private static final Faker FAKER = Faker.instance();

    private Properties getAdminProperties() {
        Properties properties = new Properties();
//        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");

        return properties;
    }

    private Properties getStreamsProperties() {
        Properties properties = new Properties();

        /* application.id
         * Specific to streams application. will be used for:
         * group.id = application.id
         * Default client.id prefix
         * Prefix to internal changelog topics
         * */
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "convert-case");

        // bootstrap.servers
        // properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");

        // default.key.serde
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // default.value.serde
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Directory location for state store.
        // This path must be unique for each streams instance sharing the same underlying filesystem.
        // Note that if not configured, then the default location will be different in each environment as
        // it is computed using System.getProperty("java.io.tmpdir")
        properties.put(StreamsConfig.STATE_DIR_CONFIG, "C:\\one-place\\kafka-streams\\data");

        // processing.guarantee - exactly once
        // properties.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);
        // properties.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);

        // auto.offset.reset
        // earliest: automatically reset the offset to the earliest offset
        // latest: automatically reset the offset to the latest offset
        // none: throw exception to the consumer if no previous offset is found for the consumer's group
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // disable all cache to demonstrate all steps involved in transformation. not recommended in PROD
        // properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0"); // deprecated
        properties.put(StreamsConfig.STATESTORE_CACHE_MAX_BYTES_CONFIG, "0");

        return properties;
    }

    private Properties getProducerProperties() {
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

    private Properties getConsumerProperties() {
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
    void createTopic() throws ExecutionException, InterruptedException {
        Properties properties = getAdminProperties();
        AdminClient adminClient = AdminClient.create(properties);

        List<String> topicNames = List.of("user.convert.case.input.txt", "user.convert.case.output.txt");
        // If 1 broker
        // List<NewTopic> topicList = topicNames.stream().map(topicName -> new NewTopic(topicName, 1, (short) 3)).toList();
        // If 3 brokers
        List<NewTopic> topicList = topicNames.stream().map(topicName -> new NewTopic(topicName, 3, (short) 3)).toList();
        CreateTopicsResult topics = adminClient.createTopics(topicList);
        KafkaFuture<Void> all = topics.all();
        all.get();
        adminClient.close();

        log.info("Created topics={}", topicList);
    }

    @Test
    void listTopics() throws ExecutionException, InterruptedException {
        Properties properties = getAdminProperties();
        AdminClient adminClient = AdminClient.create(properties);

        ListTopicsOptions listTopicsOptions = new ListTopicsOptions().listInternal(true);
        ListTopicsResult listTopicsResult = adminClient.listTopics(listTopicsOptions);
        // ListTopicsResult listTopicsResult = adminClient.listTopics();

        KafkaFuture<Collection<TopicListing>> listings = listTopicsResult.listings();
        Collection<TopicListing> topicListings = listings.get();
        for (TopicListing topicListing : topicListings) {
            log.info("topicId={}, name={}, isInternal={}", topicListing.topicId(), topicListing.name(), topicListing.isInternal());
        }

        adminClient.close();
    }

    /**
     * Send message to "input-topic-001" with key every 5 seconds
     * Pass callback to producer send method. So callback will be executed after sending message
     */
    @Test
    void producer() throws ExecutionException, InterruptedException {
        Properties properties = getProducerProperties();
        String topic = "user.convert.case.input.txt";

        Producer<String, String> producer = new KafkaProducer<>(properties);
        // producer callback
        Callback callback = (RecordMetadata recordMetadata, Exception e) ->
                log.info("message sent, topic={}, partition={}, offset={}",
                        recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("SSS");

        // send messages
        for (int i = 0; i <= 100; i++) {
            String key = simpleDateFormat.format(new Date());
            String value = FAKER.name().fullName();
            log.info("Sending value, i={}, key={}, value={}", i, key, value);
            producer.send(new ProducerRecord<>(topic, key, value), callback);
            Thread.sleep(1000 * 5); // wait 5 seconds to send next value
        }

        // close producer
        producer.close();
    }

    /**
     * Consume messages from "output-topic-001" topic
     */
    @Test
    void consumer() {
        Properties properties = getConsumerProperties();
        String topic = "user.convert.case.output.txt";

        try (Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(List.of(topic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.of(20, ChronoUnit.SECONDS));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Topic={}, partition={}, offset={}, key={}, value={}", record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                }
            }
        }
    }

    public static void main(String[] args) {
        new Example2().stream();
    }

    /**
     * do not call this message as Junit test case else it will run and stop immediately without waiting
     *
     * <ul>
     *     <li>Stream messages from input topic - user.convert.case.input.txt</li>
     *     <li>Convert case</li>
     *     <li>Send to output topic - user.convert.case.output.txt</li>
     * </ul>
     */
    private void stream() {
        Properties properties = getStreamsProperties();
        String inputTopic = "user.convert.case.input.txt";
        String outputTopic = "user.convert.case.output.txt";

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> inputStream = builder.stream(inputTopic);
        // @formatter:off
        inputStream
                .peek(((key, value) -> log.info("key={}, value={}", key, value)))
                .mapValues(value -> StringUtils.defaultIfBlank(value, "unknown").toLowerCase())
                .to(outputTopic);
        // @formatter:on

        // never call this in try-with-source. If called then application will stop immediately without waiting
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), properties);
        kafkaStreams.start();
    }

}