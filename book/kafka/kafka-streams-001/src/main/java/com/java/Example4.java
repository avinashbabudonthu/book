package com.java;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("all")
@Slf4j
public class Example4 {

    private static final Faker FAKER = Faker.instance();
    private static final String INPUT_TOPIC = "user.topology.input.txt";
    private static final String OUTPUT_TOPIC = "user.topology.output.txt";
    private static final List<String> TOPIC_NAMES = List.of(INPUT_TOPIC, OUTPUT_TOPIC);

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
        properties.put(StreamsConfig.STATE_DIR_CONFIG, "C:\\MyPC\\one-place\\kafka-streams\\data");

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

        // If 1 broker
        // List<NewTopic> topicList = TOPIC_NAMES.stream().map(topicName -> new NewTopic(topicName, 1, (short) 3)).toList();
        // If 3 brokers
        List<NewTopic> topicList = TOPIC_NAMES.stream().map(topicName -> new NewTopic(topicName, 3, (short) 3)).toList();
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

    @Test
    void deleteTopics() throws ExecutionException, InterruptedException {
        Properties properties = getAdminProperties();
        AdminClient adminClient = AdminClient.create(properties);

        // get all available topics
        ListTopicsOptions listTopicsOptions = new ListTopicsOptions().listInternal(true);
        ListTopicsResult listTopicsResult = adminClient.listTopics(listTopicsOptions);
        // ListTopicsResult listTopicsResult = adminClient.listTopics();

        // delete topics
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(TOPIC_NAMES);
        KafkaFuture<Void> all = deleteTopicsResult.all();
        all.get();
        adminClient.close();
        log.info("Deleted topics={}", TOPIC_NAMES);
    }

    @Test
    void deleteAllAvailableTopics() throws ExecutionException, InterruptedException {
        Properties properties = getAdminProperties();
        AdminClient adminClient = AdminClient.create(properties);

        // get all available topics
        ListTopicsOptions listTopicsOptions = new ListTopicsOptions().listInternal(true);
        ListTopicsResult listTopicsResult = adminClient.listTopics(listTopicsOptions);
        // ListTopicsResult listTopicsResult = adminClient.listTopics();

        KafkaFuture<Collection<TopicListing>> listings = listTopicsResult.listings();
        Collection<TopicListing> topicListings = listings.get();
        List<String> topicsList = topicListings.stream().map(TopicListing::name).toList();

        // delete topics
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(topicsList);
        KafkaFuture<Void> all = deleteTopicsResult.all();
        all.get();
        adminClient.close();
        log.info("Deleted topics={}", topicsList);
    }

    public static void main(String[] args) {
        new Example4().stream();
    }

    private void stream() {
        Topology topology = new Topology();
        String sourceName = "example-4-source";
        String sinkName = "example-4-sink";
        topology.addSource(sourceName, INPUT_TOPIC);
        topology.addProcessor("example-4-processor-1", Example4Processor::new, sourceName);
        topology.addProcessor("example-4-processor-2", Example4Processor2::new, sourceName);
        topology.addProcessor("example-4-processor-3", Example4Processor3::new, sourceName);
        topology.addSink(sinkName, OUTPUT_TOPIC, new StringSerializer(), new StringSerializer(), sourceName);

        Properties properties = getStreamsProperties();
        KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);
        kafkaStreams.start();

        // geaceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

    /**
     * Consume messages from "output-topic-001" topic
     */
    @Test
    void consumer() {
        Properties properties = getConsumerProperties();
        try (Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(List.of(OUTPUT_TOPIC));
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
     * Send message to "input-topic-001" with key every 5 seconds
     * Pass callback to producer send method. So callback will be executed after sending message
     */
    @Test
    void producer() throws ExecutionException, InterruptedException {
        Properties properties = getProducerProperties();
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
            producer.send(new ProducerRecord<>(INPUT_TOPIC, key, value), callback);
            Thread.sleep(1000 * 5); // wait 5 seconds to send next value
        }

        // close producer
        producer.close();
    }

}
